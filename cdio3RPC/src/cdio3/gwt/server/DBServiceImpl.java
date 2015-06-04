package cdio3.gwt.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cdio3.gwt.server.Connector;
import cdio3.gwt.client.model.OperatoerDTO;
import cdio3.gwt.client.service.DBService;

@SuppressWarnings("serial")
public class DBServiceImpl extends RemoteServiceServlet implements DBService {

	@SuppressWarnings("static-access")
	@Override
	public Boolean authenticateUser(String username, String password) {
		ResultSet rs = null;
		try {
			Connector conn = new Connector();
			rs = conn.doQuery("SELECT * FROM operatoer WHERE opr_navn = \"" + username + "\" AND password = \"" + password + "\"");
		} catch (DALException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(!rs.first()) return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	@SuppressWarnings("static-access")
	@Override
	public OperatoerDTO getUser(int oprId) {
		ResultSet rs = null;
		OperatoerDTO opr = new OperatoerDTO();
		try {
			Connector conn = new Connector();
			rs = conn.doQuery("SELECT * FROM operatoer WHERE opr_id = \"" + oprId + "\"");
		} catch (DALException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(!rs.first()) return null;
			opr.setOprId(rs.getInt("opr_id"));
			opr.setOprNavn(rs.getString("opr_navn"));
			opr.setIni(rs.getString("ini"));
			opr.setCpr(rs.getString("cpr"));
			opr.setPassword(rs.getString("password"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return opr;
	}

	@SuppressWarnings("static-access")
	@Override
	public ArrayList<OperatoerDTO> getUserList() {
		ResultSet rs = null;
		OperatoerDTO opr = null;
		ArrayList<OperatoerDTO> oprList = new ArrayList<OperatoerDTO>();
		
		try {
			Connector conn = new Connector();
			rs = conn.doQuery("SELECT * FROM operatoer");
		} catch (DALException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(!rs.first()) return null;
			while(rs.next()){
				opr = new OperatoerDTO();
				opr.setOprId(rs.getInt("opr_id"));
				opr.setOprNavn(rs.getString("opr_navn"));
				opr.setIni(rs.getString("ini"));
				opr.setCpr(rs.getString("cpr"));
				opr.setPassword(rs.getString("password"));
				oprList.add(opr);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return oprList;
	}

	@SuppressWarnings("static-access")
	@Override
	public Boolean deleteUser(int oprId) {
		Connector conn = null;
		try {
			conn = new Connector();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.doUpdate("DELETE FROM operatoer WHERE opr_id = " + oprId);
			return true;
		} catch (DALException e) {
			e.printStackTrace();
		}
		return false;
	}

	@SuppressWarnings("static-access")
	@Override
	public OperatoerDTO createUser(OperatoerDTO opr) {
		Connector conn = null;
		try {
			conn = new Connector();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.doUpdate(
					"INSERT INTO operatoer(opr_id, opr_navn, ini, cpr, password) "
					+ "VALUES(" + opr.getOprId() + ", '" + opr.getOprNavn() + "', '" + opr.getIni() 
					+ "', '" + opr.getCpr() + "', '" + opr.getPassword() + "'); "
				);
		} catch (DALException e) {
			e.printStackTrace();
		}
		return opr;
	}

	@SuppressWarnings("static-access")
	@Override
	public OperatoerDTO updateUser(OperatoerDTO opr) {
		Connector conn = null;
		try {
			conn = new Connector();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.doUpdate(
					"UPDATE operatoer SET  opr_navn = '" + opr.getOprNavn() +
					"', ini = '" + opr.getIni() + 
					"', cpr = '" + opr.getCpr() + 
					"', password = '" +  opr.getPassword() + 
					"' WHERE opr_id = \"" + opr.getOprId() + "\""	
					);
		} catch (DALException e) {
			e.printStackTrace();
		}
		return opr;
	}
}
