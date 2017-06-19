package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;

import adapter.ProxyAutomobile;
import model.Automobile;
import server.BuildCarModelOptions;

public class DefaultSocketServer extends Thread implements SocketServerClientInterface{
	private Socket clientSocket;
	private ObjectInputStream inStream = null;
	private ObjectOutputStream outStream= null;
	private AutoServer autoServer = new BuildCarModelOptions();
	
	public DefaultSocketServer(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public boolean openConnection() {
		try {
			this.inStream = new ObjectInputStream(this.clientSocket.getInputStream());
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public void handleSession() {
		try {
			String clientInput = (String) inStream.readObject();
			System.out.printf("Client says: %s\n", clientInput);

			if (clientInput.equalsIgnoreCase("upload")) {
				
				// Get props from client
				this.inStream = new ObjectInputStream(this.clientSocket.getInputStream());
				Properties props = (Properties) this.inStream.readObject();
				
				// create new auto
				this.autoServer.createAutoWithPropFromClientSocket(props);	
				this.autoServer.addAutoToLHM();
				
				this.outStream = new ObjectOutputStream(this.clientSocket.getOutputStream());
				this.outStream.flush();
				this.outStream.writeObject("Sucessfully created a new auto!");	
				
			} else if (clientInput.equalsIgnoreCase("configure")) {
				// Send the list of models to client
				this.outStream = new ObjectOutputStream(this.clientSocket.getOutputStream());
				this.outStream.flush();
				ArrayList<String> models = this.autoServer.getAllModels();
				this.outStream.writeObject(models);
				this.outStream.flush();

				// Client reply a model
				String model = (String) this.inStream.readObject();	
				System.out.println("Server got your choice of car: " + model);
				this.autoServer.sendAuto(outStream, model);
				
			} else if (clientInput.equalsIgnoreCase("exit")) {
				this.closeSession();
			// For Web
			} else if (clientInput.equalsIgnoreCase("getList")){
				// Send the list of models to client
				this.outStream = new ObjectOutputStream(this.clientSocket.getOutputStream());
				this.outStream.flush();
				ArrayList<String> models = this.autoServer.getAllModels();
				this.outStream.writeObject(models);
				this.outStream.flush();
			// For Web
			} else if (clientInput.equalsIgnoreCase("getModel")){
				this.outStream = new ObjectOutputStream(this.clientSocket.getOutputStream());
				String model = (String) this.inStream.readObject();	
				System.out.println("Server got your choice of car: " + model);
				this.autoServer.sendAuto(outStream, model);				
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void closeSession() {
		try {
			this.inStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showAllModels() { 
		this.autoServer.listOfModels();
	}

	public void run() {
		if (openConnection()) {
			handleSession();
		}
	}
	
}
