package modules;

import com.google.inject.AbstractModule;
import it.drwolf.impaqtsbe.startup.Startup;
import play.libs.akka.AkkaGuiceSupport;

public class Module extends AbstractModule implements AkkaGuiceSupport {

	@Override
	public void configure() {
		this.bind(Startup.class).asEagerSingleton();
	}
}
