package com.mahendra;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PublishNewsServlet
 */
@WebServlet("/send-news.do")
public class PublishNewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	@Resource (name="jms/topicFactory")
	private TopicConnectionFactory factory;
	
	@Resource(mappedName="jms/news")
	private Topic news;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PublishNewsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String msgTitle = request.getParameter("newstitle");
		String msgText = request.getParameter("newscontents");
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		
		try {
		Connection con = factory.createConnection();
		out.println("Get the connection !");
		Session session = con.createSession();
		out.println("Created a session");
		MessageProducer producer = session.createProducer(news);
		out.println("A producer is created !");
		TextMessage txt = session.createTextMessage();
		out.println("Created an empty TEXT message !");
		txt.setText(msgText);
		out.println("Set the message body !");
		txt.setStringProperty("title", msgTitle);
		out.println("Add a new attributes !");
		producer.send(txt);
		out.println("Message published !");
		con.close();
		out.println("Closed  the connection !");
		}catch(JMSException ex)
		{
			ex.printStackTrace();
		}
		
	}

}
