package fobbah.replaydataloader;

public class ChangedOwnershipEvent extends ReplayEvent 
{
	int unitID = 0;
	public ChangedOwnershipEvent(int frames_in, int playerID_in, String data)
	{
		super(frames_in, playerID_in, ReplayEvent.Event_ChangedOwnership);
		//System.out.println(data);
		String dataline = new String(data);
		unitID = Integer.parseInt(dataline.substring(0, dataline.length() - 1));
	}
	public void printEvent()
	{
		System.out.println(super.getFrame() + " " + super.getPlayerID() + " " + super.getEventName() + " " + unitID);
	}
	
}
