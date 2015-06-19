package cdio.gwt.client.model;

import java.io.Serializable;
/**
 * ProduktBatch Data Objekt
 * 
 * @author Gruppe12
 * @version 1.0
 */

@SuppressWarnings("serial")
public class ProduktBatchDTO implements Serializable
{
	int pbId;                     // i omraadet 1-99999999
	int status;					// 0: ikke paabegyndt, 1: under produktion, 2: afsluttet
	int receptId;
	
	public ProduktBatchDTO() {
	
	}
	
	public ProduktBatchDTO(int pbId, int receptId , int status) {
		this.pbId = pbId;
		this.status = status;
		this.receptId = receptId;
	}
	
	public synchronized int getPbId() { return pbId; }
	public synchronized void setPbId(int pbId) { this.pbId = pbId; }
	public synchronized int getStatus() { return status; }
	public synchronized void setStatus(int status) { this.status = status; }
	public synchronized int getReceptId() { return receptId; }
	public synchronized void setReceptId(int receptId) { this.receptId = receptId; }
	public synchronized String toString() { return pbId + "\t" + status + "\t" + receptId; }
}

