package it.drwolf.impaqtsbe.startup;

import com.typesafe.config.Config;
import play.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;

@Singleton
public class Startup {

	public static final String MANATEE_LIB_PATH = "manatee.lib.path";
	private static final String MANATEE_REGISTRY_PATH = "manatee.registry.path";
	private final Logger.ALogger logger = Logger.of(Startup.class);

	@Inject
	public Startup(Config configuration) {
		this.init(configuration);
	}

	private void init(Config configuration) {
		String manateeRegistryPath = configuration.getString(Startup.MANATEE_REGISTRY_PATH);
		if (manateeRegistryPath == null || manateeRegistryPath.isEmpty()) {
			this.logger.error("Manatee registry environment variable not found. Stopping server.");
			System.exit(1);
		}
		String manateeLibPath = configuration.getString(Startup.MANATEE_LIB_PATH);
		if (manateeLibPath == null || manateeLibPath.isEmpty()) {
			this.logger.error("Manatee lib path is required. Stopping server.");
			System.exit(1);
		}
		File manateeRegistryDir = new File(manateeRegistryPath);
		if (!manateeRegistryDir.canRead() || !manateeRegistryDir.isDirectory() || !manateeRegistryDir.canWrite()) {
			this.logger.error("Cannot access Manatee registry. Stopping server.");
			System.exit(1);
		}
		File libManateeFile = new File(manateeLibPath);
		if (libManateeFile.canRead()) {
			try {
				System.load(manateeLibPath);
			} catch (UnsatisfiedLinkError e) {
				String message = e.getMessage();
				if (message.startsWith("Native Library") && message.endsWith("already loaded in another classloader")) {
					// ok, manatee already loaded
				} else {
					this.logger.error("Error loading Manatee library. Stopping server.");
					System.exit(1);
				}
			}
		} else {
			this.logger.error("Cannot read Manatee lib path. Stopping server.");
			System.exit(1);
		}
	}
}
