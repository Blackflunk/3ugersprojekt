package cdio3.gwt.client.model;

import java.io.Serializable;

/**
 * RaavareBatch Data Objekt
 * 
 * @author Gruppe12
 * @version 1.0
 */

@SuppressWarnings("serial")
public class RaavareBatchDTO implements Serializable
{
	int rbId;                  	// i omraadet 1-99999999
	int raavareId;             	// i omraadet 1-99999999
	double maengde;            	// kan vaere negativ 
	String leverandoer;			// leverandoer info
	
	public RaavareBatchDTO() {

	}
	
	public RaavareBatchDTO(int rbId, int raavareId, double maengde, String leverandoer) {
		this.rbId = rbId;
		this.raavareId = raavareId;
		this.maengde = maengde;
		this.leverandoer = leverandoer;
	}
	
	public synchronized int getRbId() { return rbId; }
	public synchronized void setRbId(int rbId) { this.rbId = rbId; }
	public synchronized int getRaavareId() { return raavareId; }
	public synchronized void setRaavareId(int raavareId) { this.raavareId = raavareId; }
	public synchronized double getMaengde() { return maengde; }
	public synchronized void setMaengde(double maengde) { this.maengde = maengde; }
	public synchronized String getLeverandoer() { return leverandoer; }
	public synchronized void setLeverandoer(String leverandoer) { this.leverandoer = leverandoer; }
	public synchronized String toString() { 
		return rbId + "\t" + raavareId +"\t" + maengde +"\t" + leverandoer; 
	}
}
