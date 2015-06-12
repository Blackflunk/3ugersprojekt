package cdio3.gwt.client.DAOinterface;

import java.util.List;

import cdio3.gwt.client.model.RaavareDTO;

public interface IRaavareDAO {
	RaavareDTO getRaavare(int raavareId) throws DALException;
	List<RaavareDTO> getRaavareList() throws DALException;
	void createRaavare(RaavareDTO raavare) throws DALException;
	void updateRaavare(RaavareDTO raavare) throws DALException;
}
