package it.drwolf.impaqtsbe.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.fasterxml.jackson.databind.JsonNode;
import it.drwolf.impaqtsbe.dto.QueryRequest;
import it.drwolf.impaqtsbe.utils.WrapperCaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.libs.Json;

import javax.inject.Inject;
import java.io.IOException;

public class ExternalProcessActor extends AbstractActor {
	private static final Logger logger = LoggerFactory.getLogger(ExternalProcessActor.class);

	private final WrapperCaller wrapperCaller;

	@Inject
	public ExternalProcessActor(ActorRef out, String manateeRegistryPath, String manateeLibPath, String javaExecutable,
			String wrapperPath) {
		this.wrapperCaller = new WrapperCaller(out, manateeRegistryPath, manateeLibPath, javaExecutable, wrapperPath);
	}

	public static Props props(ActorRef out, String manateeRegistryPath, String manateeLibPath, String javaExecutable,
			String wrapperPath) {
		return Props.create(ExternalProcessActor.class, out, manateeRegistryPath, manateeLibPath, javaExecutable,
				wrapperPath);
	}

	@Override
	public AbstractActor.Receive createReceive() {
		return this.receiveBuilder().match(JsonNode.class, message -> this.manageQueryRequest(message)).build();
	}

	private void manageQueryRequest(JsonNode message) throws InterruptedException, IOException {
		QueryRequest queryRequest = null;
		try {
			queryRequest = Json.fromJson(message, QueryRequest.class);
		} catch (RuntimeException re) {
			//
			ExternalProcessActor.logger.error("Wrong request format");
			return;
		}
		this.wrapperCaller.executeQuery(queryRequest);
	}

}
