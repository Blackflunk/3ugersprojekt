package cdio3.gwt.client.model;

import java.io.Serializable;

/**
 * Operatoer Data Objekt
 * 
 * @author Gruppe12
 * @version 1.0
 */

@SuppressWarnings("serial")
public class OperatoerDTO implements Serializable
{
	int oprId;                     
	String oprNavn;                
	String ini;                 
	String cpr;
	String password;
	String rettighedsniveau;

	public OperatoerDTO(){
		
	}
	
	public OperatoerDTO(int oprId, String oprNavn, String ini, String cpr, String password, String rettighedsniveau){
		this.oprId = oprId;
		this.oprNavn = oprNavn;
		this.ini = ini;
		this.cpr = cpr;
		this.password = password;
		this.rettighedsniveau = rettighedsniveau;
	}
	
	public synchronized int getOprId() { return oprId; }
	public synchronized void setOprId(int oprId) { this.oprId = oprId; }
	public synchronized String getOprNavn() { return oprNavn; }
	public synchronized void setOprNavn(String oprNavn) { this.oprNavn = oprNavn; }
	public synchronized String getIni() { return ini; }
	public synchronized void setIni(String ini) { this.ini = ini; }
	public synchronized String getCpr() { return cpr; }
	public synchronized void setCpr(String cpr) { this.cpr = cpr; }
	public synchronized String getPassword() { return password; }
	public synchronized void setPassword(String password) { this.password = password; }
	public synchronized String getRettighedsniveau() {return rettighedsniveau; }
	public synchronized void setRettighedsniveau(String rettighedsniveau) { this.rettighedsniveau = rettighedsniveau; }
	public synchronized String toString() { return oprId + "\t" + oprNavn + "\t" + ini + "\t" + cpr + "\t" + password; }
}
