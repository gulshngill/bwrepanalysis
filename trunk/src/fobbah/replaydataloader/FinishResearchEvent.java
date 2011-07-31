package fobbah.replaydataloader;

public class FinishResearchEvent extends ReplayEvent 
{
	String upgradeType = "";
	int upgradeLevel;
	public FinishResearchEvent(int frames_in, int playerID_in, String data)
	{
		super(frames_in, playerID_in, ReplayEvent.Event_FinishResearch);
		//System.out.println(data);
		String dataline = new String(data);
		upgradeType = dataline.substring(0, dataline.length() - 1);
	}
	public void printEvent()
	{
		System.out.println(super.getFrame() + " " + super.getPlayerID() + " " + super.getEventName() + " " + upgradeType);
	}
	
}
