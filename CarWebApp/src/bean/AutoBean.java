package bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import model.Automobile;
import model.OptionSet;
import model.OptionSet.Option;

public class AutoBean {
	private String model;
	private double basePrice;
	private Map<String, Double> color;
	private Map<String, Double> transmission;
	private Map<String, Double> abs;
	private Map<String, Double> bags;
	private Map<String, Double> moonroof;
	
	public String getModel() {
		return this.model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public double getBasePrice() {
		return this.basePrice;
	}
	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}
	public Map<String, Double> getColor() {
		return this.color;
	}
	public void setColor(Map<String, Double> color) {
		this.color = color;
	}
	public Map<String, Double> getTransmission() {
		return this.transmission;
	}
	public void setTransmission(Map<String, Double> transmission) {
		this.transmission = transmission;
	}
	public Map<String, Double> getAbs() {
		return this.abs;
	}
	public void setAbs(Map<String, Double> abs) {
		this.abs = abs;
	}
	public Map<String, Double> getBags() {
		return this.bags;
	}
	public void setBags(Map<String, Double> bags) {
		this.bags = bags;
	}
	public Map<String, Double> getMoonroof() {
		return this.moonroof;
	}
	public void setMoonroof(Map<String, Double> moonroof) {
		this.moonroof = moonroof;
	}

	public String toString() {
		return "CarBean [color=" + this.color + ", transmission=" + this.transmission + ", abs=" + this.abs + ", bags="
				+ this.bags + ", moonroof=" + this.moonroof + "]";
	}

	public void setWithAutomobile(Automobile auto) {
		model = auto.getModelName();
		basePrice = auto.getBaseprice();
		color = new LinkedHashMap<String, Double>();
		OptionSet colorOptionSet = auto.findAnOptionSet("Color");
		ArrayList<Option> colorOptions = colorOptionSet.getOpt();
		for (Option opt : colorOptions) {
			color.put(opt.getOptionName(), opt.getOptionPrice());
		}

		transmission = new LinkedHashMap<String, Double>();
		OptionSet transmissionOptionSet = auto.findAnOptionSet("Transmission");
		ArrayList<Option> transmissionOptions = transmissionOptionSet.getOpt();
		for (Option opt : transmissionOptions) {
			transmission.put(opt.getOptionName(), opt.getOptionPrice());
		}

		abs = new LinkedHashMap<String, Double>();
		OptionSet absOptionSet = auto.findAnOptionSet("Brakes/Traction Control");
		ArrayList<Option> absOptions = absOptionSet.getOpt();
		for (Option opt : absOptions) {
			abs.put(opt.getOptionName(), opt.getOptionPrice());
		}

		bags = new LinkedHashMap<String, Double>();
		OptionSet bagOptionSet = auto.findAnOptionSet("Side Impact Air Bags");
		ArrayList<Option> bagOptions = bagOptionSet.getOpt();
		for (Option opt : bagOptions) {
			bags.put(opt.getOptionName(), opt.getOptionPrice());
		}

		moonroof = new LinkedHashMap<String, Double>();
		OptionSet moonroofOptionSet = auto.findAnOptionSet("Power Moonroof");
		ArrayList<Option> moonroofOptions = moonroofOptionSet.getOpt();
		for (Option opt : moonroofOptions) {
			moonroof.put(opt.getOptionName(), opt.getOptionPrice());
		}
	}

	public String getSerializedModel() {
		String ret = "model~" + this.model;
		ret = ret + ";";
		return ret;
	}

	public String getSerializedPrice() {
		String ret = "basicPrice~" + this.basePrice;
		ret = ret + ";";
		return ret;
	}

	public String getSerializedColor() {
		String ret = "color~";
		Iterator itr = this.color.entrySet().iterator();
		while (itr.hasNext()) {
			ret = ret + itr.next() + "%";
		}
		ret = ret + ";";
		return ret;
	}

	public String getSerializedTransmission() {
		String ret = "transmission~";
		Iterator itr = this.transmission.entrySet().iterator();
		while (itr.hasNext()) {
			ret = ret + itr.next() + "%";
		}
		ret = ret + ";";
		return ret;
	}

	public String getSerializedABS() {
		String ret = "abs~";
		Iterator itr = this.abs.entrySet().iterator();
		while (itr.hasNext()) {
			ret = ret + itr.next() + "%";
		}
		ret = ret + ";";
		System.out.println("getSerializedABS:" + ret);
		return ret;
	}

	public String getSerializedBags() {
		String ret = "bags~";
		Iterator itr = this.bags.entrySet().iterator();
		while (itr.hasNext()) {
			ret = ret + itr.next() + "%";
		}
		ret = ret + ";";
		return ret;
	}

	public String getSerializedMoonroof() {
		String ret = "moonroof~";
		Iterator itr = this.moonroof.entrySet().iterator();
		while (itr.hasNext()) {
			ret = ret + itr.next() + "%";
		}
		ret = ret + ";";
		return ret;
	}
}
