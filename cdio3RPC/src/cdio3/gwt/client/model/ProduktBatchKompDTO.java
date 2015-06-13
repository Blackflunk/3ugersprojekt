package cdio3.gwt.client.model;

import java.io.Serializable;

/**
 * ProduktBatchKomponent Data Objekt
 * 
 * @author Gruppe12
 * @version 1.0
 */

@SuppressWarnings("serial")
public class ProduktBatchKompDTO implements Serializable
{
	int pbId; 	  	// produktbatchets id
	int rbId;       // i omraadet 1-99999999
	double tara;
	double netto;
	int oprId;		// operatoer-nummer

	public ProduktBatchKompDTO() {
		
	}
	
	public ProduktBatchKompDTO(int pbId, int rbId, double tara, double netto, int oprId) {
		this.pbId = pbId;
		this.rbId = rbId;
		this.tara = tara;
		this.netto = netto;
		this.oprId = oprId;
	}
	
	public synchronized int getPbId() { return pbId; }
	public synchronized void setPbId(int pbId) { this.pbId = pbId; }
	public synchronized int getRbId() { return rbId; }
	public synchronized void setRbId(int rbId) { this.rbId = rbId; }
	public synchronized double getTara() { return tara; }
	public synchronized void setTara(double tara) { this.tara = tara; }
	public synchronized double getNetto() { return netto; }
	public synchronized void setNetto(double netto) { this.netto = netto; }
	public synchronized int getOprId() { return oprId; }
	public synchronized void setOprId(int oprId) { this.oprId = oprId; }
	public synchronized String toString() { 
		return pbId + "\t" + rbId +"\t" + tara +"\t" + netto + "\t" + oprId ; 
	}
}
