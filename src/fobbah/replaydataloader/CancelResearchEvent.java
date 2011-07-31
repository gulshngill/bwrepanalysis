package fobbah.replaydataloader;

public class CancelResearchEvent extends ReplayEvent 
{
	String upgradeType = "";
	int upgradeLevel;
	public CancelResearchEvent(int frames_in, int playerID_in, String data)
	{
		super(frames_in, playerID_in, ReplayEvent.Event_CancelResearch);
		//System.out.println(data);
		String dataline = new String(data);
		upgradeType = dataline.substring(0, dataline.length() - 1);
	}
	public void printEvent()
	{
		System.out.println(super.getFrame() + " " + super.getPlayerID() + " " + super.getEventName() + " " + upgradeType);
	}
	
}
