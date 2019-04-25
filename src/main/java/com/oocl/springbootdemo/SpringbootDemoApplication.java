package com.oocl.springbootdemo;

import com.solacesystems.jcsmp.JCSMPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.util.*;

@SpringBootApplication
public class SpringbootDemoApplication {

    public static void main(String[] args) throws JCSMPException {
        SpringApplication.run(SpringbootDemoApplication.class, args);
    }

    @Component
    static class MessageHandler {

        private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

		// Retrieve the name of the queue from the application.properties file
        @JmsListener(destination = "${solace.jms.demoQueueName1}")
        public void processMsg(Message msg) {
            StringBuffer msgAsStr = new StringBuffer("============= Received \nHeaders:");
            MessageHeaders hdrs = msg.getHeaders();
            msgAsStr.append("\nUUID: " + hdrs.getId());
            msgAsStr.append("\nTimestamp: " + hdrs.getTimestamp());
            Iterator<String> keyIter = hdrs.keySet().iterator();
            while (keyIter.hasNext()) {
                String key = keyIter.next();
                msgAsStr.append("\n" + key + ": " + hdrs.get(key));
            }
            msgAsStr.append("\nPayload: " + msg.getPayload());
            logger.info(msgAsStr.toString());
        }
    }
}
