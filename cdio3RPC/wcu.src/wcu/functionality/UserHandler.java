package wcu.functionality;

import wcu.control.WeightCommunicator;
import wcu.data.TempVare;

public class UserHandler {
	public static WeightCommunicator wc = new WeightCommunicator("127.0.0.1",
			4567);
	public static LogControl lc = new LogControl();
	public static TempVare tv = new TempVare();
	public static boolean run = true;
	public static String vaegtSvar = "";
	public static int currentOperatoer = 0;
	public static int currentRaavare = 0;
	int x;

	public UserHandler() {
		wc.connectToServer();
		this.state = State.START;
	}

	// Sekvensmaskine initialiseres.
	public enum State {
		START {
			@Override
			State changeState(String svar) {
				switch (svar) {
				case "ja":
					vaegtSvar = wc
							.writeSocket("RM20 8 \"OperatoerID?\" \" \" \" \" \r\n");
					vaegtSvar = wc.readSocket();
					vaegtSvar = vaegtSvar.substring(7, vaegtSvar.length());
					return STATE1;
				default:
					return INVALIDSTATE;
				}
			}
		},
		STATE1 {

			@Override
			State changeState(String svar) {
				
				switch (svar) {
				case "ja":
					vaegtSvar = wc.writeSocket("RM20 8 \"Varenummer?\" \" \" \" \" \r\n");
					vaegtSvar = wc.readSocket();
					vaegtSvar = vaegtSvar.substring(7, vaegtSvar.length());
					return STATE2;
				case "nej":
					return START;
				case "Q":
					return STOP;
				default:
					return INVALIDSTATE;
				}
			}
		},
		STATE2 {

			@Override
			State changeState(String svar) {
				
				switch (svar) {
				case "ja":
					//vaegtSvar = wc.writeSocket("RM20 8 \"" + rc.getRaavare(Integer.parseInt(vaegtSvar)).getRaavareNavn() + "?\"\"ja \" \" nej\" \r\n");
					vaegtSvar = wc.readSocket();
					vaegtSvar = vaegtSvar.substring(7, vaegtSvar.length());
					return STATE3;
				case "Q":
					return STOP;
				default:
					return INVALIDSTATE;
				}
			}
		},
		STATE3 {

			@Override
			State changeState(String svar) {
				
				switch (svar) {
				case "ja":
					vaegtSvar = wc.writeSocket("T\r\n");
					vaegtSvar = vaegtSvar.substring(8,vaegtSvar.length()-3);
					//tv.setTare(vaegtSvar);
					vaegtSvar = "ja";
					return STATE4;
				case "nej":
					return STATE3;
				case "Q":
					return STOP;
				default:
					return INVALIDSTATE;
				}
			}
		},		
		STATE4 {

			@Override
			State changeState(String svar) {
				
				switch (svar) {
				case "ja":
					vaegtSvar = wc.writeSocket("RM20 8 \"Paafyld Vare, svar ja ved klar!\" \" \" \" \" ");
					vaegtSvar = wc.readSocket();
					vaegtSvar = vaegtSvar.substring(7, vaegtSvar.length());
					return STATE5;
				case "Q":
					return STOP;
				default:
					return INVALIDSTATE;
				}
			}
		},
		STATE5 {

			@Override
			State changeState(String svar) {
				
				switch (svar) {
				case "ja":
					vaegtSvar = wc.writeSocket("S\r\n");
					vaegtSvar = vaegtSvar.substring(8, vaegtSvar.length()-3);
					//tv.setNetto(vaegtSvar);
					return STATE6;
				case "Q":
					return STOP;
				default:
					return INVALIDSTATE;
				}
			}
		},		
		STATE6 {

			@Override
			State changeState(String svar) {
				
				switch (svar) {
				case "ja":
					vaegtSvar = wc.writeSocket("RM20 8 \"Fjern Tara og Vaegt, svar med ja naar klar!\" \" \" \" \" \r\n");
					vaegtSvar = vaegtSvar.substring(7, vaegtSvar.length());
					return STATE7;
				case "Q":
					return STOP;
				default:
					return INVALIDSTATE;
				}
			}
		},
		STATE7 {

			@Override
			State changeState(String svar) {
				
				switch (svar) {
				case "ja":
					vaegtSvar = wc.writeSocket("T\r\n");
					vaegtSvar = vaegtSvar.substring(8,vaegtSvar.length()-3);
					//tv.setTare(vaegtSvar);
					vaegtSvar = "ja";
					return STATE8;
				case "Q":
					return STOP;
				default:
					return INVALIDSTATE;
				}
			}
		},
		STATE8 {

			@Override
			State changeState(String svar) {
				
				switch (svar) {
				case "ja":
					//Bruttokontrol
					return STATE9;
				case "Q":
					return STOP;
				default:
					return INVALIDSTATE;
				}
			}
		},
		STATE9 {

			@Override
			State changeState(String svar) {
				
				switch (svar) {
				case "ja":
					//lc.registerLogEntry(oc.getOperatoer(currentOperatoer), rc.getRaavare(currentRaavare), tv.netto.toString());
					return START;
				case "Q":
					return STOP;
				default:
					return INVALIDSTATE;
				}
			}
		},
		INVALIDSTATE {

			@Override
			State changeState(String svar) {
				System.out.println("invalidstate " + svar);
				return STOP;
			}
		},
		STOP {

			@Override
			State changeState(String svar) {
				return STOP;
			}
		};
		abstract State changeState(String svar);
	}

	private State state;

	public void runScheme(String svar) {
		this.state = this.state.changeState(svar);
	}

	public static void main(String[] args) {
		// Start af kontrolsekvens
		UserHandler u = new UserHandler();
		try {
			u.runScheme("ja");
//			if(oc.getOperatoer(Integer.parseInt(vaegtSvar)).getOprId() != 999999){
//				currentOperatoer =  oc.getOperatoer(Integer.parseInt(vaegtSvar)).getOprId();
//				u.runScheme("ja");
//			}
//			if(rc.getRaavare(Integer.parseInt(vaegtSvar)).getRaavareId() != 999999) {
//				currentRaavare = rc.getRaavare(Integer.parseInt(vaegtSvar)).getRaavareId();
//				u.runScheme("ja");
//			}
			if(vaegtSvar.equals("ja")){
				u.runScheme("ja");
			}
			if(vaegtSvar.equals("ja")){
				u.runScheme("ja");
			}
			u.runScheme("ja");
			if(vaegtSvar.equals("ja")){
				u.runScheme("ja");
			}
			if(vaegtSvar.equals("ja")){
				u.runScheme("ja");
			}
			if(vaegtSvar.equals("ja")){
				u.runScheme("ja");
			}
			
		} catch (Exception e) {
			System.out.println("intet data på port");
		}
	}
}
