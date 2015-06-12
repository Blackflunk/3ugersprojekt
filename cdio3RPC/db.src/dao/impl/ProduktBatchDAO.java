package dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cdio3.gwt.server.Connector;
import cdio3.gwt.client.model.ProduktBatchDTO;
import cdio3.gwt.server.DALException;
import dao.interf.IProduktBatchDAO;

public class ProduktBatchDAO  implements IProduktBatchDAO{

	@Override
	public ProduktBatchDTO getProduktBatch(int pbId) throws DALException {
		ResultSet rs = Connector.doQuery("SELECT * FROM produktbatch WHERE pb_id = " + pbId);
	    try {
	    	if (!rs.first()) throw new DALException("ProduktBatch " + pbId + " findes ikke");
	    	return new ProduktBatchDTO (rs.getInt("pb_id"), rs.getInt("recept_id"), rs.getInt("status"));
	    }
	    catch (SQLException e) {throw new DALException(e); }
	}
	public void deleteProduktBatch(ProduktBatchDTO produktbatch) throws DALException {
		Connector.doUpdate("DELETE FROM produktbatch WHERE pb_id = " + produktbatch.getPbId());
	}

	@Override
	public List<ProduktBatchDTO> getProduktBatchList() throws DALException {
		List<ProduktBatchDTO> list = new ArrayList<ProduktBatchDTO>();
		ResultSet rs = Connector.doQuery("SELECT * FROM produktbatch");
		try
		{
			while (rs.next()) 
			{
				list.add(new ProduktBatchDTO(rs.getInt("pb_id"), rs.getInt("recept_id"), rs.getInt("status")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public void createProduktBatch(ProduktBatchDTO produktbatch)
			throws DALException {
		Connector.doUpdate(
				"INSERT INTO produktbatch(pb_id, recept_id, status) VALUES " +
				"(" + produktbatch.getPbId() + ", '" + produktbatch.getReceptId() + "', '" + produktbatch.getStatus() + "')"
			);
		
	}

	@Override
	public void updateProduktBatch(ProduktBatchDTO produktbatch)
			throws DALException {
		Connector.doUpdate(
				"UPDATE produktbatch SET  pb_id = '" + produktbatch.getPbId() + "', recept_id = '"  + produktbatch.getReceptId() + 
				"', status = '" + produktbatch.getStatus() + "' WHERE pb_id = " +
				produktbatch.getPbId()
		);
		
	}

}
