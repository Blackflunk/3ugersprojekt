package dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cdio3.gwt.client.model.RaavareBatchDTO;
import cdio3.gwt.client.model.RaavareDTO;
import cdio3.gwt.server.Connector;
import cdio3.gwt.server.DALException;
import dao.interf.IRaavareDAO;

public class RaavareDAO implements IRaavareDAO{
	
	public RaavareDAO() throws DALException{
		Connector.doUpdate("DROP VIEW IF EXISTS vare;");
		String vie = "CREATE VIEW vare AS SELECT raavare_id, raavare_navn, leverandoer FROM raavare;";
		Connector.doUpdate(vie);
	}

	@Override
	public RaavareDTO getRaavare(int raavareId) throws DALException {
		// Benytter view "vare" istedet for "raavare"
		ResultSet rs = Connector.doQuery("SELECT * FROM vare WHERE raavare_id = " + raavareId);
	    try {
	    	if (!rs.first()) throw new DALException("Raavare " + raavareId + " findes ikke");
	    	return new RaavareDTO (rs.getInt("raavare_id"), rs.getString("raavare_navn"), rs.getString("leverandoer"));
	    }
	    catch (SQLException e) {throw new DALException(e); }
	}
	
	@Override
	public List<RaavareDTO> getRaavareList() throws DALException {
		List<RaavareDTO> list = new ArrayList<RaavareDTO>();
		// Benytter view "vare" istedet for "raavare"
		ResultSet rs = Connector.doQuery("SELECT * FROM vare");
		try
		{
			while (rs.next()) 
			{
				list.add(new RaavareDTO(rs.getInt("raavare_id"), rs.getString("raavare_navn"), rs.getString("leverandoer")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public void createRaavare(RaavareDTO raavare) throws DALException {
		Connector.doUpdate(
				"INSERT INTO raavare(raavare_id, raavare_navn, leverandoer) VALUES " +
				"(" + raavare.getRaavareId() + ", '" + raavare.getRaavareNavn() + ", '" + raavare.getLeverandoer() + "')"
			);
	}

	@Override
	public void updateRaavare(RaavareDTO raavare) throws DALException {
		Connector.doUpdate(
				"UPDATE raavare SET raavare_navn = '" + raavare.getRaavareNavn() + "', leverandoer = '" + raavare.getLeverandoer() + "' WHERE raavare_id = " +
				raavare.getRaavareId()
		);
	}

}
