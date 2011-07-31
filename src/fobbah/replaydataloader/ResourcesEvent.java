package fobbah.replaydataloader;

public class ResourcesEvent extends ReplayEvent 
{
	int currentMins;
	int currentGas;
	int cumulativeMins;
	int cumulativeGas;
	public ResourcesEvent(int frames_in, int playerID_in, String data)
	{
		super(frames_in, playerID_in, ReplayEvent.Event_Resources);
		String dataline = new String(data);
		currentMins = Integer.parseInt(dataline.substring(0, dataline.indexOf(',')));
		dataline = dataline.substring(dataline.indexOf(',') + 1);
		currentGas = Integer.parseInt(dataline.substring(0, dataline.indexOf(',')));
		dataline = dataline.substring(dataline.indexOf(',') + 1);
		cumulativeMins = Integer.parseInt(dataline.substring(0, dataline.indexOf(',')));
		dataline = dataline.substring(dataline.indexOf(',') + 1);
		cumulativeGas = Integer.parseInt(dataline.substring(0, dataline.length() - 1));
	}
	public void printEvent()
	{
		System.out.println(super.getFrame() + " " + super.getPlayerID() + " " + super.getEventName() + " " + currentMins + " " + currentGas + " " + cumulativeMins + " " + cumulativeGas);
	}
	
}
