package adapter;

public interface ChangeChoice {
	public void setDefaultOptionChoice(String modelName);
	public void printChoice(String modelName);
	public void setAnOptionChoice(String modelName, String optionSetName, String optionName, double optionPrice);
	public void printTotalPrice(String modelName);
}
