package wcu.control;

import java.sql.SQLException;

import cdio3.gwt.server.*;
import dao.impl.OperatoerDAO;
import dao.impl.ProduktBatchDAO;
import dao.impl.RaavareDAO;
import dao.impl.ReceptDAO;
import dao.impl.ReceptKompDAO;
import wcu.data.TempVare;
import wcu.exceptions.InvalidInputException;
import wcu.exceptions.WeightException;
import wcu.functionality.OprControl;
import wcu.functionality.ProduktBatchControl;
import wcu.functionality.ReceptControl;
import wcu.functionality.ReceptKompControl;
import weightsimulator.launch.*;

public class WCUController {
	ConsoleController CC = new ConsoleController();
	public OperatoerDAO oprDAO;
	public ProduktBatchDAO produktbatchDAO;
	public ReceptDAO receptDAO;
	public ReceptKompDAO receptkompDAO;
	public RaavareDAO raavareDAO;
	public TempVare tempvare;
	Connector connect;
	WeightCommunicator WC;
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
		createDAO();
		try { chooseWeight();} catch (WeightException e) { e.printStackTrace();}
		connectToWeight();
		try { verifyOperatoer();} catch (WeightException | DALException e) { e.printStackTrace();}
		try { verifyBatch();} catch (WeightException | DALException e) { e.printStackTrace();}
		System.out.println(forLength);
		for (loopNumber=0; loopNumber < forLength; loopNumber++){
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
	public void connectToWeight() {
		if (mode.equals("Simulator")) {
			WC = new WeightCommunicator("localhost", 8000);
		}
		else if (mode.equals("Weight")) {
			WC = new WeightCommunicator("169.254.2.3", 8000);
		}
		WC.connectToServer();
		
		
	}
	
	public void createDAO() {
		try {
			oprDAO = new OperatoerDAO();
			produktbatchDAO = new ProduktBatchDAO();
			receptDAO = new ReceptDAO();
			receptkompDAO = new ReceptKompDAO();
			raavareDAO = new RaavareDAO();
		} catch (DALException e) {
			e.printStackTrace();
		}
	}
	
	public void chooseWeight() throws WeightException {
		CC.printMessage("Please enter W for normal weight or WS for simulator");
		weightChoice = CC.getUserInput();
		if(weightChoice.equals("WS")){
			mode = "Simulator";
		}
		else if(weightChoice.equals("W")){
			mode = "Weight";
		}
		else {
			CC.printMessage("Invalid input please try again");
			chooseWeight();
		}
	}
	
	public void verifyOperatoer() throws WeightException, DALException {
		CC.printMessage("Indtast dit operatoer nummer: ");
		String input = CC.getUserInput();
		int OpId = Integer.parseInt(input);
		if(OpId == oprDAO.getOperatoer(OpId).getOprId()){
			user = oprDAO.getOperatoer(OpId).getOprNavn();
			CC.printMessage(user);
		}
		else{ 
			CC.printMessage("Invalid operator number");
		}
		// vis navn hvis godkendt
	}
	

	public void verifyBatch() throws WeightException, DALException {
		CC.printMessage("Indtast produktbatch nummer: ");
		String input = CC.getUserInput();
		BatchId = Integer.parseInt(input);
			if(BatchId == produktbatchDAO.getProduktBatch(BatchId).getPbId()){
				int recept_id = produktbatchDAO.getProduktBatch(BatchId).getReceptId();
				String recept_name = receptDAO.getRecept(recept_id).getReceptNavn();
				CC.printMessage(recept_name);
				System.out.println(BatchId +" "+ recept_id);
				
				forLength = receptkompDAO.getReceptKompList(recept_id).size();
			}
			else {
				throw new WeightException();
			}
		// godkend batch
		// else: throw WeightExceptions
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
		try {
			produktbatchDAO.getProduktBatch(BatchId).setStatus(1);
		} catch (DALException e) {
			e.printStackTrace();
		}
	}
	
	public void taraPreconditions() throws WeightException{
		WC.writeSocket("T\r\n");
		CC.printMessage("Læg tarabeholderen på vægten, INDTAST 'OK' når dette er gjort");
		String input = CC.getUserInput();
		try {
			CC.controlOKMessage(input);
		} catch (InvalidInputException e) {
			CC.printMessage("Ukendt input");
			taraPreconditions();
		}
		tempvare= new TempVare();
		String weight = WC.readSocket();
		tempvare.setTare(weight);
		System.out.print(tempvare.tare);
		WC.writeSocket("DW\r\n");
		String response = WC.readSocket();
		System.out.println(response);
		WC.writeSocket("T\r\n");
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
		try {
			produktbatchDAO.getProduktBatch(BatchId).setStatus(2);
		} catch (DALException e) {
			e.printStackTrace();
		}
		if (mode.equals("Simulator")) {
			WC.writeSocket("Q\r\n");
		}
	}
	
	
	
	
	

}
