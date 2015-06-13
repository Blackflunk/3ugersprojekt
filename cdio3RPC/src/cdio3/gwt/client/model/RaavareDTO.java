package cdio3.gwt.client.model;

import java.io.Serializable;

/**
 * Raavare Data Objekt
 * 
 * @author gruppe 12
 * @version 1.0
 */

@SuppressWarnings("serial")
public class RaavareDTO implements Serializable
{
    /** i omraadet 1-99999999 vaelges af brugerne */
    int raavareId;                     
    /** min. 2 max. 20 karakterer */
    String raavareNavn;    
    String leverandoer;
	
    public RaavareDTO() {

    }
    
    public RaavareDTO(int raavareId, String raavareNavn, String leverandoer) {
    	this.raavareId = raavareId;
    	this.raavareNavn = raavareNavn;
    	this.leverandoer = leverandoer;
    }
    
	public synchronized int getRaavareId() { return raavareId; }
    public synchronized void setRaavareId(int raavareId) { this.raavareId = raavareId; }
    public synchronized String getRaavareNavn() { return raavareNavn; }
    public synchronized void setRaavareNavn(String raavareNavn) { this.raavareNavn = raavareNavn; }
    public synchronized String getLeverandoer() { return leverandoer; }
    public synchronized void setLeverandoer(String leverandoer) { this.leverandoer = leverandoer; }
    public synchronized String toString() { 
		return raavareId + "\t" + raavareNavn +"\t" + leverandoer + "\t"; 
	}
}
