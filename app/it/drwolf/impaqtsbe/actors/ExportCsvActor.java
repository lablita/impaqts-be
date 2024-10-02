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

package it.drwolf.impaqtsbe.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import it.drwolf.impaqtsbe.actors.messages.ExportCsvMessage;
import it.drwolf.impaqtsbe.utils.WrapperCaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExportCsvActor extends AbstractActor {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static Props getProps() {
		return Props.create(ExportCsvActor.class);
	}

	@Override
	public Receive createReceive() {
		return this.receiveBuilder().match(ExportCsvMessage.class, message -> {
			this.processCsvFile(message);
		}).build();
	}

	private void processCsvFile(ExportCsvMessage exportCsvMessage) throws Exception {
		WrapperCaller wrapperCaller = exportCsvMessage.getWrapperCaller();
		wrapperCaller.executeQueryAndWriteCSV(exportCsvMessage.getQueryRequest(),
				exportCsvMessage.getExportCsvService(), exportCsvMessage.getQueryType(), exportCsvMessage.getUuid(),
				exportCsvMessage.isComplete());
	}

}
