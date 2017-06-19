package adapter;

public interface UpdateAuto {
	// only method names
	public void updateOptionSetName(String modelName, String optionSetName, String newOptionSetName);
	public void updateOptionPriceAndName(String modelName, String optionSetName, String optionName, String newOptionName, double newOptionPrice);
	
}
