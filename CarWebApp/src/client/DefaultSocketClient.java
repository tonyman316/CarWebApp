package client;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.Scanner;
import model.Automobile;
import server.SocketServerClientInterface;

public class DefaultSocketClient extends Thread implements SocketServerClientInterface {

	private Socket clientSocket = null;
	private String host = null;
	private int port;
	private Scanner scanner = null;
	private ObjectOutputStream outStream = null;
	private ObjectInputStream inStream = null;
	private FileInputStream inputfile = null;
	private boolean done = false;
	private CarModelOptionsIO cmoIO = new CarModelOptionsIO();
	private configChoices configChoices = new SelectCarOption();

	public DefaultSocketClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void setHost(String host) {
		this.host = host;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	public void run() {
		//while (!this.done) {
			if (openConnection()) {
				//handleSession();
				//closeSession();
			}
		//}
	}
	
	public boolean openConnection() {
		try {
			this.clientSocket = new Socket(this.host, this.port);
			this.outStream = new ObjectOutputStream(this.clientSocket.getOutputStream());
			this.inStream = new ObjectInputStream(this.clientSocket.getInputStream());

		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void upload(BufferedReader buff){
		try {
			this.outStream.writeObject("upload");
			// Prompt for a properties file
			System.out.printf("Enter a properties file path: ");
			String file = buff.readLine();
			// Load the properties object
			Properties props = this.cmoIO.loadPropsFile(inputfile, file);
			// Write and transfer props to server
			ObjectOutputStream oos = new ObjectOutputStream(this.clientSocket.getOutputStream());
			this.cmoIO.transferProps(oos, props);
			this.clientSocket.shutdownOutput();
			// Get responds from server
			ObjectInputStream ois = new ObjectInputStream(this.clientSocket.getInputStream());
			this.cmoIO.serverReply(ois);
			
			ois.close();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void configure(BufferedReader buff){
		try {
			this.outStream.writeObject("configure");
			// Let's get a list of models
			ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
			this.configChoices.printModels(ois);
			// choose a model
			this.configChoices.chooseAModel(buff, outStream);

			// Get an auto from server.
			Automobile auto = (Automobile) ois.readObject();
			if (auto != null) {
				System.out.printf("Received the following auto from the server: %s\n", auto.getModelName());
				// choose options:
				this.configChoices.chooseOptions(auto);
			}else{
				System.out.println("No Auto is Received. @o@");
			}
			ois.close();
			this.configChoices.printConfiguredAuto(auto);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	
	}
	
	public void handleSession() {
		try {
			BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));	// or use Scanner
			this.cmoIO.showMenu();
			// Client input:
			this.scanner = new Scanner(System.in);
			int userInput = 0;
			boolean validInput = false;

			while (!validInput) {
				userInput = scanner.nextInt();

				switch (userInput) {
				case 1:
					upload(buff);
					validInput = true;
					break;
				case 2:
					configure(buff);
					validInput = true;
					break;
				case 3:
					this.outStream.writeObject("exit");
					this.outStream.close();
					this.done = true;
					validInput = true;
					break;
				default:
					System.out.println("Invalid input! Please enter 1/2/3");
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeSession() {
		try {
			if (this.inputfile != null) {
				this.inputfile.close();
			}
			this.outStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Object getInput() {
		try {
			return inStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
				System.out.println("Error writing to " + clientSocket.getInetAddress());
		}
		return null;
	}

	public void sendOutput(Object object) {
		try {
			outStream.writeObject(object);
		} catch (IOException e) {
				System.out.println("Error writing to " + clientSocket.getInetAddress());
		}
	}
}
