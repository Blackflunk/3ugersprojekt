package cdio3.gwt.client.DAOinterface;

public class DALException extends Exception
{
	private static final long serialVersionUID = -5490114628089339322L;
	public DALException(String string) { super(string); }    
	public DALException(Exception e) { super(e); }
}
