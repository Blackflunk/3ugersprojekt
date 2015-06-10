package cdio3.gwt.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import cdio3.gwt.client.model.OperatoerDTO;
import cdio3.gwt.client.model.RaavareDTO;
import cdio3.gwt.client.model.ReceptDTO;

@RemoteServiceRelativePath("dbservice")
public interface DBService extends RemoteService {
	
	int authenticateUser(String username, String password);
	OperatoerDTO getUser(int oprId);
	ArrayList<OperatoerDTO> getUserList();
	Boolean deleteUser(int oprId);
	OperatoerDTO createUser(OperatoerDTO opr);
	OperatoerDTO updateUser(OperatoerDTO opr);
	ArrayList<RaavareDTO> getRaavareList();
	RaavareDTO createRaavare(RaavareDTO raa);
	ArrayList<ReceptDTO> getReceptList();
	ReceptDTO createRecept(ReceptDTO rec);
}
