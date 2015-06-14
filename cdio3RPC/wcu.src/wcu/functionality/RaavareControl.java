package wcu.functionality;

import java.util.ArrayList;

import dao.impl.RaavareDAO;
import dao.interf.IRaavareDAO;
import cdio3.gwt.client.model.RaavareDTO;;

public class RaavareControl implements IRaavareDAO {
	
	FileHandler raavareFil;
	ArrayList<RaavareDTO> rDB = new ArrayList<RaavareDTO>();
	
	public RaavareControl() {
		raavareFil = new FileHandler();
		rDB = raavareFil.readRaavareDB();
	}

	@Override
	public RaavareDTO getRaavare(int raavareID) {
		RaavareDTO hentRaavare = new RaavareDTO();
		for(int i = 0;i < rDB.size();i++){
			if(raavareID == rDB.get(i).getRaavareId()){
				return rDB.get(i);
			}
		}
		hentRaavare.setRaavareId(999999);
		return hentRaavare;
	}

	@Override
	public void createRaavare(RaavareDTO raavare) {
		// TODO Auto-generated method stub
		
	}

	//Vi udgår fra at raavaren findes
	@Override
	public void updateRaavare(RaavareDTO raavare) {
		for(int i = 0;i < rDB.size();i++){
			if(raavare.getRaavareId() == rDB.get(i).getRaavareId()){
				rDB.set(i, raavare);
			}
		}
		raavareFil.writeRaavareDB(rDB);
	}

	@Override
	public ArrayList<RaavareDTO> getRaavareList() {
		return rDB;
	}

}
