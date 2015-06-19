package dao.interf;

import java.util.List;

import cdio.gwt.client.model.OperatoerDTO;
import cdio.gwt.server.DALException;

public interface IOperatoerDAO {
	OperatoerDTO getOperatoer(int oprId) throws DALException;
	List<OperatoerDTO> getOperatoerList() throws DALException;
	void createOperatoer(OperatoerDTO opr) throws DALException, cdio.gwt.server.DALException;
	void updateOperatoer(OperatoerDTO opr) throws DALException;
	void deleteOperatoer(int oprID) throws DALException;
}
