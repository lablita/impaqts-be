package it.drwolf.impaqtsbe.utils;

import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.JsonNode;
import it.drwolf.impaqtsbe.dto.QueryRequest;
import play.libs.Json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class WrapperCaller {
	private final ActorRef out;
	private final String manateeRegistryPath;
	private final String manateeLibPath;
	private final String javaExecutable;
	private final String wrapperPath;

	public WrapperCaller(ActorRef out, String manateeRegistryPath, String manateeLibPath, String javaExecutable,
			String wrapperPath) {
		this.out = out;
		this.manateeRegistryPath = manateeRegistryPath;
		this.manateeLibPath = manateeLibPath;
		this.javaExecutable = javaExecutable;
		this.wrapperPath = wrapperPath;
	}

	public void executeQuery(QueryRequest queryRequest) throws IOException {
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.environment().put("MANATEE_REGISTRY", this.manateeRegistryPath);
		List<String> params = Arrays.asList(this.javaExecutable, "-jar", this.wrapperPath, "-l", this.manateeLibPath,
				"-c", queryRequest.getCorpus(), "-j", Json.stringify(Json.toJson(queryRequest)));
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
		List<String> params = Arrays.asList(this.javaExecutable, "-jar", this.wrapperPath, "-l", this.manateeLibPath,
				"-c", queryRequest.getCorpus(), "-j", Json.stringify(Json.toJson(queryRequest)));
		processBuilder.command(params);
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
