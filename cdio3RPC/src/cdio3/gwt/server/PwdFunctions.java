package cdio3.gwt.server;

import java.util.Random;
import java.util.regex.Pattern;

public class PwdFunctions {
	
	public String genPwd(){
		String password = "";
		String SmaaBogstaver = "abcdefghijklmnopqrstuvwxyz";
		String StoreBogstaver = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String TilladteTegn = ".-_+!?=\"";
		String Tal = "0123456789";
		int tempChoice = 0, tempChar = 0;
		Random doRand = new Random();
		for(int i = 0;i < 8;i++){
			tempChoice = doRand.nextInt(3);
			if(tempChoice == 0){tempChar = doRand.nextInt(SmaaBogstaver.length()-1);password += SmaaBogstaver.substring(tempChar, tempChar+1);}
			if(tempChoice == 1){tempChar = doRand.nextInt(StoreBogstaver.length()-1);password += StoreBogstaver.substring(tempChar, tempChar+1);}
			if(tempChoice == 2){tempChar = doRand.nextInt(TilladteTegn.length()-1);password += TilladteTegn.substring(tempChar, tempChar+1);}
			if(tempChoice == 3){tempChar = doRand.nextInt(Tal.length()-1);password += Tal.substring(tempChar, tempChar+1);}
		}
		if(checkPwd(password)){genPwd();}
		else{return password;}
		return null;
		}
	
	public boolean checkPwd(String password) {
		boolean Ucase = false;
		boolean Lcase = false;
		boolean Number = false;
		boolean Sign = false;
		
		String UnixPwd = password.substring(0, 7);
		if(Pattern.matches("[a-z]", UnixPwd)){Lcase = true;}
		if(Pattern.matches("[A-Z]", UnixPwd)){Ucase = true;}
		if(Pattern.matches("[0-9]", UnixPwd)){Number = true;}
		if(Pattern.matches("[.-_+!?=\"]", UnixPwd)){Lcase = true;}
		if(Ucase == true && Lcase == true && Number == true && Sign == true){return true;}
		else{return false;}
	}
}
