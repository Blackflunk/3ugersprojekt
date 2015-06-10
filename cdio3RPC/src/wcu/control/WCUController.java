package wcu.control;

import wcu.exceptions.InvalidInputException;
import wcu.exceptions.WeightException;

public class WCUController {
	ConsoleController CC = new ConsoleController();
	String user;
	int produkt=0;
	
	public void init() {
		runProcedure();
	}
	
	public void runProcedure() {
		try { verifyOperatoer();} catch (WeightException e) { e.printStackTrace();}
		try { verifyBatch();} catch (WeightException e) { e.printStackTrace();}
		// for antal raavare
		try { checkPreconditions();} catch (WeightException e) { e.printStackTrace();}
		try { taraPreconditions();} catch (WeightException e) { e.printStackTrace();}
		try { doWeighing();} catch (WeightException e) { e.printStackTrace();}
		// for slut
		endProduction();
		
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
		CC.printMessage("Sikre dig at vægten er ubelastet, INDTAST 'OK' når dette er gjort");
		String input = CC.getUserInput();
		try {
			CC.controlOKMessage(input);
		} catch (InvalidInputException e) {
			CC.printMessage("Ukendt input");
			checkPreconditions();
		}
		// sæt produktbatch til under produktion
	}
	
	public void taraPreconditions() throws WeightException{
		// tarer vægt
		CC.printMessage("Læg tarabeholderen på vægten, INDTAST 'OK' når dette er gjort");
		String input = CC.getUserInput();
		try {
			CC.controlOKMessage(input);
		} catch (InvalidInputException e) {
			CC.printMessage("Ukendt input");
			taraPreconditions();
		}
		// registrer tara værdi
		// tarer vægt
	}
	
	public void doWeighing() throws WeightException{
		CC.printMessage("indtast produktbatch nummer på produkt "+ produkt);
		String produktbatch = CC.getUserInput();
		doWeighingControl();
	}
	public void doWeighingControl() {
		CC.printMessage("Fuldfør afvejningen med den ønskede mængde, og indtast OK");
		String input = CC.getUserInput();
		try {
			CC.controlOKMessage(input);
		} catch (InvalidInputException e) {
			CC.printMessage("Ukendt input");
			doWeighingControl();
		}
	}
	public void endProduction() {
		// sæt produktbatchnummerets status til 'afsluttet'
	}
	
	
	
	

}
