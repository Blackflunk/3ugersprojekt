package wcu.control;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cdio.gwt.server.*;
import dao.impl.OperatoerDAO;
import dao.impl.ProduktBatchDAO;
import dao.impl.RaavareBatchDAO;
import dao.impl.RaavareDAO;
import dao.impl.ReceptDAO;
import dao.impl.ReceptKompDAO;
import wcu.data.LogDTO;
import wcu.data.TempVare;
import wcu.exceptions.InvalidInputException;
import wcu.exceptions.WeightException;
import wcu.functionality.FileHandler;


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
	int opr_id = 0,produkt=0, forLength=0, loopNumber=0, recept_id=0, rbID=0, tempID=0,raavare_id=0, BatchId = 0;
	double tolerance=0, NegCalculatedTol=0, CalculatedTol=0, Negtara = 0, Curweight = 0, finaltara=0, finalnetto=0, finalbrutto=0 ;
	
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
		CC.printMessage(WC.readSocket());
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
			opr_id = Integer.parseInt(input);
			if(opr_id != oprDAO.getOperatoer(opr_id).getOprId())
				throw new WeightException();
			user = oprDAO.getOperatoer(opr_id).getOprNavn();
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
			BatchId = Integer.parseInt(input);
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
			produktbatchDAO.getProduktBatch(BatchId).setStatus(1);
			produktbatchDAO.updateProduktBatch(produktbatchDAO.getProduktBatch(BatchId));
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
			CC.printMessage("Fejl i databehandling 22");
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
		String input = WC.readSocket();
		input = input.substring(7);
		CC.printMessage(input);
		try {
			CC.controlOKMessage(input);
			netto = WC.writeSocket("S");
			int index3 = netto.indexOf("kg");
			String tempnetto = netto.substring(7, index3);
			double nettoDoub = Double.parseDouble(tempnetto);
			tolerance = receptkompDAO.getReceptKomp(recept_id, raavare_id).getTolerance();
			System.out.println(tolerance + "= tolerance");
			double nomNetto = receptkompDAO.getReceptKomp(recept_id, raavare_id).getNomNetto();
			System.out.println(nomNetto + "= nomnetto");
			CalculatedTol = ((nettoDoub / 100) * tolerance) + nomNetto;
			System.out.println(CalculatedTol + "= CalTol");
			NegCalculatedTol = nomNetto - ((nettoDoub / 100) * tolerance);
			System.out.println(NegCalculatedTol + "= negCalTol");
			if (nettoDoub > CalculatedTol || nettoDoub < NegCalculatedTol){
				throw new WeightException();
			}
			
			int index1 = tara.indexOf("kg");
			String temptara = tara.substring(10, index1);
			finaltara = Double.parseDouble(temptara);
			finalnetto = nettoDoub;
			
			emptyWeight();
			System.out.println(Curweight);
			System.out.println(Negtara);
			
			if(Curweight != Negtara){
				throw new WeightException();
			}
			
			CC.printMessage(raavare_name+": \n"+"netto: "+finalnetto+"\n tara: "+finaltara+"\n brutto: "+finalnetto+finaltara);
			vareliste.add(new TempVare(raavare_name, finalnetto, finalnetto+finaltara, finalnetto));
		} catch (InvalidInputException e) {
			WC.writeSocket("D Ukendt Input");
			CC.printMessage("Ukendt input");
			doWeighingControl();
		} catch (DALException e) {
			WC.writeSocket("D Fejl i databehandling");
			CC.printMessage("Fejl i databehandling den her");
			doWeighingControl();
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
	
	public void emptyWeight() {
		WC.writeSocket("RM20 8 Fjern Råvare og Beholder og indtast OK");
		String secInput = WC.readSocket().substring(7);
		try{
			CC.controlOKMessage(secInput);
			String SCurweight = WC.writeSocket("S");
			int index4 = SCurweight.indexOf("kg");
			SCurweight = SCurweight.substring(8,index4);
			Curweight = Double.parseDouble(SCurweight);
			Negtara -= finaltara;
		}catch (InvalidInputException e) {
			WC.writeSocket("D Ukendt Input");
			CC.printMessage("Ukendt input");
			doWeighingControl();
		} catch (NumberFormatException e) {
			CC.printMessage("feeeeeeejl lige her yo");
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
		FileHandler fh = new FileHandler();
		LogDTO log = new LogDTO();
		log.setAfvejning(raavareformat);
		log.setOprID(opr_id);
		//log.setPaa_lager(paa_lager);
		log.setRaavareID(raavare_id);
		fh.writeLogDB(log);
	}
}