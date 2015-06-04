package cdio3.gwt.client.service;

import cdio3.gwt.client.model.OperatoerDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DBServiceAsync {

	@SuppressWarnings("rawtypes")
	void authenticateUser(String username, String password, AsyncCallback callback);
	@SuppressWarnings("rawtypes")
	void getUser(int oprId, AsyncCallback callback);
	@SuppressWarnings("rawtypes")
	void getUserList(AsyncCallback callback);
	@SuppressWarnings("rawtypes")
	void deleteUser(int oprId, AsyncCallback callback);
	@SuppressWarnings("rawtypes")
	void createUser(OperatoerDTO opr, AsyncCallback callback);
	@SuppressWarnings("rawtypes")
	void updateUser(OperatoerDTO opr, AsyncCallback callback);
}
