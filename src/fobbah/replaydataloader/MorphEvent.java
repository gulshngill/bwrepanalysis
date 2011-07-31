package fobbah.replaydataloader;

public class MorphEvent extends ReplayEvent 
{
	int unitID = 0;
	String unitType = "";
	int xloc;
	int yloc;
	public MorphEvent(int frames_in, int playerID_in, String data)
	{
		super(frames_in, playerID_in, ReplayEvent.Event_Morph);
		//System.out.println(data);
		String dataline = new String(data);
		unitID = Integer.parseInt(dataline.substring(0, dataline.indexOf(',')));
		dataline = dataline.substring(dataline.indexOf(',') + 1);
		unitType = dataline.substring(0, dataline.indexOf(','));
		dataline = dataline.substring(dataline.indexOf(',') + 2);
		xloc = Integer.parseInt(dataline.substring(0, dataline.indexOf(',')));
		dataline = dataline.substring(dataline.indexOf(',') + 1);
		yloc = Integer.parseInt(dataline.substring(0, dataline.indexOf(')')));
	}
	public void printEvent()
	{
		System.out.println(super.getFrame() + " " + super.getPlayerID() + " " + super.getEventName() + " " + unitID + " " + unitType + " " + xloc + " " + yloc);
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
