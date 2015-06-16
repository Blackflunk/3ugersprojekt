package wcu.data;

public class TempVare {
	public String tara;
	public String brutto;
	public String netto;
	
	public TempVare() {
		this.tara = "";
		this.brutto = "";
		this.netto = "";
	}
	
	public TempVare(String netto, String brutto, String tara) {
		this.tara = tara;
		this.brutto = brutto;
		this.netto = netto;
	}
	public void setNetto(String n){
		this.netto = n;
	}
	public String getNetto(){
		return this.netto;
	}
	public void setTare(String t){
		this.tara = t;
	}
	public String getTare(){
		return this.tara;
	}
	public void setBrutto(String b){
		this.brutto = b;
	}
	public String getBrutto(){
		return this.brutto;
	}

}