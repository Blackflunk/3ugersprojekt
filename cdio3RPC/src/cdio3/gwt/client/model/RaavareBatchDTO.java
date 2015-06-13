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
	
	public RaavareBatchDTO() {

	}
	
	public RaavareBatchDTO(int rbId, int raavareId, double maengde) {
		this.rbId = rbId;
		this.raavareId = raavareId;
		this.maengde = maengde;
	}
	
	public synchronized int getRbId() { return rbId; }
	public synchronized void setRbId(int rbId) { this.rbId = rbId; }
	public synchronized int getRaavareId() { return raavareId; }
	public synchronized void setRaavareId(int raavareId) { this.raavareId = raavareId; }
	public synchronized double getMaengde() { return maengde; }
	public synchronized void setMaengde(double maengde) { this.maengde = maengde; }
	public synchronized String toString() { 
		return rbId + "\t" + raavareId +"\t" + maengde; 
	}
}
