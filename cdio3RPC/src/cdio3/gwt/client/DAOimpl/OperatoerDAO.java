package cdio3.gwt.client.DAOimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import cdio3.gwt.client.DAOinterface.*;
import cdio3.gwt.client.DAOinterface.DALException;
import cdio3.gwt.client.model.*;
import cdio3.gwt.server.*;

public class OperatoerDAO implements IOperatoerDAO {
	private static Connector db;

	public OperatoerDAO(Connector db) {
		OperatoerDAO.db = db;
	}

	public OperatoerDTO getOperatoer(int oprID) throws DALException {
		try {
			String toSQL = String.format("select * from 12_operator where oprId = %d;", oprId);
			ResultSet o = db.doQuery(toSQL);
			if (0 == o.length)
				throw new DALException(oprId);

			Object[][] in = db.executeQuery(String.format("select oprType from 12_operatorType where oprId = %d;", oprId));
			int antalType = in.length;
			
			String oprType = "";
			switch(antalType){
			case 4:
				oprType = "admin";
				break;
			case 3:
				oprType = "farma";
				break;
			case 2:
				oprType = "vaerk";
				break;
			case 1:
				if(((String) in[0][0]).equals("opera"))
					oprType = "opera";
				else
					oprType = "inaktiv";
				break;
			default:
				System.out.println("burde ikke kunne komme her ned");
			}

			return new OperatoerDTO(oprId, (String) o[0][1], (String) o[0][2], (String) o[0][3], (String) o[0][4], rettighedsniveau);
		} catch (SQLException e) {
			System.out.println("Anden SQL Exception");
			return null;
		}
	}

	public void createOperatoer(OperatoerDTO opr) throws PrimaryKeyException {
		try {
			String stem = "insert into 12_operator values(%d, '%s', '%s', '%s', '%s');";
			String toSQL = String.format(String.format(opr.getOprId(), opr.getOprNavn(), opr.getIni(), opr.getCpr(), opr.getPassword()));
			int changes = doUpdate(toSQL);

			if (0 == changes)
				throw new PrimaryKeyException(opr.getOprId());

			stem = "insert into 12_rettighedsniveau values(%d, '%s');";
			String rettighedsniveau = opr.getRettighedsniveau();
			int oprID = opr.getOprId();

			int type = 0;
			if (oprType.equals("admin"))
				type = 5;
			else if (oprType.equals("farma"))
				type = 4;
			else if (oprType.equals("vaerk"))
				type = 3;
			else if (oprType.equals("opera"))
				type = 2;
			else if (oprType.equals("inaktiv"))
				type = 1;

			switch (type) {
			case 5:
				toSQL = String.format(String.format(oprId, "admin"));
				db.doUpdate(toSQL);
			case 4:
				toSQL = String.format(String.format(oprId, "farma"));
				db.doUpdate(toSQL);
			case 3:
				toSQL = String.format(String.format(oprId, "vaerk"));
				db.doUpdate(toSQL);
			case 2:
				toSQL = String.format(String.format(oprId, "opera"));
				db.doUpdate(toSQL);
				break;
			case 1:
				toSQL = String.format(String.format(oprId, "inaktiv"));
				db.doUpdate(toSQL);
			default:
				System.out.println("Forkert opr type, benyt admin, farma of vaerk, opera eller inaktiv.");
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			String[] list = e.getLocalizedMessage().split("'");
			if (4 == list.length && list[3].equals("PRIMARY")) {
				System.out.println("brug en anden primær nøgle");
				throw new DALException(opr.getOprId());
			}
		} catch (SQLException e) {
			System.out.println("Anden SQL Exception");
		}
	}

	public void updateOperatoer(OperatoerDTO opr) throws DALExistException {
		try {
			String toSQL = String.format("update 12_operator set oprNavn = '%s', ini = '%s', cpr = '%s', password = '%s' where oprId = %d;",
					opr.getOprNavn(), opr.getIni(), opr.getCpr(), opr.getPassword(), opr.getOprId());
			int change = db.doUpdate(toSQL);
			if (0 == change)
				throw new DALExistException(opr.getOprId());

			int oprId = opr.getOprId();
			String stem = "delete from 12_operatorType where oprId = %d;";
			toSQL = String.format(stem, oprId);
			db.doUpdate(toSQL);

			stem = "insert into 12_rettighedniveau values(%d, '%s');";
			String rettighedsniveau = opr.getRettighedsniveau();

			int type = 0;
			if (rettighedsniveau.equals("admin"))
				type = 5;
			else if (rettighedsniveau.equals("farma"))
				type = 4;
			else if (rettighedsniveau.equals("vaerk"))
				type = 3;
			else if (rettighedsniveau.equals("opera"))
				type = 2;
			else if (rettighedsniveau.equals("inaktiv"))
				type = 1;

			switch (type) {
			case 5:
				toSQL = String.format(String.format(stem, oprId, "admin"));
				db.doUpdate(toSQL);
			case 4:
				toSQL = String.format(String.format(stem, oprId, "farma"));
				db.doUpdate(toSQL);
			case 3:
				toSQL = String.format(String.format(stem, oprId, "vaerk"));
				db.doUpdate(toSQL);
			case 2:
				toSQL = String.format(String.format(stem, oprId, "opera"));
				db.doUpdate(toSQL);
				break;
			case 1:
				toSQL = String.format(String.format(stem, oprId, "inaktiv"));
				db.doUpdate(toSQL);
			default:
				System.out.println("Forkert rettighedsniveau, benyt admin, farma og vaerk.");
			}
		} catch (SQLException e) {
			System.out.println("Anden SQL Exception");
		}
	}

	public List<OperatoerDTO> getOperatoerList() throws DALException {
		try {
			String toSQL = "select * from 12_operator;";
			ResultSet o = Connector.doQuery(toSQL);

			if (0 == o.length)
				throw new DALException(null);

			List<OperatoerDTO> list = new ArrayList<OperatoerDTO>();

			for (int i = 0; i < o.length; i++) {
				int oprId = (Integer) o[i][0];
				ResultSet in = Connector.doQuery(String.format("select oprType from 12_rettighedsniveau where oprId = %d;", oprId));
				int antalType = in.length;
				
				String oprType = "";
				switch(antalType){
				case 4:
					oprType = "admin";
					break;
				case 3:
					oprType = "farma";
					break;
				case 2:
					oprType = "vaerk";
					break;
				case 1:
					if(((String) in[0][0]).equals("opera"))
						oprType = "opera";
					else
						oprType = "inaktiv";
					break;
				default:
					System.out.println("burde ikke kunne komme her ned");
				}
				
				list.add(new OperatoerDTO(oprId, (String) o[i][1], (String) o[i][2], (String) o[i][3], (String) o[i][4], rettighedsniveau));
			}

			return list;
		} catch (SQLException e) {
			System.out.println("Anden SQL Exception");
		}
		return null;
	}
}
