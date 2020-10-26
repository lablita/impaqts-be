package modules;

import com.google.inject.AbstractModule;
import it.drwolf.impaqtsbe.startup.Startup;

public class Module extends AbstractModule {

	@Override
	public void configure() {
		this.bind(Startup.class).asEagerSingleton();
	}
}
