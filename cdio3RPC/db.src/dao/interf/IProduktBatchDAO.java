package dao.interf;

import java.util.List;

import cdio3.gwt.client.model.ProduktBatchDTO;
import cdio3.gwt.server.DALException;

public interface IProduktBatchDAO {
	ProduktBatchDTO getProduktBatch(int pbId) throws DALException;
	List<ProduktBatchDTO> getProduktBatchList() throws DALException;
	void createProduktBatch(ProduktBatchDTO produktbatch) throws DALException;
	void updateProduktBatch(ProduktBatchDTO produktbatch) throws DALException;
}