package dao.interf;

import java.util.List;

import cdio.gwt.client.model.RaavareDTO;
import cdio.gwt.server.DALException;

public interface IRaavareDAO {
	RaavareDTO getRaavare(int raavareId) throws DALException;
	List<RaavareDTO> getRaavareList() throws DALException;
	void createRaavare(RaavareDTO raavare) throws DALException;
	void updateRaavare(RaavareDTO raavare) throws DALException;
}
