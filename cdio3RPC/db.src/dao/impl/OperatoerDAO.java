package dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import cdio3.gwt.client.model.OperatoerDTO;
import cdio3.gwt.server.Connector;
import cdio3.gwt.server.DALException;
import dao.interf.IOperatoerDAO;

public class OperatoerDAO implements IOperatoerDAO {
	
	public OperatoerDAO() throws cdio3.gwt.server.DALException{

	}
	public OperatoerDTO getOperatoer(int oprId) throws DALException {
		ResultSet rs = Connector.doQuery("SELECT * FROM operatoer WHERE opr_id = " + oprId);
	    try {
	    	if (!rs.first()) throw new DALException("Operatoeren " + oprId + " findes ikke");
	    	return new OperatoerDTO (rs.getInt("opr_id"), rs.getString("opr_navn"), rs.getString("ini"), rs.getString("cpr"), rs.getString("password"), rs.getString("rettighedsniveau"));
	    }
	    catch (SQLException e) {throw new DALException(e); }
		
	}
	
	public void createOperatoer(OperatoerDTO opr) throws DALException, cdio3.gwt.server.DALException {	
		
			Connector.doUpdate(
				"call Operatoer('" + opr.getOprId() + "', '" + opr.getOprNavn() + "', '" + opr.getIni() + "', '" + 
				opr.getCpr() + "', '" + opr.getPassword() + "', '" + opr.getRettighedsniveau() + "')"
			);
	}
	
	public void updateOperatoer(OperatoerDTO opr) throws DALException {
		Connector.doUpdate(
				"UPDATE operatoer SET  opr_id = '" 
						+ opr.getOprId() + "', opr_navn = '" 
						+ opr.getOprNavn() + "', ini =  '" 
						+ opr.getIni() + "', cpr = '" 
						+ opr.getCpr() + "', password = '" 
						+ opr.getPassword() + "', rettighedsniveau ='" 
						+ opr.getRettighedsniveau() + "' WHERE opr_id = " 
						+ opr.getOprId()
		);
	}
	
	public List<OperatoerDTO> getOperatoerList() throws DALException {
		List<OperatoerDTO> list = new ArrayList<OperatoerDTO>();
		ResultSet rs = Connector.doQuery("SELECT * FROM operatoer");
		try
		{
			while (rs.next()) 
			{
				list.add(new OperatoerDTO(rs.getInt("opr_id"), rs.getString("opr_navn"), rs.getString("ini"), rs.getString("cpr"), rs.getString("password"), rs.getString("rettighedniveau")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}
	@Override
	public void deleteOperatoer(int oprID) throws DALException {
		Connector.doUpdate("UPDATE operatoer SET rettighedsniveau = 0 WHERE opr_id = " + oprID);	
	}
		
		
}
	
