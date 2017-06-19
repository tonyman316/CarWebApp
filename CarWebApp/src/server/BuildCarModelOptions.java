package server;

import java.util.ArrayList;
import java.util.Properties;
import adapter.ProxyAutomobile;
import exception.AutoException;
import model.Automobile;
import util.FileIO;

import java.io.*;

public class BuildCarModelOptions implements AutoServer{
	private FileIO fileIO = new FileIO();
	private Automobile auto = null;
	
	public BuildCarModelOptions(){ }
	
	public void createAutoWithPropFromClientSocket(Properties props){
		try {
			this.auto = this.fileIO.buildAutoFromPropertiesFile(props);
		} catch (AutoException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addAutoToLHM(){
		ProxyAutomobile.getAllAutos().addNewAuto(this.auto);
	}
	
	// return all auto modelName
	public ArrayList<String> getAllModels(){
		return ProxyAutomobile.getAllAutos().getAutosKeys();	
	}
	
	public void listOfModels(){
		System.out.println(getAllModels());
	}
	
	public void sendAuto(ObjectOutputStream oos, String modelName) {
		Automobile auto = ProxyAutomobile.getAllAutos().findAnAuto(modelName);
		if (auto == null) {
			System.err.printf("No such model found: %s\n", modelName);
		}else{
			//auto.print();
		}

		try {
			oos.writeObject(auto);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}