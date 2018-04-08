package com.kuvshinov.mongeese;

import com.kuvshinov.mongeese.configuration.ChangeSet;
import com.kuvshinov.mongeese.configuration.ConfigLoader;
import com.kuvshinov.mongeese.configuration.MongeeseConfiguration;
import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import lombok.extern.log4j.Log4j2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Service that works with MongoDB.
 */
@Log4j2
public class ScriptExecutor {

    /**
     * Execute scripts on MongoDB.
     * If someone wasn't executed for any reason, process have to stop.
     */
    public void execute(MongeeseConfiguration configuration) throws IOException {
        MongoClientURI uri = new MongoClientURI(configuration.getMongoUri());
        try (MongoClient mongoClient = new MongoClient(uri)) {
            for (ChangeSet changeSet : configuration.getChanges()) {
                log.debug("Execute changes with id {} ...", changeSet.getId());
                MongoDatabase database = mongoClient.getDatabase(changeSet.getDatabase());
                BasicDBObject dbObject = new BasicDBObject();
                dbObject.put("eval", loadScriptFromFile(changeSet.getScriptFile()));
                database.runCommand(dbObject);
            }
        } catch (MongoTimeoutException e) {
            log.error("Can not connect to {}", configuration.getMongoUri());
        }
    }

    private String loadScriptFromFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (Files.notExists(path)) throw new FileNotFoundException("Script " + filePath + " doesn't exist");
        return new String(Files.readAllBytes(path));
    }

    public static void main(String[] args) throws IOException {
        String filePath = System.getProperty("configFile");
        log.info("Start executing scripts from {}", filePath);
        ScriptExecutor executor = new ScriptExecutor();
        executor.execute(ConfigLoader.load(filePath));
        log.info("Success");
    }
}
