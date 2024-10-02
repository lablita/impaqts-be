/*
 * Copyright (C) 2024
 * EMMACorpus
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.drwolf.impaqtsbe.controllers;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.fasterxml.jackson.databind.JsonNode;
import com.typesafe.config.Config;
import it.drwolf.impaqtsbe.actors.ExportCsvActor;
import it.drwolf.impaqtsbe.actors.messages.ExportCsvMessage;
import it.drwolf.impaqtsbe.dto.ProgressStatusDTO;
import it.drwolf.impaqtsbe.dto.QueryRequest;
import it.drwolf.impaqtsbe.services.ExportCsvService;
import it.drwolf.impaqtsbe.startup.Startup;
import it.drwolf.impaqtsbe.utils.WrapperCaller;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ExportController {

	private final Startup startup;
	private final ExportCsvService exportCsvService;
	private final ActorRef exportCsvActor;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final Config configuration;

	@Inject
	public ExportController(Startup startup, ExportCsvService exportCsvService, ActorSystem actorSystem,
			Config configuration) {
		this.startup = startup;
		this.exportCsvService = exportCsvService;
		this.exportCsvActor = actorSystem.actorOf(ExportCsvActor.getProps());
		this.configuration = configuration;
	}

	public Result downloadFileByUuid(String filename, String uuid) {
		final String csvFilename = uuid + this.configuration.getString(Startup.CSV_EXT);
		Path path = Paths.get(this.configuration.getString(Startup.CSV_TEMP_PATH) + "/" + uuid);
		final String filePathStr = path.toFile().getPath() + "/" + csvFilename;
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		final String fn = filename + "_" + sdf.format(new Date()) + ".csv";
		return Results.ok(new File(filePathStr))
				.as("text/csv")
				.withHeader(Http.HeaderNames.CONTENT_DISPOSITION, "attachment; filename=" + fn)
				.withHeader("Download-Filename", fn);
	}

	public Result exportCsv(Http.Request request) {
		JsonNode bodyAsJson = request.body().asJson();
		QueryRequest qr = Json.fromJson(bodyAsJson, QueryRequest.class);
		UUID uuid = UUID.randomUUID();
		try {
			Path path = Paths.get(this.configuration.getString(Startup.CSV_TEMP_PATH));
			if (!Files.exists(path)) {
				Files.createDirectories(path);
			}
			WrapperCaller wrapperCaller = new WrapperCaller(null, this.startup.getManateeRegistryPath(),
					this.startup.getManateeLibPath(), this.startup.getJavaExecutable(), this.startup.getWrapperPath(),
					this.startup.getDockerSwitch(), this.startup.getDockerManateeRegistry(),
					this.startup.getDockerManateePath(), this.startup.getCacheDir(), this.startup.getCsvExt(),
					this.startup.getCsvTempPath(), this.startup.getProgressFileCsv(),
					this.startup.getCorporaFolderPath());

			QueryRequest.RequestType queryType = QueryRequest.RequestType.valueOf(qr.getQueryType());
			ExportCsvMessage exportCsvMessage = new ExportCsvMessage();
			exportCsvMessage.setWrapperCaller(wrapperCaller);
			exportCsvMessage.setExportCsvService(this.exportCsvService);
			exportCsvMessage.setQueryRequest(qr);
			exportCsvMessage.setQueryType(queryType);
			exportCsvMessage.setUuid(uuid.toString());

			this.exportCsvActor.tell(exportCsvMessage, null);
			return Results.ok(Json.toJson(uuid));
		} catch (Exception e) {
			return Results.internalServerError("An error occurred while creating the csv file");
		}
	}

	public Result getProgressCsvByUUID(String uuid) {
		final String csvProgressFilename = this.configuration.getString(Startup.CSV_PROGRESS_FILE);
		Path path = Paths.get(this.configuration.getString(Startup.CSV_TEMP_PATH) + "/" + uuid);
		final String csvProgressFileStr = path.toFile().getPath() + "/" + csvProgressFilename;
		File csvProgressFile = new File(csvProgressFileStr);
		ProgressStatusDTO progressStatusDTO = new ProgressStatusDTO();
		try {
			String data = FileUtils.readFileToString(csvProgressFile, StandardCharsets.UTF_8);
			progressStatusDTO.setStatus(data);
			return Results.ok(Json.toJson(progressStatusDTO));
		} catch (IOException e) {
			//Errore dovuto al tentativo di lettura dettato dal polling del FE, mentre viene scritto il file progress.txt
			//resituisco KK
			progressStatusDTO.setStatus("KK");
			this.logger.warn("Error while opening progress file");
			return Results.ok(Json.toJson(progressStatusDTO));
		}

	}
}
