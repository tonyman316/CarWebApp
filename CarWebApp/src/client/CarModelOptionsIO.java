package client;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;

public class CarModelOptionsIO {
	
	public CarModelOptionsIO(){}
	
	public void showMenu(){
		System.out.println("\n\tAuto Configuration Menu");
		System.out.println("******************************************");
		System.out.println("1. Upload a new car");
		System.out.println("2. Configure an existing car");
		System.out.println("3. Exit");
		System.out.println("******************************************");
		System.out.println("Please enter(1/2/3): ");
	}
	
	public Properties loadPropsFile(FileInputStream infile, String file) throws IOException{
		Properties props = new Properties();
		infile = new FileInputStream(file);
		props.load(infile);
		return props;
	}
	
	// transferProps
	public void transferProps(ObjectOutputStream oos, Properties props){
		try {
			oos.writeObject(props);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// server reply{
	public void serverReply(ObjectInputStream ois){
		try {
			ois.skip(Long.MAX_VALUE);
			String responds = (String) ois.readObject();
			System.out.printf("Server reply: %s\n", responds);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}