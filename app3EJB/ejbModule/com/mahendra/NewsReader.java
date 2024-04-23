package com.mahendra;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Message-Driven Bean implementation class for: NewsReader
 */

@MessageDriven(
		
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "news"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Topic")
		},  
		mappedName = "jms/news")
public class NewsReader implements MessageListener {

    /**
     * Default constructor. 
     */
    public NewsReader() {
        // TODO Auto-generated constructor stub
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
        System.out.println("Received a new message !");
        try {
        if(message instanceof TextMessage) {
        	TextMessage txt = (TextMessage) message;
        	String title = txt.getStringProperty("title");
        	String contents = txt.getText();
        	System.out.println("News title : "+title);
        	System.out.println("---");
        	System.out.println(contents);
        	txt.acknowledge();
        }
        }catch(JMSException ex) {
        	ex.printStackTrace();
        }
    }

}
