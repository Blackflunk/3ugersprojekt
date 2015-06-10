package wcu.data;

public class OperatoerDTO {
	int oprID;
	String oprName;
	public OperatoerDTO(){
		this.oprID = 0;
		this.oprName = "";
	}
	public int getOprID(){
		return this.oprID;
	}
	public String getOprName(){
		return this.oprName;
	}
	public void setOprID(int oprID){
		this.oprID = oprID;
	}
	public void setOprName(String oprName){
		this.oprName = oprName;
	}
}
