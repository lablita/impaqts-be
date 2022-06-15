package it.drwolf.impaqtsbe.actors;

import java.io.IOException;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import it.drwolf.impaqtsbe.dto.QueryRequest;
import it.drwolf.impaqtsbe.utils.WrapperCaller;
import play.libs.Json;

public class ExternalProcessActor extends AbstractActor {
	private static final Logger logger = LoggerFactory.getLogger(ExternalProcessActor.class);
	private final WrapperCaller wrapperCaller;

	@Inject public ExternalProcessActor(ActorRef out, String manateeRegistryPath, String manateeLibPath,
			String javaExecutable, String wrapperPath, String dockerSwitch, String dockerManateeRegistry,
			String dockerManateePath) {
		this.wrapperCaller = new WrapperCaller(out, manateeRegistryPath, manateeLibPath, javaExecutable, wrapperPath,
				dockerSwitch, dockerManateeRegistry, dockerManateePath);
	}

	public static Props props(ActorRef out, String manateeRegistryPath, String manateeLibPath, String javaExecutable,
			String wrapperPath, String dockerSwitch, String dockerManateeRegistry, String dockerManateePath) {
		return Props.create(ExternalProcessActor.class, out, manateeRegistryPath, manateeLibPath, javaExecutable,
				wrapperPath, dockerSwitch, dockerManateeRegistry, dockerManateePath);
	}

	@Override public AbstractActor.Receive createReceive() {
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
