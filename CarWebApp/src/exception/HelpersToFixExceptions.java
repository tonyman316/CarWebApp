package exception;

import adapter.ProxyAutomobile;

public class HelpersToFixExceptions {
	private static String defaultFileName = "/Users/tonyman/Documents/Projects/Java/CarWebApp/src/util/FordFocusWagonZTW.txt";
	private static double defaultBaseprice = 20000;
	public HelpersToFixExceptions(){ }

	public void fixIt(int errorno){
		switch(errorno){
		
		case 1: fixFileName();
			break;
		case 2: fixMissingPriceInTextFile();
			break;
		case 3: fixMissingOptionSetData();
			break;
		case 4: fixMissingOptionData();
			break;
		default: 
			return;
		}
	}
	
	private void fixFileName(){
		ProxyAutomobile.setDefaultFileName(defaultFileName);
		System.out.printf("Using default fileName: FordFocusWagonZTW.txt\n");
	}
	
	private void fixMissingPriceInTextFile(){	// need fix
		ProxyAutomobile.setDefaultBaseprice(defaultBaseprice);
		System.out.printf("Using default baseprice: 20000\n");
	}
	
	private void fixMissingOptionSetData(){
		// To do
	}
	
	private void fixMissingOptionData(){
		// To do
	}
}
