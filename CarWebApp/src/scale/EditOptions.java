package scale;

import adapter.ProxyAutomobile;
import exception.AutoException;
import model.Automobile;

public class EditOptions extends ProxyAutomobile implements Runnable {

	private Thread thread;
	private int opsId;
	private String input[];
	private String modelName;
	
	public EditOptions(){}
	public EditOptions(int opsId, String [] input, String modelName){
		this.thread = new Thread(this);
		this.opsId = opsId;
		this.input = input;
		this.modelName = modelName;
		this.thread.start();	// call run()
	}
	
	@Override
	public void run() {
		EditOptionsInThread editOIT = new EditOptionsInThread();
		Automobile auto = ProxyAutomobile.getAllAutos().findAnAuto(this.modelName);
		switch(opsId){
			case 1: editOIT.op1(auto, this.input); break;
			case 2: editOIT.op2(auto, this.input); break;
			case 3: try {
				editOIT.op3(auto, this.input);
			} catch (NumberFormatException | AutoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} break;
			case 4: editOIT.op4(auto, this.input); break;
		}
	}	
	
}


/*	
	private int _randomSleep(int min, int max) {
		Random rn = new Random();
		int randint = rn.nextInt((max - min) + 1) + min;
		return randint;
	} 
	
	int rsleep = _randomSleep(5000,10000);
	System.out.printf("\nSleeping for %dms, on model %s\n", rsleep, this.modelName);
	
	try { 
		Thread.sleep(rsleep); 
	}
	catch (InterruptedException e) { 
		System.out.printf("Caught an InterruptedException! %s\n", e.toString()); 
	}
*/
	
