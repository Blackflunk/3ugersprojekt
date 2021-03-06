package cdio3.gwt.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
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
import cdio3.gwt.server.TokenHandler;

public class MainGUI extends Composite {
	String rettighedsniveau = "0";
	String token = "";
	String currusername = "";
	String curruserpass = "";
	String curruseret = "";
	String stilling = "";
	boolean infocall = false;

	private AbsolutePanel vPanel = new AbsolutePanel();
	private VerticalPanel menupanel = new VerticalPanel();
	private HorizontalPanel submenupanel = new HorizontalPanel();
	private FlowPanel contentpanel = new FlowPanel();
	private VerticalPanel externalvpanel = new VerticalPanel();
	private FlowPanel infopanel = new FlowPanel();
	private FlowPanel sysinfopanel = new FlowPanel();

	private Label vaelgmenu = new Label("Vælg nu i submenu.");

	Button opengetuser = new Button("Find user");
	Button opengetuserlist = new Button("Get userlist");
	Button opendeleteuser = new Button("Delete user");
	Button openupdateuser = new Button("Update user");
	Button opencreateuser = new Button("Create user");

	Button logout = new Button ("Log ud");

	Button openadminpanel = new Button ("Admin panel");
	Button openfarmaceutpanel = new Button ("Farmaceut panel");
	Button openvaerkfoererpanel = new Button ("Værkfører panel");

	Button openopretraavare = new Button("Opret Råvare");
	Button opengetraavarelist = new Button("Vis Råvarer");
	Button openopretrecept = new Button("Opret Recept");
	Button openreceptliste = new Button("Vis Recepter");

	Button openopretraavarebatch = new Button("Opret Råvarebatch");
	Button opengetraavarebatchlist = new Button("Vis Råvarebatches");
	Button opencreateproduktbatch = new Button("Opret Produktbatch");
	Button openproduktbatchlist = new Button("Vis Produktbatches");
	Button openproduktbatchkomplist = new Button("Vis Produktbatchkomponenter");

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

		startMenu();
	}

	private class AuthenticationClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			String username = userNameTxt.getText();
			currusername = username;
			String pwd = userPwdTxt.getText();
			curruserpass = pwd;
			serviceImpl.authenticateUser(username, pwd);
		}
	}

	private class createUserClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			String entity = "operatoer";
			serviceImpl.checkIdExist(Integer.parseInt(addUserIdTxt.getText()), entity);
		}
	}

	private class createRaavareClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			String entity = "raavare";
			serviceImpl.checkIdExist(Integer.parseInt(addRaavareIdTxt.getText()), entity);
		}
	}

	private class createReceptClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			String entity = "recept";
			serviceImpl.checkIdExist(Integer.parseInt(addReceptIdTxt.getText()), entity);
			
		}
	}

	private class createRaavareBatchClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			String entity = "raavarebatch";
			serviceImpl.checkIdExist(Integer.parseInt(addRaavareBatchIdTxt.getText()), entity);
			
		}
	}

	private class createProduktBatchClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			
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
			this.contentpanel.add(html);
		}else{
			serviceImpl.getStilling(token);
			this.rettighedsniveau = rettighedsniveau;
			code = "<b>Har hentet rettighedsniveau: " + rettighedsniveau + "</b></br>";
			html.setHTML(code);
			this.contentpanel.add(html);
			if (rettighedsniveau.equals("0")){
				code = "<b>Brugeren eksisterer ikke</b></br>";
				html.setHTML(code);
				this.contentpanel.add(html);
				startMenu();
				this.contentpanel.clear();
			}
			mainMenu();
		}
	}

	public void checkUserId(int reply){
		externalvpanel.setStyleName("externalvpanel-style");
		this.contentpanel.add(externalvpanel);
		this.externalvpanel.clear();
		HTML html = new HTML();
		String code = "";
		if(reply == 1) { 
			code = "<b>Bruger ID eksisterer allerede, skriv et nyt</b></br>";
			html.setHTML(code);
			this.externalvpanel.add(html);
		}
		else
			serviceImpl.validatePassword(addUserPwdTxt.getText());
	}

	public void checkRaavareId(int reply){
		externalvpanel.setStyleName("externalvpanel-style");
		this.contentpanel.add(externalvpanel);
		this.externalvpanel.clear();
		HTML html = new HTML();
		String code = "";
		if(reply == 1) { 
			code = "<b>Raavare ID eksisterer allerede, skriv et nyt</b></br>";
			html.setHTML(code);
			this.externalvpanel.add(html);
		}
		else
			createRaavare();
	}
	public void checkReceptId(int reply){
		externalvpanel.setStyleName("externalvpanel-style");
		this.contentpanel.add(externalvpanel);
		this.externalvpanel.clear();
		HTML html = new HTML();
		String code = "";
		if(reply == 1) { 
			code = "<b>Recept ID eksisterer allerede, skriv et nyt</b></br>";
			html.setHTML(code);
			this.externalvpanel.add(html);
		}
		else
		createRecept();
	}
	public void checkRaavareBatchId(int reply){
		externalvpanel.setStyleName("externalvpanel-style");
		this.contentpanel.add(externalvpanel);
		this.externalvpanel.clear();
		HTML html = new HTML();
		String code = "";
		if(reply == 1) { 
			code = "<b>Raavarebatch ID eksisterer allerede, skriv et nyt</b></br>";
			html.setHTML(code);
			this.externalvpanel.add(html);
		}
		else
			createRaavareBatch();
	}
	
	public void checkProduktBatchId(int reply){
		externalvpanel.setStyleName("externalvpanel-style");
		this.contentpanel.add(externalvpanel);
		this.externalvpanel.clear();
		HTML html = new HTML();
		String code = "";
		if(reply == 1) { 
			code = "<b>Produktbatch ID eksisterer allerede, skriv et nyt</b></br>";
			html.setHTML(code);
			this.externalvpanel.add(html);
		}
		else
			createProduktBatch();
	}
	public void validatePassword(boolean svar){
		externalvpanel.setStyleName("externalvpanel-style");
		this.contentpanel.add(externalvpanel);
		if(svar){createUser();}
		else{
			HTML html = new HTML();
			String code = "<b>Dit password følger ikke reglerne, skriv et nyt</b></br>";
			html.setHTML(code);
			this.externalvpanel.add(html);
		}
	}
	public void setStilling(String stilling){
		this.stilling = stilling;
		userInfo();
		sysInfo();
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
	public void createRaavare() {
		RaavareDTO raa = new RaavareDTO();
		raa.setRaavareId(Integer.parseInt(addRaavareIdTxt.getText()));
		raa.setRaavareNavn(addRaavareNavnTxt.getText());
		raa.setLeverandoer(addRaavareLeverandoerTxt.getText());
		serviceImpl.createRaavare(raa);
	}
		public void createRecept() {
			ReceptDTO rac = new ReceptDTO();
			rac.setReceptId(Integer.parseInt(addReceptIdTxt.getText()));
			rac.setReceptNavn(addReceptNavnTxt.getText());
			serviceImpl.createRecept(rac);
		}
		public void createRaavareBatch() {
			RaavareBatchDTO rab = new RaavareBatchDTO();
			rab.setRbId(Integer.parseInt(addRaavareBatchIdTxt.getText()));
			rab.setRaavareId(Integer.parseInt(addRaavareBatchRaavareIdTxt.getText()));
			rab.setMaengde(Integer.parseInt(addRaavareBatchMaengdeTxt.getText()));
			serviceImpl.createRaavareBatch(rab);
		}
		public void createProduktBatch() {
			ProduktBatchDTO pb = new ProduktBatchDTO();
			pb.setPbId(Integer.parseInt(addProduktBatchIdTxt.getText()));
			pb.setReceptId(Integer.parseInt(addProduktBatchStatusTxt.getText()));
			pb.setStatus(Integer.parseInt(addProduktBatchReceptIdTxt.getText()));
			serviceImpl.createProduktBatch(pb);
		}
	
	public void deletedElement(boolean result) {
		externalvpanel.setStyleName("externalvpanel-style");
		this.contentpanel.add(externalvpanel);
		this.externalvpanel.clear();
		HTML html = new HTML();

		String code = "<b>Brugeren er gjort inaktiv:</b> " + result + "</br>";

		html.setHTML(code);
		this.externalvpanel.add(html);
	}

	public void displayOperatoer(OperatoerDTO info) {
		externalvpanel.setStyleName("externalvpanel-style");
		this.contentpanel.add(externalvpanel);
		this.externalvpanel.clear();
		

		HTML html = new HTML();

		String code = "<b>ID:</b> " + info.getOprId() + "</br>";
		code = code + "<b>Navn:</b> " + info.getOprNavn() + "</br>";
		code = code + "<b>Ini:</b> " + info.getIni() + "</br>";
		code = code + "<b>Cpr:</b> " + info.getCpr() + "</br>";
		code = code + "<b>Password:</b> " + info.getPassword() + "</br>";
		code = code + "<b>Rettighedsniveau:</b> " + info.getRettighedsniveau() + "</br>";

		html.setHTML(code);
		this.externalvpanel.add(html);
	}

	public void displayRaavare(RaavareDTO info) {
		externalvpanel.setStyleName("externalvpanel-style");
		this.contentpanel.add(externalvpanel);
		this.externalvpanel.clear();
		HTML html = new HTML();

		String code = "<b>ID:</b> " + info.getRaavareId() + "</br>";
		code = code + "<b>Navn:</b> " + info.getRaavareNavn() + "</br>";

		html.setHTML(code);
		this.externalvpanel.add(html);
	}

	public void displayRecept(ReceptDTO info) {
		externalvpanel.setStyleName("externalvpanel-style");
		this.contentpanel.add(externalvpanel);
		this.externalvpanel.clear();
		HTML html = new HTML();

		String code = "<b>ID:</b> " + info.getReceptId() + "</br>";
		code = code + "<b>Navn:</b> " + info.getReceptNavn() + "</br>";

		html.setHTML(code);
		this.externalvpanel.add(html);
	}

	public void displayRaavareBatch(RaavareBatchDTO info) {
		externalvpanel.setStyleName("externalvpanel-style");
		this.contentpanel.add(externalvpanel);
		this.externalvpanel.clear();
		HTML html = new HTML();

		String code = "<b>Råvarebatchens ID:</b> " + info.getRbId() + "</br>";
		code = code + "<b>Råvarens ID:</b> " + info.getRaavareId() + "</br>";
		code = code + "<b>Mængde:</b> " + info.getMaengde() + "</br>";

		html.setHTML(code);
		this.externalvpanel.add(html);
	}

	public void displayProduktBatch(ProduktBatchDTO info) {
		externalvpanel.setStyleName("externalvpanel-style");
		this.contentpanel.add(externalvpanel);
		this.externalvpanel.clear();
		HTML html = new HTML();

		String code = "<b>Produktbatchens ID:</b> " + info.getPbId() + "</br>";
		code = code + "<b>Produktbatchens status:</b> " + info.getStatus() + "</br>";
		code = code + "<b>Receptens ID:</b> " + info.getReceptId() + "</br>";

		html.setHTML(code);
		this.externalvpanel.add(html);
	}

	public void displayOperatoerListe(ArrayList<OperatoerDTO> oprList){
		externalvpanel.setStyleName("externalvpanel-style");
		this.contentpanel.add(externalvpanel);
		this.externalvpanel.clear();

		if(infocall){
			for(int i = 0;i < oprList.size();i++){
				if(oprList.get(i).getOprNavn() == currusername && oprList.get(i).getPassword() == curruserpass){
					curruseret = oprList.get(i).getRettighedsniveau();
					infocall = false;
					break;
				}
			}
		}else{

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
		infocall = false;
	}

	public void displayRaavareListe(ArrayList<RaavareDTO> raaList){
		externalvpanel.setStyleName("externalvpanel-style");
		this.contentpanel.add(externalvpanel);
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
		externalvpanel.setStyleName("externalvpanel-style");
		this.contentpanel.add(externalvpanel);
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
		externalvpanel.setStyleName("externalvpanel-style");
		this.contentpanel.add(externalvpanel);
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
		externalvpanel.setStyleName("externalvpanel-style");
		this.contentpanel.add(externalvpanel);
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
		externalvpanel.setStyleName("externalvpanel-style");
		this.contentpanel.add(externalvpanel);
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
			vPanel.remove(infopanel);
			vPanel.remove(sysinfopanel);
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

		opengetuser.setStyleName("submenubuttonselected-style");
		opengetuserlist.setStyleName("submenubutton-style");
		opendeleteuser.setStyleName("submenubutton-style");
		openupdateuser.setStyleName("submenubutton-style");
		opencreateuser.setStyleName("submenubutton-style");

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

		opengetuser.setStyleName("submenubutton-style");
		opengetuserlist.setStyleName("submenubuttonselected-style");
		opendeleteuser.setStyleName("submenubutton-style");
		openupdateuser.setStyleName("submenubutton-style");
		opencreateuser.setStyleName("submenubutton-style");
	}

	public void openDeleteUser(){
		this.externalvpanel.clear();
		this.contentpanel.clear();
		this.contentpanel.add(deleteusertext);

		opengetuser.setStyleName("submenubutton-style");
		opengetuserlist.setStyleName("submenubutton-style");
		opendeleteuser.setStyleName("submenubuttonselected-style");
		openupdateuser.setStyleName("submenubutton-style");
		opencreateuser.setStyleName("submenubutton-style");

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

		opengetuser.setStyleName("submenubutton-style");
		opengetuserlist.setStyleName("submenubutton-style");
		opendeleteuser.setStyleName("submenubutton-style");
		openupdateuser.setStyleName("submenubutton-style");
		opencreateuser.setStyleName("submenubuttonselected-style");

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

		opengetuser.setStyleName("submenubutton-style");
		opengetuserlist.setStyleName("submenubutton-style");
		opendeleteuser.setStyleName("submenubutton-style");
		openupdateuser.setStyleName("submenubuttonselected-style");
		opencreateuser.setStyleName("submenubutton-style");

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

		openopretraavare.setStyleName("submenubutton-style");
		opengetraavarelist.setStyleName("submenubuttonselected-style");
		openopretrecept.setStyleName("submenubutton-style");
		openreceptliste.setStyleName("submenubutton-style");
	}

	public void openGetReceptList(){
		this.externalvpanel.clear();
		this.contentpanel.clear();
		this.contentpanel.add(getreceptlist);
		serviceImpl.getReceptList();

		openopretraavare.setStyleName("submenubutton-style");
		opengetraavarelist.setStyleName("submenubutton-style");
		openopretrecept.setStyleName("submenubutton-style");
		openreceptliste.setStyleName("submenubuttonselected-style");
	}

	public void openGetRaavareBatchList(){
		this.externalvpanel.clear();
		this.contentpanel.clear();
		this.contentpanel.add(getraavarebatchlist);
		serviceImpl.getRaavareBatchList();

		openopretraavarebatch.setStyleName("submenubutton-style");
		opengetraavarebatchlist.setStyleName("submenubuttonselected-style");
		opencreateproduktbatch.setStyleName("submenubutton-style");
		openproduktbatchlist.setStyleName("submenubutton-style");
		openproduktbatchkomplist.setStyleName("submenubutton-style");
	}

	public void openGetProduktBatchList(){
		this.externalvpanel.clear();
		this.contentpanel.clear();
		this.contentpanel.add(getproduktbatchlist);
		serviceImpl.getProduktBatchList();

		openopretraavarebatch.setStyleName("submenubutton-style");
		opengetraavarebatchlist.setStyleName("submenubutton-style");
		opencreateproduktbatch.setStyleName("submenubutton-style");
		openproduktbatchlist.setStyleName("submenubuttonselected-style");
		openproduktbatchkomplist.setStyleName("submenubutton-style");
	}

	public void openGetProduktBatchKomponentList(){
		this.externalvpanel.clear();
		this.contentpanel.clear();
		this.contentpanel.add(getproduktbatchkomplist);
		serviceImpl.getProduktBatchKompList();

		openopretraavarebatch.setStyleName("submenubutton-style");
		opengetraavarebatchlist.setStyleName("submenubutton-style");
		opencreateproduktbatch.setStyleName("submenubutton-style");
		openproduktbatchlist.setStyleName("submenubutton-style");
		openproduktbatchkomplist.setStyleName("submenubuttonselected-style");
	}

	public void openCreateRaavare(){
		this.externalvpanel.clear();
		this.contentpanel.clear();
		this.contentpanel.add(createraavareid);

		openopretraavare.setStyleName("submenubuttonselected-style");
		opengetraavarelist.setStyleName("submenubutton-style");
		openopretrecept.setStyleName("submenubutton-style");
		openreceptliste.setStyleName("submenubutton-style");

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

		openopretraavare.setStyleName("submenubutton-style");
		opengetraavarelist.setStyleName("submenubutton-style");
		openopretrecept.setStyleName("submenubuttonselected-style");
		openreceptliste.setStyleName("submenubutton-style");

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

		openopretraavarebatch.setStyleName("submenubuttonselected-style");
		opengetraavarebatchlist.setStyleName("submenubutton-style");
		opencreateproduktbatch.setStyleName("submenubutton-style");
		openproduktbatchlist.setStyleName("submenubutton-style");
		openproduktbatchkomplist.setStyleName("submenubutton-style");

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

		openopretraavarebatch.setStyleName("submenubutton-style");
		opengetraavarebatchlist.setStyleName("submenubutton-style");
		opencreateproduktbatch.setStyleName("submenubuttonselected-style");
		openproduktbatchlist.setStyleName("submenubutton-style");
		openproduktbatchkomplist.setStyleName("submenubutton-style");

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
		this.infopanel.clear();
		this.sysinfopanel.clear();

	

		logout.addClickHandler(new openLogUdClickHandler());
		logout.setStyleName("menubutton-style");
		openadminpanel.setStyleName("menubutton-style");
		openadminpanel.addClickHandler(new openAdminSubMenuClickHandler());
		openfarmaceutpanel.setStyleName("menubutton-style");
		openfarmaceutpanel.addClickHandler(new openFarmaceutSubMenuClickHandler());
		openvaerkfoererpanel.setStyleName("menubutton-style");
		openvaerkfoererpanel.addClickHandler(new openVaerkfoererSubMenuClickHandler());
		if (rettighedsniveau == "1"){
			adminSubMenu();
			this.menupanel.add(openadminpanel);
			this.menupanel.add(openfarmaceutpanel);
			this.menupanel.add(openvaerkfoererpanel);
			this.menupanel.add(logout);
		}
		else if (rettighedsniveau == "2"){
			farmaceutSubMenu();
			this.menupanel.add(openfarmaceutpanel);
			this.menupanel.add(openvaerkfoererpanel);
			this.menupanel.add(logout);
		}
		else if (rettighedsniveau ==  "3"){
			vaerkfoererSubMenu();
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
		this.infopanel.clear();
		this.sysinfopanel.clear();

		openfarmaceutpanel.setStyleName("menubutton-style");
		openvaerkfoererpanel.setStyleName("menubutton-style");
		logout.setStyleName("menubutton-style");
		openadminpanel.setStyleName("menubuttonselected-style");

		this.contentpanel.add(vaelgmenu);

		opengetuser.setStyleName("submenubutton-style");
		opengetuser.addClickHandler(new openGetUserClickHandler());
		this.submenupanel.add(opengetuser);

		opengetuserlist.setStyleName("submenubutton-style");
		opengetuserlist.addClickHandler(new openGetUserListClickHandler());
		this.submenupanel.add(opengetuserlist);

		opendeleteuser.setStyleName("submenubutton-style");
		opendeleteuser.addClickHandler(new openDeleteUserClickHandler());
		this.submenupanel.add(opendeleteuser);

		openupdateuser.setStyleName("submenubutton-style");
		openupdateuser.addClickHandler(new openUpdateUserClickHandler());
		this.submenupanel.add(openupdateuser);

		opencreateuser.setStyleName("submenubutton-style");
		opencreateuser.addClickHandler(new openCreateUserClickHandler());
		this.submenupanel.add(opencreateuser);
		userInfo();
		sysInfo();
	}

	public void farmaceutSubMenu(){
		this.submenupanel.clear();
		this.contentpanel.clear();
		this.externalvpanel.clear();
		this.infopanel.clear();
		this.sysinfopanel.clear();

		openfarmaceutpanel.setStyleName("menubuttonselected-style");
		openvaerkfoererpanel.setStyleName("menubutton-style");
		logout.setStyleName("menubutton-style");
		openadminpanel.setStyleName("menubutton-style");
		this.contentpanel.add(vaelgmenu);

		openopretraavare.setStyleName("submenubutton-style");
		openopretraavare.addClickHandler(new openCreateRaavareClickHandler());
		this.submenupanel.add(openopretraavare);

		opengetraavarelist.setStyleName("submenubutton-style");
		opengetraavarelist.addClickHandler(new openGetRaavareListClickHandler());
		this.submenupanel.add(opengetraavarelist);

		openopretrecept.setStyleName("submenubutton-style");
		openopretrecept.addClickHandler(new openCreateReceptClickHandler());
		this.submenupanel.add(openopretrecept);

		openreceptliste.setStyleName("submenubutton-style");
		openreceptliste.addClickHandler(new openGetReceptListClickHandler());
		this.submenupanel.add(openreceptliste);
		userInfo();
		sysInfo();
	}

	public void vaerkfoererSubMenu(){
		this.submenupanel.clear();
		this.contentpanel.clear();
		this.externalvpanel.clear();
		this.infopanel.clear();
		this.sysinfopanel.clear();

		openfarmaceutpanel.setStyleName("menubutton-style");
		openvaerkfoererpanel.setStyleName("menubuttonselected-style");
		logout.setStyleName("menubutton-style");
		openadminpanel.setStyleName("menubutton-style");
		this.contentpanel.add(vaelgmenu);

		//TODO lav click handlers
		openopretraavarebatch.setStyleName("submenubutton-style");
		openopretraavarebatch.addClickHandler(new openCreateRaavareBatchClickHandler());
		this.submenupanel.add(openopretraavarebatch);

		opengetraavarebatchlist.setStyleName("submenubutton-style");
		opengetraavarebatchlist.addClickHandler(new openGetRaavarebatchListClickHandler());
		this.submenupanel.add(opengetraavarebatchlist);

		opencreateproduktbatch.setStyleName("submenubutton-style");
		opencreateproduktbatch.addClickHandler(new openCreateProduktBatchClickHandler());
		this.submenupanel.add(opencreateproduktbatch);

		openproduktbatchlist.setStyleName("submenubutton-style");
		openproduktbatchlist.addClickHandler(new openGetProduktBatchClickHandler());
		this.submenupanel.add(openproduktbatchlist);

		openproduktbatchkomplist.setStyleName("submenubutton-style");
		openproduktbatchkomplist.addClickHandler(new openGetProduktBatchKomponentClickHandler());
		this.submenupanel.add(openproduktbatchlist);
		userInfo();
		sysInfo();
	}
	
	public void sysInfo(){
		sysinfopanel.clear();
		sysinfopanel.setStyleName("sysinfopanel-style");
		this.vPanel.add(sysinfopanel);
		
		HTML syshtml = new HTML();
		String sysinfo = "<b><h4>System information</b></h4>";
		sysinfo = sysinfo + "<b>Din token: </b>" + token.substring(0, 10) + "</br>";
		for(int i=10;i<200;i+=15){
		String tokennew = token.substring(i, i+15);
		sysinfo = sysinfo + tokennew + "</br>";
		}
		syshtml.setHTML(sysinfo);
		this.sysinfopanel.add(syshtml);
	}
	
	public void userInfo(){
		infopanel.clear();
		infopanel.setStyleName("infopanel-style");
		this.vPanel.add(infopanel);
		HTML html = new HTML();
		String userinfo = "<b><h4>Bruger information</b></h4>";
		userinfo = userinfo + "<b>Dit navn: </b>" + currusername + "</br>";
		userinfo = userinfo + "<b>Din stilling: </b>" + stilling + "</br>";
		html.setHTML(userinfo);
		this.infopanel.add(html);
	}

}
