package com.mahendra;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ReadMessageServlet
 */
@WebServlet("/read-message.do")
public class ReadMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Resource(name="jms/topicFactory")
	private TopicConnectionFactory factory;
	
	@Resource(mappedName="jms/news")
	private Topic news;
	
	
	private Session session;
	private TopicConnection con;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReadMessageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() throws ServletException {
    	try {
    	TopicConnection con = factory.createTopicConnection();
    	con.start(); 
		session = con.createSession();
    	}catch(JMSException ex) {
    		ex.printStackTrace();
    	}
    }
    
    @Override
    public void destroy() {
    	try {
    	session.close();
    	con.close();
    	}catch(JMSException ex) {
    		ex.printStackTrace();
    	}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		
		try {
			
			MessageConsumer consumer = session.createConsumer(news);
			Message message = consumer.receive(5000);
			if(message != null && message instanceof TextMessage) {
				TextMessage txt = (TextMessage) message;
				out.println("News : " +txt.getStringProperty("title"));
				out.println(txt.getText());
			}else {
				out.println("No Messages found !");
			}
			
		}catch(JMSException ex) {
			ex.printStackTrace();
		}
		
		
		out.close();
	}

}
