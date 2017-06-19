package exception;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import util.FileIO;

// custom exception
// reference fix problems

public class AutoException extends Exception {
	
	private static final long serialVersionUID = 7641024835382375692L;
	
	private String message;
	private int errorno;
	private HelpersToFixExceptions fixHelpers;
	private HashMap<Integer, String> errornoAndMessage;
	
	public AutoException(){ 
		super();
	}
	
	public AutoException(int errorno, String message){
		super(message);
		this.errorno = errorno;
		this.message = message;
		loadErrorCodesFromFile();
	}
	
	public AutoException(String message){
		super(message);
		this.message = message;
	}
	
	public AutoException(int errorno){
		this(errorno,"");
		
		if (errornoAndMessage.containsKey(errorno)) {
			setMessage(errornoAndMessage.get(errorno));
		}
		fixHelpers = new HelpersToFixExceptions();
	}
	
	// Getter, Setter
	public int getErrorno() {
		return errorno;
	}

	public void setErrorno(int errorno) {
		this.errorno = errorno;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	// Methods
	private void loadErrorCodesFromFile() {
		FileIO fileIO = new FileIO();
		errornoAndMessage = fileIO.loadErrorCodesFromFile();
		//System.out.println(errornoAndMessage);
	}
	
	public void writeExceptionToFile() {
		try {
        	String timestamp = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss z").format(Calendar.getInstance().getTime());
			String data = " - Errorno: " + this.errorno + ", Message: " + this.message + "\n";
			File file = new File("autoExceptionsLog.txt");

			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fileWritter = new FileWriter(file.getName(), true); 	 // true: append
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(timestamp);
			bufferWritter.write(data);
			bufferWritter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void fixAProblem(int errorno){
		this.fixHelpers.fixIt(errorno);
		System.out.println("Called fixAProblem");

	}
	
	public void print() {
		System.out.printf("Exception Caught! Errorno: [%d] Message: %s\n", errorno, message);
	}
	

}
