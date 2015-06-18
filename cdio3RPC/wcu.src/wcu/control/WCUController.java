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
	String weightChoice="", user="", tara="", netto="", brutto="", weight="", mode="", produktbatch="", raavare_name="", recept_name="";
	int produkt=0, forLength=0, loopNumber=0, BatchId2=0, recept_id=0, rbID=0, tempID=0,raavare_id=0, Negtara = 0, Curweight = 0;
	double tolerance=0, NegCalculatedTol=0, CalculatedTol=0;
	public void init() {
		runProcedure();
	}
	
	public void runProcedure() {
		connectToDatabase();
		createDAO();
		createFilewriter();
		chooseWeight();
		connectToWeight();
		verifyOperatoer();
		verifyBatch();
		for (loopNumber=0; loopNumber < forLength; loopNumber++){
			checkPreconditions();
			taraPreconditions(loopNumber);
			doWeighing(loopNumber);
		}
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
		// CC.printMessage(WC.readSocket());
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
	
	public void chooseWeight(){
		CC.printMessage("Forbind til en af vægtene fra listen \n 1. Mettler BBK Vægt \n 2. Mettler BBK Vægt-simulator");
		weightChoice = CC.getUserInput();
		if(weightChoice.equals("2")){
			mode = "Simulator";
		}
		else if(weightChoice.equals("1")){
			mode = "Weight";
		}
		else {
			CC.printMessage("Ukendt input");
			chooseWeight();
		}
	}
	
	public void verifyOperatoer() {
		WC.writeSocket("RM20 8 Indtast Operatoer nummer:");
		String input = WC.readSocket();
		input = input.substring(7);
		CC.printMessage(input);
		try {
			int OpId = Integer.parseInt(input);
			if(OpId != oprDAO.getOperatoer(OpId).getOprId())
				throw new WeightException();
			user = oprDAO.getOperatoer(OpId).getOprNavn();
			WC.writeSocket("D " + user);
			CC.printMessage("Operatoer: "+user);
		} catch (WeightException e) {
			WC.writeSocket("D Ukendt operatoer");
			CC.printMessage("Ukendt operatoer");
			verifyOperatoer();
		} catch (NumberFormatException e){
			WC.writeSocket("D Ukendt input");
			CC.printMessage("Ukendt input");
			verifyOperatoer();
		} catch (DALException e) {
			WC.writeSocket("D Fejl i databehandling");
			CC.printMessage("Fejl i databehandling");
			verifyOperatoer();
		}
	}
	
	public void verifyBatch() {
		WC.writeSocket("RM20 8 Indtast produktbatch nummer");
		String input = WC.readSocket().substring(7);
		CC.printMessage(input);
		try {
			int BatchId = Integer.parseInt(input);
			BatchId2 = BatchId;
			if(BatchId != produktbatchDAO.getProduktBatch(BatchId).getPbId())
				throw new WeightException();
			recept_id = produktbatchDAO.getProduktBatch(BatchId).getReceptId();
			recept_name = receptDAO.getRecept(recept_id).getReceptNavn();
			WC.writeSocket(recept_name);
			CC.printMessage(recept_name);
			CC.printMessage(BatchId +" "+ recept_id);
			forLength = receptkompDAO.getReceptKompList(recept_id).size();
		} catch (WeightException e) {
			WC.writeSocket("D Ukendt produktbatch");
			CC.printMessage("Ukendt produktbatch");
			verifyBatch();
		} catch (NumberFormatException e) {
			WC.writeSocket("D Ukendt input");
			CC.printMessage("Ukendt input");
			verifyBatch();
		} catch (DALException e) {
			WC.writeSocket("D Fejl i databehandling");
			CC.printMessage("Fejl i databehandling");
			verifyBatch();
		}
	}
	
	public void checkPreconditions() {
		WC.writeSocket("RM20 8 Sikre dig at vægten er ubelastet, INDTAST 'OK' når dette er gjort");
		String input = WC.readSocket().substring(7);
		try {
			CC.controlOKMessage(input);
			tara = WC.writeSocket("T");
			produktbatchDAO.getProduktBatch(BatchId2).setStatus(1);
			produktbatchDAO.updateProduktBatch(produktbatchDAO.getProduktBatch(BatchId2));
		} catch (InvalidInputException e) {
			WC.writeSocket("D Ukendt input");
			CC.printMessage("Ukendt input");
			checkPreconditions();
		} catch (DALException e) {
			WC.writeSocket("D Fejl i databehandling");
			CC.printMessage("Fejl i databehandling");
			checkPreconditions();
		}
	}
	
	public void taraPreconditions(int loopNumber){
		WC.writeSocket("RM20 8 Læg tarabeholderen på vægten, INDTAST 'OK' når dette er gjort");
		String input = WC.readSocket().substring(7);
		try {
			CC.controlOKMessage(input);
			tara = WC.writeSocket("T");
			CC.printMessage(tara);
		} catch (InvalidInputException e) {
			WC.writeSocket("D Ukendt input");
			CC.printMessage("Ukendt input");
			taraPreconditions(loopNumber);
		}
	}
	
	public void doWeighing(int loopNumber){
		try {
			raavare_id = 0;
			raavare_id = receptkompDAO.getReceptKompList(recept_id).get(loopNumber).getRaavareId();
			raavare_name = raavareDAO.getRaavare(raavare_id).getRaavareNavn();
			WC.writeSocket("RM20 8 indtast raavarebatch nummer på raavare " + raavare_name);
			produktbatch = WC.readSocket().substring(7);
			rbID = Integer.parseInt(produktbatch);
			tempID = raavarebatchDAO.getRaavareBatch(rbID).getRaavareId();
			if(tempID != raavare_id)
				throw new WeightException();
			doWeighingControl();
		} catch (DALException e) {
			WC.writeSocket("D Fejl i databehandling");
			CC.printMessage("Fejl i databehandling");
			doWeighing(loopNumber);
		} catch (WeightException e) {
			WC.writeSocket("D Ukendt produktbatch");
			CC.printMessage("Ukendt produktbatch");
			doWeighing(loopNumber);
		} catch (NumberFormatException e) {
			WC.writeSocket("D Ukendt input");
			CC.printMessage("Ukendt input");
			doWeighing(loopNumber);
		}
	}
	
	public void doWeighingControl() {
		WC.writeSocket("RM20 8 Fuldfør afvejningen med den ønskede mængde, og indtast OK");
		String input = WC.readSocket().substring(7);
		try {
			CC.controlOKMessage(input);
			netto = WC.writeSocket("S");
			int index3 = netto.indexOf("kg");
			String tempnetto = netto.substring(7, index3);
			double nettoDoub = Double.parseDouble(tempnetto);
//			tolerance = receptkompDAO.getReceptKomp(recept_id, raavare_id).getTolerance();
//			CalculatedTol = ((nettoDoub / 100) * tolerance) + receptkompDAO.getReceptKomp(recept_id, raavare_id).getNomNetto();
//			NegCalculatedTol = receptkompDAO.getReceptKomp(recept_id, raavare_id).getNomNetto() - ((nettoDoub / 100) * tolerance);
//			if (nettoDoub > CalculatedTol || nettoDoub < NegCalculatedTol){
//				throw new WeightException();
//			}
//			
			WC.writeSocket("RM20 8 Fjern Råvare og Beholder og indtast OK");
//			String secInput = WC.readSocket().substring(7);
//			try{
//				CC.controlOKMessage(secInput);
//				Curweight = Integer.parseInt(WC.writeSocket("S"));
//				int tempTara = Integer.parseInt(tara.substring(7));
//				Negtara -= tempTara;
//			}catch (InvalidInputException e) {
//				WC.writeSocket("D Ukendt Input");
//				CC.printMessage("Ukendt input");
//				doWeighingControl();
//			}
//			
			if(Curweight != Negtara){
				throw new WeightException();
			}
			
			int index1 = tara.indexOf("kg");
			String temptara = tara.substring(10, index1);
			double finaltara = Double.parseDouble(temptara);
			double finalnetto = nettoDoub;
			CC.printMessage(raavare_name+": \n"+"netto: "+finalnetto+"\n tara: "+finaltara+"\n brutto: "+finalnetto+finaltara);
			vareliste.add(new TempVare(raavare_name, finalnetto, finalnetto+finaltara, finalnetto));
		} catch (InvalidInputException e) {
			WC.writeSocket("D Ukendt Input");
			CC.printMessage("Ukendt input");
			doWeighingControl();
//		} catch (DALException e) {
//			WC.writeSocket("D Fejl i databehandling");
//			CC.printMessage("Fejl i databehandling");
//			doWeighingControl();
		} catch (WeightException e) {
			WC.writeSocket("D Vægt er ikke inde for tolerance grænserne");
			CC.printMessage("Vægt er ikke inde for tolerance grænserne");
			doWeighingControl();
		} catch (NumberFormatException e) {
			WC.writeSocket("D number");
			CC.printMessage("number");
			doWeighingControl();
		}
	}
	public void endProduction() {
		try {
			produktbatchDAO.getProduktBatch(BatchId2).setStatus(2);
			produktbatchDAO.updateProduktBatch(produktbatchDAO.getProduktBatch(BatchId2));
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