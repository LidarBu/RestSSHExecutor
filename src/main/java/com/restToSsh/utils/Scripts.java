package com.restToSsh.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class Scripts {

    @Autowired
    Environment environment;

    public String AllRunningBPMs;
    public String AllRunningPorts;
    public String BPMInfo;
    public String PortBPM;
    public String StartBPM;
    public String StopBPM;
    public String LogBPM;


    @PostConstruct
    public void init() {
        String scriptsPath = environment.getProperty("ssh.scripts.path");
        Field[] fields = Scripts.class.getDeclaredFields();
        File file = null;
        String command;


        for (Field field : fields) {
            try {

                field.setAccessible(true);
                String fieldName = field.getName();
                if (!fieldName.equals("environment")) {
                    command = Files.readString(
                            Paths.get(scriptsPath + "/" + fieldName + ".sh"),
                            StandardCharsets.US_ASCII);
                    field.set(this, command);
                }
            } catch (IOException | IllegalAccessException e) {
                e.printStackTrace();
            }


        }

    }


    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getAllRunningBPMs() {
        return AllRunningBPMs;
    }

    public void setAllRunningBPMs(String allRunningBPMs) {
        AllRunningBPMs = allRunningBPMs;
    }

    public String getAllRunningPorts() {
        return AllRunningPorts;
    }

    public void setAllRunningPorts(String allRunningPorts) {
        AllRunningPorts = allRunningPorts;
    }

    public String getBPMInfo() {
        return BPMInfo;
    }

    public void setBPMInfo(String BPMInfo) {
        this.BPMInfo = BPMInfo;
    }

    public String getPortBPM() {
        return PortBPM;
    }

    public void setPortBPM(String portBPM) {
        PortBPM = portBPM;
    }

    public String getStartBPM() {
        return StartBPM;
    }

    public void setStartBPM(String startBPM) {
        StartBPM = startBPM;
    }

    public String getStopBPM() {
        return StopBPM;
    }

    public void setStopBPM(String stopBPM) {
        StopBPM = stopBPM;
    }

    public String getLogBPM() {
        return LogBPM;
    }

    public void setLogBPM(String logBPM) {
        LogBPM = logBPM;
    }
}
