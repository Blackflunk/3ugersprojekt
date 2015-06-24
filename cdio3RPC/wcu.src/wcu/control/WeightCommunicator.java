package wcu.control;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class WeightCommunicator {

	// opret forbindelse til vægt
	private String IP;
	private int portdst;
	private Socket sock ;
	private BufferedReader instream;
	private DataOutputStream outstream;
	
	public WeightCommunicator(String IP, int portdst){
		this.IP = IP;
		this.portdst = portdst;
	}

	//Med denne metode forbinder vi til enten vægten eller vægt simulatoren.
	public void connectToServer() {
		try {
			sock = new Socket(IP, portdst);
			outstream = 
					new DataOutputStream(sock.getOutputStream()); 
			instream = 
					new BufferedReader(new
							InputStreamReader(sock.getInputStream()));
		} catch (UnknownHostException e) {
			System.out.println("Kan ikke finde host");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO Exception");
			e.printStackTrace();
		} 
	}
	
	//Når vi skal sende beskeder igennem vores socket fungerer det således:
	public String writeSocket(String message){
		try {
			outstream.writeBytes(message + "\r\n");
			return instream.readLine();
		} catch (IOException e) {
			System.out.println("Write failed");
			e.printStackTrace();
		}
		return message;
		
	}
	
	//Når vi læser igennem vores socket er det således:
	public String readSocket(){
		String readMessage = "";
		try {
				readMessage = instream.readLine();
		} catch (IOException e) {
			System.out.println("Read failed");
			e.printStackTrace();
		}
		return readMessage;
	}
}
