package scale;

import exception.AutoException;
import model.Automobile;
import model.OptionSet;

// A helper class for EditOptions's ops methods
public class EditOptionsInThread {
	
	// update optionSetName with synchronized method. (enabled multithreading)
	public synchronized void op1(Automobile auto, String [] input){
		auto.updateAnOptionSet(auto.getModelName(), input[0], input[1]);
	}
	
	// update an option (name and price) with synchronized block. (enabled multithreading)
	public void op2(Automobile auto, String [] input){
		OptionSet optionSet = new OptionSet();
		OptionSet.Option option = optionSet.new Option(input[2], Double.parseDouble(input[3]));
		// synchronized block to lock an object --> option
		synchronized(option){
			auto.updateAnOptionInAnOptionSet(auto.getModelName(), input[0], input[1], option.getOptionName(), option.getOptionPrice());
		}
		//auto.updateAnOptionInAnOptionSet(auto.getModelName(), input[0], input[1], input[2], Double.parseDouble(input[3]));
	}
	
	// update basePrice without synchronized. (single thread)
	public void op3(Automobile auto, String [] input) throws NumberFormatException, AutoException{
		auto.setBaseprice(Double.parseDouble(input[0]));
	}
	
	// update an option price without synchronized. (single thread)
	public void op4(Automobile auto, String [] input){
		auto.updateAnOptionPriceInAnOptionSet(auto.getModelName(), input[0], input[1], Double.parseDouble(input[2]));
	}
}
