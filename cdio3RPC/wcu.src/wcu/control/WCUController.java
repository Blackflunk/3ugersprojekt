package wcu.control;

import cdio3.gwt.server.DALException;
import wcu.exceptions.InvalidInputException;
import wcu.exceptions.WeightException;
import wcu.functionality.OprControl;
import wcu.functionality.ProduktBatchControl;
import wcu.functionality.ReceptControl;
import wcu.functionality.ReceptKompControl;
import weightsimulator.launch.*;

public class WCUController {
	ConsoleController CC = new ConsoleController();
	public static OprControl oc = new OprControl();
	public static ProduktBatchControl pbc = new ProduktBatchControl();
	public static ReceptControl recc = new ReceptControl();
	public static ReceptKompControl recK = new ReceptKompControl();
	String user;
	String[] start;
	String weightChoice;
	int produkt=0;
	String mode;
	int forLength, loopNumber, BatchId;
	
	public void init() throws DALException {
		runProcedure();
	}
	
	public void runProcedure() throws DALException {
		try { chooseWeight();} catch (WeightException e) { e.printStackTrace();}
		try { verifyOperatoer();} catch (WeightException e) { e.printStackTrace();}
		try { verifyBatch();} catch (WeightException e) { e.printStackTrace();}
		for (loopNumber=0; loopNumber == forLength; loopNumber++){
		try { checkPreconditions();} catch (WeightException e) { e.printStackTrace();}
		try { taraPreconditions();} catch (WeightException e) { e.printStackTrace();}
		try { doWeighing();} catch (WeightException e) { e.printStackTrace();}
		}
		// for slut
		endProduction();
		
	}
	
	public void chooseWeight() throws WeightException {
		CC.printMessage("Please enter W for normal weight or WS for simulator");
		weightChoice = CC.getUserInput();
		if(weightChoice == "WS"){
			mode = "Silmulator";
			launch.main(start);
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
		int OpId = Integer.parseInt(input);
		if(OpId == oc.getOperatoer(OpId).getOprId()){
			user = oc.getOperatoer(OpId).getOprNavn();
			CC.printMessage(user);
		}
		else{ 
			CC.printMessage("Invalid operator number");
		}
		// vis navn hvis godkendt
	}
	

	public void verifyBatch() throws WeightException {
		CC.printMessage("Indtast produktbatch nummer: ");
		String input = CC.getUserInput();
		BatchId = Integer.parseInt(input);
		try {
			if(BatchId == pbc.getProduktBatch(BatchId).getPbId()){
				CC.printMessage(recc.getRecept(pbc.getProduktBatch(BatchId).getReceptId()).getReceptNavn());
				forLength = recK.getReceptKompList(BatchId).size();
			}
			else {
				throw new WeightException();
			}
		} catch (DALException e) {
			e.printStackTrace();
		}
		// godkend batch
		// else: throw WeightExceptions
	}
	
	public void checkPreconditions() throws WeightException, DALException {
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
		pbc.getProduktBatch(BatchId).setStatus(1);
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
	public void endProduction() throws DALException {
		pbc.getProduktBatch(BatchId).setStatus(2);
		// sæt produktbatchnummerets status til 'afsluttet'
	}
	
	
	
	
	

}
