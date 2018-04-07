package com.kuvshinov.mongeese.configuration;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Utility class.
 * Can not be extended.
 */
public final class ConfigLoader {

    /**
     * Instance can not be created.
     */
    private ConfigLoader() { }

    /**
     * Load configuration from external YAML file.
     *
     * @param filePath - absolute or relative path to configuration file.
     * @return instance of {@link MongeeseConfiguration}
     */
    public static MongeeseConfiguration load(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (Files.notExists(path)) throw new FileNotFoundException("File " + filePath + " doesn't exist!");
        Yaml yaml = new Yaml(new Constructor(MongeeseConfiguration.class));
        return (MongeeseConfiguration) yaml.load(Files.newInputStream(path));
    }
}
