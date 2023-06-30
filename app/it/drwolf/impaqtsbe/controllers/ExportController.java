package it.drwolf.impaqtsbe.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import it.drwolf.impaqtsbe.dto.QueryRequest;
import it.drwolf.impaqtsbe.services.ExportCsvService;
import it.drwolf.impaqtsbe.startup.Startup;
import it.drwolf.impaqtsbe.utils.WrapperCaller;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Inject;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ExportController {

    private final Startup startup;

    private final ExportCsvService exportCsvService;

    @Inject
    public ExportController(Startup startup, ExportCsvService exportCsvService) {
        this.startup = startup;
        this.exportCsvService = exportCsvService;
    }


    public Result downloadFileByUuid(String filename, String uuid) {
        final String csvFilename = uuid + CorpusController.CSV_EXT;
        Path path = Paths.get(CorpusController.TEMP_CSV_PATH);
        final String filePathStr = path.toFile().getPath() + "/" + csvFilename;

        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        final String fn = filename + "_" + sdf.format(new Date()) + ".csv";
        return Results.ok(new File(filePathStr))
                .withHeader(Http.HeaderNames.CONTENT_TYPE, "application/octet-stream")
                .withHeader(Http.HeaderNames.CONTENT_DISPOSITION, "attachment; filename=" + fn)
                .withHeader("Download-Filename", fn);
    }

    public Result exportCsv(Http.Request request) {
        JsonNode bodyAsJson = request.body().asJson();
        QueryRequest qr = Json.fromJson(bodyAsJson, QueryRequest.class);
        UUID uuid = UUID.randomUUID();
        final String filename = uuid + CorpusController.CSV_EXT;

        try {
            Path path = Paths.get(CorpusController.TEMP_CSV_PATH);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            final String filePathStr = path.toFile().getPath() + "/" + filename;

            WrapperCaller wrapperCaller = new WrapperCaller(null, this.startup.getManateeRegistryPath(),
                    this.startup.getManateeLibPath(), this.startup.getJavaExecutable(), this.startup.getWrapperPath(),
                    this.startup.getDockerSwitch(), this.startup.getDockerManateeRegistry(),
                    this.startup.getDockerManateePath(), this.startup.getCacheDir());

            QueryRequest.RequestType queryType = QueryRequest.RequestType.valueOf(qr.getQueryType());
            wrapperCaller.executeQueryAndWriteCSV(qr, exportCsvService, queryType, filePathStr);
        } catch (Exception e) {
            return Results.internalServerError("An error occurred while creating the csv file");
        }

        return Results.ok(Json.toJson(uuid));
    }
}
