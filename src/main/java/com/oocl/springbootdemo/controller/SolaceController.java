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

    @Value("${solace.jms.host}") private String hostname;
    @Value("${solace.jms.msgVpn}") private String vpnName;
    @Value("${solace.jms.clientUsername}") private String username;
    @Value("${solace.jms.clientPassword}") private String password;
    @Value("${solace.jms.demoQueueName}") private String queueName;

    @PostMapping("/sendQueue")
    public Response send(@RequestBody User user) {
        Response response = new Response();
        final JCSMPProperties properties = new JCSMPProperties();
        properties.setProperty(JCSMPProperties.HOST, hostname);
        properties.setProperty(JCSMPProperties.USERNAME, username);
        properties.setProperty(JCSMPProperties.VPN_NAME, vpnName);
        properties.setProperty(JCSMPProperties.PASSWORD, password);

        final JCSMPSession session;
        try {
            session = JCSMPFactory.onlyInstance().createSession(properties);
            session.connect();

            this.sendObject(session, User.getJson(user));
        } catch (InvalidPropertiesException e) {
            e.printStackTrace();
        } catch (JCSMPException e) {
            e.printStackTrace();
        }

        response.setStatus(GlobalConstant.SUCCESS);
        response.setResult(user);
        return response;
    }

    private void sendObject(JCSMPSession session, String json) throws JCSMPException {
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
        msg.setText(json);
        prod.send(msg, queue);
    }
}
