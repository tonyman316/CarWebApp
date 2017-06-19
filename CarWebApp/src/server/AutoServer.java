package server;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Properties;

public interface AutoServer {
	
	public ArrayList<String> getAllModels();
	public void listOfModels();
	public void createAutoWithPropFromClientSocket(Properties props);
	public void addAutoToLHM();
	public void sendAuto(ObjectOutputStream oos, String modelName);
	
}
