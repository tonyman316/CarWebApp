package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import exception.AutoException;
import model.OptionSet.Option;

// templates
public class Automobile implements Serializable{

	private static final long serialVersionUID = 5049172650022164136L;
	
	private String modelName;
	private double baseprice;
	private ArrayList<OptionSet> optionSet = new ArrayList<OptionSet>();
	//private ArrayList<Option> choice = new ArrayList<Option>(); // A list of choice: black, automatic...
	//Use LHM <optionSetName, Option>
	private LinkedHashMap<String, Option> choice = new LinkedHashMap<String, Option>();

	// Constructors
	public Automobile() { }
	public Automobile(String name){
		this.modelName = name;
		this.baseprice = 0;
	}
	public Automobile(double baseprice){
		this.modelName = "NONE";
		this.baseprice = baseprice;
	}
	public Automobile(String name, double baseprice) {
		this.modelName = name;
		this.baseprice = baseprice;
	}

	// Getter, setter
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String name) {
		this.modelName = name;
	}
	public double getBaseprice() {
		return baseprice;
	}
	public void setBaseprice(double baseprice) throws AutoException{
		if(baseprice == -1){
			throw new AutoException(2);
		}else{
			this.baseprice = baseprice;
		}
	}
	public ArrayList<OptionSet> getOptionSet() {
		return optionSet;
	}
	public void setOptionSet(ArrayList<OptionSet> optionSet) {
		this.optionSet = optionSet;
	}
	public ArrayList<String> getOptionSetNames(){
		ArrayList<String> optionSetNames = new ArrayList<String>();
		for (int i = 0; i < this.optionSet.size(); i++) {
			optionSetNames.add(this.optionSet.get(i).getOptionSetName());
		}
		return optionSetNames;
	}
	public ArrayList<String> getOptionNames(int optionSetIndex){
		int optionIndex = this.optionSet.get(optionSetIndex).getOpt().size();
		ArrayList<String> optionNames = new ArrayList<String>();
		for (int i = 0; i < optionIndex; i++) {
			optionNames.add(this.optionSet.get(optionSetIndex).getOpt().get(i).getOptionName());
		}
		return optionNames;
	}
	public ArrayList<Double> getOptionPrices(int optionSetIndex){
		int optionIndex = this.optionSet.get(optionSetIndex).getOpt().size();
		ArrayList<Double> optionPrices = new ArrayList<Double>();
		for (int i = 0; i < optionIndex; i++) {
			optionPrices.add(this.optionSet.get(optionSetIndex).getOpt().get(i).getOptionPrice());
		}
		return optionPrices;
	} 
	
	public void setOptionChoice(String optionSetName, String optionName) {
		findAnOptionSet(optionSetName).setChoice(optionName);
	}
	
	public String getOptionChoice(String optionSetName) { 
		return findAnOptionSet(optionSetName).getChoiceName();
	}

	// Methods : 20
	// Add an optionSet to the optionSet arrayList
	public void addOptionSet(String name, int optionSize) {
		this.optionSet.add(new OptionSet(name, optionSize));
	}

	// Add an option to a corresponding optionSet
	public void addOptionToSet(String optSetName, String OptionName, double OptionPrice) {
		findAnOptionSet(optSetName).addNewOption(OptionName, OptionPrice);
	}
	
	public void addOptionToSet(int index, String OptionName, double OptionPrice) {
		findAnOptionSet(index).addNewOption(OptionName, OptionPrice);
	}
	
	// return a corresponding optionSet using optionSetName to find it
	public OptionSet findAnOptionSet(String optionSetName) {
		boolean found = false;
		int i = 0;
		while (!found && i < optionSet.size()) {
			if (optionSet.get(i).getOptionSetName().equalsIgnoreCase(optionSetName)) {
				found = true;
			} else {
				i++;
			}
		}
		if (found) {
			return optionSet.get(i);
		} else {
			return null;
		}
	}
	
	// Get OptionSet (by index value)
	public OptionSet findAnOptionSet(int i) {
		try {
			return optionSet.get(i);

		} catch (IndexOutOfBoundsException e) {
			System.out.printf("Error: %s", e);
			return null;
		}
	}
	
	public int findAnOptionSetIndex(String optionSetName){
		int index=0;
		boolean found = false;
		int i = 0;
		while (!found && i < optionSet.size()) {
			if(optionSet.get(i).getOptionSetName().equalsIgnoreCase(optionSetName)) {
//				System.out.println("in optionSet: " + optionSet.get(i).getOptionSetName());
//				System.out.println("input: " + optionSetName);
				found = true;
				index = i;
			} else {
				i++;
			}
		}
		if (found) {
			return index;
		} else {
			return -1;
		}
	}
	
	//Find Option with name (in context of OptionSet)	
	public OptionSet.Option findOptionInOptionSet(String optionSetName, String optionName) {
		try {
			return findAnOptionSet(optionSetName).getOption(optionName);

		} catch (NullPointerException e) {
			System.out.printf("Error %s\n", e);
			//System.out.printf("Unable to read option from %s Option Set\n", optionSetName);
			return null;
		}
	}
	
	// delete an optionSet by optionSetName
	public void deleteAnOptionSet(String optionSetName){
		optionSet.remove(findAnOptionSetIndex(optionSetName));
	}
	
	// delete an optionSet by index
	public void deleteAnOptionSet(int index){
		optionSet.remove(index);
	}
	
	// delete an option in an optionSet by optionName
	public void deleteAnOptionInOptionSet(String optionSetName, String optionName){
		findAnOptionSet(optionSetName).deleteAnOption(optionName);
	}
	
	public void deleteAnOptionInOptionSet(int index, String optionName){
		findAnOptionSet(index).deleteAnOption(optionName);
	}
	
	public void deleteAllOptionSets() {
		optionSet.clear();
	}
	
	public void deleteAllOptionsInASet(String optionSetName){
		findAnOptionSet(optionSetName).getOpt().clear();
	}
	
	public void deleteAllOptionsInASet(int index){
		findAnOptionSet(index).getOpt().clear();
	}
	
	// update an optionSet, by optionSetName
	public void updateAnOptionSet(String modelName, String optionSetName, String newOptionSetName){
		findAnOptionSet(optionSetName).setOptionSetName(newOptionSetName);		
	}
	
	// update an optionSet, by optionSet index
	public void updateAnOptionSet(String modelName, int index, String newOptionSetName){
		findAnOptionSet(index).setOptionSetName(newOptionSetName);		
	}
	
	// update an option in an optionSet
	public void updateAnOptionInAnOptionSet(String modelName, String optionSetName, String optionName, String newOptionName, double newOptionPrice) {
		try {
			int optionIndex = findAnOptionSet(optionSetName).findAnOption(optionName);	// find option index
			findAnOptionSet(optionSetName).setOption(optionIndex, newOptionName, newOptionPrice);	// update option at that index
		} catch (NullPointerException e) {
			System.out.printf("Error: %s\n", e);
			System.out.printf("Unable to find Option Set %s to update\n", optionSetName);
		}
	}
	
	// update an optionName in an optionSet
	public void updateAnOptionNameInAnOptionSet(String modelName, String optSetName, String optionName, String newOptionName) {
		try {
			int optionIndex = findAnOptionSet(optSetName).findAnOption(optionName);	// find option index
			double optionOldPrice = findAnOptionSet(optSetName).getOption(optionName).getOptionPrice();
			findAnOptionSet(optSetName).setOption(optionIndex, newOptionName, optionOldPrice);	// update option at that index
		} catch (NullPointerException e) {
			System.out.printf("Error: %s\n", e);
			System.out.printf("Unable to find Option Set %s to update\n", optSetName);
		}
	}
	
	// update an optionPrice in an optionSet
	public void updateAnOptionPriceInAnOptionSet(String modelName, String optSetName, String optionName, double newOptionPrice) {
		try {
			int optionIndex = findAnOptionSet(optSetName).findAnOption(optionName);	// find option index
			findAnOptionSet(optSetName).setOption(optionIndex, optionName, newOptionPrice);	// update option at that index
		} catch (NullPointerException e) {
			System.out.printf("Error: %s\n", e);
			System.out.printf("Unable to find Option Set %s to update\n", optSetName);
		}
	}
	
	public void setDefaultChoiceForAnOptionSet(){
		// set default key, values for choice LHM
		this.choice.put("Color", optionSet.get(0).getOpt().get(0));
		this.choice.put("Transmission", optionSet.get(1).getOpt().get(0));
		this.choice.put("BrakesTractionControl", optionSet.get(2).getOpt().get(0));
		this.choice.put("SideImpactAirBags", optionSet.get(3).getOpt().get(0));
		this.choice.put("PowerMoonroof", optionSet.get(4).getOpt().get(0));

		for (int i = 0; i < optionSet.size(); i++) {
			 optionSet.get(i).setChoice(choice.get(i).getOptionName()); // set each optionSet's choice in optionSet class
		}
	}
	public void printChoice(){	// print the choice in Automobile
		ArrayList<String> choiceKeys = new ArrayList<String>(this.choice.keySet());
		for(int i=0; i<choiceKeys.size(); i++){
			System.out.printf("%s: \t\t", choiceKeys.get(i));
			choice.get(choiceKeys.get(i)).printOption();
		}
	}
	
	public void printChoiceFromOptionSet(){
		for(int i=0; i<this.optionSet.size(); i++){
			System.out.println(optionSet.get(i).getChoiceName());
		}	
	}
	
	// getOptionChoice("transmission") would return "standard" 
	public String getOptionChoiceName(String optionSetName){
		return choice.get(optionSetName).getOptionName();
	}
	
	// getOptionChoicePrice("transmission") would return ­815.
	public double getOptionChoicePrice(String optionSetName){
		return choice.get(optionSetName).getOptionPrice();
	}
	
	// The method setOptionChoice() is for choosing a particular option in an option set.
	public void setOptionChoiceInAuto(String optionSetName, String optionName, double optionPrice){
		OptionSet optionSetTemp = new OptionSet();
		OptionSet.Option optionTemp = optionSetTemp.new Option(optionName, optionPrice);
		//optionTemp.printOption();
		this.choice.put(optionSetName, optionTemp);

		// set choice in optionSet class
		setOptionChoice2(optionSetName, optionName);	
	}
	
	public double getTotalPrice(){
		double totalPrice = this.baseprice;
		ArrayList<String> choiceKeys = new ArrayList<String>(this.choice.keySet());
		for(int i=0; i<choiceKeys.size(); i++){
			totalPrice += choice.get(choiceKeys.get(i)).getOptionPrice();
			//System.out.printf("\n%.2f:", totalPrice);
		}
		return totalPrice;
	}
	public double getTotalPrice2(){
		double totalPrice = this.baseprice;
		for (int i = 0; i < optionSet.size(); i++) {
			totalPrice += optionSet.get(i).getChoicePrice();
		}
		return totalPrice;
	}
	
	//For choice in OptionSet class
	public String getOptionChoiceName2(String OptionSetName) {
		int OptionSetIndex = findAnOptionSetIndex(OptionSetName);
		return optionSet.get(OptionSetIndex).getChoiceName();
	}

	public double getOptionChoicePrice2(String setName) {
		int OptionSetIndex = findAnOptionSetIndex(setName);
		return optionSet.get(OptionSetIndex).getChoicePrice();
	}

	public void setOptionChoice2(String OptionSetName, String optionName) {
		int OptionSetIndex = findAnOptionSetIndex(OptionSetName);
		optionSet.get(OptionSetIndex).setChoice(optionName);
	}
	//For choice in OptionSet class

	public void print() {
		System.out.printf("Model: %s", this.modelName);
		System.out.printf("\nBaseprice: %s\n", this.baseprice);
		for (int i = 0; i < optionSet.size(); i++) {
			optionSet.get(i).printOptionSet();
		}
	}

}
