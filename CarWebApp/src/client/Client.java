package client;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Client {

	public Client(){ }
	
	public static void main(String args[]) {
		String localHost = "";
		int port = 5555;

		try {
			localHost = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			System.err.println("Unable to find local host");
		}
		System.out.printf("Connecting to %s:%d\n", localHost, port);
		DefaultSocketClient dsc = new DefaultSocketClient(localHost, port);
		dsc.start();
	}
}
	
/*
 * Path:	

/Users/TonyCP/Documents/workspace/CarWebApp/src/util/FordFocusWagonZTW.Properties
/Users/TonyCP/Documents/workspace/CarWebApp/src/util/HondaCivic.Properties
	
/Users/TonyCP/Documents/workspace/CarWebApp/src/util/ToyotaCorolla.Properties
/Users/TonyCP/Documents/workspace/CarWebApp/src/util/NissanAltima.Properties

*/

