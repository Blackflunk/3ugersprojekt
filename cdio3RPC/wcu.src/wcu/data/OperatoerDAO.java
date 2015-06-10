package wcu.data;

import java.util.ArrayList;

public interface OperatoerDAO {
	public OperatoerDTO getOperatoer(int oprID);
	public void updateOperatoer(OperatoerDTO oprID);
	public void createOperatoer(OperatoerDTO opr);
	public void deleteOperatoer(int oprID);
	public ArrayList<OperatoerDTO> getOperatoerList();
}
