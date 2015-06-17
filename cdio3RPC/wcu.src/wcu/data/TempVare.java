package wcu.data;

public class TempVare {
	public double tara;
	public double brutto;
	public double netto;
	
	public TempVare() {
		this.tara = 0.0;
		this.brutto = 0.0;
		this.netto = 0.0;
	}
	
	public TempVare(double netto, double brutto, double tara) {
		this.tara = tara;
		this.brutto = brutto;
		this.netto = netto;
	}
	public void setNetto(double n){
		this.netto = n;
	}
	public double getNetto(){
		return this.netto;
	}
	public void setTare(double t){
		this.tara = t;
	}
	public double getTare(){
		return this.tara;
	}
	public void setBrutto(double b){
		this.brutto = b;
	}
	public double getBrutto(){
		return this.brutto;
	}

}