package wcu.functionality;

import java.util.ArrayList;

import dao.interf.IOperatoerDAO;
import cdio3.gwt.client.model.OperatoerDTO;

public class OprControl implements IOperatoerDAO{

	FileHandler operatoerFil;
	ArrayList<OperatoerDTO> oDB = new ArrayList<OperatoerDTO>();

	public OprControl() {

		operatoerFil = new FileHandler();
		oDB = operatoerFil.readOperatoerDB();

	}

	@Override
	public OperatoerDTO getOperatoer(int oprID) {
		OperatoerDTO hentOperatoer = new OperatoerDTO();
		for (int i = 0; i < oDB.size(); i ++){
			if (oprID == oDB.get(i).getOprId()){
				return oDB.get(i);
			}
		}
		hentOperatoer.setOprId(999999);
		return hentOperatoer;
	}

	@Override
	//	Skal Kigges på
	public void updateOperatoer(OperatoerDTO oprID) {
		for(int i = 0; i < oDB.size(); i++){
			if(oprID.getOprId() == oDB.get(i).getOprId()){
				oDB.set(i, oprID);
			}
		}
		operatoerFil.writeOperatoerDB(oDB);
	}

	@Override
	public ArrayList<OperatoerDTO> getOperatoerList() {
		return oDB;
	}

	@Override
	//	Skal kigges på
	public void createOperatoer(OperatoerDTO opr) {
				oDB.add(opr);
				operatoerFil.writeOperatoerDB(oDB);
	}

	@Override

	public void deleteOperatoer(int oprID) {
		for(int i = 0; i < oDB.size(); i++){
			if(oprID == oDB.get(i).getOprId()){
				oDB.remove(i);
				operatoerFil.writeOperatoerDB(oDB);
			}
		}
	}
}



