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
	private boolean complete = false;

	public ExportCsvService getExportCsvService() {
		return exportCsvService;
	}

	public QueryRequest getQueryRequest() {
		return queryRequest;
	}

	public QueryRequest.RequestType getQueryType() {
		return queryType;
	}

	public String getUuid() {
		return uuid;
	}

	public WrapperCaller getWrapperCaller() {
		return wrapperCaller;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public void setExportCsvService(ExportCsvService exportCsvService) {
		this.exportCsvService = exportCsvService;
	}

	public void setQueryRequest(QueryRequest queryRequest) {
		this.queryRequest = queryRequest;
	}

	public void setQueryType(QueryRequest.RequestType queryType) {
		this.queryType = queryType;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setWrapperCaller(WrapperCaller wrapperCaller) {
		this.wrapperCaller = wrapperCaller;
	}

}
