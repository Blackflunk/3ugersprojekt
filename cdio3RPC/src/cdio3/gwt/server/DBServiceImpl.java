package cdio3.gwt.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cdio3.gwt.server.Connector;
import cdio3.gwt.client.model.OperatoerDTO;
import cdio3.gwt.client.model.ProduktBatchDTO;
import cdio3.gwt.client.model.ProduktBatchKompDTO;
import cdio3.gwt.client.model.RaavareBatchDTO;
import cdio3.gwt.client.model.RaavareDTO;
import cdio3.gwt.client.model.ReceptDTO;
import cdio3.gwt.client.service.DBService;

@SuppressWarnings("serial")
public class DBServiceImpl extends RemoteServiceServlet implements DBService {
	
	//TODO mangler slet og create funktionalitet mange af de nye metoder.

	@SuppressWarnings("static-access")
	@Override
	public int authenticateUser(String username, String password) {
		ResultSet rs = null;
		int rettighedsniveau = 0;
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
			if(!rs.first()) rettighedsniveau = 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (rs.first()) rettighedsniveau = rs.getInt("rettighedsniveau");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rettighedsniveau;
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
				opr.setRettighedsniveau(rs.getInt("rettighedsniveau"));
				oprList.add(opr);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return oprList;
	}

	@SuppressWarnings("static-access")
	@Override
	public Boolean deleteElement(int eId,String valg) {
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
					"UPDATE operatoer "
				  + "SET rettighedsniveau = '0' "
				  + "WHERE opr_id = '" + eId + "'"
				  );
			return true;
			}
			catch (DALException e) {
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

	@SuppressWarnings("static-access")
	@Override
	public ArrayList<RaavareDTO> getRaavareList() {
		ResultSet rs = null;
		RaavareDTO raa = null;
		ArrayList<RaavareDTO> raaList = new ArrayList<RaavareDTO>();
		Connector conn = null;
		
		try {
			conn = new Connector();
			rs = conn.doQuery("SELECT * FROM raavare");
		}catch (DALException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(!rs.first()) return null;
			while(rs.next()){
				raa = new RaavareDTO();
				raa.setRaavareId(rs.getInt("raavare_id"));
				raa.setRaavareNavn(rs.getString("raavare_navn"));
				raa.setLeverandoer(rs.getString("leverandoer"));
				raaList.add(raa);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return raaList;
	}

	@SuppressWarnings("static-access")
	@Override
	public RaavareDTO createRaavare(RaavareDTO raa) {
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
					"INSERT INTO raavare(raavare_id, raavare_navn, leverandoer, cpr) "
					+ "VALUES(" + raa.getRaavareId() + ", '" + raa.getRaavareNavn() + "', '" + raa.getLeverandoer() + "'); "
				);
		} catch (DALException e) {
			e.printStackTrace();
		}
		return raa;
	}

	@SuppressWarnings("static-access")
	@Override
	public ArrayList<ReceptDTO> getReceptList() {
		ResultSet rs = null;
		ReceptDTO rec = null;
		ArrayList<ReceptDTO> recList = new ArrayList<ReceptDTO>();
		Connector conn = null;
		
		try {
			conn = new Connector();
			rs = conn.doQuery("SELECT * FROM recept");
		}catch (DALException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(!rs.first()) return null;
			while(rs.next()){
				rec = new ReceptDTO();
				rec.setReceptId(rs.getInt("recept_id"));
				rec.setReceptNavn(rs.getString("recept_navn"));
				recList.add(rec);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recList;
	}

	@SuppressWarnings("static-access")
	@Override
	public ReceptDTO createRecept(ReceptDTO rec) {
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
					"INSERT INTO recept(recept_id, recept_navn) "
					+ "VALUES(" + rec.getReceptId() + ", '" + rec.getReceptId() + "'); "
				);
		} catch (DALException e) {
			e.printStackTrace();
		}
		return rec;
	}

	@SuppressWarnings("static-access")
	@Override
	public ArrayList<RaavareBatchDTO> getRaavareBatchList() {
		ResultSet rs = null;
		RaavareBatchDTO rb = null;
		ArrayList<RaavareBatchDTO> rbList = new ArrayList<RaavareBatchDTO>();
		Connector conn = null;
		
		try {
			conn = new Connector();
			rs = conn.doQuery("SELECT * FROM raavarebatch");
		}catch (DALException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(!rs.first()) return null;
			while(rs.next()){
				rb = new RaavareBatchDTO();
				rb.setRbId(rs.getInt("rb_id"));
				rb.setRaavareId(rs.getInt("raavare_id"));
				rb.setMaengde(rs.getInt("maengde"));
				rbList.add(rb);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rbList;
	}

	@Override
	public RaavareBatchDTO createRaavareBatch(RaavareBatchDTO rec) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("static-access")
	@Override
	public ArrayList<ProduktBatchDTO> getProduktBatchList() {
		ResultSet rs = null;
		ProduktBatchDTO pb = null;
		ArrayList<ProduktBatchDTO> pbList = new ArrayList<ProduktBatchDTO>();
		Connector conn = null;
		
		try {
			conn = new Connector();
			rs = conn.doQuery("SELECT * FROM produktbatch");
		}catch (DALException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(!rs.first()) return null;
			while(rs.next()){
				pb = new ProduktBatchDTO();
				pb.setPbId(rs.getInt("pb_id"));
				pb.setReceptId(rs.getInt("recept_id"));
				pb.setStatus(rs.getInt("status"));
				pbList.add(pb);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pbList;
	}

	@Override
	public ProduktBatchDTO createProduktBatch(ProduktBatchDTO rec) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("static-access")
	@Override
	public ArrayList<ProduktBatchKompDTO> getProduktBatchKomponentList() {
		ResultSet rs = null;
		ProduktBatchKompDTO pbk = null;
		ArrayList<ProduktBatchKompDTO> pbkList = new ArrayList<ProduktBatchKompDTO>();
		Connector conn = null;
		
		try {
			conn = new Connector();
			rs = conn.doQuery("SELECT * FROM produktbatchkomponent");
		}catch (DALException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(!rs.first()) return null;
			while(rs.next()){
				pbk = new ProduktBatchKompDTO();
				pbk.setPbId(rs.getInt("pb_id"));
				pbk.setRbId(rs.getInt("recept_id"));
				pbk.setTara(rs.getDouble("status"));
				pbk.setNetto(rs.getDouble("netto"));
				pbk.setOprId(rs.getInt("opr_id"));
				pbkList.add(pbk);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pbkList;
	}

	@Override
	public ProduktBatchKompDTO createProduktBatchKomponent(
			ProduktBatchKompDTO rec) {
		// TODO Auto-generated method stub
		return null;
	}
}
