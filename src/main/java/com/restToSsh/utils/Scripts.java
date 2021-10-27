package com.restToSsh.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

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

    public String AllRunningBPMS;
    public String AllRunningPorts;
    public String BPMInfo;
    public String PortsBPM;
    public String StartBPM;
    public String StopBPM;
    public String LogBPM;


    {
        String scriptsPath = environment.getProperty("ssh.scripts.path");
        Field[] fields = Scripts.class.getDeclaredFields();
        File file = null;
        String command;


        for (Field field : fields) {
            try {
                field.setAccessible(true);
                String fieldName = field.getName();
                command = Files.readString(
                        Paths.get(scriptsPath + "/" + fieldName + ".sh"),
                        StandardCharsets.US_ASCII);
                field.set(this, command);
            } catch (IOException | IllegalAccessException e) {
                e.printStackTrace();
            }


        }

    }


}
