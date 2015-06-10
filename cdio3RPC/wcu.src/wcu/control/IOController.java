package wcu.control;

import java.io.IOException;

import wcu.boundary.TUI;

public class IOController {
	TUI TUI = new TUI();

	public String getStringInput() {
		String n = "";
		try {
			n = TUI.getResponse();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return n;
	}

	public int getIntInput() throws IOException {
		int n = 0;
		try {
			n = TUI.getIntResponse();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return n;
	}
	
	public void printMessage(String message) {
		TUI.printMessage(message);
	}

	/**
	 * her foregår udprintningen af menudelene
	 * variablen menu_point styrer hvor i menuen klienten er. Hvis der er sket en fejl indtastning, eller et input vi ikke vil acceptere, skal den samme menu_point køres igen, 
	 * 		evt. også en fejlbesked, brug her metoden printmessage()
	 * kontrollen af, hvor i menuen vi er, sker altså ikke her
	 * 
	 * skal der bruges et input efter et menupunkt, brug da getStringInput eller getIntInput efter type input ønsket, kontroller dette input, - ikke et brugbar input, kør samme menu_point 
	 * under menupunkt 3 skal der udskrives navnet på vareren, sæt dette navn ind i variablen 'item', når de andre menupunkter køres, er det ligegyldigt hvad denne variablel er.
	 * 
	 */

	public void printMenu(int menu_point, String item) {
		switch(menu_point) {
		case 1: TUI.printMessage("Enter your username");
		break;
		case 2: TUI.printMessage("Enter item number");
		break;
		case 3: TUI.printMessage("The item number represents: "+item);
		break;
		// verificering er igennem et password?
		case 4: TUI.printMessage("Enter your password");
		break;
		case 5: TUI.printMessage("Tara the weight");
		break;
		case 6: TUI.printMessage("Put the item on the weight");
		break;
		case 7: TUI.printMessage("Enter the net weight of the item");
		break;
		case 8: TUI.printMessage("Please do gross control");
		break;
		case 9: TUI.printMessage("Enter the used item, and introduce the weighing in the log");
		break;
		}

	}


}
