package com.oocl.springbootdemo.controller;

import com.oocl.springbootdemo.constant.GlobalConstant;
import com.oocl.springbootdemo.model.AutoConfigModel;
import com.oocl.springbootdemo.model.Response;
import com.oocl.springbootdemo.model.User;
import com.oocl.springbootdemo.service.AutoConfigService;
import com.oocl.springbootdemo.service.UserService;
import com.solacesystems.jcsmp.InvalidPropertiesException;
import com.solacesystems.jcsmp.JCSMPException;
import com.solacesystems.jcsmp.JCSMPFactory;
import com.solacesystems.jcsmp.JCSMPProperties;
import com.solacesystems.jcsmp.JCSMPSession;
import com.solacesystems.jcsmp.JCSMPStreamingPublishEventHandler;
import com.solacesystems.jcsmp.TextMessage;
import com.solacesystems.jcsmp.Topic;
import com.solacesystems.jcsmp.XMLMessageProducer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SolaceController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AutoConfigService autoConfigService;
	
	@PostMapping("/send")
	public Response send(@RequestBody User user){
		Response response = new Response();

		final JCSMPProperties properties = new JCSMPProperties();
		properties.setProperty(JCSMPProperties.HOST, "tcp://mr8ksiwsp23vv.messaging.solace.cloud:20096");
		properties.setProperty(JCSMPProperties.USERNAME, "solace-cloud-client");
		properties.setProperty(JCSMPProperties.VPN_NAME,  "msgvpn-jfgwkefw7lz");
		properties.setProperty(JCSMPProperties.PASSWORD, "ghd5bq8ddnm41t008obaubh11j");

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
        System.out.printf("Producer received error for msg: %s@%s - %s%n",
            messageID,timestamp,e);
      }
    });

    final Topic topic = JCSMPFactory.onlyInstance().createTopic("tutorial/topic");
    TextMessage msg = JCSMPFactory.onlyInstance().createMessage(TextMessage.class);
    msg.setText(json);
    prod.send(msg,topic);
  }
}
