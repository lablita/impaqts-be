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
