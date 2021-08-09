package com.smoothiemx.junitapp.app.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

import java.util.Map;
import java.util.Properties;

public class ConditionalTest {

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void testSoloWindows() {
    }

    @Test
    @EnabledOnOs({OS.LINUX, OS.MAC})
    void testSoloMacYLinux() {
    }

    @Test
    @DisabledOnOs(OS.WINDOWS)
    void testNoWindows() {
    }

    @Test
    @EnabledOnJre(JRE.JAVA_8)
    void testSoloJdk8() {
    }

    @Test
    @EnabledOnJre(JRE.JAVA_16)
    void testSoloJdk16() {
    }

    @Test
    @DisabledOnJre(JRE.JAVA_15)
    void testNoJdk15() {
    }

    @Test
    @DisabledOnJre(JRE.JAVA_8)
    void testNoJdk8() {
    }

    @Test
    void testImprimirSystemProperties() {
        Properties properties = System.getProperties();
        properties.forEach((k, v) -> {
            System.out.println(k + " = " + v);
        });
    }

    @Test
    @EnabledIfSystemProperty(named = "java.version", matches = "1.8.0_301")
    void testJavaVersion() {
    }

    @Test
    @DisabledIfSystemProperty(named = "os.arch", matches = ".*32.*")
    void testSolo64() {
    }

    @Test
    @DisabledIfSystemProperty(named = "os.arch", matches = ".*64.*")
    void testNo64() {
    }

    @Test
    @EnabledIfSystemProperty(named = "user.name", matches = "Adrian")
    void testUsername() {
    }

    @Test
    @EnabledIfSystemProperty(named = "ENV", matches = "DEV")
    void testDev() {
    }

    @Test
    void testImprimirVariablesAmbiente() {
        Map<String, String> getenv = System.getenv();
        getenv.forEach((k,v) -> {
            System.out.println(k + " = " + v);
        });
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "JAVA_HOME", matches = ".*jdk1.8.0_301.*")
    void testJavaHome() {
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "NUMBER_OF_PROCESSORS", matches = "16")
    void testProcesadores() {
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "ENVIRONMENT", matches = "DEV")
    void testEnv() {
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "ENVIRONMENT", matches = "PROD")
    void testEnvProdDisabled() {
    }
}