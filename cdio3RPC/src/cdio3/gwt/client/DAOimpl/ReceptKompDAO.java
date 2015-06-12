package cdio3.gwt.client.DAOimpl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cdio3.gwt.server.Connector;
import cdio3.gwt.client.DAOinterface.DALException;
import cdio3.gwt.client.DAOinterface.IReceptKompDAO;
import cdio3.gwt.client.model.ReceptDTO;
import cdio3.gwt.client.model.ReceptKompDTO;
import cdio3.gwt.server.DALException;

public class ReceptKompDAO implements IReceptKompDAO {

	@Override
	public ReceptKompDTO getReceptKomp(int receptkompId, int raavareId)
			throws DALException {
		ReceptKompDTO resReceptKomp = null;
		ResultSet rs = Connector.doQuery("SELECT * FROM receptkomponent WHERE recept_id = " + receptkompId);
		try
		{
			while (rs.next()) 
			{
				
				resReceptKomp = new ReceptKompDTO(rs.getInt("recept_id"), rs.getInt("raavare_id"), rs.getDouble("nom_netto"), rs.getDouble("tolerance"));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return resReceptKomp;
	}
	
	public void deleteReceptKomp(ReceptKompDTO receptkomponent) throws DALException {
		Connector.doUpdate("DELETE FROM receptkomponent WHERE recept_id = " + receptkomponent.getReceptId());
	}
	
	@Override
	public List<ReceptKompDTO> getReceptKompList(int receptkompId)
			throws DALException {
		List<ReceptKompDTO> list = new ArrayList<ReceptKompDTO>();
		ResultSet rs = Connector.doQuery("SELECT * FROM receptkomponent WHERE recept_id = " + receptkompId);
		try
		{
			while (rs.next()) 
			{
				list.add(new ReceptKompDTO(rs.getInt("recept_id"), rs.getInt("raavare_id"), rs.getDouble("nom_netto"), rs.getDouble("tolerance")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public List<ReceptKompDTO> getReceptKompList() throws DALException {
		List<ReceptKompDTO> list = new ArrayList<ReceptKompDTO>();
		ResultSet rs = Connector.doQuery("SELECT * FROM receptkomponent");
		try
		{
			while (rs.next()) 
			{
				list.add(new ReceptKompDTO(rs.getInt("recept_id"), rs.getInt("raavare_id"), rs.getDouble("nom_netto"), rs.getDouble("tolerance")));
			}
		}
		catch (SQLException e) { throw new DALException(e); }
		return list;
	}

	@Override
	public void createReceptKomp(ReceptKompDTO receptkomponent)
			throws DALException {
		Connector.doUpdate(
				"INSERT INTO receptkomponent(receptkomp_id, receptkomp_navn, nom_netto, tolerance) VALUES " +
				"(" + receptkomponent.getReceptId() + ", '" + receptkomponent.getRaavareId() + ", '" + receptkomponent.getNomNetto() 
				+ ", '" + receptkomponent.getTolerance() + "')"
			);
	}

	@Override
	public void updateReceptKomp(ReceptKompDTO receptkomponent)
			throws DALException {
		Connector.doUpdate(
				"UPDATE receptkomponent SET  raavare_id = '" + receptkomponent.getRaavareId() + "', nom_netto = '" + receptkomponent.getNomNetto() + "', tolerance = '" 
				+ receptkomponent.getTolerance() + "' WHERE recept_id = " + receptkomponent.getReceptId()
		);
	}

}
