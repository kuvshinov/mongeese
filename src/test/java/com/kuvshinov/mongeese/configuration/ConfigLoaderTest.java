package com.kuvshinov.mongeese.configuration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

@RunWith(JUnit4.class)
public class ConfigLoaderTest {

    @Test(expected = FileNotFoundException.class)
    public void testFileNotFound() throws IOException {
        ConfigLoader.load("blablabla");
    }

    @Test
    public void testOk() throws IOException, URISyntaxException {
        URL resource = this.getClass().getResource("/config.example.yml");
        MongeeseConfiguration configuration = ConfigLoader.load(Paths.get(resource.toURI()).toString());
        Assert.assertNotNull(configuration);
        Assert.assertNotNull(configuration.getMongoUri());
        Assert.assertNotNull(configuration.getChanges());
        Assert.assertEquals(1, configuration.getChanges().size());

        ChangeSet entry = configuration.getChanges().get(0);
        Assert.assertEquals("my_first_change", entry.getId());
        Assert.assertEquals("Sergey Kuvshinov", entry.getAuthor());
        Assert.assertEquals("mongeese", entry.getDatabase());
        Assert.assertEquals("scripts/test.js", entry.getScriptFile());
    }
}
