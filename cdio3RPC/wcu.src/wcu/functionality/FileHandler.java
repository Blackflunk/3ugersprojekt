package wcu.functionality;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import wcu.data.LogDTO;
import cdio3.gwt.client.model.*;



public class FileHandler implements IFileHandler {
	String csv_Character = ",";
	String current_operatoer_File = "data.text/operatoer.txt";
	String current_Raavare_File = "data.text/store.txt";
	String current_Log_File = "data.text/log.txt";
	String current_productBatch_File = "data.text/produktBatch.txt";
	BufferedReader br = null;
	BufferedWriter bw = null;
	String linje = "";

	@Override
	public ArrayList<LogDTO> readLogDB() {
		LogDTO nyLBasePost;
		ArrayList<LogDTO> nyLBase = new ArrayList<LogDTO>();
		try {
			br = new BufferedReader(new FileReader(current_Log_File));
			while((linje = br.readLine()) != null){
				String[] tempDB = linje.split(csv_Character);
				nyLBasePost = new LogDTO();
				nyLBasePost.setDato(tempDB[0]);
				nyLBasePost.setOprID(Integer.parseInt(tempDB[1]));
				nyLBasePost.setRaavareID(Integer.parseInt(tempDB[2]));
				nyLBasePost.setAfvejning(tempDB[3]);
				nyLBasePost.setPaa_lager(tempDB[4]);
				nyLBase.add(nyLBasePost);
			}
		}
		catch(Exception e){
			System.out.println("Show me I work!");
		}
		return nyLBase;

	}

	@Override
	public void writeLogDB(LogDTO logEntry) {
		try {
			BufferedWriter writer = 
					new BufferedWriter (new FileWriter(current_Log_File, true));
				writer.write(logEntry.getDato() + ", " + logEntry.getOprID() + ", " + logEntry.getRaavareID() + 
						", " + logEntry.getAfvejning() + ", " + logEntry.getPaa_lager() + "\n");
				writer.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}