package wcu.control;

import wcu.exceptions.InvalidInputException;
import wcu.exceptions.WeightException;
import wcu.functionality.OprControl;
import weightsimulator.launch.*;

public class WCUController {
	ConsoleController CC = new ConsoleController();
	public static OprControl oc = new OprControl();
	String user;
	String[] start;
	String weightChoice;
	int produkt=0;
	String mode;
	
	public void init() {
		runProcedure();
	}
	
	public void runProcedure() {
		try { chooseWeight();} catch (WeightException e) { e.printStackTrace();}
		try { verifyOperatoer();} catch (WeightException e) { e.printStackTrace();}
		try { verifyBatch();} catch (WeightException e) { e.printStackTrace();}
		// for antal raavare
		try { checkPreconditions();} catch (WeightException e) { e.printStackTrace();}
		try { taraPreconditions();} catch (WeightException e) { e.printStackTrace();}
		try { doWeighing();} catch (WeightException e) { e.printStackTrace();}
		// for slut
		endProduction();
		
	}
	
	public void chooseWeight() throws WeightException {
		CC.printMessage("Please enter W for normal weight or WS for simulator");
		weightChoice = CC.getUserInput();
		if(weightChoice == "WS"){
			mode = "Silmulator";
		}
		else if(weightChoice == "W"){
			mode = "Weight";
		}
		else {
			CC.printMessage("Invalid input please try again");
			chooseWeight();
		}
	}
	
	public void verifyOperatoer() throws WeightException {
		CC.printMessage("Indtast dit operatoer nummer: ");
		String input = CC.getUserInput();
		// godkend operatoer nummer
		// else: throw WeightExceptions
		user = "mads";
		// vis navn hvis godkendt
	}
	
	public void verifyBatch() throws WeightException {
		CC.printMessage("Indtast produktbatch nummer: ");
		String input = CC.getUserInput();	
		// godkend batch
		// else: throw WeightExceptions
		CC.printMessage("Du skal producerer receptet: "+ "Saltvand med citron");
	}
	
	public void checkPreconditions() throws WeightException {
		if(mode == "Weight"){
		CC.printMessage("Sikre dig at vægten er ubelastet, INDTAST 'OK' når dette er gjort");
		String input = CC.getUserInput();
		try {
			CC.controlOKMessage(input);
		} catch (InvalidInputException e) {
			CC.printMessage("Ukendt input");
			checkPreconditions();
		}
		}
		else{
			
		}
		// sæt produktbatch til under produktion
	}
	
	public void taraPreconditions() throws WeightException{
		if(mode == "Weight"){
		// tarer vægt
		CC.printMessage("Læg tarabeholderen på vægten, INDTAST 'OK' når dette er gjort");
		String input = CC.getUserInput();
		try {
			CC.controlOKMessage(input);
		} catch (InvalidInputException e) {
			CC.printMessage("Ukendt input");
			taraPreconditions();
		}
		}
		else {
			
		}
		// registrer tara værdi
		// tarer vægt
	}
	
	public void doWeighing() throws WeightException{
		if(mode == "Weight"){
		CC.printMessage("indtast produktbatch nummer på produkt "+ produkt);
		String produktbatch = CC.getUserInput();
		doWeighingControl();
		}
		else{
			
		}
	}
	public void doWeighingControl() {
		if(mode == "Weight"){
		CC.printMessage("Fuldfør afvejningen med den ønskede mængde, og indtast OK");
		String input = CC.getUserInput();
		try {
			CC.controlOKMessage(input);
		} catch (InvalidInputException e) {
			CC.printMessage("Ukendt input");
			doWeighingControl();
		}
		}
		else{
			
		}
	}
	public void endProduction() {
		// sæt produktbatchnummerets status til 'afsluttet'
	}
	
	
	
	
	

}
