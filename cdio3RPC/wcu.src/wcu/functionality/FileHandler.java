package wcu.functionality;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import data.LogDTO;
import data.OperatoerDTO;
import data.RaavareDTO;


public class FileHandler implements IFileHandler {
	String csv_Character = ",";
	String current_operatoer_File = "data.text/operatoer.txt";
	String current_Raavare_File = "data.text/store.txt";
	String current_Log_File = "data.text/log.txt";
	BufferedReader br = null;
	BufferedWriter bw = null;
	String linje = "";

	@Override
	public ArrayList<OperatoerDTO> readOperatoerDB() {
		OperatoerDTO nyOBasePost;
		ArrayList<OperatoerDTO> nyOBase = new ArrayList<OperatoerDTO>();
		try {
			br = new BufferedReader(new FileReader(current_operatoer_File));
			while((linje = br.readLine()) != null){
				String[] tempDB = linje.split(csv_Character);
				nyOBasePost = new OperatoerDTO();
				nyOBasePost.setOprID(Integer.parseInt(tempDB[0]));
				nyOBasePost.setOprName(tempDB[1]);
				nyOBase.add(nyOBasePost);
			}
		}catch(Exception e){
			System.out.println("Show me I work!");
		}
		return nyOBase;
	}

	@Override
	public ArrayList<RaavareDTO> readRaavareDB() {
		RaavareDTO nyRBasePost;
		ArrayList<RaavareDTO> nyRBase = new ArrayList<RaavareDTO>();
		try {
			br = new BufferedReader(new FileReader(current_Raavare_File));
			while((linje = br.readLine()) != null){
				String[] tempDB = linje.split(csv_Character);
				nyRBasePost = new RaavareDTO();
				nyRBasePost.setRaavareID(Integer.parseInt(tempDB[0]));
				nyRBasePost.setRaavareName(tempDB[1]);
				nyRBasePost.setRaavareWeight(tempDB[2]);
				nyRBase.add(nyRBasePost);
			}	
		}catch(Exception e){
			System.out.println("Show me I work!");
		}
		return nyRBase;
	}

	@Override
	public void writeOperatoerDB(ArrayList<OperatoerDTO> oprDB) {
		try {
			BufferedWriter writer = 
					new BufferedWriter (new FileWriter(current_operatoer_File));
			for(int i = 0;i <oprDB.size();i++){
				writer.write(oprDB.get(i).getOprID() +", "+ oprDB.get(i).getOprName() +", "+ "\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void writeRaavareDB(ArrayList<RaavareDTO> rDB) {
		try {
			BufferedWriter writer = 
					new BufferedWriter (new FileWriter(current_Raavare_File));
			for(int i = 0;i < rDB.size();i++){
				writer.write(rDB.get(i).getRaavareID() +", "+ rDB.get(i).getRaavareName() +", "+ rDB.get(i).getRaavareWeight() +"\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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




