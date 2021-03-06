package cdio3.gwt.client.service;

import java.util.ArrayList;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import cdio3.gwt.client.gui.MainGUI;
import cdio3.gwt.client.model.OperatoerDTO;
import cdio3.gwt.client.model.ProduktBatchDTO;
import cdio3.gwt.client.model.ProduktBatchKompDTO;
import cdio3.gwt.client.model.RaavareBatchDTO;
import cdio3.gwt.client.model.RaavareDTO;
import cdio3.gwt.client.model.ReceptDTO;

public class DBServiceClientImpl implements DBServiceClientInt {
	private DBServiceAsync service;
	private MainGUI maingui;
	private int rettighedsniveau = 0;
	String token = "";
	private String authenticateCallback = "";
	private String stillingCallback = "";
	private String getUserCallback = "";
	private String validateCallback = "";
	private String deleteCallback = "";
	private String entity = "";
	
	//TODO mangler metoder som slet på alle.
	
	
	public DBServiceClientImpl(String url) {
		System.out.println(url);
		this.service = GWT.create(DBService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) this.service;
		endpoint.setServiceEntryPoint(url);
		
		this.maingui = new MainGUI(this);
	}

	@Override
	public void validatePassword(String password){
		this.service.validatePassword(password, new DefaultCallback());
		validateCallback = "yes";
	}
	
	@Override
	public void checkIdExist(int id, String entity) {
		this.entity = entity;
		this.service.checkIdExist(id, entity, new DefaultCallback());
	}
	
	@Override
	public void authenticateUser(String username, String password){
		authenticateCallback = "yes";
		this.service.authenticateUser(username, password, new DefaultCallback());
	}
	@Override
	public void getStilling(String token){
		stillingCallback = "yes";
		this.service.getUserStilling(token, new DefaultCallback());
	}
	@Override
	public void getUser(int oprId, String token) {
		this.service.getUser(oprId, token, new DefaultCallback());
	}

	@Override
	public void getUserList() {
		this.service.getUserList(new DefaultCallback());
	}
	
	@Override
	public void deleteElement(int eId, String valg) {
		this.service.deleteElement(eId, valg, new DefaultCallback());
		deleteCallback = "yes";
	}

	@Override
	public void createUser(OperatoerDTO opr) {
		this.service.createUser(opr, new DefaultCallback());
	}

	@Override
	public void updateUser(OperatoerDTO opr) {
		this.service.updateUser(opr, new DefaultCallback());
	}
	
	public MainGUI getMainGUI(){
		return this.maingui;
	}
	
	@Override
	public void getRaavareList() {
		this.service.getRaavareList(new DefaultCallback());
		
	}

	@Override
	public void createRaavare(RaavareDTO raa) {
		this.service.createRaavare(raa,new DefaultCallback());
		
	}

	@Override
	public void getReceptList() {
		this.service.getReceptList(new DefaultCallback());
		
	}

	@Override
	public void createRecept(ReceptDTO rec) {
		this.service.createRecept(rec,new DefaultCallback());
	}
	
	@Override
	public void getRaavareBatchList() {
		this.service.getRaavareBatchList(new DefaultCallback());
		
	}

	@Override
	public void createRaavareBatch(RaavareBatchDTO rab) {
		this.service.createRaavareBatch(rab, new DefaultCallback());
		
	}

	@Override
	public void getProduktBatchList() {
		this.service.getProduktBatchList(new DefaultCallback());
		
	}

	@Override
	public void createProduktBatch(ProduktBatchDTO prb) {
		this.service.createProduktBatch(prb, new DefaultCallback());
		
	}

	@Override
	public void getProduktBatchKompList() {
		this.service.getProduktBatchKomponentList(new DefaultCallback());
		
	}
	
	@Override
	public void getUserRights(String token) {
		getUserCallback = "yes";
		this.service.getUserRights(token, new DefaultCallback());
		
	}
	
	@SuppressWarnings("rawtypes")
	private class DefaultCallback implements AsyncCallback {

		@Override
		public void onFailure(Throwable caught) {
			System.out.println("An Error has occured");
		}

		@SuppressWarnings("unchecked")
		@Override
		public void onSuccess(Object result) {

			if(result instanceof OperatoerDTO){
				OperatoerDTO opr = (OperatoerDTO) result;
				maingui.displayOperatoer(opr);
			}
			else if(result instanceof RaavareDTO){
				RaavareDTO raa = (RaavareDTO) result;
				maingui.displayRaavare(raa);
			}
			else if(result instanceof ReceptDTO){
				if(rettighedsniveau == 4){
				ReceptDTO rec = (ReceptDTO) result;
				maingui.displayRecept(rec);
				}
			}
			else if(result instanceof RaavareBatchDTO){
				RaavareBatchDTO rab = (RaavareBatchDTO) result;
				maingui.displayRaavareBatch(rab);
			}
			else if(result instanceof ProduktBatchDTO){
				ProduktBatchDTO pb = (ProduktBatchDTO) result;
				maingui.displayProduktBatch(pb);
			}
			// Hvis der modtages integer (Login)
			else if(result instanceof String){
				String rettighedsniveau = (String) result;
				if(authenticateCallback.equals("yes")){
				authenticateCallback = "";
				token = rettighedsniveau;
				maingui.authenticateOperatoer(rettighedsniveau);
				}
				else if(getUserCallback.equals("yes")){maingui.authenticateOperatoer(rettighedsniveau);getUserCallback = "";}
				else if(stillingCallback.equals("yes")){maingui.setStilling(rettighedsniveau);stillingCallback = "";}
			}
			else if(result instanceof Boolean){
					boolean svar = (Boolean) result;
					if(deleteCallback.equals("yes")){maingui.deletedElement(svar);deleteCallback = "";}
					if(validateCallback.equals("yes")){maingui.validatePassword(svar);validateCallback = "";}
			}
			else if(result instanceof Integer){
				int svar = (Integer) result;
				if(entity.equals("operatoer")){maingui.checkUserId(svar);}
				if(entity.equals("raavare")){maingui.checkRaavareId(svar);}
				if(entity.equals("raavarebatch")){maingui.checkRaavareBatchId(svar);}
				if(entity.equals("recept")){maingui.checkReceptId(svar);}
				if(entity.equals("produktbatch")){maingui.checkProduktBatchId(svar);}
			}
		
			//Listerne
			else if(result instanceof ArrayList<?>){
				if (((ArrayList<?>)result).get(0) instanceof OperatoerDTO){
				ArrayList<OperatoerDTO> oprList = (ArrayList<OperatoerDTO>) result;
				maingui.displayOperatoerListe(oprList);
				}
				
				if (((ArrayList<?>)result).get(0) instanceof RaavareDTO){
					ArrayList raaList = (ArrayList<RaavareDTO>) result;
					maingui.displayRaavareListe(raaList);
				}
				if (((ArrayList<?>)result).get(0) instanceof ReceptDTO){
					ArrayList racList = (ArrayList<ReceptDTO>) result;
					maingui.displayReceptListe(racList);
				}
				if (((ArrayList<?>)result).get(0) instanceof RaavareBatchDTO){
					ArrayList raab = (ArrayList<RaavareBatchDTO>) result;
					maingui.displayRaavareBatchListe(raab);
				}
				if (((ArrayList<?>)result).get(0) instanceof ProduktBatchDTO){
					ArrayList pb = (ArrayList<ProduktBatchDTO>) result;
					maingui.displayProduktBatchListe(pb);
				}
				if (((ArrayList<?>)result).get(0) instanceof ProduktBatchKompDTO){
					ArrayList pbk = (ArrayList<ProduktBatchKompDTO>) result;
					maingui.displayProduktBatchKompListe(pbk);
				}
			}
		}
	}
}
