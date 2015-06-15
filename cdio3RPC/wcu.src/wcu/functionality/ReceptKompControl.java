package wcu.functionality;

import java.util.ArrayList;
import java.util.List;

import cdio3.gwt.client.model.ReceptDTO;
import cdio3.gwt.client.model.ReceptKompDTO;
import cdio3.gwt.server.DALException;
import dao.interf.IReceptKompDAO;

public class ReceptKompControl implements IReceptKompDAO{
	FileHandler receptKompFil;
	ArrayList<ReceptKompDTO> recKDB = new ArrayList<ReceptKompDTO>();

	public ReceptKompControl() {

		receptKompFil = new FileHandler();
		recKDB = receptKompFil.readReceptKompDB();

	}

	@Override
	public ReceptKompDTO getReceptKomp(int receptId, int raavareId){
		ReceptKompDTO hentReceptKomp = new ReceptKompDTO();
		for (int i = 0; i < recKDB.size(); i ++){
			if (receptId == recKDB.get(i).getReceptId()){
				return recKDB.get(i);
			}
		}
		return hentReceptKomp;
	}

	@Override
	public List<ReceptKompDTO> getReceptKompList(int receptId) {
		return recKDB;
	}

	@Override
	public List<ReceptKompDTO> getReceptKompList(){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createReceptKomp(ReceptKompDTO receptkomponent){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateReceptKomp(ReceptKompDTO receptkomponent){
		// TODO Auto-generated method stub
		
	}
}
