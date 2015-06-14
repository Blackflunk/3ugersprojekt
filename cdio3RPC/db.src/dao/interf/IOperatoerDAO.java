package dao.interf;

import java.util.List;

import cdio3.gwt.client.model.OperatoerDTO;
import cdio3.gwt.server.DALException;

public interface IOperatoerDAO {
	OperatoerDTO getOperatoer(int oprId) throws DALException;
	List<OperatoerDTO> getOperatoerList() throws DALException;
	void createOperatoer(OperatoerDTO opr) throws DALException, cdio3.gwt.server.DALException;
	void updateOperatoer(OperatoerDTO opr) throws DALException;
	void deleteOperatoer(int oprID);
}
