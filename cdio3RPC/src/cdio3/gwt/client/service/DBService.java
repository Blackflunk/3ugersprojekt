package cdio3.gwt.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import cdio3.gwt.client.model.OperatoerDTO;

@RemoteServiceRelativePath("dbservice")
public interface DBService extends RemoteService {
	
	int authenticateUser(String username, String password);
	OperatoerDTO getUser(int oprId);
	ArrayList<OperatoerDTO> getUserList();
	Boolean deleteUser(int oprId);
	OperatoerDTO createUser(OperatoerDTO opr);
	OperatoerDTO updateUser(OperatoerDTO opr);
}
