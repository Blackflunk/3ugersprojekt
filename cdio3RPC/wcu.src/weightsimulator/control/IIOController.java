package weightsimulator.control;

import java.io.IOException;

public interface IIOController {
	public void closeServer();
	public void getUser() throws IOException;
}
