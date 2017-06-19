package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import adapter.BuildAuto;
import adapter.CreateAuto;

public class Server {
	private ServerSocket serverSocket = null;
	private Socket clientSocket = null;

	public Server() { }
	
	public void run() {
		try {
		    this.serverSocket = new ServerSocket(5555);
		} catch (IOException e) {
		    System.err.println("Could not listen on port...");
		    System.exit(1);
		}
		
		DefaultSocketServer dss = null;
		try {
			while (true) {
				this.clientSocket = this.serverSocket.accept();
				System.out.printf("New connection from %s:%d\n", clientSocket.getInetAddress().getHostAddress(), clientSocket.getPort());
				dss = new DefaultSocketServer(this.clientSocket);
				dss.showAllModels();
				dss.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		buildAllAutos();
		Server server = new Server();
		server.run();

	}
	
	// for testing purpose only
	private static void buildAllAutos(){
		String FordFocus = "/Users/tonyman/Documents/Projects/Java/CarWebApp/src/util/FordFocusWagonZTW.txt";
		String HondaCivic = "/Users/tonyman/Documents/Projects/Java/CarWebApp/src/util/HondaCivic.txt";
		String ToyotaCorolla = "/Users/tonyman/Documents/Projects/Java/CarWebApp/src/util/ToyotaCorolla.txt";
		
		CreateAuto createAuto = new BuildAuto();
		createAuto.buildAuto(FordFocus,0);
		createAuto.buildAuto(HondaCivic,0);
		createAuto.buildAuto(ToyotaCorolla,0);

		System.out.println("Pre-loaded autos...");
		//createAuto.printAllAutos();
	}
}


