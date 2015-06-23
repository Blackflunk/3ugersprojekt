package dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cdio3.gwt.client.model.RaavareBatchDTO;
import cdio3.gwt.server.Connector;
import cdio3.gwt.server.DALException;
import dao.interf.IRaavareBatchDAO;

public class RaavareBatchDAO implements IRaavareBatchDAO{

	@Override
	public RaavareBatchDTO getRaavareBatch(int rbId) throws DALException {
		ResultSet rs = Connector.doQuery("SELECT * FROM raavarebatch WHERE rb_id = " + rbId);
	    try {
	    	if (!rs.first()) throw new DALException("RaavareBatch " + rbId + " findes ikke");
	    	return new RaavareBatchDTO (rs.getInt("rb_id"), rs.getInt("raavare_id"), rs.getDouble("maengde"));
	    }
	    catch (SQLException e) {throw new DALException(e); }
	}

	@Override
	public List<RaavareBatchDTO> getRaavareBatchList() throws DALException {
		List<RaavareBatchDTO> list = new ArrayList<RaavareBatchDTO>();
		ResultSet rs = Connector.doQuery("SELECT * FROM raavarebatch");
		try
		{
			while (rs.next()) 
			{
				list.add(new RaavareBatchDTO(rs.getInt("pb_id"), rs.getInt("rb_id"), rs.getDouble("maengde")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public List<RaavareBatchDTO> getRaavareBatchList(int raavareId)
			throws DALException {
		List<RaavareBatchDTO> list = new ArrayList<RaavareBatchDTO>();
		ResultSet rs = Connector.doQuery("SELECT * FROM raavarebatch" + " WHERE raavare_id = " + raavareId);
		try
		{
			while (rs.next()) 
			{
				list.add(new RaavareBatchDTO(rs.getInt("rb_id"), rs.getInt("raavare_id"), rs.getDouble("maengde")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public void createRaavareBatch(RaavareBatchDTO raavarebatch)
			throws DALException {
		Connector.doUpdate(
				"INSERT INTO raavarebatch(rb_id, raavare_id, maengde) VALUES " +
				"(" + raavarebatch.getRbId() + ", '" + raavarebatch.getRaavareId() + "', '" + ", '" + raavarebatch.getMaengde());
	}

	@Override
	public void updateRaavareBatch(RaavareBatchDTO raavarebatch)
			throws DALException {
		Connector.doUpdate(
				"UPDATE raavarebatch SET  raavare_id = '" + raavarebatch.getRaavareId() + "', maengde = '" + raavarebatch.getMaengde() + "' WHERE rb_id = " +
				raavarebatch.getRbId()
		);
	}

}
