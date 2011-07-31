package fobbah.replaydataloader;
import java.util.LinkedList;
import java.util.HashMap;

public class Replay 
{
	public String repPath = "";
	public String mapName = "";
	public int numStartPositions = 0;
	public int numPlayers = 0;
	public LinkedList<ReplayPlayer> players = new LinkedList<ReplayPlayer>();
	public LinkedList<ReplayEvent> repEvents = new LinkedList<ReplayEvent>();
	public HashMap<Integer, LinkedList<ReplayEvent>> playerRepEvents = new HashMap<Integer, LinkedList<ReplayEvent>>();
	
	public LinkedList<MorphEvent> morphEvents = new LinkedList<MorphEvent>();
	public HashMap<Integer, LinkedList<DiscoveredEvent>> playerDiscoveredUnitEvents = new HashMap<Integer, LinkedList<DiscoveredEvent>>();
	
	public HashMap<Integer, Boolean> playerLostGame = new HashMap<Integer, Boolean>();
	
	private HashMap<Integer, Integer> unitIDCreatedFrame;
	
	public boolean hasLoser = true;
	
	//Note: only works for 2 players
	public int getOpponentID(int playerID)
	{
		//int opponent = -1;
		for(ReplayPlayer p : players)
		{
			if(p.getPlayerNum() != playerID)
			{
				return p.getPlayerNum();
			}
		}
		return -1;
	}
	
	public int getCreatedFrame(int unitID)
	{
		try
		{
			return unitIDCreatedFrame.get(unitID);
		}
		catch (Exception e)
		{
			System.out.println("error while trying to get created frame for unit: " + unitID);
			e.printStackTrace();
			return -1;
		}
	}
	
	public int getFirstMorphedFrame(int unitID, String unitType)
	{
		for (MorphEvent m : morphEvents)
		{
			if(m.getUnitID() == unitID)
			{
				if(m.getUnitType().equals(unitType))
				{
					return m.getFrame();
				}
			}
		}
		return -2;
	}
	
	public Replay()
	{
		//Initialise as a blank state
	}
	
	public void printReplay()
	{
		System.out.println(repPath);
		System.out.println(mapName);
		System.out.println(numStartPositions);
		for (ReplayPlayer p : players)
		{
			p.printPlayer();
		}
		for (ReplayEvent evt : repEvents)
		{
			evt.printEvent();
		}
	}
	
	//Parse the data contained within this Replay Game Data into this replay object
	public void loadRGD(String data)
	{
		try
		{
			//System.out.println(data);
			String[] datalines = data.split("\n");
			int nextline = 0;
			while(datalines[nextline].charAt(0) == '[')
			{
				nextline++;
			}
			repPath = datalines[nextline].substring(datalines[nextline].indexOf(':') + 2, datalines[nextline].length() - 1);
			nextline++;
			mapName = datalines[nextline].substring(datalines[nextline].indexOf(':') + 2, datalines[nextline].length() - 1);
			nextline++;
			numStartPositions = Integer.parseInt(datalines[nextline].substring(datalines[nextline].indexOf(':') + 2, datalines[nextline].length() - 1));
			nextline++;
			nextline++;
			
			//initialise the neutral player
			playerRepEvents.put(-1, new LinkedList<ReplayEvent>());
			
			//iterate until we hit the line "Begin replay data:\n"
			while(datalines[nextline].charAt(0) != 'B')
			{
				ReplayPlayer p = ReplayPlayer.loadFromLine(datalines[nextline]);
				numPlayers ++;
				players.add(p);
				playerRepEvents.put(p.getPlayerNum(), new LinkedList<ReplayEvent>());
				playerDiscoveredUnitEvents.put(p.getPlayerNum(), new LinkedList<DiscoveredEvent>());
				nextline++;
			}
			nextline++;
			while(datalines[nextline].charAt(0) != '[' && nextline < datalines.length)
			{
				ReplayEvent nextEvent = ReplayEvent.parseEvent(datalines[nextline]);
				repEvents.add(nextEvent);
				
				if(nextEvent.getEventType() == ReplayEvent.Event_Morph)
				{
					morphEvents.add((MorphEvent)nextEvent);
				}
				
				if(nextEvent.getEventType() == ReplayEvent.Event_Discovered)
				{
					this.playerDiscoveredUnitEvents.get(nextEvent.getPlayerID()).add(((DiscoveredEvent)nextEvent));
				}

				if(nextEvent.getEventType() == ReplayEvent.Event_PlayerLeftGame)
				{
					this.playerLostGame.put(nextEvent.getPlayerID(), true);
					hasLoser = true;
				}

				if(playerRepEvents.containsKey(nextEvent.getPlayerID()))
				{
					playerRepEvents.get(nextEvent.getPlayerID()).add(nextEvent);
				}
				if(nextEvent.getEventName().equals("Unknown"))
				{
					System.out.print("unknown event type: " +  datalines[nextline]);
				}
				nextline++;
			}
			
			//printReplay();
		}
		catch (Exception e)
		{
			System.out.println("Error parseing replay: " + this.repPath);
			e.printStackTrace();
			//System.out.println(this.repPath);
			//System.out.println()
		}
	}
	
	//Parse the data contained within this Replay Location Data into this replay object
	public void loadRLD(String data)
	{
		//TODO
	}
	
	//Parse the data contained within this Replay Order Data into this replay object
	public void loadROD(String data)
	{
		//TODO
	}
	
}
