package fobbah.replaydataloader;

public class DiscoveredEvent extends ReplayEvent 
{
	int unitID = 0;
	String unitType = "";
	public DiscoveredEvent(int frames_in, int playerID_in, String data)
	{
		super(frames_in, playerID_in, ReplayEvent.Event_Discovered);
		//System.out.println(data);
		String dataline = new String(data);
		unitID = Integer.parseInt(dataline.substring(0, dataline.indexOf(',')));
		dataline = dataline.substring(dataline.indexOf(',') + 1);
		unitType = dataline.substring(0, dataline.length() - 1);
	}
	public void printEvent()
	{
		System.out.println(super.getFrame() + " " + super.getPlayerID() + " " + super.getEventName() + " " + unitID + " " + unitType);
	}
	public String getUnitType()
	{
		return unitType;
	}
	public int getUnitID()
	{
		return unitID;
	}
	
}
