package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import model.Automobile;
import model.OptionSet;

public class SelectCarOption implements configChoices {
	private ArrayList<String> models = null;
	
	public SelectCarOption(){ }
	
	// Prompts the user for available models.
	public void printModels(ObjectInputStream ois){
		try {
			// Get all modelNames
			this.models = (ArrayList<String>) ois.readObject();
			System.out.println("Models in server: ");
			System.out.println("----------------------------------------------------------------");
			for (int index = 0; index < models.size(); index++) {
				System.out.printf("%2d. %s\n", index + 1, models.get(index));
			}
			System.out.println("----------------------------------------------------------------");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
		
	// Prompt user to choose a model
	public void chooseAModel(BufferedReader buff, ObjectOutputStream outStream){
		try {
			int modelchoice = 0;
			do {
				System.out.println("Choose a model to configure: ");
				modelchoice = Integer.parseInt(buff.readLine());
				// System.out.println("You chose: " + conf);
			} while (modelchoice <= 0 || modelchoice - 1 >= models.size());
			// write modelName to server
			outStream.writeObject(models.get(modelchoice - 1)); 
			outStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void chooseOptions(Automobile auto){
		BufferedReader cons = new BufferedReader(new InputStreamReader(System.in));
		ArrayList<String> optionSetNames = null;
		ArrayList<String> optionNames = null;
		ArrayList<Double> optionPrices = null;
		boolean done = false;
		int optionSetSelection;
		int optionSelection;
		int index;

		while (!done) {
			try {
				System.out.println("----------------------------------------------------------------");
				System.out.println("Option Set:");
				// Print all optionSetNames
				optionSetNames = auto.getOptionSetNames();
				for (index = 1; index <= optionSetNames.size(); index++) {
					System.out.printf("%2d. %s\n", index, optionSetNames.get(index - 1));
				}
				System.out.println(" 0. Done Configure");
				System.out.println("----------------------------------------------------------------");
				
				// Prompt the user to choose an optionSet
				do{
					System.out.println("Choose an option set: ");
					optionSetSelection = Integer.parseInt(cons.readLine());
					//System.out.println("You choose option set: " + opsetSelection);
				}while(optionSetSelection < 0 || optionSetSelection-1 >= optionSetNames.size());

				if (optionSetSelection == 0) {
					done = true;
					continue;
				}

				// Print the available options and price in a selected optionSet
				System.out.println("----------------------------------------------------------------");
				optionNames = auto.getOptionNames(optionSetSelection - 1);
				optionPrices = auto.getOptionPrices(optionSetSelection -1);
				System.out.printf("There are %d options for: %s\n", optionNames.size(), optionSetNames.get(optionSetSelection-1));
				for (index = 1; index <= optionNames.size(); index++) {
					System.out.printf("%2d. %s: $%.2f\n", index, optionNames.get(index-1), optionPrices.get(index-1) );
				}
				System.out.println("----------------------------------------------------------------");
				
				// Prompt the user to Choose an option
				do{
					System.out.println("Choose an option: ");
					optionSelection = Integer.parseInt(cons.readLine());
					//System.out.println("You choose option: " + optionSelection);
				}while(optionSelection <= 0 || optionSelection-1 >= optionNames.size());

				// Set choice into the auto object
				OptionSet optionSetTemp = new OptionSet();
				OptionSet.Option optionTemp = optionSetTemp.new Option();
				optionTemp = auto.findOptionInOptionSet(optionSetNames.get(optionSetSelection-1), optionNames.get(optionSelection-1));
				
				auto.setOptionChoiceInAuto(optionSetNames.get(optionSetSelection-1), optionTemp.getOptionName(), optionTemp.getOptionPrice());
				
			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// print configured auto
	public synchronized void printConfiguredAuto(Automobile auto){
		double totalPrice = 0;
		System.out.println("----------------------------------------------------------------");
		System.out.printf("The new configured %s...\n", auto.getModelName());
		System.out.printf("Base price: $%.2f\n\n", auto.getBaseprice());
		
		auto.printChoice();
		totalPrice = auto.getTotalPrice();
		
		System.out.println("----------------------------------------------------------------");
		System.out.printf("\t\t\t\tTotal price: $%.2f\n", totalPrice);
	}
	
}
