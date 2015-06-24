package wcu.control;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cdio3.gwt.server.*;
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

//Afvejnings System Enhed
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
		// Vælger om det er Simulator eller fysisk vægt.
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
			// Den tara automatisk.
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
			// Nedenfor tager vi fat i en råvare ud fra recepten og finder navnet.
			raavare_id = receptkompDAO.getReceptKompList(recept_id).get(loopNumber).getRaavareId();
			raavare_name = raavareDAO.getRaavare(raavare_id).getRaavareNavn();
			//RM20 kommando skriver besked til vægten at der skal indskrives hvilken råvarebatch at råvaren hører til.
			WC.writeSocket("RM20 8 indtast raavarebatch nummer på raavare " + raavare_name);
			produktbatch = WC.readSocket().substring(7);
			//Vi definerer nu nummeret af råvarebatchen og parser.
			rbID = Integer.parseInt(produktbatch);
			// Vi tager nu råvare ID'et ud fra råvarebatchet og sammenligner med 
			tempID = raavarebatchDAO.getRaavareBatch(rbID).getRaavareId();
			// Vi tjekker om raavareId står overens med det råvareId vi fandt ud fra råvarebatchen.
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
		// Vi indtaster vægten på den afvejede mængde.
		WC.writeSocket("RM20 8 Fuldfør afvejningen med den ønskede mængde, og indtast OK");
		String input = WC.readSocket();
		input = input.substring(7);
		CC.printMessage(input);
		try {
			CC.controlOKMessage(input);
			//Aflæser vægten.
			netto = WC.writeSocket("S");
			int index3 = netto.indexOf("kg");
			//Fjerner output i starten samt kg til sidst samt parser til double.
			String tempnetto = netto.substring(7, index3);
			double nettoDoub = Double.parseDouble(tempnetto);
			//Tager tolerancen ud fra receptkomponenten.
			tolerance = receptkompDAO.getReceptKomp(recept_id, raavare_id).getTolerance();
			System.out.println(tolerance + "= tolerance");
			//Tager netto værdien fra receptkomponenten.
			double nomNetto = receptkompDAO.getReceptKomp(recept_id, raavare_id).getNomNetto();
			System.out.println(nomNetto + "= nomnetto");
			//Finder den øvre og nedre grænse for hvad vægten må være ud fra tolerancen.
			CalculatedTol = ((nettoDoub / 100) * tolerance) + nomNetto;
			System.out.println(CalculatedTol + "= CalTol");
			NegCalculatedTol = nomNetto - ((nettoDoub / 100) * tolerance);
			System.out.println(NegCalculatedTol + "= negCalTol");
			
			// Hvis netto værdien er højere eller lavere en den nominerede grænse, kastes en exception.
			if (nettoDoub > CalculatedTol || nettoDoub < NegCalculatedTol){
				throw new WeightException();
			}
			
			// Vi ser på taraværdien og kalder den for finaltara
			int index1 = tara.indexOf("kg");
			String temptara = tara.substring(10, index1);
			finaltara = Double.parseDouble(temptara);
			finalnetto = nettoDoub;
			
			//Nu kører vi så metoden nedenfor, som sørger for at tømme vægten.
			emptyWeight();
			System.out.println(Curweight);
			System.out.println(Negtara);
			
			// Da vi nu har fjernet råvaren burde vi have en vægt på -tara,
			// da vi sletter vægten.
			if(Curweight != Negtara){
				throw new WeightException();
			}
			
			// Vi kan nu tilføje informationerne ind i vores vareliste.
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
		// Giver en besked til vægten om at vjerne råvaren og beholderen.
		WC.writeSocket("RM20 8 Fjern Råvare og Beholder og indtast OK");
		String secInput = WC.readSocket().substring(7);
		try{
			CC.controlOKMessage(secInput);
			// Aflæser den nuværende vægt.
			String SCurweight = WC.writeSocket("S");
			int index4 = SCurweight.indexOf("kg");
			SCurweight = SCurweight.substring(8,index4);
			Curweight = Double.parseDouble(SCurweight);
			// Definerer den negative tara som værende det omvendte af final.
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
			//Færdiggører produktionen ved at sætte status til 2, således at den er blevet færdig.
			produktbatchDAO.getProduktBatch(BatchId).setStatus(2);
			//Opdaterer produktbatchen
			produktbatchDAO.updateProduktBatch(produktbatchDAO.getProduktBatch(BatchId));
		} catch (DALException e) {
			e.printStackTrace();
		}
		
		//Opdaterer loggen nedenfor.
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