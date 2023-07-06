package modules;

import com.google.inject.AbstractModule;
import it.drwolf.impaqtsbe.actors.DeleteTempPathActor;
import it.drwolf.impaqtsbe.cron.CronImpaqts;
import play.libs.akka.AkkaGuiceSupport;

public class SchedulerModule extends AbstractModule implements AkkaGuiceSupport {

	@Override
	protected void configure() {
		this.bindActor(DeleteTempPathActor.class, "delete-temp-path-content-actor");
		this.bind(CronImpaqts.class).asEagerSingleton();
	}
}
