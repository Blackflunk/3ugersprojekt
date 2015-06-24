package weightsimulator.launch;
import weightsimulator.control.ClientController;
import weightsimulator.control.IOController;
import weightsimulator.entity.WeightData;

public class launch {
	static WeightData vaegtdata = new WeightData();
	static IOController io;
	static ClientController cc;
	public static void main(String args[]){
		vaegtdata.setUserChoice(args);
		cc = new ClientController(vaegtdata);
		io = new IOController(vaegtdata);
		//Starter start() i clientcontroller.
		cc.start();
		try {
			//Starter start() i IOcontroller.
			io.start();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}