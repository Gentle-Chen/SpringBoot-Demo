package com.oocl.springbootdemo.config;

import com.solacesystems.jcsmp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;

import javax.jms.ConnectionFactory;
import java.io.*;

@EnableJms
@Configuration
public class ConsumerConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerConfiguration.class);
    @Value("${solace.jms.host}") private String hostname;
    @Value("${solace.jms.msgVpn}") private String vpnName;
    @Value("${solace.jms.clientUsername}") private String username;
    @Value("${solace.jms.clientPassword}") private String password;
    @Value("${solace.jms.demoQueueName}") private String queueName;
    // Example configuration of the ConnectionFactory: we instantiate it here ourselves and set an error handler
    @Bean
    public DefaultJmsListenerContainerFactory cFactory(ConnectionFactory connectionFactory, DemoErrorHandler errorHandler) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setErrorHandler(errorHandler);
        return factory;
    }

    @Bean
    public Topic topic1() {
        return JCSMPFactory.onlyInstance().createTopic("*/topic");
    }

    @Bean
    public JCSMPSession session(Topic topic) throws JCSMPException {
        final JCSMPProperties properties = new JCSMPProperties();
        properties.setProperty(JCSMPProperties.HOST, hostname);
        properties.setProperty(JCSMPProperties.USERNAME, username);
        properties.setProperty(JCSMPProperties.VPN_NAME, vpnName);
        properties.setProperty(JCSMPProperties.PASSWORD, password);
        JCSMPSession session = JCSMPFactory.onlyInstance().createSession(properties);
        session.connect();
        final XMLMessageConsumer cons = session.getMessageConsumer(new XMLMessageListener() {
            @Override
            public void onReceive(BytesXMLMessage msg) {
                if (msg instanceof TextMessage) {
                    System.out.printf("TextMessage received: '%s'%n", ((TextMessage)msg).getText());
                } else {
                    System.out.println("Message received.");
                }
                System.out.printf("Message Dump:%n%s%n", msg.dump());
                //                latch.countDown();  // unblock main thread
            }

            @Override
            public void onException(JCSMPException e) {
                System.out.printf("Consumer received exception: %s%n", e);
            }
        });
        session.addSubscription(topic);
        cons.start();
        return session;
    }
    @Service
    public class DemoErrorHandler implements ErrorHandler {

        @Override
        public void handleError(Throwable t) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(os);
            t.printStackTrace(ps);
            try {
                String output = os.toString("UTF8");
                logger.error("============= Error processing message: " + t.getMessage() + "\n" + output);
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

}
