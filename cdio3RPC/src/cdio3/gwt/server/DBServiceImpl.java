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
		OperatoerDTO opr;
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
			opr = new OperatoerDTO(
								rs.getInt("opr_id"),
								rs.getString("opr_navn"),
								rs.getString("ini"),
								rs.getString("cpr"),
								rs.getString("password"),
								rs.getInt("rettighedsniveau"));
			return opr;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
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
				opr = new OperatoerDTO(
									rs.getInt("opr_id"),
									rs.getString("opr_navn"),
									rs.getString("ini"),
									rs.getString("cpr"),
									rs.getString("password"),
									rs.getInt("rettighedsniveau"));
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
					+ "VALUES(" + opr.getOprId() + ", '" 
								+ opr.getOprNavn() + "', '" 
								+ opr.getIni() + "', '" 
								+ opr.getCpr() + "', '" 
								+ opr.getPassword() + "'); "
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
				raa = new RaavareDTO(
									rs.getInt("raavare_id"),
									rs.getString("raavare_navn"));
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
					"INSERT INTO raavare(raavare_id, raavare_navn) "
					+ "VALUES(" + raa.getRaavareId() + ", '" 
								+ raa.getRaavareNavn() + "'); "
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
				rec = new ReceptDTO(
									rs.getInt("recept_id"),
									rs.getString("recept_navn"));
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
					+ "VALUES(" + rec.getReceptId() + ", '" 
								+ rec.getReceptNavn() + "'); "
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
				rb = new RaavareBatchDTO(
										rs.getInt("rb_id"),
										rs.getInt("raavare_id"),
										rs.getInt("maengde"),
										rs.getString("leverandoer"));
				rbList.add(rb);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rbList;
	}

	@SuppressWarnings("static-access")
	@Override
	public RaavareBatchDTO createRaavareBatch(RaavareBatchDTO rec) {
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
					"INSERT INTO raavarebatch(rb_id, raavare_id, maengde, leverandoer) "
					+ "VALUES(" + rec.getRbId() + ", " 
								+ rec.getRaavareId() + ", " 
								+ rec.getMaengde() + ", " 
								+ rec.getLeverandoer() + "); "
				);
		} catch (DALException e) {
			e.printStackTrace();
		}
		return rec;
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
				pb = new ProduktBatchDTO(
										rs.getInt("pb_id"),
										rs.getInt("recept_id"),
										rs.getInt("status"));
				pbList.add(pb);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pbList;
	}

	@SuppressWarnings("static-access")
	@Override
	public ProduktBatchDTO createProduktBatch(ProduktBatchDTO pb) {
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
					"INSERT INTO produktbatch(pb_id, status, recept_id) "
					+ "VALUES(" + pb.getPbId() + ", " + pb.getStatus() + ", " + pb.getReceptId() + "); "
				);
		} catch (DALException e) {
			e.printStackTrace();
		}
		return pb;
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
				pbk = new ProduktBatchKompDTO(
											rs.getInt("pb_id"),
											rs.getInt("rb_id"),
											rs.getDouble("tara"),
											rs.getDouble("netto"),
											rs.getInt("opr_id"));
				pbkList.add(pbk);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pbkList;
	}

	@SuppressWarnings("static-access")
	@Override
	public ProduktBatchKompDTO createProduktBatchKomponent(
			ProduktBatchKompDTO pb) {
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
					"INSERT INTO produktbatchkomponent(pb_id, rb_id, tara, netto, opr_id) "
					+ "VALUES('" + pb.getPbId() + "', '" 
								 + pb.getRbId() + "', '" 
								 + pb.getTara() + "', '" 
								 + pb.getNetto()+ "', '"
								 + pb.getOprId() + "'); "
					);
		} catch (DALException e) {
			e.printStackTrace();
		}
		return pb;
	}
}
