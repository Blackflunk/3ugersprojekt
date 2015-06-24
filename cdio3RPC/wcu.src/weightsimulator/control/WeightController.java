package weightsimulator.control;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import weightsimulator.boundary.TUI;
import weightsimulator.entity.WeightData;
import weightsimulator.exceptions.InputLengthException;
import weightsimulator.exceptions.UnknownInputException;
import weightsimulator.exceptions.UnsupportedWeightException;

public class WeightController implements IWeightController{
	Socket sock;
	WeightData wd;
	static BufferedReader instream;
	static DataOutputStream outstream;
	TUI tui = new TUI();
	static String inline;

	public WeightController(Socket sock, WeightData wd) {
		this.sock = sock;
		this.wd = wd;
	}
	public WeightController(){
		
	}
	@Override
	public int server_Run() throws IOException, UnknownInputException,
			InputLengthException, UnsupportedWeightException {
		instream = new BufferedReader(new InputStreamReader(
				sock.getInputStream()));
		outstream = new DataOutputStream(sock.getOutputStream());
		this.wd.setConnected_host(sock.getInetAddress());
		tui.printMenu(this.wd);
		outstream.flush();
		outstream
				.writeUTF("Velkommen til Mettler BBK Vægt-simulator " + "\r\n");
		while (!(inline = instream.readLine().toUpperCase()).isEmpty()
				&& this.wd.isRun()) {
			try{
				//Når vægten modtager besked fra putty, kommer en mærkelig besked, denne har specifikt 21 tegn, vi starter derfor efter 21 tegn.
			if (inline.startsWith("Ÿ")) {
				inline = inline.substring(21, inline.length());
			}
			if (inline.startsWith("�")) {
				inline = inline.substring(21, inline.length());
			}
			//Hvis vi modtager en RM20 kommando så sætter vi den i weightdata og skriver til socket at vi er 
			if (inline.startsWith("RM20")) {
				this.wd.setStreng_fra_bruger(inline.substring(0));
				if(this.wd.getStreng_fra_bruger().length()<6){
					throw new UnknownInputException();
				}else{
				this.wd.setRm20_kommando(inline.substring(7, inline.length()));
				writeSocket("RM20 " + "B");
				tui.printMenu(this.wd);
				}
			} else if (inline.startsWith("DW")) {
				this.wd.setInstruktionsdisplay1("");
				this.wd.setStreng_fra_bruger(inline.substring(0));
				writeSocket("DW " + "A");
				tui.printMenu(this.wd);
			} else if (inline.startsWith("D")) {
				if (inline.equals("D")) {
					throw new UnknownInputException();
				} else if (inline.length() > 9) {
					throw new InputLengthException();
				}
				this.wd.setInstruktionsdisplay1(inline);
				this.wd.setStreng_fra_bruger(inline.substring(0));
				writeSocket("D " + "A");
				tui.printMenu(this.wd);
			} else if (inline.startsWith("P111")) {
				if (inline.equals("P111")) {
					this.wd.setStreng_fra_bruger(inline);
					this.wd.setInstruktionsdisplay2("");
					tui.printMenu(this.wd);
				} else if (inline.length() > 35) {
					throw new InputLengthException();
				} else {
					this.wd.setStreng_fra_bruger(inline);
					this.wd.setInstruktionsdisplay2(inline.substring(5,
							inline.length()));
					writeSocket("P111 " + "A");
					tui.printMenu(this.wd);
				}
			} else if (inline.startsWith("T")) {
				//Sætter strengen fra brugeren herefter sætter vi tara til brutto vægten.
				this.wd.setStreng_fra_bruger(inline);
				this.wd.setTara(wd.getBrutto());
				// Skriver tilbage til socket og sender beskeden.
				writeSocket("T " + "S " + "     " + (wd.getTara()) + " kg ");
				// Fremviser menuen igennem tui.
				tui.printMenu(this.wd);
			} else if (inline.equals("S")) {
				//Hvis beskeden er S er det fordi vi gerne vil have vægten sendt tilbage til operatør.
				this.wd.setStreng_fra_bruger(inline.substring(0));
				if (wd.getNetto() >= 0) {
					writeSocket("S " + "S" + "      " + (wd.getNetto())
							+ " kg ");
				} else if (wd.getNetto() < 0) {
					writeSocket("S " + "S" + "     " + (wd.getNetto()) + " kg ");
				}
			} else if (inline.startsWith("B")) {
				// Ikke eksisterende på den rigtige vægt
				if (inline.equalsIgnoreCase("B")) {
					throw new UnknownInputException();
				}else if(!inline.startsWith(" ", 1)){
					throw new UnknownInputException();
				}else {
					String temp = inline.substring(2, inline.length());
					this.wd.setStreng_fra_bruger(inline.substring(0));
					if (Double.parseDouble(temp) <= 6.02
							&& Double.parseDouble(temp) >= 0) {
						this.wd.setBrutto(Double.parseDouble(temp));
						writeSocket("DB ");
						tui.printMenu(this.wd);
					} else {
						throw new UnsupportedWeightException();
					}
				}
			} else if ((inline.startsWith("Q"))) {
				// denne ordre findes heller ikke p� den fysiske v�gt.
				this.wd.setStreng_fra_bruger(inline.substring(0));
				writeSocket("Vægt lukkes");
				tui.printMessage("Program stoppet Q modtaget på com port"
						+ "\r\n");
				wd.setRun(false);
				closeStreams();
				return 0;
			} else {
				throw new UnknownInputException();
			}
		}
		catch(UnsupportedWeightException e){
			outstream.writeBytes("ES" + "\r\n");
		}catch(UnknownInputException e){
			outstream.writeBytes("ES" + "\r\n");
		}catch(InputLengthException e){
			outstream.writeBytes("ES" + "\r\n");
		}
		}
		return 1;
	}
	@Override
	public void writeSocket(String s) throws IOException {

		outstream.writeBytes(s + "\r\n");
	}
	@Override
	public void closeStreams() {
		try {
			if (outstream != null)
				outstream.close();
			if (instream != null)
				instream.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}

				
