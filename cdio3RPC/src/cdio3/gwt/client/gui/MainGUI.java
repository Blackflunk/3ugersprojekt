package cdio3.gwt.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import cdio3.gwt.client.model.OperatoerDTO;
import cdio3.gwt.client.model.RaavareDTO;
import cdio3.gwt.client.service.DBServiceClientImpl;

public class MainGUI extends Composite {
	int rettighedsniveau = 0;
	
	private VerticalPanel vPanel = new VerticalPanel();
	private HorizontalPanel menupanel = new HorizontalPanel();
	private VerticalPanel contentpanel = new VerticalPanel();
	private VerticalPanel externalvpanel = new VerticalPanel();
	
	private Button openlogin;
	private Button opengetuser;
	private Button opengetuserlist;
	private Button opendeleteuser;
	private Button opencreateuser;
	private Button openupdateuser;
	
	private Label getuserlist = new Label("Nedenfor er brugerne opstillet: ");
	
	private Label brugerinaktiv = new Label("Brugeren du forsøger at logge på er inaktiv");

	private Label loginuseracc = new Label("Skriv brugernavn:");
	private TextBox userNameTxt;
	private Label loginuserpass = new Label("Skriv kodeord:");
	private TextBox userPwdTxt;
	
	private Label getusername = new Label("Skriv brugerens ID:");
	private TextBox getUserNameTxt;
	private Label deleteusertext = new Label("Skriv brugerens ID:");
	private TextBox deleteUserIdTxt;

	private Label createuserid = new Label("Skriv brugerens ID:");
	private TextBox addUserIdTxt;
	private Label createusername = new Label("Skriv brugerens navn:");
	private TextBox addUserNameTxt;
	private Label createuserini = new Label("Skriv brugerens initialer:");
	private TextBox addUserIniTxt;
	private Label createusercpr = new Label("Skriv brugerens CPR nummer: ");
	private TextBox addUserCprTxt;
	private Label createuserpass = new Label("Skriv brugerens kodeord: ");
	private TextBox addUserPwdTxt;
	
	private Label upuserid = new Label("Skriv brugerens ID: ");
	private TextBox upUserIdTxt;
	private Label upusername = new Label("Skriv brugerens nye navn: ");
	private TextBox upUserNameTxt;
	private Label upuserini = new Label("Skriv brugerens nye initialer: ");
	private TextBox upUserIniTxt;
	private Label upusercpr = new Label("Skriv brugerens nye CPR nummer ");
	private TextBox upUserCprTxt;
	private Label upuserpass = new Label("Skriv brugerens nye password: ");
	private TextBox upUserPwdTxt;
	
	
	private DBServiceClientImpl serviceImpl;
	
	public MainGUI(DBServiceClientImpl serviceImpl) {
		
		
		initWidget(this.vPanel);
		this.serviceImpl = serviceImpl;
		
		this.vPanel.add(menupanel);
		this.vPanel.add(contentpanel);
		this.vPanel.add(externalvpanel);
		
		startMenu();
		
	
	}
	
	private class AuthenticationClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			String username = userNameTxt.getText();
			String pwd = userPwdTxt.getText();
			serviceImpl.authenticateUser(username, pwd);
		}
	}
	
	private class createUserClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			OperatoerDTO opr = new OperatoerDTO();
			opr.setOprId(Integer.parseInt(addUserIdTxt.getText()));
			opr.setOprNavn(addUserNameTxt.getText());
			opr.setIni(addUserIniTxt.getText());
			opr.setCpr(addUserCprTxt.getText());
			opr.setPassword(addUserPwdTxt.getText());
			serviceImpl.createUser(opr);
		}
	}
	
	private class updateUserClickHandler implements ClickHandler {
		
		@Override
		public void onClick(ClickEvent event) {
			OperatoerDTO opr = new OperatoerDTO();
			opr.setOprId(Integer.parseInt(upUserIdTxt.getText()));
			opr.setOprNavn(upUserNameTxt.getText());
			opr.setIni(upUserIniTxt.getText());
			opr.setCpr(upUserCprTxt.getText());
			opr.setPassword(upUserPwdTxt.getText());
			serviceImpl.updateUser(opr);
		}
	}
	
	private class getUserClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			int oprId = Integer.parseInt(getUserNameTxt.getText());
			serviceImpl.getUser(oprId);
		}
	}
	
	private class getUserListClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			serviceImpl.getUserList();
		}
	}
	
	private class deleteUserClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			int oprId = Integer.parseInt(deleteUserIdTxt.getText());
			serviceImpl.deleteUser(oprId);
		}
	}

	public void authenticateOperatoer(int svar) {
		this.externalvpanel.clear();
		HTML html = new HTML();
		rettighedsniveau = svar;
		
		if (svar == 1){
		String code = "<b>Brugeren du forsøger at logge på er inaktiv.</b></br>";
		html.setHTML(code);
		this.externalvpanel.add(html);
		}
		
		if(rettighedsniveau == 4)
		adminMenu();
		this.contentpanel.clear();
		if (rettighedsniveau == 3)
		farmaceutMenu();
		this.contentpanel.clear();
		if (rettighedsniveau == 2)
		vaerkfoererMenu();
		this.contentpanel.clear();
		if (rettighedsniveau == 1)
		startMenu();
	}
	
	public void deletedOperatoer(boolean result) {
		this.externalvpanel.clear();
		HTML html = new HTML();
		
		String code = "<b>Bruger slettet:</b> " + result + "</br>";
		
		html.setHTML(code);
		this.externalvpanel.add(html);
	}
	
	public void displayOperatoer(OperatoerDTO info) {
		this.externalvpanel.clear();
		HTML html = new HTML();
		
		String code = "<b>ID:</b> " + info.getOprId() + "</br>";
		code = code + "<b>Navn:</b> " + info.getOprNavn() + "</br>";
		code = code + "<b>Ini:</b> " + info.getIni() + "</br>";
		code = code + "<b>Cpr:</b> " + info.getCpr() + "</br>";
		code = code + "<b>Password:</b> " + info.getPassword() + "</br>";
		
		html.setHTML(code);
		this.externalvpanel.add(html);
	}
	
	public void displayOperatoerListe(ArrayList<OperatoerDTO> oprList){
		this.externalvpanel.clear();
		for(int i = 0;i < oprList.size();i++){
			HTML html = new HTML();
			
			String code = "</br><b>ID:</b> " + oprList.get(i).getOprId() + "</br>";
			code = code + "<b>Navn:</b> " + oprList.get(i).getOprNavn() + "</br>";
			code = code + "<b>Ini:</b> " + oprList.get(i).getIni() + "</br>";
			code = code + "<b>Cpr:</b> " + oprList.get(i).getCpr() + "</br>";
			code = code + "<b>Password:</b> " + oprList.get(i).getPassword() + "</br>";
			code = code + "<b>Rettighedsniveau:</b> " + oprList.get(i).getRettighedsniveau() + "</br>";
			
			html.setHTML(code);
			this.externalvpanel.add(html);
		}
	}
	
	public void displayRaavareListe(ArrayList<RaavareDTO> raaList){
		this.externalvpanel.clear();
		for(int i = 0;i < raaList.size();i++){
			HTML html = new HTML();
			
			String code = "</br><b>ID:</b> " + raaList.get(i).getRaavareId() + "</br>";
			code = code + "<b>Navn:</b> " + raaList.get(i).getRaavareNavn() + "</br>";
			code = code + "<b>Leverandør:</b> " + raaList.get(i).getLeverandoer() + "</br>";

			
			html.setHTML(code);
			this.externalvpanel.add(html);
		}
	}
	
	private class openLoginClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			openLogin();
			
		}
	}
	
	private class openGetUserClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			openGetUser();
			
		}
	}
	
	private class openGetUserListClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			openGetUserList();
			
		}
	}
	
	private class openDeleteUserClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			openDeleteUser();
			
		}
	}
	
	private class openCreateUserClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			openCreateUser();
			
		}
	}
	
	private class openUpdateUserClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			openUpdateUser();
			
		}
	}
	
	private class openCreateRaavareClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			openCreateRaavare();
			
		}
	}
	
	private class openGetRaavareListClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			openGetRaavareList();
			
		}
	}
	
	public void openLogin(){
		this.externalvpanel.clear();
		this.contentpanel.clear();
		this.contentpanel.add(loginuseracc);
		userNameTxt = new TextBox();
		this.contentpanel.add(userNameTxt);
		
		this.contentpanel.add(loginuserpass);
		userPwdTxt = new TextBox();
		this.contentpanel.add(userPwdTxt);
		
		Button authenticateBtn = new Button("Login");
		authenticateBtn.addClickHandler(new AuthenticationClickHandler());
		this.contentpanel.add(authenticateBtn);
	}
	
	public void openGetUser(){
		this.externalvpanel.clear();
		this.contentpanel.clear();
		this.contentpanel.add(getusername);
		getUserNameTxt = new TextBox();
		this.contentpanel.add(getUserNameTxt);
		
		Button getUserBtn = new Button("OK");
		getUserBtn.addClickHandler(new getUserClickHandler());
		this.contentpanel.add(getUserBtn);
	}
	
	public void openGetUserList(){
		this.externalvpanel.clear();
		this.contentpanel.clear();
		this.contentpanel.add(getuserlist);
		serviceImpl.getUserList();
	}
	
	public void openDeleteUser(){
		this.externalvpanel.clear();
		this.contentpanel.clear();
		this.contentpanel.add(deleteusertext);
		deleteUserIdTxt = new TextBox();
		this.contentpanel.add(deleteUserIdTxt);
		
		Button deleteUserBtn = new Button("OK");
		deleteUserBtn.addClickHandler(new deleteUserClickHandler());
		this.contentpanel.add(deleteUserBtn);
	}
	
	public void openCreateUser(){
		this.externalvpanel.clear();
		this.contentpanel.clear();
		this.contentpanel.add(createuserid);
		addUserIdTxt = new TextBox();
		this.contentpanel.add(addUserIdTxt);
		
		this.contentpanel.add(createusername);
		addUserNameTxt = new TextBox();
		this.contentpanel.add(addUserNameTxt);
		
		this.contentpanel.add(createuserini);
		addUserIniTxt = new TextBox();
		this.contentpanel.add(addUserIniTxt);
		
		this.contentpanel.add(createusercpr);
		addUserCprTxt = new TextBox();
		this.contentpanel.add(addUserCprTxt);
		
		this.contentpanel.add(createuserpass);
		addUserPwdTxt = new TextBox();
		this.contentpanel.add(addUserPwdTxt);
		
		Button createUserBtn = new Button("OK");
		createUserBtn.addClickHandler(new createUserClickHandler());
		this.contentpanel.add(createUserBtn);	
	}
	
	public void openUpdateUser(){
		this.externalvpanel.clear();
		this.contentpanel.clear();
		this.contentpanel.add(upuserid);
		upUserIdTxt = new TextBox();
		this.contentpanel.add(upUserIdTxt);
		
		this.contentpanel.add(upusername);
		upUserNameTxt = new TextBox();
		this.contentpanel.add(upUserNameTxt);
		
		this.contentpanel.add(upuserini);
		upUserIniTxt = new TextBox();
		this.contentpanel.add(upUserIniTxt);
		
		this.contentpanel.add(upusercpr);
		upUserCprTxt = new TextBox();
		this.contentpanel.add(upUserCprTxt);
		
		this.contentpanel.add(upuserpass);
		upUserPwdTxt = new TextBox();
		this.contentpanel.add(upUserPwdTxt);
		
		Button updateUserBtn = new Button("OK");
		updateUserBtn.addClickHandler(new updateUserClickHandler());
		this.contentpanel.add(updateUserBtn);	
	}
	
	public void openGetRaavareList(){
		this.externalvpanel.clear();
		this.contentpanel.clear();
		this.contentpanel.add(getuserlist);
		serviceImpl.getRaavareList();
	}
	
	public void openCreateRaavare(){
		this.externalvpanel.clear();
		this.contentpanel.clear();
		this.contentpanel.add(createuserid);
		addUserIdTxt = new TextBox();
		this.contentpanel.add(addUserIdTxt);
		
		this.contentpanel.add(createusername);
		addUserNameTxt = new TextBox();
		this.contentpanel.add(addUserNameTxt);
		
		this.contentpanel.add(createuserini);
		addUserIniTxt = new TextBox();
		this.contentpanel.add(addUserIniTxt);
		
		this.contentpanel.add(createusercpr);
		addUserCprTxt = new TextBox();
		this.contentpanel.add(addUserCprTxt);
		
		this.contentpanel.add(createuserpass);
		addUserPwdTxt = new TextBox();
		this.contentpanel.add(addUserPwdTxt);
		
		Button createUserBtn = new Button("OK");
		createUserBtn.addClickHandler(new createUserClickHandler());
		this.contentpanel.add(createUserBtn);	
	}
	
	public void startMenu(){
		Button openlogin = new Button("Login");
		openlogin.addClickHandler(new openLoginClickHandler());
		this.menupanel.add(openlogin);
	}
	
	public void adminMenu(){
		this.menupanel.clear();
		
		Button opengetuser = new Button("Find user");
		opengetuser.addClickHandler(new openGetUserClickHandler());
		this.menupanel.add(opengetuser);
		
		Button opengetuserlist = new Button("Get userlist");
		opengetuserlist.addClickHandler(new openGetUserListClickHandler());
		this.menupanel.add(opengetuserlist);
		
		Button opendeleteuser = new Button("Delete user");
		opendeleteuser.addClickHandler(new openDeleteUserClickHandler());
		this.menupanel.add(opendeleteuser);
		
		Button openupdateuser = new Button("Update user");
		openupdateuser.addClickHandler(new openUpdateUserClickHandler());
		this.menupanel.add(openupdateuser);
		
		Button opencreateuser = new Button("Create user");
		opencreateuser.addClickHandler(new openCreateUserClickHandler());
		this.menupanel.add(opencreateuser);
	}
	
	public void farmaceutMenu(){
		this.menupanel.clear();
		
		Button openopretraavare = new Button("Opret Råvare");
		openopretraavare.addClickHandler(new openCreateRaavareClickHandler());
		this.menupanel.add(openopretraavare);
		
		Button opengetraavarelist = new Button("Vis Råvarer");
		opengetraavarelist.addClickHandler(new openGetRaavareListClickHandler());
		this.menupanel.add(opengetraavarelist);
		
		Button opendeleteraavare = new Button("Slet Råvare");
		opendeleteraavare.addClickHandler(new openGetUserListClickHandler());
		this.menupanel.add(opendeleteraavare);
		
		Button openopretrecept = new Button("Opret Recept");
		openopretrecept.addClickHandler(new openDeleteUserClickHandler());
		this.menupanel.add(openopretrecept);
		
		Button openreceptliste = new Button("Vis Recepter");
		openreceptliste.addClickHandler(new openUpdateUserClickHandler());
		this.menupanel.add(openreceptliste);
		
		Button opendeleterecept = new Button("Slet Recept");
		opendeleterecept.addClickHandler(new openCreateUserClickHandler());
		this.menupanel.add(opendeleterecept);
	}
	
	public void vaerkfoererMenu(){
		this.menupanel.clear();
		
		//TODO lav click handlers
		Button openopretraavarebatch = new Button("Opret Råvarebatch");
		openopretraavarebatch.addClickHandler(new openGetUserClickHandler());
		this.menupanel.add(openopretraavarebatch);
		
		Button opengetraavarebatchlist = new Button("Vis Råvarebatches");
		opengetraavarebatchlist.addClickHandler(new openGetUserListClickHandler());
		this.menupanel.add(opengetraavarebatchlist);
		
		Button opendeleteraavarebatch = new Button("Slet Råvarebatch");
		opendeleteraavarebatch.addClickHandler(new openGetUserListClickHandler());
		this.menupanel.add(opendeleteraavarebatch);
		
		Button opencreateproduktbatch = new Button("Opret Produktbatch");
		opencreateproduktbatch.addClickHandler(new openDeleteUserClickHandler());
		this.menupanel.add(opencreateproduktbatch);
		
		Button openproduktbatchlist = new Button("Vis Produktbatches");
		openproduktbatchlist.addClickHandler(new openUpdateUserClickHandler());
		this.menupanel.add(openproduktbatchlist);
		
		Button opendeleteproduktbatch = new Button("Slet Produktbatch");
		opendeleteproduktbatch.addClickHandler(new openCreateUserClickHandler());
		this.menupanel.add(opendeleteproduktbatch);
	}

}
