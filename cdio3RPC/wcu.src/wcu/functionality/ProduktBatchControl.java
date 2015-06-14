package wcu.functionality;

import java.util.ArrayList;
import java.util.List;

import cdio3.gwt.client.model.ProduktBatchDTO;
import cdio3.gwt.server.DALException;
import dao.interf.IProduktBatchDAO;

public class ProduktBatchControl implements IProduktBatchDAO{


	FileHandler produktBatchFil;
	ArrayList<ProduktBatchDTO> pbDB = new ArrayList<ProduktBatchDTO>();

	public ProduktBatchControl() {

		produktBatchFil = new FileHandler();
		pbDB = produktBatchFil.readProduktBatchDB();

	}
	
	@Override
	public ProduktBatchDTO getProduktBatch(int pbId) throws DALException {
		ProduktBatchDTO hentProduktBatch = new ProduktBatchDTO();
		for (int i = 0; i < pbDB.size(); i ++){
			if (pbId == pbDB.get(i).getPbId()){
				return pbDB.get(i);
			}
		}
		hentProduktBatch.setPbId(999999);
		return hentProduktBatch;
	}
	

	@Override
	public List<ProduktBatchDTO> getProduktBatchList() throws DALException {
		return pbDB;
	}

	@Override
	public void createProduktBatch(ProduktBatchDTO produktbatch) throws DALException {
		pbDB.add(produktbatch);
		produktBatchFil.writeProduktBatchDB(pbDB);
	}

	@Override
	public void updateProduktBatch(ProduktBatchDTO produktbatch) throws DALException {
		for(int i = 0; i < pbDB.size(); i++){
			if(produktbatch.getPbId() == pbDB.get(i).getPbId()){
				pbDB.set(i, produktbatch);
			}
		}
		produktBatchFil.writeProduktBatchDB(pbDB);
	}
	

}
