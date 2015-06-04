package cdio3.gwt.client;

import cdio3.gwt.client.service.DBServiceClientImpl;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Cdio3 implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		DBServiceClientImpl clientImpl = new DBServiceClientImpl(GWT.getModuleBaseURL() + "dbservice");
		RootPanel.get().add(clientImpl.getMainGUI());
	}
}