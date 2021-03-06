package wcu.control;

import java.io.IOException;

import wcu.boundary.TUI;
import wcu.exceptions.InvalidInputException;

//Tager imod besked fra bruger og laver fejlsøgning.
public class ConsoleController {
	TUI tui = new TUI();
	
	public String getUserInput() {
		String n = "";
		try {
			n = tui.getResponse();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (n.equals("")) {
			return getUserInput();
		} else {
			return n;
		}
	}
	public void printMessage(String message){
		tui.printMessage(message);
	}
	
	public void controlOKMessage(String message) throws InvalidInputException{
		if (!message.equals("OK")){
			throw new InvalidInputException();
		}
	}

}
