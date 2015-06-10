package wcu.boundary;

import java.io.IOException;

public interface IUI {
	void printMessage(String message);
	String getResponse() throws IOException;
	int getIntResponse() throws IOException;
}
