package fobbah.replaydataloader;

public class BasicEvent extends ReplayEvent 
{
	public BasicEvent(int framecount, int playerID, String eventType)
	{
		//Fix to return event type based on string
		super(framecount, playerID, ReplayEvent.Event_Unknown);
		if(eventType.equals("PlayerLeftGame"))
		{
			this.setEventType(ReplayEvent.Event_PlayerLeftGame);
		}
	}
	public void printEvent()
	{
		System.out.println(super.getFrame() + " " + super.getPlayerID() + " " + super.getEventName());
	}
}
