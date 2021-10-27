package com.restToSsh;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

public class Config {

    @Autowired
    Environment environment;
    public static final Logger logger = LoggerFactory.getLogger(Config.class);

    @Bean
    Session getSession() {
        String host = environment.getProperty("ssh.hostname");
        String keyPath = environment.getProperty("ssh.key.path");
        String userName = environment.getProperty("ssh.username");
        Session session = null;
        try {
            JSch jsch = new JSch();
            jsch.addIdentity(keyPath);
            session = jsch.getSession(userName, host);

        } catch (JSchException e) {
            logger.error("cant initialize ssh session");
            e.printStackTrace();
            System.exit(1);
        }
        return session;
    }
}
