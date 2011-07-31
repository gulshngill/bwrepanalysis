package fobbah.replaydataloader;

public abstract class ReplayEvent 
{
	private int frame;
	private int playerID;
	private int eventType;
	
	public static final int Event_Unknown = 0;
	public static final int Event_Created = 1;
	public static final int Event_Resources = 2;
	public static final int Event_Discovered = 3;
	public static final int Event_Destroyed = 4;
	public static final int Event_Morph = 5;
	public static final int Event_ChangedOwnership = 6;
	public static final int Event_StartUpgrade = 7;
	public static final int Event_FinishUpgrade = 8;
	public static final int Event_CancelUpgrade = 9;
	public static final int Event_StartResearch =  10;
	public static final int Event_FinishResearch = 11;
	public static final int Event_CancelResearch = 12;
	public static final int Event_PlayerLeftGame = 13;
	
	
	public ReplayEvent(int frame_in, int playerID_in, int eventType_in)
	{
		frame = frame_in;
		playerID = playerID_in;
		eventType = eventType_in;
	}
	public String getEventName()
	{
		switch(eventType)
		{
			case Event_Created: return "Created";
			case Event_Resources: return "Resources";
			case Event_Discovered: return "Discovered";
			case Event_Destroyed: return "Destroyed";
			case Event_Morph: return "Morph";
			case Event_ChangedOwnership: return "ChangedOwnership";
			case Event_StartUpgrade: return "StartUpgrade";
			case Event_FinishUpgrade: return "FinishUpgrade";
			case Event_CancelUpgrade: return "CancelUpgrade";
			case Event_StartResearch: return "StartResearch";
			case Event_FinishResearch: return "FinishResearch";
			case Event_CancelResearch: return "CancelResearch";
			case Event_PlayerLeftGame: return "PlayerLeftGame";
			default : return "Unknown";
		}
	}
	public int getFrame()
	{
		return frame;
	}
	public int getPlayerID()
	{
		return playerID;
	}
	
	public int getEventType()
	{
		return eventType;
	}
	
	public abstract void printEvent();
	
	public static ReplayEvent parseEvent(String data)
	{
		//System.out.println(data);
		String dataline = new String(data);
		int frm = Integer.parseInt(dataline.substring(0, dataline.indexOf(',')));
		dataline = dataline.substring(dataline.indexOf(',') + 1);
		int pnum = Integer.parseInt(dataline.substring(0, dataline.indexOf(',')));
		dataline = dataline.substring(dataline.indexOf(',') + 1);
		String etype;
		if(dataline.indexOf(',') != -1)
		{
			etype = dataline.substring(0, dataline.indexOf(','));
			if(etype.equals("Created"))
			{
				return new CreatedEvent(frm, pnum, dataline.substring(dataline.indexOf(',') + 1));
			}
			else if (etype.equals("R"))
			{
				return new ResourcesEvent(frm, pnum, dataline.substring(dataline.indexOf(',') + 1));
			}
			else if (etype.equals("Discovered"))
			{
				return new DiscoveredEvent(frm, pnum, dataline.substring(dataline.indexOf(',') + 1));
			}
			else if (etype.equals("Destroyed"))
			{
				return new DestroyedEvent(frm, pnum, dataline.substring(dataline.indexOf(',') + 1));
			}
			else if (etype.equals("Morph"))
			{
				return new MorphEvent(frm, pnum, dataline.substring(dataline.indexOf(',') + 1));
			}
			else if (etype.equals("ChangedOwnership"))
			{
				return new ChangedOwnershipEvent(frm, pnum, dataline.substring(dataline.indexOf(',') + 1));
			}
			else if (etype.equals("StartUpgrade"))
			{
				return new StartUpgradeEvent(frm, pnum, dataline.substring(dataline.indexOf(',') + 1));
			}
			else if (etype.equals("FinishUpgrade"))
			{
				return new FinishUpgradeEvent(frm, pnum, dataline.substring(dataline.indexOf(',') + 1));
			}
			else if (etype.equals("CancelUpgrade"))
			{
				return new CancelUpgradeEvent(frm, pnum, dataline.substring(dataline.indexOf(',') + 1));
			}
			else if (etype.equals("StartResearch"))
			{
				return new StartResearchEvent(frm, pnum, dataline.substring(dataline.indexOf(',') + 1));
			}
			else if (etype.equals("FinishResearch"))
			{
				return new FinishResearchEvent(frm, pnum, dataline.substring(dataline.indexOf(',') + 1));
			}
			else if (etype.equals("CancelResearch"))
			{
				return new CancelResearchEvent(frm, pnum, dataline.substring(dataline.indexOf(',') + 1));
			}
			else
			{
				return new UnknownEvent(frm, pnum);
			}
		}
		else
		{
			//System.out.println(dataline);
			etype = dataline.substring(0, dataline.length() - 1);
			return new BasicEvent(frm, pnum, etype);
		}
		//return new UnknownEvent;
	}
	
	protected void setEventType(int etype)
	{
		eventType = etype;
	}
	
}
