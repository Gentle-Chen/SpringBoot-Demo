package com.oocl.springbootdemo.controller;

import com.oocl.springbootdemo.constant.GlobalConstant;
import com.oocl.springbootdemo.model.Response;
import com.oocl.springbootdemo.model.User;
import com.oocl.springbootdemo.service.AutoConfigService;
import com.oocl.springbootdemo.service.UserService;
import com.solacesystems.jcsmp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SolaceController {

    @Autowired private UserService userService;

    @Autowired private AutoConfigService autoConfigService;

    @Autowired private JCSMPSession session;

    @Value("${solace.jms.host}") private String hostname;
    @Value("${solace.jms.msgVpn}") private String vpnName;
    @Value("${solace.jms.clientUsername}") private String username;
    @Value("${solace.jms.clientPassword}") private String password;
    @Value("${solace.jms.demoQueueName}") private String queueName;

    @PostMapping("/sendQueue")
    public Response send(@RequestBody User user) throws JCSMPException {
        Response response = new Response();

        sendQueueObject(session, User.getJson(user), queueName);

        response.setStatus(GlobalConstant.SUCCESS);
        response.setResult(user);
        return response;
    }

    @PostMapping("/sendTopic")
    public Response sendTopic(@RequestBody String topicName) throws JCSMPException {
        Response response = new Response();

        this.sendTopicObject(session, topicName);

        response.setStatus(GlobalConstant.SUCCESS);
        response.setResult(topicName);
        return response;
    }

    public static void sendQueueObject(JCSMPSession session, String json, String queueName) throws JCSMPException {
        XMLMessageProducer prod = session.getMessageProducer(new JCSMPStreamingPublishEventHandler() {

            @Override
            public void responseReceived(String messageID) {
                System.out.println("Producer received response for msg: " + messageID);
            }

            @Override
            public void handleError(String messageID, JCSMPException e, long timestamp) {
                System.out.printf("Producer received error for msg: %s@%s - %s%n", messageID, timestamp, e);
            }
        });

        //        final Topic topic = JCSMPFactory.onlyInstance().createTopic("tutorial/topic");
        final Queue queue = JCSMPFactory.onlyInstance().createQueue(queueName);
        TextMessage msg = JCSMPFactory.onlyInstance().createMessage(TextMessage.class);
        msg.setText(json + "\n");
        prod.send(msg, queue);
    }

    void sendTopicObject(JCSMPSession session, String topicName) throws JCSMPException {
        XMLMessageProducer prod = session.getMessageProducer(new JCSMPStreamingPublishEventHandler() {

            @Override
            public void responseReceived(String messageID) {
                System.out.println("Producer received response for msg: " + messageID);
            }

            @Override
            public void handleError(String messageID, JCSMPException e, long timestamp) {
                System.out.printf("Producer received error for msg: %s@%s - %s%n", messageID, timestamp, e);
            }
        });

        final Topic topic = JCSMPFactory.onlyInstance().createTopic(topicName);

        TextMessage msg = JCSMPFactory.onlyInstance().createMessage(TextMessage.class);
        msg.setText(topicName + " 收到: tony test topic matcher");
        prod.send(msg, topic);
    }
}
