package com.horntvedt.case1.integrasjon;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public abstract class TestBase {

    @ClassRule
    public final static EnvironmentVariables environmentVariables = new EnvironmentVariables();

    @LocalServerPort
    protected String randomServerPort;

    protected static String getMessage(final String fileName) throws IOException {
        File responseMessage = new ClassPathResource(fileName).getFile();
        return FileUtils.readFileToString(responseMessage, "utf-8");
    }

}
