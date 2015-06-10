package wcu.functionality;

import java.util.ArrayList;

import data.LogDTO;
import data.OperatoerDTO;
import data.RaavareDTO;

public interface IFileHandler {

	ArrayList<OperatoerDTO> readOperatoerDB();
	ArrayList<RaavareDTO> readRaavareDB();
	ArrayList<LogDTO> readLogDB();
	void writeOperatoerDB(ArrayList<OperatoerDTO> oprDB);
	void writeRaavareDB(ArrayList<RaavareDTO> rDB);
	void writeLogDB(LogDTO logEntry);

}
