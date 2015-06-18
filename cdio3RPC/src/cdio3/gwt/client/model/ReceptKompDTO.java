package cdio3.gwt.client.model;

import java.io.Serializable;

/**
 * ReceptKomponent Data Objekt
 * 
 * @author Gruppe12
 * @version 1.0
 */

@SuppressWarnings("serial")
public class ReceptKompDTO implements Serializable
{
	int receptId;               // auto genereres fra 1..n   
	int raavareId;             	// i omraadet 1-99999999
	double nomNetto;            // skal vaere positiv og passende stor
	double tolerance;           // skal vaere positiv og passende stor

	public ReceptKompDTO() {

	}

	public ReceptKompDTO(int receptId, int raavareId, double nomNetto, double tolerance) {
		this.receptId = receptId;
		this.raavareId = raavareId;
		this.nomNetto = nomNetto;
		this.tolerance = tolerance;
	}
	
	public synchronized int getReceptId() { return receptId; }
	public synchronized void setReceptId(int receptId) { this.receptId = receptId; }
	public synchronized int getRaavareId() { return raavareId; }
	public synchronized void setRaavareId(int raavareId) { this.raavareId = raavareId; }
	public synchronized double getNomNetto() { return nomNetto; }
	public synchronized void setNomNetto(int nomNetto) { this.nomNetto = nomNetto; }
	public synchronized double getTolerance() { return tolerance; }
	public synchronized void setTolerance(int tolerance) { this.tolerance = tolerance; }
	public synchronized String toString() { 
		return receptId + "\t" + raavareId + "\t" + nomNetto + "\t" + tolerance; 
	}
}
