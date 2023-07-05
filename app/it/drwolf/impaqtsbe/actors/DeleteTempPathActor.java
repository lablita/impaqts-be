package it.drwolf.impaqtsbe.actors;

import akka.actor.AbstractActor;
import com.typesafe.config.Config;
import it.drwolf.impaqtsbe.startup.Startup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;

public class DeleteTempPathActor extends AbstractActor {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final String tempPath;

	@Inject
	public DeleteTempPathActor(Config configuration) {
		this.tempPath = configuration.getString(Startup.CSV_TEMP_PATH);
	}

	public Receive createReceive() {
		return receiveBuilder().matchAny(message -> {
			this.deleteFolder(new File(this.tempPath));
			this.logger.info("Deleted temporary path: " + this.tempPath);
		}).build();
	}

	private void deleteFolder(File folder) {
		if (folder.isDirectory()) {
			File[] files = folder.listFiles();
			if (files != null) {
				for (File file : files) {
					deleteFolder(file);
				}
			}
		}
		folder.delete();
	}
}
