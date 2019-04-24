package com.oocl.springbootdemo.controller;

import com.oocl.springbootdemo.model.User;
import com.solacesystems.jcsmp.BytesXMLMessage;
import com.solacesystems.jcsmp.InvalidPropertiesException;
import com.solacesystems.jcsmp.JCSMPException;
import com.solacesystems.jcsmp.JCSMPFactory;
import com.solacesystems.jcsmp.JCSMPProperties;
import com.solacesystems.jcsmp.JCSMPSession;
import com.solacesystems.jcsmp.TextMessage;
import com.solacesystems.jcsmp.Topic;
import com.solacesystems.jcsmp.XMLMessageConsumer;
import com.solacesystems.jcsmp.XMLMessageListener;
import java.util.concurrent.CountDownLatch;

public class Consumer {
  public static void main(String[] args){
    final JCSMPProperties properties = new JCSMPProperties();
    properties.setProperty(JCSMPProperties.HOST, "tcp://mr8ksiwsp23vv.messaging.solace.cloud:20096");
    properties.setProperty(JCSMPProperties.USERNAME, "solace-cloud-client");
    properties.setProperty(JCSMPProperties.VPN_NAME,  "msgvpn-jfgwkefw7lz");
    properties.setProperty(JCSMPProperties.PASSWORD, "ghd5bq8ddnm41t008obaubh11j");

    final JCSMPSession session;
    try {
      session = JCSMPFactory.onlyInstance().createSession(properties);
      session.connect();

      getMessage(session);
    } catch (InvalidPropertiesException e) {
      e.printStackTrace();
    } catch (JCSMPException e) {
      e.printStackTrace();
    }

  }

  static void getMessage(JCSMPSession session) throws JCSMPException {
    final CountDownLatch latch = new CountDownLatch(1);

    final XMLMessageConsumer cons = session.getMessageConsumer(new XMLMessageListener() {

      @Override
      public void onReceive(BytesXMLMessage msg) {
        if (msg instanceof TextMessage) {
          System.out.printf("TextMessage received: '%s'%n",
              ((TextMessage)msg).getText());
          System.out.println("User=" + User.getUser(((TextMessage)msg).getText()));
        } else {
          System.out.println("Message received.");
        }
        System.out.printf("Message Dump:%n%s%n",msg.dump());
        latch.countDown();  // unblock main thread
      }

      @Override
      public void onException(JCSMPException e) {
        System.out.printf("Consumer received exception: %s%n",e);
        latch.countDown();  // unblock main thread
      }
    });

    final Topic topic = JCSMPFactory.onlyInstance().createTopic("tutorial/topic");
    session.addSubscription(topic);
    cons.start();

    try {
      latch.await(); // block here until message received, and latch will flip
    } catch (InterruptedException e) {
      System.out.println("I was awoken while waiting");
    }
  }
}
