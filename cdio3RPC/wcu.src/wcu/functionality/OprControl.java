package wcu.functionality;

import java.util.ArrayList;

import wcu.data.OperatoerDAO;
import wcu.data.OperatoerDTO;

public class OprControl implements OperatoerDAO{

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
			if (oprID == oDB.get(i).getOprID()){
				return oDB.get(i);
			}
		}
		hentOperatoer.setOprID(999999);
		return hentOperatoer;
	}

	@Override
	//	Skal Kigges på
	public void updateOperatoer(OperatoerDTO oprID) {
		for(int i = 0; i < oDB.size(); i++){
			if(oprID.getOprID() == oDB.get(i).getOprID()){
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
			if(oprID == oDB.get(i).getOprID()){
				oDB.remove(i);
				operatoerFil.writeOperatoerDB(oDB);
			}
		}
	}
}



