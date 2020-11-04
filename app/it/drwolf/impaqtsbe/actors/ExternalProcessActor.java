package it.drwolf.impaqtsbe.actors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import it.drwolf.impaqtsbe.dto.QueryRequest;
import play.libs.Json;

public class ExternalProcessActor extends AbstractActor {
	private static final Logger logger = LoggerFactory.getLogger(ExternalProcessActor.class);

	public static Props props(ActorRef out, String manateeRegistryPath, String manateeLibPath, String javaExecutable,
			String wrapperPath) {
		return Props.create(ExternalProcessActor.class, out, manateeRegistryPath, manateeLibPath, javaExecutable,
				wrapperPath);
	}

	private final ActorRef out;
	private final String manateeRegistryPath;
	private final String manateeLibPath;
	private final String javaExecutable;
	private final String wrapperPath;
	private final String dockerJavaPath;
	private final String dockerSwitch;

	private final String dockerExecutableStatement;

	@Inject
	public ExternalProcessActor(ActorRef out, String manateeRegistryPath, String manateeLibPath, String javaExecutable,
			String wrapperPath, String dockerJavaPath, String dockerSwitch, String dockerExecutableStatement) {
		this.out = out;
		this.manateeRegistryPath = manateeRegistryPath;
		this.manateeLibPath = manateeLibPath;
		this.javaExecutable = javaExecutable;
		this.wrapperPath = wrapperPath;
		this.dockerJavaPath = dockerJavaPath;
		this.dockerSwitch = dockerSwitch;
		this.dockerExecutableStatement = dockerExecutableStatement;
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
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.environment().put("MANATEE_REGISTRY", this.manateeRegistryPath);
		List<String> params;
		if (this.dockerSwitch.equals("yes")) {
			params = Arrays.asList(this.dockerJavaPath, "-jar", this.wrapperPath, "-l", this.manateeLibPath, "-c",
					"susanne", "-j", Json.stringify(Json.toJson(queryRequest)));
		} else {
			params = Arrays.asList(this.javaExecutable, "-jar", this.wrapperPath, "-l", this.manateeLibPath, "-c",
					"susanne", "-j", Json.stringify(Json.toJson(queryRequest)));
		}
		processBuilder.command(params);
		Process process = processBuilder.start();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("###")) {
					// skip comments line
					continue;
				}
				this.out.tell(Json.parse(line), this.self());
			}
		}
	}

}
