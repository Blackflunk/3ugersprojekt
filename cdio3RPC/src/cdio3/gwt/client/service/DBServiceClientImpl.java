package cdio3.gwt.client.service;

import java.util.ArrayList;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import cdio3.gwt.client.gui.MainGUI;
import cdio3.gwt.client.model.OperatoerDTO;

public class DBServiceClientImpl implements DBServiceClientInt {
	private DBServiceAsync service;
	private MainGUI maingui;
	private int loggedin = 0;
	
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
			else if(result instanceof Integer){
				if(loggedin == 0){
					Integer svar = (Integer) result;
					maingui.authenticateOperatoer(svar);
					}
				}
				else if(result instanceof Boolean){
					boolean svar = (Boolean) result;
					maingui.deletedOperatoer(svar);
				}
			else if(result instanceof ArrayList<?>){
				ArrayList oprList = (ArrayList<OperatoerDTO>) result;
				maingui.displayOperatoerListe(oprList);
			}
		}
	
	}
}
