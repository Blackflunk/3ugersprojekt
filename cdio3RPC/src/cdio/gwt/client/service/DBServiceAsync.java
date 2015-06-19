package cdio.gwt.client.service;

import cdio.gwt.client.model.OperatoerDTO;
import cdio.gwt.client.model.ProduktBatchDTO;
import cdio.gwt.client.model.ProduktBatchKompDTO;
import cdio.gwt.client.model.RaavareBatchDTO;
import cdio.gwt.client.model.RaavareDTO;
import cdio.gwt.client.model.ReceptDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DBServiceAsync {
	//TODO mangler slet raavare, recept, raavarebatch, produktbatch, produktbatchkomp.

	@SuppressWarnings("rawtypes")
	void getUserStilling(String token, AsyncCallback callback);
	@SuppressWarnings("rawtypes")
	void validatePassword(String password, AsyncCallback callback);
	@SuppressWarnings("rawtypes")
	void checkIdExist(int id, String entity, AsyncCallback callback);
	@SuppressWarnings("rawtypes")
	void getUserRights(String token, AsyncCallback callback);
	@SuppressWarnings("rawtypes")
	void authenticateUser(String username, String password, AsyncCallback callback);
	@SuppressWarnings("rawtypes")
	void getUser(int OprId, String token, AsyncCallback callback);
	@SuppressWarnings("rawtypes")
	void getUserList(AsyncCallback callback);
	@SuppressWarnings("rawtypes")
	void deleteElement(int eId,String valg, AsyncCallback callback);
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
