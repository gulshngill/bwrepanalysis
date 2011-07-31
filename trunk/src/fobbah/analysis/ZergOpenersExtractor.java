package fobbah.analysis;
import fobbah.replaydataloader.*;
import java.util.LinkedList;

public class ZergOpenersExtractor 
{
	
	private String repPath;
	
	//Zerg Initial Openers
	private int dronecount = 4;
	private boolean pooldone = false;
	private boolean gasDone = false;
	private boolean ovyDone = false;
	private boolean hatchdone = false;
	private String openerType = "";
	
	public static String ninepool = "1";
	public static String overpool = "2";
	public static String twelvehatch = "3";
	public static String ninepoolgas = "4";
	public static String twelvepool = "5";
	
	public int observedPoolFrame = -1;
	
	public int playerID = -1;
	
	public boolean isKnownOpener()
	{
		return(	openerType.equals(ninepool) || 
				openerType.equals(overpool) ||
				openerType.equals(twelvehatch) ||
				openerType.equals(ninepoolgas) ||
				openerType.equals(twelvepool));
	}
		
	public void initialiseOpenerFeatures(Replay r)
	{
		LinkedList<ReplayEvent> events = r.playerRepEvents.get(playerID);
		for (ReplayEvent evt : events)
		{
			if(evt.getEventType() == ReplayEvent.Event_Morph)
			{
				MorphEvent mevt = (MorphEvent)evt;			
				if(mevt.getUnitType().equals("Zerg Drone"))
				{
					dronecount++;
				}
				else if(mevt.getUnitType().equals("Zerg Spawning Pool"))
				{
					if(openerType.equals(""))
					{
						if(dronecount < 9)
						{
							//openerType = dronecount+"pool";
							openerType = -dronecount + "";
						}
						else if(dronecount == 9 && ovyDone == true)
						{
							//openerType = "overpool";
							openerType = overpool;
						}
						else if(dronecount == 9 && gasDone == true)
						{
							//openerType = "9poolgas";
							openerType = ninepoolgas;
							
						}
						else if(dronecount == 9)
						{
							//openerType = "9pool";
							openerType = ninepool;
						}
						else if(dronecount == 12)
						{
							//openerType = "12pool";
							openerType = twelvepool;
						}
					}
					dronecount--;
				}
				else if(mevt.getUnitType().equals("Zerg Hatchery"))
				{
					if(openerType.equals(""))
					{
						if(dronecount == 12)
						{
							//openerType = dronecount+"hatch";
							openerType = twelvehatch;
						}
					}
					dronecount--;
				}
				else if(mevt.getUnitType().equals("Zerg Extractor"))
				{
					gasDone = true;
					dronecount--;
				}
				else if(mevt.getUnitType().equals("Zerg Overlord"))
				{
					ovyDone = true;
				}
			}
		}
		if(openerType.equals(""))
		{
			openerType = "-100";
		}
		
		LinkedList<DiscoveredEvent> observerEvents = r.playerDiscoveredUnitEvents.get(r.getOpponentID(playerID));
		
		for (DiscoveredEvent d : observerEvents)
		{
			if(d.getUnitType().equals("Zerg Spawning Pool"))
			{
				if(observedPoolFrame <= 0)
				{
					observedPoolFrame = r.getFirstMorphedFrame(d.getUnitID(), d.getUnitType());
				}
				//System.out.println(observedPoolFrame);
			}
		}
			
	}
	
	public static String getFeatureTypes()
	{
		return "opener,player,path,observedPoolFrame";
	}
	
	
	public String getFeatures()
	{
		return openerType + "," + playerID + "," + repPath.substring(17) + "," + observedPoolFrame;
	}
	
	public String getOpener()
	{
		return openerType;
	}
	
	public ZergOpenersExtractor(Replay r, String repPath_in, int player)
	{
		playerID = player;
		repPath = repPath_in;
		initialiseOpenerFeatures(r);
	}
	
}
