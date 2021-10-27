package com.restToSsh.utils;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayOutputStream;

public class Executor {

    @Autowired
    Session session;

    @Autowired
    Scripts scripts;

    public static final Logger logger = LoggerFactory.getLogger(Executor.class);

    public String getPort(String bpmNum) {
        String command = String.format(scripts.PortsBPM, bpmNum);
        String response = exe(command);
        System.out.println(response );
        return response;
    }


    private String exe(String command) {
        ChannelExec channel = null;
        try {
            session.connect();

            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            ByteArrayOutputStream response = new ByteArrayOutputStream();
            channel.setOutputStream(response);
            channel.connect();

            while (channel.isConnected()) {
                Thread.sleep(50);
            }
            String output = new String(response.toByteArray());
            return output;
        } catch (JSchException | InterruptedException e) {
            logger.warn(e.getMessage());
            e.printStackTrace();
        } finally {
            session.disconnect();

            if (channel != null)
                channel.disconnect();
            return null;
        }

    }


}
