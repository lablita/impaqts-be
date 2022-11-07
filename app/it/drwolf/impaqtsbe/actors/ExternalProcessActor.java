package it.drwolf.impaqtsbe.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.fasterxml.jackson.databind.JsonNode;
import it.drwolf.impaqtsbe.dto.ErrorResponse;
import it.drwolf.impaqtsbe.dto.QueryRequest;
import it.drwolf.impaqtsbe.dto.QueryResponse;
import it.drwolf.impaqtsbe.security.JWKSSecured;
import it.drwolf.impaqtsbe.utils.WrapperCaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.libs.Json;
import play.mvc.Http;

import javax.inject.Inject;
import java.io.IOException;

public class ExternalProcessActor extends AbstractActor {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final WrapperCaller wrapperCaller;

	private final String idToken;
	private final ActorRef actorRef;
	private final JWKSSecured jwkSecured;

	@Inject
	public ExternalProcessActor(ActorRef actorRef, String manateeRegistryPath, String manateeLibPath,
			String javaExecutable, String wrapperPath, String dockerSwitch, String dockerManateeRegistry,
			String dockerManateePath, JWKSSecured jwksSecured, String idToken) {
		this.wrapperCaller = new WrapperCaller(actorRef, manateeRegistryPath, manateeLibPath, javaExecutable,
				wrapperPath, dockerSwitch, dockerManateeRegistry, dockerManateePath);
		this.jwkSecured = jwksSecured;
		this.idToken = idToken;
		this.actorRef = actorRef;
	}

	public static Props props(ActorRef out, String manateeRegistryPath, String manateeLibPath, String javaExecutable,
			String wrapperPath, String dockerSwitch, String dockerManateeRegistry, String dockerManateePath,
			JWKSSecured jwksSecured, String idToken) {
		return Props.create(ExternalProcessActor.class, out, manateeRegistryPath, manateeLibPath, javaExecutable,
				wrapperPath, dockerSwitch, dockerManateeRegistry, dockerManateePath, jwksSecured, idToken);
	}

	private boolean canUserExecuteQuery(String idToken, QueryRequest queryRequest) {
		if (idToken != null && !idToken.isEmpty()) {
			boolean isAdmin = this.jwkSecured.isAdmin(idToken);
			boolean isAdvancedUser = this.jwkSecured.isAdvancedUser(idToken);
			boolean isUser = this.jwkSecured.isUser(idToken);
			logger.debug(String.format("Admin: %b - Advanced user: %b - User: %b", isAdmin, isAdvancedUser, isUser));
		}
		return true;
	}

	@Override
	public AbstractActor.Receive createReceive() {
		return this.receiveBuilder().match(JsonNode.class, message -> this.manageQueryRequest(message)).build();
	}

	private void manageQueryRequest(JsonNode message) {
		QueryRequest queryRequest = null;
		try {
			queryRequest = Json.fromJson(message, QueryRequest.class);
		} catch (RuntimeException re) {
			re.printStackTrace();
			this.logger.error("Wrong request format");
			return;
		}
		try {
			boolean canUserExecuteQuery = this.canUserExecuteQuery(this.idToken, queryRequest);
			if (canUserExecuteQuery) {
				this.wrapperCaller.executeQuery(queryRequest);
			} else {
				QueryResponse qrError = new QueryResponse();
				ErrorResponse er = new ErrorResponse();
				er.setErrorCode(Http.Status.FORBIDDEN);
				er.setErrorMessage("Non hai il permesso di eseguire questo tipo di richiesta.");
				qrError.setErrorResponse(er);
				this.actorRef.tell(Json.toJson(qrError), null);
			}
		} catch (IOException e) {
			this.logger.error(String.format("Error executing query %s", Json.stringify(message)));
		}
	}

}
