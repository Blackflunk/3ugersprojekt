package wcu.control;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.thirdparty.javascript.rhino.head.regexp.SubString;

import cdio3.gwt.server.*;
import dao.impl.OperatoerDAO;
import dao.impl.ProduktBatchDAO;
import dao.impl.RaavareBatchDAO;
import dao.impl.RaavareDAO;
import dao.impl.ReceptDAO;
import dao.impl.ReceptKompDAO;
import wcu.data.TempVare;
import wcu.exceptions.InvalidInputException;
import wcu.exceptions.WeightException;


public class WCUController {
	ConsoleController CC = new ConsoleController();
	public OperatoerDAO oprDAO;
	public ProduktBatchDAO produktbatchDAO;
	public ReceptDAO receptDAO;
	public ReceptKompDAO receptkompDAO;
	public RaavareDAO raavareDAO;
	public RaavareBatchDAO raavarebatchDAO;
	public TempVare tempvare;
	public FileWriter filewriter;
	Connector connect;
	WeightCommunicator WC;
	ArrayList<TempVare> vareliste = new ArrayList<TempVare>(); 
	String weightChoice, user, tara, netto, brutto, weight, mode, produktbatch, raavare_name, recept_name;
	int produkt=0, forLength, loopNumber, BatchId, recept_id, rbID, tempID;
	
	public void init() {
		runProcedure();
	}
	
	public void runProcedure() {
		connectToDatabase();
		createDAO();
		createFilewriter();
		try { chooseWeight();} catch (WeightException e) { e.printStackTrace();}
		connectToWeight();
		try { verifyOperatoer();} catch (WeightException | DALException e) { e.printStackTrace();}
		try { verifyBatch();} catch (WeightException | DALException e) { e.printStackTrace();}
		System.out.println(forLength);
		for (loopNumber=0; loopNumber < forLength; loopNumber++){
		try { checkPreconditions();} catch (WeightException e) { e.printStackTrace();}
		try { taraPreconditions(loopNumber);} catch (WeightException e) { e.printStackTrace();}
		try { doWeighing(loopNumber);} catch (WeightException e) { e.printStackTrace();}
		}
		// for slut
		endProduction();
	}
	
	public void createFilewriter() {
		try {
			filewriter = new FileWriter("log.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		System.out.println(WC.readSocket());
	}
	
	public void createDAO() {
		try {
			oprDAO = new OperatoerDAO();
			produktbatchDAO = new ProduktBatchDAO();
			receptDAO = new ReceptDAO();
			receptkompDAO = new ReceptKompDAO();
			raavareDAO = new RaavareDAO();
			raavarebatchDAO = new RaavareBatchDAO();
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
		WC.writeSocket("RM20 8 Indtast Operatoer nummer:");
		String input = WC.readSocket().substring(7);
		CC.printMessage(input);		
		int OpId = Integer.parseInt(input);
		if(OpId == oprDAO.getOperatoer(OpId).getOprId()){
			user = oprDAO.getOperatoer(OpId).getOprNavn();
			WC.writeSocket("D " + user);
			CC.printMessage(user);
		}
		else{ 
			WC.writeSocket("D Invalid operator number");
			CC.printMessage("Invalid operator number");
		}
		// vis navn hvis godkendt
	}
	
	public void verifyBatch() throws WeightException, DALException {
		CC.printMessage("Indtast produktbatch nummer: ");
		String input = CC.getUserInput();
		BatchId = Integer.parseInt(input);
			if(BatchId == produktbatchDAO.getProduktBatch(BatchId).getPbId()){
				recept_id = produktbatchDAO.getProduktBatch(BatchId).getReceptId();
				recept_name = receptDAO.getRecept(recept_id).getReceptNavn();
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
		tara = WC.writeSocket("T");
		try {
			produktbatchDAO.getProduktBatch(BatchId).setStatus(1);
			produktbatchDAO.updateProduktBatch(produktbatchDAO.getProduktBatch(BatchId));
		} catch (DALException e) {
			e.printStackTrace();
		}
	}
	
	public void taraPreconditions(int loopNumber) throws WeightException{
		CC.printMessage("Læg tarabeholderen på vægten, INDTAST 'OK' når dette er gjort");
		String input = CC.getUserInput();
		try {
			System.out.println("teeest");
			CC.controlOKMessage(input);
			tara = WC.writeSocket("T");
		} catch (InvalidInputException e) {
			CC.printMessage("Ukendt input");
			taraPreconditions(loopNumber);
		}
		
		System.out.println(tara);
		System.out.println("test2");
		System.out.println(tara);
		
	}
	
	public void doWeighing(int loopNumber) throws WeightException{
		int raavare_id = 0;
		try {
			raavare_id = receptkompDAO.getReceptKompList(recept_id).get(loopNumber).getRaavareId();
		} catch (DALException e) {
			e.printStackTrace();
		}
		try {
			raavare_name = raavareDAO.getRaavare(raavare_id).getRaavareNavn();
		} catch (DALException e) {
			e.printStackTrace();
		}
		CC.printMessage("indtast raavarebatch nummer på raavare "+ raavare_name);
		produktbatch = CC.getUserInput();
		rbID = Integer.parseInt(produktbatch);
		try {
			tempID = raavarebatchDAO.getRaavareBatch(rbID).getRaavareId();
		} catch (DALException e) {
			e.printStackTrace();
		}
		if(tempID != raavare_id){
			CC.printMessage("invalid batch number please enter it again");
			doWeighing(loopNumber);
		}
		else{
		doWeighingControl();
		}

		
	}
	public void doWeighingControl() {
		
		CC.printMessage("Fuldfør afvejningen med den ønskede mængde, og indtast OK");
		String input = CC.getUserInput();
		try {
			CC.controlOKMessage(input);
			System.out.println("her kommer s");
			netto = WC.writeSocket("S");
			System.out.println(netto);
			
			int index1 = tara.indexOf("kg");
			String temptara = tara.substring(10, index1);
			double finaltara = Double.parseDouble(temptara);
			System.out.println("tara: "+finaltara);
			int index2 = netto.indexOf("kg");
			String tempnetto = netto.substring(10, index2);
			double finalnetto = Double.parseDouble(tempnetto);
			System.out.println("netto: "+finalnetto);
			vareliste.add(new TempVare(raavare_name, finalnetto, finalnetto+finaltara, finalnetto));
			
		} catch (InvalidInputException e) {
			CC.printMessage("Ukendt input");
			doWeighingControl();
		}
	}
	public void endProduction() {
		try {
			produktbatchDAO.getProduktBatch(BatchId).setStatus(2);
			produktbatchDAO.updateProduktBatch(produktbatchDAO.getProduktBatch(BatchId));
		} catch (DALException e) {
			e.printStackTrace();
		}
		if (mode.equals("Simulator")) {
			WC.writeSocket("Q");
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String raavareformat="";
		for (int i=0; i<vareliste.size(); i++){
			raavareformat += vareliste.get(i).name;
			raavareformat += "\n brutto: ";
			raavareformat += vareliste.get(i).brutto;
			raavareformat += " netto: ";
			raavareformat += vareliste.get(i).netto;
			raavareformat += " tara: ";
			raavareformat += vareliste.get(i).tara;
			raavareformat += "\n \n";
					}
		try {
			filewriter.write("log for recept: "+recept_name+"\n"+"Operatoer: "+user+"\n"+
					"Date and time: "+dateFormat.format(date)+"\n \n"+ raavareformat
					);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}