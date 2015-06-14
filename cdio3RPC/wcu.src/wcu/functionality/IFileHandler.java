package wcu.functionality;

import java.util.ArrayList;

import wcu.data.LogDTO;
import cdio3.gwt.client.model.*;



public interface IFileHandler {

	ArrayList<OperatoerDTO> readOperatoerDB();
	ArrayList<RaavareDTO> readRaavareDB();
	ArrayList<LogDTO> readLogDB();
	ArrayList<ProduktBatchDTO> readProduktBatchDB();
	ArrayList<ReceptDTO> readReceptDB();
	ArrayList<ReceptKompDTO> readReceptKompDB();
	void writeOperatoerDB(ArrayList<OperatoerDTO> oprDB);
	void writeRaavareDB(ArrayList<RaavareDTO> rDB);
	void writeLogDB(LogDTO logEntry);
	void writeProduktBatchDB(ArrayList<ProduktBatchDTO> pbDB);
	void writeReceptDB(ArrayList<ReceptDTO> recDB);
	void writeReceptKompDB(ArrayList<ReceptKompDTO> recKDB);

}
