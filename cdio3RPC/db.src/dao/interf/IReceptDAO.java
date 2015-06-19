package dao.interf;

import java.util.List;

import cdio.gwt.client.model.ReceptDTO;
import cdio.gwt.server.DALException;

public interface IReceptDAO {
	ReceptDTO getRecept(int receptId) throws DALException;
	List<ReceptDTO> getReceptList() throws DALException;
	void createRecept(ReceptDTO recept) throws DALException;
	void updateRecept(ReceptDTO recept) throws DALException;
}
