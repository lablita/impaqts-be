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
