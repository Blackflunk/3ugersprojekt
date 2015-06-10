package cdio3.gwt.client.service;

import cdio3.gwt.client.model.OperatoerDTO;
import cdio3.gwt.client.model.ProduktBatchDTO;
import cdio3.gwt.client.model.ProduktBatchKompDTO;
import cdio3.gwt.client.model.RaavareBatchDTO;
import cdio3.gwt.client.model.RaavareDTO;
import cdio3.gwt.client.model.ReceptDTO;

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
	@SuppressWarnings("rawtypes")
	void getRaavareList(AsyncCallback callback);
	@SuppressWarnings("rawtypes")
	void createRaavare(RaavareDTO raa, AsyncCallback callback);
	@SuppressWarnings("rawtypes")
	void getReceptList(AsyncCallback callback);
	@SuppressWarnings("rawtypes")
	void createRecept(ReceptDTO rec,AsyncCallback callback);
	@SuppressWarnings("rawtypes")
	void getRaavareBatchList(AsyncCallback callback);
	@SuppressWarnings("rawtypes")
	void createRaavareBatch(RaavareBatchDTO rec,AsyncCallback callback);
	@SuppressWarnings("rawtypes")
	void getProduktBatchList(AsyncCallback callback);
	@SuppressWarnings("rawtypes")
	void createProduktBatch(ProduktBatchDTO rec,AsyncCallback callback);
	@SuppressWarnings("rawtypes")
	void getProduktBatchKomponentList(AsyncCallback callback);
	@SuppressWarnings("rawtypes")
	void createProduktBatchKomponent(ProduktBatchKompDTO rec,AsyncCallback callback);
	
}
