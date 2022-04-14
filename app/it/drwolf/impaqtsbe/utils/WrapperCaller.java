package it.drwolf.impaqtsbe.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;

import akka.actor.ActorRef;
import it.drwolf.impaqtsbe.dto.QueryRequest;
import play.Logger;
import play.libs.Json;

public class WrapperCaller {
	private final ActorRef out;
	private final String manateeRegistryPath;
	private final String manateeLibPath;
	private final String javaExecutable;
	private final String wrapperPath;
	private final String dockerSwitch;
	private final String dockerManateeRegistry;
	private final String dockerManateePath;

	public WrapperCaller(ActorRef out, String manateeRegistryPath, String manateeLibPath, String javaExecutable,
			String wrapperPath, String dockerSwitch, String dockerManateeRegistry, String dockerManateePath) {
		this.out = out;
		this.manateeRegistryPath = manateeRegistryPath;
		this.manateeLibPath = manateeLibPath;
		this.javaExecutable = javaExecutable;
		this.wrapperPath = wrapperPath;
		this.dockerSwitch = dockerSwitch;
		this.dockerManateeRegistry = dockerManateeRegistry;
		this.dockerManateePath = dockerManateePath;
	}

	public void executeQuery(QueryRequest queryRequest) throws IOException {
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.environment().put("MANATEE_REGISTRY", this.manateeRegistryPath);
		List<String> params;
		if (this.dockerSwitch.equals("yes")) {
			params = Arrays.asList("/usr/local/bin/docker", "run", "-e", this.dockerManateeRegistry, "-v",
					this.dockerManateePath, "--rm", "--name", "manatee", "manatee", "java", "-jar", this.wrapperPath,
					"-l", this.manateeLibPath, "-c", queryRequest.getCorpus(), "-j",
					Json.stringify(Json.toJson(queryRequest)));
		} else {
			Logger.debug("Query: " + Json.stringify(Json.toJson(queryRequest)));
			Logger.debug("CQL: " + Json.toJson(queryRequest.getQueryPattern().getCql()));
			params = Arrays.asList(this.javaExecutable, "-jar", this.wrapperPath, "-l", this.manateeLibPath, "-c",
					"susanne", "-j", Json.stringify(Json.toJson(queryRequest)));
		}
		System.out.println(params.stream().collect(Collectors.joining(" ")));
		processBuilder.command(params);
		Process process = processBuilder.start();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("###")) {
					// skip comments line
					continue;
				}
				this.out.tell(Json.parse(line), null);
			}
		}
	}

	public JsonNode retrieveMetadatumValues(QueryRequest queryRequest) throws IOException {
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.environment().put("MANATEE_REGISTRY", this.manateeRegistryPath);
		List<String> params;
		if (this.dockerSwitch.equals("yes")) {
			params = Arrays.asList("/usr/local/bin/docker", "run", "-e", this.dockerManateeRegistry, "-v",
					this.dockerManateePath, "--rm", "--name", "manatee", "manatee", "java", "-jar", this.wrapperPath,
					"-l", this.manateeLibPath, "-c", queryRequest.getCorpus(), "-j",
					Json.stringify(Json.toJson(queryRequest)));
		} else {
			params = Arrays.asList(this.javaExecutable, "-jar", this.wrapperPath, "-l", this.manateeLibPath, "-c",
					queryRequest.getCorpus(), "-j", Json.stringify(Json.toJson(queryRequest)));
		}
		processBuilder.command(params);
		System.out.println(params.stream().collect(Collectors.joining(" ")));
		Process process = processBuilder.start();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("###")) {
					// skip comments line
					continue;
				}
				return Json.parse(line);
			}
		}
		return Json.toJson(null);
	}
}
