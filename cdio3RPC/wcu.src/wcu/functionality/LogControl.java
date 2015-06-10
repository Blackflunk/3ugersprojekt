package wcu.functionality;

import java.util.ArrayList;

import data.LogDAO;
import data.LogDTO;
import data.OperatoerDTO;
import data.RaavareDTO;

public class LogControl implements LogDAO{
	FileHandler fh = new FileHandler();
	LogDTO logEntry;
	ArrayList<LogDTO> logList = new ArrayList<LogDTO>();
	
	@Override
	public void registerLogEntry(OperatoerDTO oprID,RaavareDTO raavare, String afvejning) {
		logEntry  = new LogDTO();
		logEntry.setAfvejning(afvejning);
		logEntry.setOprID(oprID.getOprID());
		logEntry.setPaa_lager(raavare.getRaavareWeight());
		logEntry.setRaavareID(raavare.getRaavareID());
		fh.writeLogDB(logEntry);
		
	}
	@Override
	public ArrayList<LogDTO> getLogList() {
		logList = fh.readLogDB();
		return logList;
	}
}
