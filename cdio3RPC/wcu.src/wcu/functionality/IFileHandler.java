package wcu.functionality;

import java.util.ArrayList;

import wcu.data.LogDTO;
import cdio.gwt.client.model.*;



public interface IFileHandler {

	ArrayList<LogDTO> readLogDB();
	void writeLogDB(LogDTO logEntry);
}
