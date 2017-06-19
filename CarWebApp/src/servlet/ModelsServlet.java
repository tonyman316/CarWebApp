package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import adapter.ProxyAutomobile;
import client.DefaultSocketClient;
import server.Server;

// This Servlet interact with the Client to get all models.
//@WebServlet("/showList")
public class ModelsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DefaultSocketClient dsc = null;
	ArrayList<String> list = new ArrayList<String>();

	@Override
	public void init(ServletConfig config) throws ServletException {
		String localHost = "";
		int port = 5555;

		try {
			localHost = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			System.err.println("Unable to find local host");
		}
		System.out.printf("Connecting to %s:%d\n", localHost, port);
		dsc = new DefaultSocketClient(localHost, port);
		dsc.start();
		
		// Wait for a while to connect to the server. 
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		communicate();
	}
	// Interaction between client and server. To get a list of auto names
	private void communicate(){
		String command = "getList";
		dsc.sendOutput((Object) command);
		// !Caution!
		// wait for a while...for server to respond!
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		list = (ArrayList<String>) dsc.getInput();

	}

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		// HTML - print list of model

		out.println("<!DOCTYPE html><html><head>");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		out.println("<title>List</title>");
		out.println("</head><body><CENTER>");

		out.println("<H1>Please choose a model:</H1>\n");
		out.println("<form action=\"getAModel\" method=\"Get\">");
		out.println("<select name = \"model\">");
		for (String ls : list) {
			out.println("<option value=\"" + ls + "\">" + ls + "</option>");
		}
		out.println("</select>");
		out.println("<br>");
		out.println("<input type=\"submit\" value=\"Done\">");
		out.println("</form></CENTER></BODY></html>");
		
		dsc.closeSession();

	}
}
