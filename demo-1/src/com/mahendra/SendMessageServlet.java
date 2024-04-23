package com.mahendra;

import java.io.IOException;

import javax.annotation.Resource;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SendMessageServlet
 */
@WebServlet("/send-message.do")
public class SendMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(name = "jms/queueConnection")
	private QueueConnectionFactory factory;

	@Resource(mappedName="jms/order")
	private Queue queue1;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SendMessageServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String msg = request.getParameter("msg");
		response.setContentType("text/plain");
		response.getWriter().println("The message was : " + msg);
		
		
		try {
		Connection con = factory.createConnection();
		Session session = con.createSession();
		MessageProducer producer = session.createProducer(queue1);
		producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		TextMessage txt = session.createTextMessage(msg);
		producer.send(txt);
		con.close();
		}catch(JMSException ex) {
			response.getWriter().println("Error: "+ex.getMessage());
			ex.printStackTrace(System.out);
		}
		response.getWriter().close();
	}

}
