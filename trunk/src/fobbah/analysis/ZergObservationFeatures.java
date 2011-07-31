package fobbah.analysis;
import fobbah.replaydataloader.*;
import java.util.LinkedList;

public class ZergObservationFeatures 
{
	//Frame Building Constructed
	private int secondHatch = 0;
	private int thirdHatch = 0;
	private int lair = 0;
	private int spire = 0;
	private int hydra = 0;
	private int lurkers = 0;
	private int zerglingspeed = 0;
	private int pool = 0;
	
	
	//Frame Observed Building
	
	private String repPath;
	
	public String getFeatures()
	{
		return secondHatch + "," + thirdHatch + "," + lair + "," + spire + "," + hydra + "," + lurkers + "," + zerglingspeed + "," + pool + "," + repPath;
	}
	
	public static String getFeatureTypes()
	{
		return "secondHatch,thirdHatch,lair,spire,hydraden,lurkers,zerglingspeed,pool,path";
	}
	
	public void loadBasicFeatures(LinkedList<ReplayEvent> events, String repPath_in)
	{
		repPath = repPath_in;
		for (ReplayEvent evt : events)
		{
			if(evt.getEventType() == ReplayEvent.Event_StartResearch)
			{
				StartResearchEvent revt = (StartResearchEvent)evt;
				if(revt.getResearchType().equals("Lurker Aspect"))
				{
					if(lurkers == 0)
					{
						lurkers = revt.getFrame();
					}
				}
			}
			else if(evt.getEventType() == ReplayEvent.Event_StartUpgrade)
			{
				StartUpgradeEvent revt = (StartUpgradeEvent)evt;
				if(revt.getUpgradeType().equals("Metabolic Boost"))
				{
					if(zerglingspeed == 0)
					{
						zerglingspeed = revt.getFrame();
					}
				}				
			}
			else if(evt.getEventType() == ReplayEvent.Event_Morph)
			{

				MorphEvent mevt = (MorphEvent)evt;
				
				if(mevt.getUnitType().equals("Zerg Hatchery"))
				{
					if(secondHatch == 0)
					{
						secondHatch = mevt.getFrame();
					}
					else if(thirdHatch == 0)
					{
						thirdHatch = mevt.getFrame();
					}
				}
				
				if(mevt.getUnitType().equals("Zerg Lair"))
				{
					if(lair == 0)
					{
						lair = mevt.getFrame();
					}
				}
				
				if(mevt.getUnitType().equals("Zerg Spire"))
				{
					if(spire == 0)
					{
						spire = mevt.getFrame();
					}
				}
				
				if(mevt.getUnitType().equals("Zerg Hydralisk Den"))
				{
					if(hydra == 0)
					{
						hydra = mevt.getFrame();
					}
				}
				
				if(mevt.getUnitType().equals("Zerg Spawning Pool"))
				{
					if(pool == 0)
					{
						pool = mevt.getFrame();
					}
				}
			}
		}
	}
	
	public ZergObservationFeatures(LinkedList<ReplayEvent> events, String repPath_in, int maxObsFrame)
	{
		
	}
	
	public ZergObservationFeatures(LinkedList<ReplayEvent> events, String repPath_in)
	{
		loadBasicFeatures(events, repPath_in);
	}
}
