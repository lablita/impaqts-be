package it.drwolf.impaqtsbe.startup;

import com.typesafe.config.Config;
import play.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;

@Singleton
public class Startup {

    public static final String MANATEE_LIB_PATH = "manatee.lib.path";
    public static final String IMPAQTS_WRAPPER_JAR_PATH = "impaqts.wrapper.jar.path";
    private static final String MANATEE_REGISTRY_PATH = "manatee.registry.path";
    private static final String JAVA_EXECUTABLE_PATH = "java.executable.path";
    private static final String DOCKER_SWITCH = "docker.switch";
    private static final String DOCKER_MANATEE_REGISTRY = "docker.manatee.registry";
    private static final String DOCKER_MANATEE_PATH = "docker.manatee.path";
    private static final String CORPORA_FOLDER_PATH = "corpora.folder.path";
    private static final String CACHE_DIR = "cache.dir";
    private final Logger.ALogger logger = Logger.of(Startup.class);
    private String manateeRegistryPath;
    private String manateeLibPath;
    private String javaExecutable;
    private String wrapperPath;
    private String dockerSwitch;
    private String dockerManateeRegistry;
    private String dockerManateePath;
    private String cacheDir;
    private String corporaFolderPath;

    @Inject
    public Startup(Config configuration) {
        this.init(configuration);
    }

    public String getCorporaFolderPath() {
        return this.corporaFolderPath;
    }

    public String getDockerManateePath() {
        return this.dockerManateePath;
    }

    public String getDockerManateeRegistry() {
        return this.dockerManateeRegistry;
    }

    public String getDockerSwitch() {
        return this.dockerSwitch;
    }

    public String getJavaExecutable() {
        return this.javaExecutable;
    }

    public String getCacheDir() {
        return this.cacheDir;
    }

    public String getManateeLibPath() {
        return this.manateeLibPath;
    }

    public String getManateeRegistryPath() {
        return this.manateeRegistryPath;
    }

    public String getWrapperPath() {
        return this.wrapperPath;
    }

    private void init(Config configuration) {
        this.manateeRegistryPath = configuration.getString(Startup.MANATEE_REGISTRY_PATH);
        if (this.manateeRegistryPath == null || this.manateeRegistryPath.isEmpty()) {
            this.logger.error("Manatee registry environment variable not found. Stopping server.");
            System.exit(1);
        }
        this.manateeLibPath = configuration.getString(Startup.MANATEE_LIB_PATH);
        if (this.manateeLibPath == null || this.manateeLibPath.isEmpty()) {
            this.logger.error("Manatee lib path is required. Stopping server.");
            System.exit(1);
        }
        this.wrapperPath = configuration.getString(Startup.IMPAQTS_WRAPPER_JAR_PATH);
        if (this.wrapperPath == null || this.wrapperPath.isEmpty()) {
            this.logger.error("Impaqts wrapper jar path not found. Stopping server.");
            System.exit(1);
        }
        this.javaExecutable = configuration.getString(Startup.JAVA_EXECUTABLE_PATH);
        if (this.javaExecutable == null || this.javaExecutable.isEmpty()) {
            this.logger.error("Java executable path not found. Stopping server.");
            System.exit(1);
        }
        this.corporaFolderPath = configuration.getString((Startup.CORPORA_FOLDER_PATH));
        if (this.corporaFolderPath == null || this.corporaFolderPath.isEmpty()) {
            this.logger.error("Java executable path not found. Stopping server.");
            System.exit(1);
        }
        this.cacheDir = configuration.getString(Startup.CACHE_DIR);
        if (this.cacheDir == null || this.cacheDir.isEmpty()) {
            this.logger.error("Cache path not found. Stopping server.");
            System.exit(1);
        }
        this.dockerSwitch = configuration.getString(Startup.DOCKER_SWITCH);
        if (this.dockerSwitch == null || !this.dockerSwitch.equals("yes")) {
            File manateeRegistryDir = new File(this.manateeRegistryPath);
            if (!manateeRegistryDir.canRead() || !manateeRegistryDir.isDirectory() || !manateeRegistryDir.canWrite()) {
                this.logger.error("Cannot access Manatee registry. Stopping server.");
                System.exit(1);
            }
            File libManateeFile = new File(this.manateeLibPath);
            if (!libManateeFile.canRead()) {
                this.logger.error("Cannot read Manatee lib path. Stopping server.");
                System.exit(1);
            }
            File wrapper = new File(this.wrapperPath);
            if (!wrapper.canRead()) {
                this.logger.error("Cannot read Impaqts wrapper jar. Stopping server.");
                System.exit(1);
            }
            File je = new File(this.javaExecutable);
            if (!je.canExecute()) {
                this.logger.error("Cannot execute java. Stopping server.");
                System.exit(1);
            }
        } else {
            this.dockerManateeRegistry = configuration.getString(Startup.DOCKER_MANATEE_REGISTRY);
            if (this.dockerSwitch.equals("yes") && (this.dockerManateeRegistry == null
                    || this.dockerManateeRegistry.isEmpty())) {
                this.logger.error("Docker Manatee Registry not found. Stopping server.");
                System.exit(1);
            }
            this.dockerManateePath = configuration.getString(Startup.DOCKER_MANATEE_PATH);
            if (this.dockerSwitch.equals("yes") && (this.dockerManateePath == null
                    || this.dockerManateePath.isEmpty())) {
                this.logger.error("Docker Manatee Path not found. Stopping server.");
                System.exit(1);
            }
        }
    }
}
