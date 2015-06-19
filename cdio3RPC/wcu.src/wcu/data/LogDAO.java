package wcu.data;

import java.util.ArrayList;

import cdio.gwt.client.model.OperatoerDTO;
import cdio.gwt.client.model.RaavareDTO;

public interface LogDAO {
	
	public void registerLogEntry(OperatoerDTO oprID, RaavareDTO raavare, String afvejning);
	public ArrayList<LogDTO> getLogList();
}
