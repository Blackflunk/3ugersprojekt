package wcu.control;

import cdio3.gwt.server.DALException;

public class demo {

	public static void main(String[] args) {
		WCUController WCU = new WCUController();
		try {
			WCU.init();
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
