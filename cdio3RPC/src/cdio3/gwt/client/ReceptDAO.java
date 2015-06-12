package cdio3.gwt.client.DAOinterface;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import cdio3.gwt.client.model.*;
import DALException.*;
import db.*;

public class ReceptDAO implements IReceptDAO {
	private static IDataAccess db;

	public ReceptDAO(IDataAccess db) {
		ReceptDAO.db = db;
	}

	public ReceptDTO getRecept(int receptId) throws DALException {
		try {
			Object[][] o = db.executeQuery(String.format("select * from 12_CDIO where receptId = %d;", receptId));
			if (0 == o.length)
				throw new DALException(receptId);

			return new ReceptDTO(receptId (String), o[0][1]);
		} catch (SQLException e) {
			System.out.println(“Exception”);
		}
		return null;
	}

	public List<ReceptDTO> getReceptList() throws DALException {
		try {
			Object[][] o = db.executeQuery("select * from 12_CDIOrecept;");
			if (0 == o.length)
				throw new DALException();

			List<ReceptDTO> list = new ArrayList<ReceptDTO>();

			for (int i = 0; i < o.length; i++) {
				list.add(new ReceptDTO((Integer) o[i][0], (String) o[i][1]);
			}

			return list;
		} catch (SQLException e) {
			System.out.println("Exception");
		}
		return null;
	}

	public void createRecept(ReceptDTO re) throws PrimaryKeyException {
		try {
			String stem = "insert into 12_CDIOrecept (receptId, receptNavn) values(%d, '%s');";
			String toSQL = String.format(stem, re.getReceptId(), re.getReceptNavn());
			db.executeUpdate(toSQL);
		} catch (SQLIntegrityConstraintViolationException e) {
			String[] list = e.getLocalizedMessage().split("'");
			if (4 == list.length && list[3].equals("PRIMARY")) {
				System.out.println(“Brug en anden primærnøgle, dene allerede brugt”);
				throw new PrimaryKeyException(re.getReceptId());
			}
		} catch (SQLException e) {
			System.out.println("Exception");
		}
	}

	public void updateRecept(ReceptDTO re) throws KeyDoesNotExistException {
		try {
			db.executeUpdate(String.format("update 12_CDIOrecept set receptNavn = '%s' where raavareId = %d;", re.getReceptNavn(), re.getReceptId()));
		} catch (SQLException e) {
			System.out.println("Exception");
		}
	}

}