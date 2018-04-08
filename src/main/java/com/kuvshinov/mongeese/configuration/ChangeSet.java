package com.kuvshinov.mongeese.configuration;

import lombok.Data;

@Data
public class ChangeSet {
    private String id;
    private String author;
    private String database;
    private String scriptFile;
}
