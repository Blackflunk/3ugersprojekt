package wcu.data;

import java.util.ArrayList;

public interface RaavareDAO {
	public RaavareDTO getRaavare(int raavareID);
	public void createRaavare(RaavareDTO raavare);
	public void updateRaavare(RaavareDTO raavare);
	public ArrayList<RaavareDTO> getRaavareList();
}
