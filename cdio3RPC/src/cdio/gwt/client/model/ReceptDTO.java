package cdio.gwt.client.model;

import java.io.Serializable;

/**
 * Recept Data Objekt
 * 
 * @author Gruppe12
 * @version 1.0
 */

@SuppressWarnings("serial")
public class ReceptDTO implements Serializable
{
	/** Recept nr i omraadet 1-99999999 */
	int receptId;
	/** Receptnavn min. 2 max. 20 karakterer */
	String receptNavn;
	
	public ReceptDTO() {

	}
	
	public ReceptDTO(int receptId, String receptNavn) {
		this.receptId = receptId;
		this.receptNavn = receptNavn;
	}

    public synchronized int getReceptId() { return receptId; }
	public synchronized void setReceptId(int receptId) { this.receptId = receptId; }
	public synchronized String getReceptNavn() { return receptNavn; }
	public synchronized void setReceptNavn(String receptNavn) { this.receptNavn = receptNavn; }
	public synchronized String toString() { 
		return receptId + "\t" + receptNavn; 
	}
}
