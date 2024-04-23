package com.mahendra;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Message-Driven Bean implementation class for: MessageReader
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "physicalName"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue")
		}, 
		mappedName = "jms/order")
public class MessageReader implements MessageListener {

    /**
     * Default constructor. 
     */
    public MessageReader() {
        // TODO Auto-generated constructor stub
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
    	  try {
    	       if ( message instanceof TextMessage) {
    	    	   TextMessage txt = (TextMessage) message;
    	    	   System.out.println("Message : "+txt.getText());
    	    	   txt.acknowledge(); // inform Brocker, that message is "consumed"
    	       }}catch(JMSException ex) {
    	    	   ex.printStackTrace();
    	       }
    }

}
