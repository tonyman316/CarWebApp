package client;

import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import model.Automobile;

public interface configChoices {

	public void printModels(ObjectInputStream ois);
	public void chooseAModel(BufferedReader buff, ObjectOutputStream outStream);
	public void chooseOptions(Automobile auto);
	public void printConfiguredAuto(Automobile auto);

}
