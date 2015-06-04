package cdio3.gwt.client.service;

import cdio3.gwt.client.model.OperatoerDTO;

public interface DBServiceClientInt {

	void authenticateUser(String username, String password);
	void getUser(int oprId);
	void getUserList();
	void deleteUser(int oprId);
	void createUser(OperatoerDTO opr);
	void updateUser(OperatoerDTO opr);
}
