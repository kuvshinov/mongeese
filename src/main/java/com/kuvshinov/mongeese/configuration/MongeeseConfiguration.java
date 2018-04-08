package com.kuvshinov.mongeese.configuration;

import lombok.Data;

import java.util.List;

@Data
public class MongeeseConfiguration {
    private String mongoUri;
    private List<ChangeSet> changes;
}
