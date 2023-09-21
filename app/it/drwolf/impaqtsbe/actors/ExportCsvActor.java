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
