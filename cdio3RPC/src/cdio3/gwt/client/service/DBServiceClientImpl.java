package cdio3.gwt.client.service;

import java.util.ArrayList;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import cdio3.gwt.client.gui.MainGUI;
import cdio3.gwt.client.model.OperatoerDTO;
import cdio3.gwt.client.model.RaavareDTO;
import cdio3.gwt.client.model.ReceptDTO;

public class DBServiceClientImpl implements DBServiceClientInt {
	private DBServiceAsync service;
	private MainGUI maingui;
	private int rettighedsniveau = 0;
	
	
	public DBServiceClientImpl(String url) {
		System.out.println(url);
		this.service = GWT.create(DBService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) this.service;
		endpoint.setServiceEntryPoint(url);
		
		this.maingui = new MainGUI(this);
	}
	
	@Override
	public void authenticateUser(String username, String password){
		this.service.authenticateUser(username, password, new DefaultCallback());
	}
	@Override
	public void getUser(int oprId) {
		this.service.getUser(oprId, new DefaultCallback());
	}

	@Override
	public void getUserList() {
		this.service.getUserList(new DefaultCallback());
	}

	@Override
	public void deleteUser(int oprId) {
		this.service.deleteUser(oprId, new DefaultCallback());
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
				if(rettighedsniveau == 4){
				OperatoerDTO opr = (OperatoerDTO) result;
				maingui.displayOperatoer(opr);
				}
			}
			// Hvis der modtages integer (Login)
			else if(result instanceof Integer){
				if(rettighedsniveau == 0){
					Integer svar = (Integer) result;
					rettighedsniveau = svar;
					maingui.authenticateOperatoer(svar);
					}
				}
				else if(result instanceof Boolean){
				if (((ArrayList<?>)result).get(0) instanceof OperatoerDTO){
					boolean svar = (Boolean) result;
					maingui.deletedOperatoer(svar);
					}
				}
			else if(result instanceof ArrayList<?>){
				if (((ArrayList<?>)result).get(0) instanceof OperatoerDTO){
				ArrayList oprList = (ArrayList<OperatoerDTO>) result;
				maingui.displayOperatoerListe(oprList);
				}
				
				if (((ArrayList<?>)result).get(0) instanceof RaavareDTO){
					ArrayList raaList = (ArrayList<RaavareDTO>) result;
					maingui.displayRaavareListe(raaList);
			
				}
				
				if (((ArrayList<?>)result).get(0) instanceof ReceptDTO){
					ArrayList racList = (ArrayList<ReceptDTO>) result;
				
				}
			}
		}
	
	}
}
