package adapter;
import model.AllAutos;
import model.Automobile;
import scale.EditOptions;
import server.BuildCarModelOptions;
import util.FileIO;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Properties;

import exception.AutoException;
import exception.HelpersToFixExceptions;

public abstract class ProxyAutomobile {
	
	// static, shared between files.
	private static Automobile automobile;
	private static String defaultFileName;
	private static double defaultBaseprice;
	private FileIO fileIO = new FileIO();
	private static AllAutos allAutos = new AllAutos();
	private BuildCarModelOptions bcmo = new BuildCarModelOptions();
	
	// getter, setter
	public static Automobile getAutomobile() {
		return automobile;
	}
	public static String getDefaultFileName() {
		return defaultFileName;
	}
	public static void setDefaultFileName(String defaultFileName) {
		ProxyAutomobile.defaultFileName = defaultFileName;
	}
	public static double getDefaultBaseprice() {
		return defaultBaseprice;
	}
	public static void setDefaultBaseprice(double defaultBaseprice) {
		ProxyAutomobile.defaultBaseprice = defaultBaseprice;
	}
	public static AllAutos getAllAutos() {
		return allAutos;
	}
	
	// All methods implementation: 5
	// create
	public void buildAuto(String fileName, int fileType) {
		switch (fileType) {
		case 0:
			buildAutoFromTextFile(fileName);
			break;
		case 1:
			Properties props = null;
			try {
				props = fileIO.readPropertiesFile(fileName);
			} catch (AutoException | IOException e) {
				e.printStackTrace();
			}
			buildAutoFromPropertiesFile(props);
			break;
		default:
			System.out.println("Wrong file type!");
			break;
		}
	}
		
	public void buildAutoFromTextFile(String fileName){		
		try {			
			fileIO.setFileName(fileName);
			automobile = fileIO.readTextFile(fileName);		// use throws in FileIO method
		} catch (AutoException ae) {
			ae.print();
			ae.writeExceptionToFile();
			System.out.println("Calling fixAProblem");
			ae.fixAProblem(ae.getErrorno());
			System.out.println("After fix");
			
			// Nested try and catch
			try{
				System.out.println("Try again after fixing fileName\n");
				fileIO.setFileName(defaultFileName);
				automobile = fileIO.readTextFile(fileName);	
			}catch(AutoException ae2){
				ae2.print();
				ae2.writeExceptionToFile();
				ae2.fixAProblem(ae2.getErrorno());
			}
		}
		ProxyAutomobile.allAutos.addNewAuto(automobile);
	}
	
	// For CreateAuto interface
	public void buildAutoFromPropertiesFile(Properties props){
		this.bcmo.createAutoWithPropFromClientSocket(props);
		this.bcmo.addAutoToLHM();
	}

	// print
	public void printAnAuto(String modelName){
		allAutos.findAnAuto(modelName).print();		
	}
	public void printAllAutos(){
		allAutos.printAllAuto();
	}
	
	// update
	public void updateOptionSetName(String modelName, String optionSetName, String newOptionSetName){
		allAutos.findAnAuto(modelName).updateAnOptionSet(modelName, optionSetName, newOptionSetName);
		//automobile.updateAnOptionSet(automobile.getModelName(), optionSetName, newOptionSetName);
	}
	
	public void updateOptionPriceAndName(String modelName, String optionSetName, String optionName, String newOptionName, double newOptionPrice){
		allAutos.findAnAuto(modelName).updateAnOptionInAnOptionSet(modelName, optionSetName, optionName, newOptionName, newOptionPrice);
		//automobile.updateAnOptionInAnOptionSet(automobile.getModelName(),optionSetName, optionName, newOptionName, newOptionPrice);
	}
	
	// Change Choice
	public void setDefaultOptionChoice(String modelName){
		allAutos.findAnAuto(modelName).setDefaultChoiceForAnOptionSet();
	}
		
	public void printChoice(String modelName){
		System.out.println("modelName: "+ modelName);
		allAutos.findAnAuto(modelName).printChoice();
	}
	
	public void setAnOptionChoice(String modelName, String optionSetName, String optionName, double optionPrice){
		allAutos.findAnAuto(modelName).setOptionChoiceInAuto(optionSetName, optionName, optionPrice);
	}
	
	public void printTotalPrice(String modelName){
		System.out.printf("\nTotal price: $%.2f", allAutos.findAnAuto(modelName).getTotalPrice());
	}
	
	public void fixAuto(int errorno){
		HelpersToFixExceptions helper = new HelpersToFixExceptions();
		helper.fixIt(errorno);
	}
	
	// MultiThreading part (lab4)
	public void Edit(String modelName, int opsId, String [] input, boolean sync){
		EditOptions editOptions = new EditOptions(opsId, input, modelName);
	}
	
	// Auto Server interface: 5
	public ArrayList<String> getAllModels(){
		return this.bcmo.getAllModels();
	}
	public void listOfModels(){
		this.bcmo.listOfModels();
	}
	public void createAutoWithPropFromClientSocket(Properties props){
		this.bcmo.createAutoWithPropFromClientSocket(props);
	}
	public void addAutoToLHM(){
		this.bcmo.addAutoToLHM();
	}
	public void sendAuto(ObjectOutputStream oos, String modelName) {
		this.bcmo.sendAuto(oos, modelName);
	}
	// Auto Server interface: 5

	
}
