package fobbah.replaydataloader;

public class UnknownEvent extends ReplayEvent 
{
	public UnknownEvent(int framecount, int playerID)
	{
		super(framecount, playerID, ReplayEvent.Event_Unknown);
	}
	public void printEvent()
	{
		System.out.println(super.getFrame() + " " + super.getPlayerID() + " " + super.getEventName());
	}
}
