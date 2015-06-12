package cdio3.gwt.client.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ProduktBatchDTO implements Serializable
{
	int pbId;                     // i omraadet 1-99999999
	int status;					// 0: ikke paabegyndt, 1: under produktion, 2: afsluttet
	int receptId;
	
	public ProduktBatchDTO(int int1, int int2, int int3) {
		// TODO Auto-generated constructor stub
	}
	public int getPbId() { return pbId; }
	public void setPbId(int pbId) { this.pbId = pbId; }
	public int getStatus() { return status; }
	public void setStatus(int status) { this.status = status; }
	public int getReceptId() { return receptId; }
	public void setReceptId(int receptId) { this.receptId = receptId; }
	public String toString() { return pbId + "\t" + status + "\t" + receptId; }
}

