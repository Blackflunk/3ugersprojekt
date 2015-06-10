package wcu.data;

import java.util.ArrayList;

public interface LogDAO {
	
	public void registerLogEntry(OperatoerDTO oprID, RaavareDTO raavare, String afvejning);
	public ArrayList<LogDTO> getLogList();
}
