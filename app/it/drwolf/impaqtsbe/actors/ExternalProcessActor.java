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
	private final String accessToken;
	private final ActorRef actorRef;
	private final JWKSSecured jwkSecured;

	@Inject
	public ExternalProcessActor(ActorRef actorRef, String manateeRegistryPath, String manateeLibPath,
			String javaExecutable, String wrapperPath, String dockerSwitch, String dockerManateeRegistry,
			String dockerManateePath, String cacheDir, JWKSSecured jwksSecured, String accessToken) {
		this.wrapperCaller = new WrapperCaller(actorRef, manateeRegistryPath, manateeLibPath, javaExecutable,
				wrapperPath, dockerSwitch, dockerManateeRegistry, dockerManateePath, cacheDir);
		this.jwkSecured = jwksSecured;
		this.accessToken = accessToken;
		this.actorRef = actorRef;
	}

	public static Props props(ActorRef out, String manateeRegistryPath, String manateeLibPath, String javaExecutable,
			String wrapperPath, String dockerSwitch, String dockerManateeRegistry, String dockerManateePath,
			String cacheDir, JWKSSecured jwksSecured, String accessToken) {
		return Props.create(ExternalProcessActor.class, out, manateeRegistryPath, manateeLibPath, javaExecutable,
				wrapperPath, dockerSwitch, dockerManateeRegistry, dockerManateePath, cacheDir, jwksSecured,
				accessToken);
	}

	private boolean canUserExecuteQuery(String accessToken, QueryRequest queryRequest) {
		if (accessToken != null && !accessToken.isEmpty()) {
			boolean isAdmin = this.jwkSecured.isAdmin(accessToken);
			boolean isAdvancedUser = this.jwkSecured.isAdvancedUser(accessToken);
			boolean isUser = this.jwkSecured.isUser(accessToken);
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
			boolean canUserExecuteQuery = this.canUserExecuteQuery(this.accessToken, queryRequest);
			if (canUserExecuteQuery) {
				this.wrapperCaller.executeQuery(queryRequest);
			} else {
				QueryResponse qrError = new QueryResponse(queryRequest.getId());
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
