package driver;

import java.net.InetAddress;
import java.net.UnknownHostException;

import adapter.BuildAuto;
import adapter.CreateAuto;
import client.DefaultSocketClient;
import server.Server;

public class Driver {

	public static void main(String[] args) {
//		Server server = new Server();
//		server.run();

		testCreateAuto();
		
	}
	public static void testCreateAuto(){
		String FordFocus = "/Users/tonyman/Documents/Projects/Java/CarWebApp/src/util/FordFocusWagonZTW.txt";
		String HondaCivic = "/Users/tonyman/Documents/Projects/Java/CarWebApp/src/util/HondaCivic.txt";
		
		CreateAuto createAuto = new BuildAuto();
		createAuto.buildAuto(FordFocus,0);
		createAuto.buildAuto(HondaCivic,0);
		//createAuto.printAnAuto("Ford Wagon ZTW");
		//createAuto.printAnAuto("Honda Civic");

		createAuto.printAllAutos();
	}

}
