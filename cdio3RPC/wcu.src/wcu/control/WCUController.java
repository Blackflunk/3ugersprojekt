package wcu.control;

import java.sql.SQLException;

import cdio3.gwt.server.*;
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
	Connector connect;
	String user;
	String[] start;
	String weightChoice;
	int produkt=0;
	String mode;
	int forLength, loopNumber, BatchId;
	
	public void init() {
		runProcedure();
	}
	
	public void runProcedure() {
		connectToDatabase();
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
	
	public void connectToDatabase() {
		try {
			connect = new Connector();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void chooseWeight() throws WeightException {
		CC.printMessage("Please enter W for normal weight or WS for simulator");
		weightChoice = CC.getUserInput();
		if(weightChoice.equals("WS")){
			mode = "Simulator";
			launch.main(start);
		}
		else if(weightChoice.equals("W")){
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
			if(BatchId == pbc.getProduktBatch(BatchId).getPbId()){
				CC.printMessage(recc.getRecept(pbc.getProduktBatch(BatchId).getReceptId()).getReceptNavn());
				forLength = recK.getReceptKompList(BatchId).size();
			}
			else {
				throw new WeightException();
			}
		// godkend batch
		// else: throw WeightExceptions
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
	public void endProduction() {
		pbc.getProduktBatch(BatchId).setStatus(2);
		// sæt produktbatchnummerets status til 'afsluttet'
	}
	
	
	
	
	

}
