package util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Properties;

import exception.AutoException;
import model.Automobile;

public class FileIO {
	private String autoExceptionPath = "/Users/tonyman/Documents/Projects/Java/CarWebApp/src/util/";
	private String fileName;
	
	public FileIO() { }
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) throws AutoException {
		File f = new File(fileName);
		if (!f.exists()) {
			throw new AutoException(1);
		}else{
			this.fileName = fileName;
		}
	}
	
//	public Automobile readFile(String fileName, int fileType) throws AutoException, IOException{
//		switch(fileType){
//		case 0: return readTextFile(fileName); 
//		case 1: return buildAutoFromPropertiesFile(fileName);
//		default: return null;
//		}
//	}
	
	//readPropertiesFile and return a Properties object
	public Properties readPropertiesFile(String fileName) throws AutoException, IOException{
		Properties props= new Properties();
		FileInputStream infile = null;
		
		try {
			infile = new FileInputStream(fileName); 
			props.load(infile); //This loads the entire file in memory.
		} catch (FileNotFoundException e) {
			throw new AutoException(1);
		} catch (IOException e) {
			System.out.printf("Failed to read the properties file in: %s\n", e.toString());
		}
		return props;
	}
	
	public Automobile buildAutoFromPropertiesFile(String fileName, Properties props) throws AutoException, IOException{
		Automobile model = new Automobile();
		
		// Check if the auto exists in file
		if (!props.getProperty("Model").equals(null)) {
			model = loadProperties(props);
		}
		return model;
	}
	
	public Automobile buildAutoFromPropertiesFile(Properties props) throws AutoException, IOException{
		Automobile model = new Automobile();
		
		// Check if the auto exists in file
		if (!props.getProperty("Model").equals(null)) {
			model = loadProperties(props);
		}
		return model;
	}
	
	private Automobile loadProperties(Properties props) throws NumberFormatException, AutoException{		
		Automobile model = new Automobile();

		model.setModelName(props.getProperty("Model"));
		model.setBaseprice(Double.parseDouble(props.getProperty("BasePrice")));
		
		// Color
		model.addOptionSet(props.getProperty("OptionSetName1"), Integer.parseInt(props.getProperty("OptionSetName1Count")));
		model.addOptionToSet(props.getProperty("OptionSetName1"), props.getProperty("OptionName1a"), Double.parseDouble(props.getProperty("OptionPrice1a")));
		model.addOptionToSet(props.getProperty("OptionSetName1"), props.getProperty("OptionName1b"), Double.parseDouble(props.getProperty("OptionPrice1b")));
		model.addOptionToSet(props.getProperty("OptionSetName1"), props.getProperty("OptionName1c"), Double.parseDouble(props.getProperty("OptionPrice1c")));
		

		// Transmission
		model.addOptionSet(props.getProperty("OptionSetName2"), Integer.parseInt(props.getProperty("OptionSetName2Count")));
		model.addOptionToSet(props.getProperty("OptionSetName2"), props.getProperty("OptionName2a"), Double.parseDouble(props.getProperty("OptionPrice2a")));
		model.addOptionToSet(props.getProperty("OptionSetName2"), props.getProperty("OptionName2b"), Double.parseDouble(props.getProperty("OptionPrice2b")));

		// BrakesTractionControl
		model.addOptionSet(props.getProperty("OptionSetName3"), Integer.parseInt(props.getProperty("OptionSetName3Count")));
		model.addOptionToSet(props.getProperty("OptionSetName3"), props.getProperty("OptionName3a"), Double.parseDouble(props.getProperty("OptionPrice3a")));
		model.addOptionToSet(props.getProperty("OptionSetName3"), props.getProperty("OptionName3b"), Double.parseDouble(props.getProperty("OptionPrice3b")));
		model.addOptionToSet(props.getProperty("OptionSetName3"), props.getProperty("OptionName3c"), Double.parseDouble(props.getProperty("OptionPrice3c")));

		// Side Impact Air Bags
		model.addOptionSet(props.getProperty("OptionSetName4"), Integer.parseInt(props.getProperty("OptionSetName4Count")));
		model.addOptionToSet(props.getProperty("OptionSetName4"), props.getProperty("OptionName4a"), Double.parseDouble(props.getProperty("OptionPrice4a")));
		model.addOptionToSet(props.getProperty("OptionSetName4"), props.getProperty("OptionName4b"), Double.parseDouble(props.getProperty("OptionPrice4b")));		
		
		// Power Moonroof
		model.addOptionSet(props.getProperty("OptionSetName5"), Integer.parseInt(props.getProperty("OptionSetName5Count")));
		model.addOptionToSet(props.getProperty("OptionSetName5"), props.getProperty("OptionName5a"), Double.parseDouble(props.getProperty("OptionPrice5a")));
		model.addOptionToSet(props.getProperty("OptionSetName5"), props.getProperty("OptionName5b"), Double.parseDouble(props.getProperty("OptionPrice5b")));
		
		return model;
	}
	
	public Automobile readTextFile(String fileName) throws AutoException{
		Automobile model = new Automobile();
		try {
			FileReader file = new FileReader(this.fileName);
			BufferedReader buff = new BufferedReader(file);
			boolean eof = false;
			while (!eof) {
				String line = buff.readLine();
				if (line == null)
					eof = true;
				else {
						//System.out.println("Reading line " + counter + ": " + line);
						// second parameter < 0 prevents stripping trailing empty matches 
					buildAutoParser(model, line.split(":", -1), buff);	
				}
			}
			buff.close();
		} catch (Exception error) {
			System.out.println("ReadFileError:" + error.toString());
		}
				
		return model;
	}
	
	private void buildAutoParser(Automobile model, String[] splittedLine, BufferedReader buff) throws AutoException{

		switch (splittedLine[0]) {
		case "Model":
			try{
				model.setModelName(splittedLine[1]);
			}catch(Exception e){
				throw new AutoException(5);
			}
			break;
		case "BasePrice":
			//System.out.println("splittedLine[1]: " + splittedLine[1]);			
			if(splittedLine[1].isEmpty()){
				System.out.println("splittedLine[1]: empty..");
				splittedLine[1] = "-1";
			}
			try {
				model.setBaseprice(Double.parseDouble(splittedLine[1]));
			} catch (AutoException ae) {
				ae.print();
				ae.writeExceptionToFile();
				System.out.println("Calling fixAProblem");
				ae.fixAProblem(ae.getErrorno());
				System.out.println("After fix");
			}
			// What should I do after I catch MissingPriceInTextFile?
			break;
		case "Option":
			try{
				model.addOptionSet(splittedLine[1], Integer.parseInt(splittedLine[2]));
			}catch(ArrayIndexOutOfBoundsException e){
				throw new AutoException(3);
			}
			int optionSize = Integer.parseInt(splittedLine[2]);
//			System.out.println("optionName: " + splittedLine[0]);
//			System.out.println("optionSize: " + optionSize);

			for (int i = 0; i < optionSize; i++) {
				// set options
				try {
					String[] option = buff.readLine().split(":");
					//System.out.println("optionName: " + option[0]);
					//System.out.println("optionPrice: " + option[1]);
					model.addOptionToSet(splittedLine[1], option[0], Double.parseDouble(option[1]));
				} catch (Exception error) {
					System.out.println("Error: " + error.toString());
					throw new AutoException(4);
				}
			}
			break;
		}
	}
	
	public void automotiveSerialization(Automobile model){
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("automotive.ser"));
			out.writeObject(model);
			out.flush();
			out.close();
			System.out.println("\nAutomotive Serialization success!");  

		} catch (Exception e) {
			System.out.print("Error: " + e);
			System.exit(1);
		}
	}
	
	public Automobile automotiveDeserialization(){
		try {

			ObjectInputStream in = new ObjectInputStream(new FileInputStream("automotive.ser"));
			Automobile model  = (Automobile) in.readObject(); // cast it as Automotive object 
			in.close();
			System.out.println("Automotive Deserialization success!\n");
			return model;

		} catch (Exception e) {
			System.out.print("Error: " + e);
			System.exit(1);
			return null;
		}
	}
	
	// Properties Serialization
	public void propertiesSerialization(Properties props){
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("props.ser"));
			out.writeObject(props);
			out.flush();
			out.close();
			System.out.println("\nProperties Serialization success!");  

		} catch (Exception e) {
			System.out.print("Error: " + e);
			System.exit(1);
		}
	}
	
	// Properties Deserialization
	public Properties propertiesDeserialization(){
		try {

			ObjectInputStream in = new ObjectInputStream(new FileInputStream("props.ser"));
			Properties props  = (Properties) in.readObject(); // cast it as Automotive object 
			in.close();
			System.out.println("Automotive Deserialization success!\n");
			return props;

		} catch (Exception e) {
			System.out.print("Error: " + e);
			System.exit(1);
			return null;
		}
	}
	
	public HashMap<Integer, String> loadErrorCodesFromFile() {
		HashMap<Integer, String> errorsFromFile = new HashMap<>();
		try {
			FileReader file = new FileReader(autoExceptionPath + "AutoExceptionList.txt");
			BufferedReader buff = new BufferedReader(file);
			boolean eof = false;
			int currentCode = 0;
			String line = "";
			String[] splittedLine;
			while (!eof) {
				line = buff.readLine();
				if (line == null) {
					eof = true;
				} else {
					splittedLine = line.split(":");
					currentCode = Integer.parseInt(splittedLine[0]);
					errorsFromFile.put(currentCode, splittedLine[1]);
				}
			}
			buff.close();
		} catch (IOException e) {
			System.out.println("Error: " + e.toString());
		}
		return errorsFromFile;
	}

}
