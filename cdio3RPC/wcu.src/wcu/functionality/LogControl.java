package wcu.functionality;

import java.util.ArrayList;

import wcu.data.LogDAO;
import wcu.data.LogDTO;
import cdio3.gwt.client.model.RaavareDTO;
import cdio3.gwt.client.model.OperatoerDTO;


public class LogControl implements LogDAO{
	FileHandler fh = new FileHandler();
	LogDTO logEntry;
	ArrayList<LogDTO> logList = new ArrayList<LogDTO>();
	
	@Override
	public void registerLogEntry(OperatoerDTO oprID,RaavareDTO raavare, String afvejning) {
		logEntry  = new LogDTO();
		logEntry.setAfvejning(afvejning);
		logEntry.setOprID(oprID.getOprId());
		logEntry.setPaa_lager(raavare.getRaavareWeight());
		logEntry.setRaavareID(raavare.getRaavareId());
		fh.writeLogDB(logEntry);
		
	}
	@Override
	public ArrayList<LogDTO> getLogList() {
		logList = fh.readLogDB();
		return logList;
	}

}
