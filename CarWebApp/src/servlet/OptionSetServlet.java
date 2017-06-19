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

import adapter.BuildAuto;
import adapter.CreateAuto;
import adapter.ProxyAutomobile;
import client.DefaultSocketClient;
import model.Automobile;

// This Servlet interact with the Client to get all OptionSets.
@WebServlet("/getAModel")
public class OptionSetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DefaultSocketClient dsc = null;

	@Override
	public void init(ServletConfig config) {
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
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
	}
	
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Interaction between client and server. To get an auto.
		String command = "getModel";
		dsc.sendOutput((Object) command);
		String modelName = request.getParameter("model");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		dsc.sendOutput((Object) modelName);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Automobile automobile = (Automobile) dsc.getInput();
		// Dispatch to jsp to print automobile options
		request.getSession().setAttribute("auto", automobile);
		request.getRequestDispatcher("configure.jsp").forward(request, response);
		
		dsc.closeSession();
	}
}
