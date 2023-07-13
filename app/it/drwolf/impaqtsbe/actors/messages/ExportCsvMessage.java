package it.drwolf.impaqtsbe.actors.messages;

import it.drwolf.impaqtsbe.dto.QueryRequest;
import it.drwolf.impaqtsbe.services.ExportCsvService;
import it.drwolf.impaqtsbe.utils.WrapperCaller;

public class ExportCsvMessage {

    private WrapperCaller wrapperCaller;

    private ExportCsvService exportCsvService;

    private QueryRequest queryRequest;
    private QueryRequest.RequestType queryType;
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ExportCsvService getExportCsvService() {
        return exportCsvService;
    }

    public void setExportCsvService(ExportCsvService exportCsvService) {
        this.exportCsvService = exportCsvService;
    }

    public WrapperCaller getWrapperCaller() {
        return wrapperCaller;
    }

    public void setWrapperCaller(WrapperCaller wrapperCaller) {
        this.wrapperCaller = wrapperCaller;
    }

    public QueryRequest getQueryRequest() {
        return queryRequest;
    }

    public void setQueryRequest(QueryRequest queryRequest) {
        this.queryRequest = queryRequest;
    }

    public QueryRequest.RequestType getQueryType() {
        return queryType;
    }

    public void setQueryType(QueryRequest.RequestType queryType) {
        this.queryType = queryType;
    }

}
