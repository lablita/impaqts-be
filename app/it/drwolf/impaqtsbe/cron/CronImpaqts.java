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

package it.drwolf.impaqtsbe.cron;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import it.drwolf.impaqtsbe.startup.Startup;
import scala.concurrent.duration.Duration;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Singleton
public class CronImpaqts {

	private final DateTimeFormatter onlyDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

	@Inject
	public CronImpaqts(final ActorSystem system,
			@Named("delete-temp-path-content-actor") final ActorRef deleteTempPathContentActor, Config configuration) {
		String csvSchedulerHour = configuration.getString(Startup.CSV_SCHEDULER_HOUR);
		LocalDateTime now = LocalDateTime.now();
		String todayStartAsString = now.format(this.onlyDateFormatter).concat(" " + csvSchedulerHour);
		LocalDateTime todayStart = LocalDateTime.parse(todayStartAsString, this.dateTimeFormatter);
		Long delayMilliSec = this.checkToday(now, todayStart, 1);
		system.scheduler()
				.schedule(Duration.create(delayMilliSec, TimeUnit.MILLISECONDS), Duration.create(1, TimeUnit.DAYS),
						deleteTempPathContentActor, "", system.dispatcher(), null);

	}

	private long checkToday(LocalDateTime now, LocalDateTime todayStart, int daysAfterReschedule) {
		if (now.isBefore(todayStart)) {
			// calcolate millis diff
			return java.time.Duration.between(now, todayStart).toMillis();
		} else {
			LocalDateTime tomorrowStart = todayStart.plusDays(daysAfterReschedule);
			return java.time.Duration.between(now, tomorrowStart).toMillis();
		}
	}

}
