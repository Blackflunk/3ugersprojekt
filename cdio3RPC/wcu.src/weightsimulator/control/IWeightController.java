package weightsimulator.control;

import java.io.IOException;

import weightsimulator.exceptions.InputLengthException;
import weightsimulator.exceptions.UnknownInputException;
import weightsimulator.exceptions.UnsupportedWeightException;

public interface IWeightController {
	public int server_Run() throws IOException, UnknownInputException,
	InputLengthException, UnsupportedWeightException;
	public void writeSocket(String s) throws IOException;
	public void closeStreams();
}
