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
		String UnixPwd = "";
		if(password.length() > 8){UnixPwd = password.substring(0, 7);}
		else{UnixPwd = password;}
		if(Pattern.compile(".*[a-z].*").matcher(UnixPwd).matches()){Lcase = true;}
		if(Pattern.compile(".*[A-Z].*").matcher(UnixPwd).matches()){Ucase = true;}
		if(Pattern.compile(".*[0-9].*").matcher(UnixPwd).matches()){Number = true;}
		if(Pattern.compile(".*[.-_+!?=].*").matcher(UnixPwd).matches()){Sign = true;}
		if(Ucase && Lcase && Number && Sign){return true;}
		else{return false;}
	}
}
