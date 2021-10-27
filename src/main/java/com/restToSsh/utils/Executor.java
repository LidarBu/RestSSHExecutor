package com.restToSsh.utils;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;

@Component
public class Executor {

    @Autowired
    Scripts scripts;

    @Autowired
    Environment environment;

    public static final Logger logger = LoggerFactory.getLogger(Executor.class);


    public String getPort(String bpmNum) {
        String command = String.format(scripts.PortBPM, bpmNum);
        System.out.printf(command);
        String response = exe(command);
        System.out.println(response);
        return response;
    }


    private String exe(String command) {
        Session session = getSession();
        ChannelExec channel = null;
        String output = null;
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
            output = new String(response.toByteArray());
            return output;
        } catch (JSchException | InterruptedException e) {
            logger.warn(e.getMessage());
            e.printStackTrace();
        } finally {
            session.disconnect();


            if (channel != null)
                channel.disconnect();

        }
        return output;
    }


    private Session getSession() {
        String host = environment.getProperty("ssh.hostname");
        String keyPath = environment.getProperty("ssh.key.path");
        String userName = environment.getProperty("ssh.username");
        Session session = null;
        try {
            JSch jsch = new JSch();
            jsch.addIdentity(keyPath);
            jsch.setKnownHosts(environment.getProperty("ssh.knownhosts.path"));
            session = jsch.getSession(userName, host);

        } catch (JSchException e) {
            logger.error("cant initialize ssh session");
            e.printStackTrace();
            System.exit(1);
        }
        return session;
    }


    public Scripts getScripts() {
        return scripts;
    }

    public void setScripts(Scripts scripts) {
        this.scripts = scripts;
    }

    public static Logger getLogger() {
        return logger;
    }


}
