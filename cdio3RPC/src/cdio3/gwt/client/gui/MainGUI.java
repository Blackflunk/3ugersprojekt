package cdio3.gwt.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import cdio3.gwt.client.model.OperatoerDTO;
import cdio3.gwt.client.model.ProduktBatchDTO;
import cdio3.gwt.client.model.ProduktBatchKompDTO;
import cdio3.gwt.client.model.RaavareBatchDTO;
import cdio3.gwt.client.model.RaavareDTO;
import cdio3.gwt.client.model.ReceptDTO;
import cdio3.gwt.client.service.DBServiceClientImpl;
import cdio3.gwt.server.PwdFunctions;

public class MainGUI extends Composite {
	String rettighedsniveau = "0";
	String token = "";

	private AbsolutePanel vPanel = new AbsolutePanel();
	private VerticalPanel menupanel = new VerticalPanel();
	private HorizontalPanel submenupanel = new HorizontalPanel();
	private VerticalPanel contentpanel = new VerticalPanel();
	private VerticalPanel externalvpanel = new VerticalPanel();

	private Button openlogin;
	private Button opengetuser;
	private Button opengetuserlist;
	private Button opendeleteuser;
	private Button opencreateuser;
	private Button openupdateuser;

	private Label getuserlist = new Label("Nedenfor er brugerne opstillet: ");
	private Label getraavarelist = new Label("Nedenfor er råvarerne opstillet: ");
	private Label getreceptlist = new Label("Nedenfor er recepterne opstillet: ");
	private Label getraavarebatchlist = new Label("Nedenfor er råvarebatchesne opstillet:");
	private Label getproduktbatchlist = new Label("Nedenfor er produktbatchesne opstillet:");
	private Label getproduktbatchkomplist = new Label("Nedenfor er produktbatchkomponenterne opstillet: ");

	private Label brugerinaktiv = new Label("Brugeren eksisterer ikke.");

	private Label loginuseracc = new Label("Skriv brugernavn:");
	private TextBox userNameTxt;
	private Label loginuserpass = new Label("Skriv kodeord:");
	private TextBox userPwdTxt;

	private Label getusername = new Label("Skriv brugerens ID: (KUN TAL)");
	private TextBox getUserNameTxt;
	private Label deleteusertext = new Label("Skriv brugerens ID: (KUN TAL)");
	private TextBox deleteUserIdTxt;

	private Label createuserid = new Label("Skriv brugerens ID: (KUN TAL)");
	private TextBox addUserIdTxt;
	private Label createusername = new Label("Skriv brugerens navn: ");
	private TextBox addUserNameTxt;
	private Label createuserini = new Label("Skriv brugerens initialer:");
	private TextBox addUserIniTxt;
	private Label createusercpr = new Label("Skriv brugerens CPR nummer: ");
	private TextBox addUserCprTxt;
	private Label createuserpass = new Label("Skriv brugerens kodeord: ");
	private TextBox addUserPwdTxt;
	private Label createuserret = new Label("Skriv brugerens rettighedsniveau: (KUN TAL)");
	private TextBox addUserRetTxt;

	private Label createraavareid = new Label("Skriv råvarens id: (KUN TAL)");
	private TextBox addRaavareIdTxt;
	private Label createraavarenavn = new Label("Skriv råvarens navn: ");
	private TextBox addRaavareNavnTxt;
	private Label createraavareleverandoer = new Label("Skriv leverandør: ");
	private TextBox addRaavareLeverandoerTxt;

	private Label createreceptid = new Label("Skriv receptens id: (KUN TAL)");
	private TextBox addReceptIdTxt;
	private Label createreceptnavn = new Label("Skriv receptens navn: ");
	private TextBox addReceptNavnTxt;

	private Label createraavarebatchid = new Label("Skriv råvarebatchens id: (KUN TAL)");
	private TextBox addRaavareBatchIdTxt;
	private Label createraavarebatchraavareid = new Label("Skriv råvarens ID: (KUN TAL)");
	private TextBox addRaavareBatchRaavareIdTxt;
	private Label createraavarebatchmaengde = new Label("Skriv mængden: ");
	private TextBox addRaavareBatchMaengdeTxt;

	private Label createproduktbatchid = new Label("Skriv produktbatchens ID: (KUN TAL)");
	private TextBox addProduktBatchIdTxt;
	private Label createproduktbatchstatus = new Label("Skriv status på produktbatchen: ");
	private TextBox addProduktBatchStatusTxt;
	private Label createproduktbatchreceptid = new Label("Skriv produktbatchens recept ID: (KUN TAL)");
	private TextBox addProduktBatchReceptIdTxt;

	private Label upuserid = new Label("Skriv brugerens ID: (KUN TAL)");
	private TextBox upUserIdTxt;
	private Label upusername = new Label("Skriv brugerens nye navn: ");
	private TextBox upUserNameTxt;
	private Label upuserini = new Label("Skriv brugerens nye initialer: ");
	private TextBox upUserIniTxt;
	private Label upusercpr = new Label("Skriv brugerens nye CPR nummer ");
	private TextBox upUserCprTxt;
	private Label upuserpass = new Label("Skriv brugerens nye password: ");
	private TextBox upUserPwdTxt;
	private Label upuserret = new Label("Skriv brugerens rettighedsniveau: (KUN TAL)");
	private TextBox upUserRetTxt;


	private DBServiceClientImpl serviceImpl;

	public MainGUI(DBServiceClientImpl serviceImpl) {
		vPanel.setStyleName("page-style");
		initWidget(this.vPanel);
		this.serviceImpl = serviceImpl;

		submenupanel.setStyleName("submenu-style");
		this.vPanel.add(submenupanel);
		menupanel.setStyleName("menu-style");
		this.vPanel.add(menupanel);
		contentpanel.setStyleName("content-style");
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
			serviceImpl.getUserID(Integer.parseInt(addUserIdTxt.getText()));
		}
	}

	private class createRaavareClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RaavareDTO raa = new RaavareDTO();
			raa.setRaavareId(Integer.parseInt(addRaavareIdTxt.getText()));
			raa.setRaavareNavn(addRaavareNavnTxt.getText());
			raa.setLeverandoer(addRaavareLeverandoerTxt.getText());
			serviceImpl.createRaavare(raa);
		}
	}

	private class createReceptClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			ReceptDTO rac = new ReceptDTO();
			rac.setReceptId(Integer.parseInt(addReceptIdTxt.getText()));
			rac.setReceptNavn(addReceptNavnTxt.getText());
			serviceImpl.createRecept(rac);
		}
	}

	private class createRaavareBatchClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RaavareBatchDTO rab = new RaavareBatchDTO();
			rab.setRbId(Integer.parseInt(addRaavareBatchIdTxt.getText()));
			rab.setRaavareId(Integer.parseInt(addRaavareBatchRaavareIdTxt.getText()));
			rab.setMaengde(Integer.parseInt(addRaavareBatchMaengdeTxt.getText()));
			serviceImpl.createRaavareBatch(rab);
		}
	}

	private class createProduktBatchClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			ProduktBatchDTO pb = new ProduktBatchDTO();
			pb.setPbId(Integer.parseInt(addProduktBatchIdTxt.getText()));
			pb.setReceptId(Integer.parseInt(addProduktBatchStatusTxt.getText()));
			pb.setStatus(Integer.parseInt(addProduktBatchReceptIdTxt.getText()));
			serviceImpl.createProduktBatch(pb);
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
			opr.setRettighedsniveau(upUserRetTxt.getText());
			serviceImpl.updateUser(opr);
		}
	}

	private class getUserClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			int oprId = Integer.parseInt(getUserNameTxt.getText());
			serviceImpl.getUser(oprId, token);
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
			serviceImpl.deleteElement(oprId,"operatoer");
		}
	}

	public void authenticateOperatoer(String rettighedsniveau) {
		this.contentpanel.clear();
		HTML html = new HTML();
		String code = "";
		if(rettighedsniveau.length() > 2){
			this.token = rettighedsniveau;
			serviceImpl.getUserRights(token);
			code = "<b>Henter bruger rettighedsniveau</b></br>";
			html.setHTML(code);
			this.externalvpanel.add(html);
		}else {
			this.rettighedsniveau = rettighedsniveau;
			code = "<b>Har hentet rettighedsniveau: " + rettighedsniveau + "</b></br>";
			html.setHTML(code);
			this.externalvpanel.add(html);
			if (rettighedsniveau.equals("0")){
				code = "<b>Brugeren eksisterer ikke</b></br>";
				html.setHTML(code);
				this.externalvpanel.add(html);
				startMenu();
				this.contentpanel.clear();
			}
			mainMenu();
		}
	}

	public void checkIfUserIdExists(int reply){
		this.contentpanel.clear();
		HTML html = new HTML();
		int opr_id = Integer.parseInt(addUserIdTxt.getText());
		String code = "";
		if(opr_id == reply) { 
			code = "<b>Bruger ID eksisterer allerede, skriv et nyt</b></br>";
			html.setHTML(code);
			this.externalvpanel.add(html);
		}
		else
			serviceImpl.validatePassword(addUserPwdTxt.getText());
			createUser();
	}
	public void validatePassword(boolean svar){
		if(svar){createUser();}
		else{
			this.contentpanel.clear();
			HTML html = new HTML();
			String code = "<b>Dit password følger ikke reglerne, skriv et nyt</b></br>";
			html.setHTML(code);
			this.externalvpanel.add(html);
		}
	}
	public void createUser(){
		OperatoerDTO opr = new OperatoerDTO();
		opr.setOprId(Integer.parseInt(addUserIdTxt.getText()));
		opr.setOprNavn(addUserNameTxt.getText());
		opr.setIni(addUserIniTxt.getText());
		opr.setCpr(addUserCprTxt.getText());
		opr.setPassword(addUserPwdTxt.getText());
		opr.setRettighedsniveau(addUserRetTxt.getText());
		serviceImpl.createUser(opr);

	}
	public void deletedElement(boolean result) {
		this.externalvpanel.clear();
		HTML html = new HTML();

		String code = "<b>Brugeren er gjort inaktiv:</b> " + result + "</br>";

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
		code = code + "<b>Rettighedsniveau:</b> " + info.getRettighedsniveau() + "</br>";

		html.setHTML(code);
		this.contentpanel.add(html);
	}

	public void displayRaavare(RaavareDTO info) {
		this.externalvpanel.clear();
		HTML html = new HTML();

		String code = "<b>ID:</b> " + info.getRaavareId() + "</br>";
		code = code + "<b>Navn:</b> " + info.getRaavareNavn() + "</br>";

		html.setHTML(code);
		this.externalvpanel.add(html);
	}

	public void displayRecept(ReceptDTO info) {
		this.externalvpanel.clear();
		HTML html = new HTML();

		String code = "<b>ID:</b> " + info.getReceptId() + "</br>";
		code = code + "<b>Navn:</b> " + info.getReceptNavn() + "</br>";

		html.setHTML(code);
		this.externalvpanel.add(html);
	}

	public void displayRaavareBatch(RaavareBatchDTO info) {
		this.externalvpanel.clear();
		HTML html = new HTML();

		String code = "<b>Råvarebatchens ID:</b> " + info.getRbId() + "</br>";
		code = code + "<b>Råvarens ID:</b> " + info.getRaavareId() + "</br>";
		code = code + "<b>Mængde:</b> " + info.getMaengde() + "</br>";

		html.setHTML(code);
		this.externalvpanel.add(html);
	}

	public void displayProduktBatch(ProduktBatchDTO info) {
		this.externalvpanel.clear();
		HTML html = new HTML();

		String code = "<b>Produktbatchens ID:</b> " + info.getPbId() + "</br>";
		code = code + "<b>Produktbatchens status:</b> " + info.getStatus() + "</br>";
		code = code + "<b>Receptens ID:</b> " + info.getReceptId() + "</br>";

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
			this.contentpanel.add(html);
		}
	}

	public void displayRaavareListe(ArrayList<RaavareDTO> raaList){
		this.externalvpanel.clear();
		for(int i = 0;i < raaList.size();i++){
			HTML html = new HTML();

			String code = "</br><b>ID:</b> " + raaList.get(i).getRaavareId() + "</br>";
			code = code + "<b>Navn:</b> " + raaList.get(i).getRaavareNavn() + "</br>";

			html.setHTML(code);
			this.externalvpanel.add(html);
		}
	}

	public void displayReceptListe(ArrayList<ReceptDTO> racList){
		this.externalvpanel.clear();
		for(int i = 0;i < racList.size();i++){
			HTML html = new HTML();

			String code = "</br><b>ID:</b> " + racList.get(i).getReceptId() + "</br>";
			code = code + "<b>Navn:</b> " + racList.get(i).getReceptNavn() + "</br>";

			html.setHTML(code);
			this.externalvpanel.add(html);
		}
	}

	public void displayRaavareBatchListe(ArrayList<RaavareBatchDTO> rabList){
		this.externalvpanel.clear();
		for(int i = 0;i < rabList.size();i++){
			HTML html = new HTML();

			String code = "</br><b>Råvarebatchens ID:</b> " + rabList.get(i).getRbId() + "</br>";
			code = code + "<b>Råvarens ID:</b> " + rabList.get(i).getRaavareId() + "</br>";
			code = code + "<b>Mængden:</b> " + rabList.get(i).getMaengde() + "</br>";

			html.setHTML(code);
			this.externalvpanel.add(html);
		}
	}

	public void displayProduktBatchListe(ArrayList<ProduktBatchDTO> pbList){
		this.externalvpanel.clear();
		for(int i = 0;i < pbList.size();i++){
			HTML html = new HTML();

			String code = "</br><b>Produktbatchens ID:</b> " + pbList.get(i).getPbId() + "</br>";
			code = code + "<b>Status:</b> " + pbList.get(i).getStatus() + "</br>";
			code = code + "<b>Receptens ID:</b> " + pbList.get(i).getReceptId() + "</br>";

			html.setHTML(code);
			this.externalvpanel.add(html);
		}
	}

	public void displayProduktBatchKompListe(ArrayList<ProduktBatchKompDTO> pbkList){
		this.externalvpanel.clear();
		for(int i = 0;i < pbkList.size();i++){
			HTML html = new HTML();

			String code = "</br><b>Produktbatchens ID:</b>" + pbkList.get(i).getPbId() + "</br>";
			code = code + "<b>Råvarebatchens ID:</b> " + pbkList.get(i).getRbId() + "</br>";
			code = code + "<b>Tara:</b> " + pbkList.get(i).getTara() + "</br>";
			code = code + "<b>Netto:</b> " + pbkList.get(i).getNetto() + "</br>";
			code = code + "<b>Operat�r ID:</b> " + pbkList.get(i).getOprId() + "</br>";

			html.setHTML(code);
			this.externalvpanel.add(html);
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

	private class openCreateReceptClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			openCreateRecept();
		}
	}

	private class openGetReceptListClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			openGetReceptList();
		}
	}

	private class openCreateRaavareBatchClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			openCreateRaavareBatch();
		}
	}

	private class openGetRaavarebatchListClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			openGetRaavareBatchList();
		}
	}

	private class openCreateProduktBatchClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			openCreateProduktBatch();
		}
	}

	private class openGetProduktBatchClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			openGetProduktBatchList();
		}
	}

	private class openGetProduktBatchKomponentClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			openGetProduktBatchKomponentList();
		}
	}

	private class openLogUdClickHandler implements ClickHandler {
		//TODO
		@Override
		public void onClick(ClickEvent event) {
			contentpanel.clear();
			submenupanel.clear();
			menupanel.clear();
			startMenu();
		}
	}

	private class openAdminSubMenuClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			adminSubMenu();
		}
	}

	private class openFarmaceutSubMenuClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			farmaceutSubMenu();
		}
	}

	private class openVaerkfoererSubMenuClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			vaerkfoererSubMenu();
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
		getUserNameTxt.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if (!Character.isDigit(event.getCharCode())) {
					((TextBox) event.getSource()).cancelKey();
				}
			}
		});
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
		deleteUserIdTxt.addKeyPressHandler(new KeyPressHandler() {

			public void onKeyPress(KeyPressEvent event) {
				if (!Character.isDigit(event.getCharCode())) {
					((TextBox) event.getSource()).cancelKey();
				}
			}
		});
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
		addUserIdTxt.addKeyPressHandler(new KeyPressHandler() {

			public void onKeyPress(KeyPressEvent event) {
				if (!Character.isDigit(event.getCharCode())) {
					((TextBox) event.getSource()).cancelKey();
				}
			}
		});
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

		this.contentpanel.add(createuserret);
		addUserRetTxt = new TextBox();
		addUserRetTxt.addKeyPressHandler(new KeyPressHandler() {

			public void onKeyPress(KeyPressEvent event) {
				if (!Character.isDigit(event.getCharCode())) {
					((TextBox) event.getSource()).cancelKey();
				}
			}
		});
		this.contentpanel.add(addUserRetTxt);

		Button createUserBtn = new Button("OK");
		createUserBtn.addClickHandler(new createUserClickHandler());
		this.contentpanel.add(createUserBtn);	
	}

	public void openUpdateUser(){
		this.externalvpanel.clear();
		this.contentpanel.clear();
		this.contentpanel.add(upuserid);
		upUserIdTxt = new TextBox();
		upUserIdTxt.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if (!Character.isDigit(event.getCharCode())) {
					((TextBox) event.getSource()).cancelKey();
				}
			}
		});
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

		this.contentpanel.add(upuserret);
		upUserRetTxt = new TextBox();
		upUserRetTxt.addKeyPressHandler(new KeyPressHandler() {

			public void onKeyPress(KeyPressEvent event) {
				if (!Character.isDigit(event.getCharCode())) {
					((TextBox) event.getSource()).cancelKey();
				}
			}
		});
		this.contentpanel.add(upUserRetTxt);

		Button updateUserBtn = new Button("OK");
		updateUserBtn.addClickHandler(new updateUserClickHandler());
		this.contentpanel.add(updateUserBtn);	
	}

	public void openGetRaavareList(){
		this.externalvpanel.clear();
		this.contentpanel.clear();
		this.contentpanel.add(getraavarelist);
		serviceImpl.getRaavareList();
	}

	public void openGetReceptList(){
		this.externalvpanel.clear();
		this.contentpanel.clear();
		this.contentpanel.add(getreceptlist);
		serviceImpl.getReceptList();
	}

	public void openGetRaavareBatchList(){
		this.externalvpanel.clear();
		this.contentpanel.clear();
		this.contentpanel.add(getraavarebatchlist);
		serviceImpl.getRaavareBatchList();
	}

	public void openGetProduktBatchList(){
		this.externalvpanel.clear();
		this.contentpanel.clear();
		this.contentpanel.add(getproduktbatchlist);
		serviceImpl.getProduktBatchList();
	}

	public void openGetProduktBatchKomponentList(){
		this.externalvpanel.clear();
		this.contentpanel.clear();
		this.contentpanel.add(getproduktbatchkomplist);
		serviceImpl.getProduktBatchKompList();
	}

	public void openCreateRaavare(){
		this.externalvpanel.clear();
		this.contentpanel.clear();
		this.contentpanel.add(createraavareid);
		addRaavareIdTxt = new TextBox();
		addRaavareIdTxt.addKeyPressHandler(new KeyPressHandler() {

			public void onKeyPress(KeyPressEvent event) {
				if (!Character.isDigit(event.getCharCode())) {
					((TextBox) event.getSource()).cancelKey();
				}
			}
		});
		this.contentpanel.add(addRaavareIdTxt);

		this.contentpanel.add(createraavarenavn);
		addRaavareNavnTxt = new TextBox();
		this.contentpanel.add(addRaavareNavnTxt);

		this.contentpanel.add(createraavareleverandoer);
		addRaavareLeverandoerTxt = new TextBox();
		this.contentpanel.add(addRaavareLeverandoerTxt);	

		Button createUserBtn = new Button("OK");
		createUserBtn.addClickHandler(new createRaavareClickHandler());
		this.contentpanel.add(createUserBtn);	
	}

	public void openCreateRecept(){
		this.externalvpanel.clear();
		this.contentpanel.clear();
		this.contentpanel.add(createreceptid);
		addReceptIdTxt = new TextBox();
		addReceptIdTxt.addKeyPressHandler(new KeyPressHandler() {

			public void onKeyPress(KeyPressEvent event) {
				if (!Character.isDigit(event.getCharCode())) {
					((TextBox) event.getSource()).cancelKey();
				}
			}
		});
		this.contentpanel.add(addReceptIdTxt);


		this.contentpanel.add(createreceptnavn);
		addReceptNavnTxt = new TextBox();
		this.contentpanel.add(addReceptNavnTxt);

		Button createUserBtn = new Button("OK");
		createUserBtn.addClickHandler(new createReceptClickHandler());
		this.contentpanel.add(createUserBtn);	
	}

	public void openCreateRaavareBatch(){
		this.externalvpanel.clear();
		this.contentpanel.clear();
		this.contentpanel.add(createraavarebatchid);
		addRaavareBatchIdTxt = new TextBox();
		addRaavareBatchIdTxt.addKeyPressHandler(new KeyPressHandler() {

			public void onKeyPress(KeyPressEvent event) {
				if (!Character.isDigit(event.getCharCode())) {
					((TextBox) event.getSource()).cancelKey();
				}
			}
		});
		this.contentpanel.add(addRaavareBatchIdTxt);

		this.contentpanel.add(createraavarebatchraavareid);
		addRaavareBatchRaavareIdTxt = new TextBox();
		addRaavareBatchRaavareIdTxt.addKeyPressHandler(new KeyPressHandler() {

			public void onKeyPress(KeyPressEvent event) {
				if (!Character.isDigit(event.getCharCode())) {
					((TextBox) event.getSource()).cancelKey();
				}
			}
		});
		this.contentpanel.add(addRaavareBatchRaavareIdTxt);

		this.contentpanel.add(createraavarebatchmaengde);
		addRaavareBatchMaengdeTxt = new TextBox();
		this.contentpanel.add(addRaavareBatchMaengdeTxt);

		Button createUserBtn = new Button("OK");
		createUserBtn.addClickHandler(new createRaavareBatchClickHandler());
		this.contentpanel.add(createUserBtn);	
	}

	public void openCreateProduktBatch(){
		this.externalvpanel.clear();
		this.contentpanel.clear();
		this.contentpanel.add(createproduktbatchid);
		addProduktBatchIdTxt = new TextBox();
		addProduktBatchIdTxt.addKeyPressHandler(new KeyPressHandler() {

			public void onKeyPress(KeyPressEvent event) {
				if (!Character.isDigit(event.getCharCode())) {
					((TextBox) event.getSource()).cancelKey();
				}
			}
		});
		this.contentpanel.add(addProduktBatchIdTxt);

		this.contentpanel.add(createproduktbatchstatus);
		addProduktBatchStatusTxt = new TextBox();
		this.contentpanel.add(addProduktBatchStatusTxt);

		this.contentpanel.add(createproduktbatchreceptid);
		addProduktBatchReceptIdTxt = new TextBox();
		addProduktBatchReceptIdTxt.addKeyPressHandler(new KeyPressHandler() {

			public void onKeyPress(KeyPressEvent event) {
				if (!Character.isDigit(event.getCharCode())) {
					((TextBox) event.getSource()).cancelKey();
				}
			}
		});
		this.contentpanel.add(addProduktBatchReceptIdTxt);

		Button createUserBtn = new Button("OK");
		createUserBtn.addClickHandler(new createProduktBatchClickHandler());
		this.contentpanel.add(createUserBtn);	
	}

	public void mainMenu(){
		this.menupanel.clear();
		Button logout = new Button ("Log ud");
		logout.addClickHandler(new openLogUdClickHandler());
		Button openadminpanel = new Button ("Admin panel");
		openadminpanel.addClickHandler(new openAdminSubMenuClickHandler());
		Button openfarmaceutpanel = new Button ("Farmaceut panel");
		openfarmaceutpanel.addClickHandler(new openFarmaceutSubMenuClickHandler());
		Button openvaerkfoererpanel = new Button ("Værkfører panel");
		openvaerkfoererpanel.addClickHandler(new openVaerkfoererSubMenuClickHandler());

		if (rettighedsniveau == "1"){
			this.menupanel.add(openadminpanel);
			this.menupanel.add(openfarmaceutpanel);
			this.menupanel.add(openvaerkfoererpanel);
			this.menupanel.add(logout);
		}
		else if (rettighedsniveau == "2"){
			this.menupanel.add(openfarmaceutpanel);
			this.menupanel.add(openvaerkfoererpanel);
			this.menupanel.add(logout);
		}
		else if (rettighedsniveau ==  "3"){
			this.menupanel.add(openvaerkfoererpanel);
			this.menupanel.add(logout);
		}
		else if (rettighedsniveau == "4"){
			this.menupanel.add(logout);
		}
	}

	public void startMenu(){
		this.menupanel.clear();
		this.contentpanel.clear();
		this.externalvpanel.clear();
		openLogin();
	}

	public void adminSubMenu(){
		this.submenupanel.clear();
		this.contentpanel.clear();
		this.externalvpanel.clear();

		Button opengetuser = new Button("Find user");

		opengetuser.addClickHandler(new openGetUserClickHandler());
		this.submenupanel.add(opengetuser);

		Button opengetuserlist = new Button("Get userlist");
		opengetuserlist.addClickHandler(new openGetUserListClickHandler());
		this.submenupanel.add(opengetuserlist);

		Button opendeleteuser = new Button("Delete user");
		opendeleteuser.addClickHandler(new openDeleteUserClickHandler());
		this.submenupanel.add(opendeleteuser);

		Button openupdateuser = new Button("Update user");
		openupdateuser.addClickHandler(new openUpdateUserClickHandler());
		this.submenupanel.add(openupdateuser);

		Button opencreateuser = new Button("Create user");
		opencreateuser.addClickHandler(new openCreateUserClickHandler());
		this.submenupanel.add(opencreateuser);
	}

	public void farmaceutSubMenu(){
		this.submenupanel.clear();
		this.contentpanel.clear();
		this.externalvpanel.clear();

		Button openopretraavare = new Button("Opret Råvare");
		openopretraavare.addClickHandler(new openCreateRaavareClickHandler());
		this.submenupanel.add(openopretraavare);

		Button opengetraavarelist = new Button("Vis Råvarer");
		opengetraavarelist.addClickHandler(new openGetRaavareListClickHandler());
		this.submenupanel.add(opengetraavarelist);

		Button openopretrecept = new Button("Opret Recept");
		openopretrecept.addClickHandler(new openCreateReceptClickHandler());
		this.submenupanel.add(openopretrecept);

		Button openreceptliste = new Button("Vis Recepter");
		openreceptliste.addClickHandler(new openGetReceptListClickHandler());
		this.submenupanel.add(openreceptliste);

	}

	public void vaerkfoererSubMenu(){
		this.submenupanel.clear();
		this.contentpanel.clear();
		this.externalvpanel.clear();

		//TODO lav click handlers
		Button openopretraavarebatch = new Button("Opret Råvarebatch");
		openopretraavarebatch.addClickHandler(new openCreateRaavareBatchClickHandler());
		this.submenupanel.add(openopretraavarebatch);

		Button opengetraavarebatchlist = new Button("Vis Råvarebatches");
		opengetraavarebatchlist.addClickHandler(new openGetRaavarebatchListClickHandler());
		this.submenupanel.add(opengetraavarebatchlist);


		Button opencreateproduktbatch = new Button("Opret Produktbatch");
		opencreateproduktbatch.addClickHandler(new openCreateProduktBatchClickHandler());
		this.submenupanel.add(opencreateproduktbatch);

		Button openproduktbatchlist = new Button("Vis Produktbatches");
		openproduktbatchlist.addClickHandler(new openGetProduktBatchClickHandler());
		this.submenupanel.add(openproduktbatchlist);

		Button openproduktbatchkomplist = new Button("Vis Produktbatchkomponenter");
		openproduktbatchkomplist.addClickHandler(new openGetProduktBatchKomponentClickHandler());
		this.submenupanel.add(openproduktbatchlist);

	}

}
