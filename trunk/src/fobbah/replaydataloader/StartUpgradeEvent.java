package fobbah.replaydataloader;

public class StartUpgradeEvent extends ReplayEvent 
{
	String upgradeType = "";
	int upgradeLevel;
	public StartUpgradeEvent(int frames_in, int playerID_in, String data)
	{
		super(frames_in, playerID_in, ReplayEvent.Event_StartUpgrade);
		//System.out.println(data);
		String dataline = new String(data);
		upgradeType = dataline.substring(0, dataline.indexOf(','));
		dataline = dataline.substring(dataline.indexOf(',') + 1);
		upgradeLevel = Integer.parseInt(dataline.substring(0,dataline.length() - 1));
	}
	public void printEvent()
	{
		System.out.println(super.getFrame() + " " + super.getPlayerID() + " " + super.getEventName() + " " + upgradeType + " " + upgradeLevel);
	}
	public String getUpgradeType()
	{
		return upgradeType;
	}
	public int getUpgradeLevel()
	{
		return upgradeLevel;
	}
	
}
