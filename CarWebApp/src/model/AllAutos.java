package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class AllAutos {
	// key=modelName, value=auto
	protected LinkedHashMap<String, Automobile> autos = new LinkedHashMap<String, Automobile>();
	
	// Constructors
	public AllAutos(){}
	public AllAutos(LinkedHashMap<String,Automobile> autos){
		this.autos = autos;
	}
	
	// Getter, setter
	public LinkedHashMap<String, Automobile> getAutos() {
		return autos;
	}
	public void setAutos(LinkedHashMap<String, Automobile> autos) {
		this.autos = autos;
	}
	
	// Methods
	public void addNewAuto(Automobile auto){
		if(auto != null){
			this.autos.put(auto.getModelName(), auto);
		}
	}
	// delete an auto in autos by object
	public void deleteAuto(Automobile auto){
		if(auto != null && this.autos.containsValue(auto)){
			deleteAuto(auto.getModelName());
		}
	}
	// delete an auto in autos by modelName
	public void deleteAuto(String modelName){
		if(modelName != null){
			this.autos.remove(modelName);
		}
	}
	
	public Automobile findAnAuto(String modelName){
		//Automobile tempAuto = new Automobile();
		Automobile genCar = new Automobile();
		if(modelName != null){
			genCar = this.autos.get(modelName);
		}
		return genCar;
	}
	
	public ArrayList<String> getAutosKeys(){
		ArrayList<String> autosKeys = new ArrayList<String>(this.autos.keySet());
		return autosKeys;
	}

	public void printAllAuto() {
		// Get a set of keys and use the keys to find and print all autos
		ArrayList<String> autosKeys = new ArrayList<String>(this.autos.keySet());
		for (int i = 0; i < autosKeys.size(); i++) {
			findAnAuto(autosKeys.get(i)).print();
			System.out.println();
			// System.out.println("autosKeys " + i + ": " + autosKeys.get(i));
		}
	}

	
	
}
