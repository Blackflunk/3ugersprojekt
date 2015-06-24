package weightsimulator.control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import weightsimulator.entity.WeightData;
import weightsimulator.exceptions.InputLengthException;
import weightsimulator.exceptions.UnknownInputException;
import weightsimulator.exceptions.UnsupportedWeightException;

public class IOController implements Runnable, IIOController {
	Thread t;
	String name = "IO-Tråd";
	static ServerSocket listener;
	static int portdst = 8000;
	static Socket sock;
	WeightData vaegtdata = new WeightData();
	ClientController cc;
	WeightController wc;

	// Konstruktør - standard
	public IOController(WeightData vaegtdata) {
		this.vaegtdata = vaegtdata;
		if (this.vaegtdata.getUserChoice().length == 0) {
		} else if (this.vaegtdata.getUserChoice().length > 1) {
			if(this.vaegtdata.getUserChoice().length > 0){
				portdst = Integer.parseInt(this.vaegtdata.getUserChoice()[0]);
			}
		}
		this.vaegtdata.setRun(true);
		
	}
	@Override
	public void getUser() throws IOException {
		try {
			// Opretter en socket til at lytte fra kommandoer kommende fra ASE.
			listener = new ServerSocket(portdst);
			sock = listener.accept();
			//Opretter WeightController med socket samt objektet vægtdata vi fik modtaget fra launch.
			wc = new WeightController(sock, vaegtdata);
			//Opstarter serveren.
			int programTest = wc.server_Run();
			if(programTest == 0){
				closeServer();
			}else{
				wc.server_Run();
			}
		}catch(IOException | UnknownInputException | InputLengthException | UnsupportedWeightException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void closeServer() {
		try {
			if (!listener.isClosed())
				listener.close();
				System.exit(0);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			getUser();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void start() throws InterruptedException {
		System.out.println("Starting " + name);
		if (t == null) {
			t = new Thread(this, name);
			//Vi starter tråden t, denne går videre til run().
			t.start();
		}
	}
}
