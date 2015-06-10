package wcu.functionality;

import java.util.ArrayList;

import wcu.data.LogDTO;
import wcu.data.OperatoerDTO;
import wcu.data.RaavareDTO;

public interface IFileHandler {

	ArrayList<OperatoerDTO> readOperatoerDB();
	ArrayList<RaavareDTO> readRaavareDB();
	ArrayList<LogDTO> readLogDB();
	void writeOperatoerDB(ArrayList<OperatoerDTO> oprDB);
	void writeRaavareDB(ArrayList<RaavareDTO> rDB);
	void writeLogDB(LogDTO logEntry);

}
