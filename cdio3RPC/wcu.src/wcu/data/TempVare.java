package wcu.data;

public class TempVare {
	public String tare;
	public String brutto;
	public String netto;
	
	public TempVare() {
		this.tare = "";
		this.brutto = "";
		this.netto = "";
	}
	public void setNetto(String n){
		this.netto = n;
	}
	public String getNetto(){
		return this.netto;
	}
	public void setTare(String t){
		this.tare = t;
	}
	public String getTare(){
		return this.tare;
	}
	public void setBrutto(String b){
		this.brutto = b;
	}
	public String getBrutto(){
		return this.brutto;
	}

}
