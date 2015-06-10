package wcu.data;

public class RaavareDTO {

	int raavareID;
	String raavareName;
	String raavareWeight;
	
	public RaavareDTO() {
		this.raavareID = 0;
		this.raavareName = "";
		this.raavareWeight = "";
	}
	public int getRaavareID(){
		return this.raavareID;
	}
	public String getRaavareName(){
		return this.raavareName;
	}
	public String getRaavareWeight(){
		return this.raavareWeight;
	}
	public void setRaavareID(int raavareID){
		this.raavareID = raavareID;
	}
	public void setRaavareName(String raavareName){
		this.raavareName = raavareName;
	}
	public void setRaavareWeight(String raavareWeight){
		this.raavareWeight = raavareWeight;
	}


}
