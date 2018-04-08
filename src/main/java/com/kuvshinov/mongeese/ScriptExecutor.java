package com.kuvshinov.mongeese;

import com.kuvshinov.mongeese.configuration.ChangeSet;
import com.kuvshinov.mongeese.configuration.MongeeseConfiguration;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Service that works with MongoDB.
 */
public class ScriptExecutor {

    /**
     * Execute scripts on MongoDB.
     * If someone wasn't executed for any reason, process have to stop.
     */
    public void execute(MongeeseConfiguration configuration) throws IOException {
        MongoClientURI uri = new MongoClientURI(configuration.getMongoUri());
        try (MongoClient mongoClient = new MongoClient(uri)) {
            for (ChangeSet changeSet : configuration.getChanges()) {
                MongoDatabase database = mongoClient.getDatabase(changeSet.getDatabase());
                BasicDBObject dbObject = new BasicDBObject();
                dbObject.put("eval", loadScriptFromFile(changeSet.getScriptFile()));
                database.runCommand(dbObject);
            }
        }
    }

    private String loadScriptFromFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (Files.notExists(path)) throw new FileNotFoundException("Script " + filePath + " doesn't exist");
        return new String(Files.readAllBytes(path));
    }
}
