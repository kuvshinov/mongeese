package com.kuvshinov.mongeese.configuration;

import lombok.Data;

import java.util.List;

@Data
public class MongeeseConfiguration {

    private String mongoUri;

    private List<ConfigEntry> changes;

    @Data
    public static class ConfigEntry {
        private String id;
        private String author;
        private String database;
        private String scriptFile;
    }
}
