package model;

import java.io.Serializable;
import java.util.ArrayList;

public class OptionSet implements Serializable{
	
	private static final long serialVersionUID = 5479541544562255725L;
	
	protected String optionSetName;
	protected ArrayList<Option> opt = new ArrayList<Option>();	// all options in a optionSet
	protected int optionSize;
	protected Option choice;

	// Constructors
	public OptionSet() { }
	protected OptionSet(String optionSetName, ArrayList<Option> opt) {
		this.optionSetName = optionSetName;
		this.opt = opt;
	}
	
	protected OptionSet(String optionSetName, int optionSize) {
		this.optionSetName = optionSetName;
		this.optionSize = optionSize;
	}

	protected OptionSet(String optionSetName, ArrayList<Option> opt, int optionSize) {
		this.optionSetName = optionSetName;
		this.opt = opt;
		this.optionSize = optionSize;
	}

	// getter, setter
	protected String getOptionSetName() {
		return optionSetName;
	}
	protected void setOptionSetName(String optionSetName) {
		this.optionSetName = optionSetName;
	}
	public ArrayList<Option> getOpt() {
		return opt;
	}
	protected void setOpt(ArrayList<Option> opt) {
		this.opt = opt;
	}
	
	// Set option at a specific index
	protected void setOption(int index, String optionName, double optionPrice){
        opt.set(index, new Option(optionName, optionPrice));
    }
    
	protected void setOption(String optionName, double optionPrice){
        opt.add(new Option(optionName, optionPrice));
    }
	// methods
	// Add an new option to opt arrayList
	protected void addNewOption(String optionName, double optionPrice) {
		opt.add(new Option(optionName, optionPrice));
	}
	
	// find an option return it's index by using optionName
	protected int findAnOption(String optionName) {
		boolean found = false;
		int i = 0;
		while (!found && i < opt.size()) {
			if (opt.get(i).getOptionName().equalsIgnoreCase(optionName)) {
				found = true;
			} else {
				i++;
			}
		}
		if (found) {
			return i;
		} else {
			return -1;
		}
	}
	
	protected void deleteAnOption(String optionName){
		opt.remove(findAnOption(optionName));
	}

	protected Option getOption(String optionName) {
		int index;
		if ((index = findAnOption(optionName)) != -1) {
			return opt.get(index);
		} else {
			return null;
		}
	}
    
	protected void printOptionSet() {
		System.out.printf("%s:\n", optionSetName);
		for (int i = 0; i < opt.size(); i++) {
			System.out.printf("\t%s: %.2f\n", opt.get(i).optionName, opt.get(i).optionPrice);
		}
	}
	
//	protected void setOptionChoice(String optionName, double optionPrice){
//		this.choice = new Option(optionName, optionPrice);	
//	}
	
		// Inner class Option
		public class Option implements Serializable{
	
		private static final long serialVersionUID = 6025922070024690225L;	
		protected String optionName;
		protected double optionPrice;
	
		// Constructors
		public Option(){ }
		
		public Option(String optionName, double optionPrice){ 
			this.optionName = optionName;
			this.optionPrice = optionPrice;
		}

		// Getter, setter
		public String getOptionName() {
			return optionName;
		}
		protected void setOptionName(String optionName) {
			this.optionName = optionName;
		}
		public double getOptionPrice() {
			return optionPrice;
		}
		protected void setOptionPrice(double optionPrice) {
			this.optionPrice = optionPrice;
		}
		public void printOption(){
			System.out.printf("\t%s: %.2f\n", optionName, optionPrice);
		}

	}
		protected String getChoiceName() {
			return choice.getOptionName();
		}

		protected double getChoicePrice() {
			return choice.getOptionPrice();
		}

		protected void setChoice(String optionName) {
			int optionIndex = findAnOption(optionName);
			choice = opt.get(optionIndex);
		}
}