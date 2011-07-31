package fobbah.analysis;
import fobbah.replaydataloader.*;
import java.util.LinkedList;

public class ZergEISFeatures 
{
	
	private String repPath;
	
	//EIS Opening Features:
	private BuildTiming zergPool = new BuildTiming();
	private BuildTiming zergOverlord = new BuildTiming();
	private BuildTiming zergZergling = new BuildTiming();
	private BuildTiming zergZerglingSpeed = new BuildTiming();
	private BuildTiming zergCrackling = new BuildTiming();
	private BuildTiming zergBurrow = new BuildTiming();
	private BuildTiming zergGas = new BuildTiming();
	private BuildTiming zergSecondGas = new BuildTiming();
	private BuildTiming zergSecondHatch = new BuildTiming();
	private BuildTiming zergThirdHatch = new BuildTiming();
	private BuildTiming zergFourthHatch = new BuildTiming();
	private BuildTiming zergFifthHatch = new BuildTiming();
	private BuildTiming zergSixthHatch = new BuildTiming();
	private BuildTiming zergSeventhHatch = new BuildTiming();
	private BuildTiming zergHydraDen = new BuildTiming();
	private BuildTiming zergHydra = new BuildTiming();
	private BuildTiming zergHydraRange = new BuildTiming();
	private BuildTiming zergHydraSpeed = new BuildTiming();
	private BuildTiming zergLurker = new BuildTiming();
	private BuildTiming zergLair = new BuildTiming();
	private BuildTiming zergSpire = new BuildTiming();
	private BuildTiming zergMutalisk = new BuildTiming();
	private BuildTiming zergScourge = new BuildTiming();
	private BuildTiming zergAirAttack = new BuildTiming();
	private BuildTiming zergAirArmor = new BuildTiming();
	private BuildTiming zergQueenNest = new BuildTiming();
	private BuildTiming zergQueen = new BuildTiming();
	private BuildTiming zergHive = new BuildTiming();
	private BuildTiming zergGreaterSpire = new BuildTiming();
	private BuildTiming zergGardian = new BuildTiming();
	private BuildTiming zergDevour = new BuildTiming();
	private BuildTiming zergUltraliskCavern = new BuildTiming();
	private BuildTiming zergUltralisk = new BuildTiming();
	private BuildTiming zergUltraliskArmor = new BuildTiming();
	private BuildTiming zergUltraliskSpeed = new BuildTiming();
	private BuildTiming zergDefilerMound = new BuildTiming();
	private BuildTiming zergDefiler = new BuildTiming();
	private BuildTiming zergConsume = new BuildTiming();
	private BuildTiming zergevoDen = new BuildTiming();
	private BuildTiming zergMeleeWeapons1 = new BuildTiming();
	private BuildTiming zergRangeWeapons1 = new BuildTiming();
	private BuildTiming zergGroundArmor1 = new BuildTiming();
	private BuildTiming zergMeleeWeapons2 = new BuildTiming();
	private BuildTiming zergRangeWeapons2 = new BuildTiming();
	private BuildTiming zergGroundArmor2 = new BuildTiming();
	private BuildTiming zergCreep = new BuildTiming();
	private BuildTiming zergSunken = new BuildTiming();
	private BuildTiming zergSpore = new BuildTiming();
	

	
	public String getFeatures()
	{
		//return secondHatch + "," + thirdHatch + "," + lair + "," + spire + "," + hydra + "," + lurkers + "," + zerglingspeed + "," + pool + "," + repPath;
		return 	zergPool + "," +
				zergOverlord + "," +
				zergZergling + "," +
				zergZerglingSpeed + "," +
				zergCrackling + "," +
				zergBurrow + "," +
				zergGas + "," +
				zergSecondGas + "," +
				zergSecondHatch + "," +
				zergThirdHatch + "," +
				zergFourthHatch + "," +
				zergFifthHatch + "," +
				zergSixthHatch + "," +
				zergSeventhHatch + "," +
				zergHydraDen + "," +
				zergHydra + "," +
				zergHydraRange + "," +
				zergHydraSpeed + "," +
				zergLurker + "," +
				zergLair + "," +
				zergSpire + "," +
				zergMutalisk + "," +
				zergScourge + "," +
				zergAirAttack + "," +
				zergAirArmor + "," +
				zergQueenNest + "," +
				zergQueen + "," +
				zergHive + "," +
				zergGreaterSpire + "," +
				zergGardian + "," +
				zergDevour + "," +
				zergUltraliskCavern + "," +
				zergUltralisk + "," +
				zergUltraliskArmor + "," +
				zergUltraliskSpeed + "," +
				zergDefilerMound + "," +
				zergDefiler + "," +
				zergConsume + "," +
				zergevoDen + "," +
				zergMeleeWeapons1 + "," +
				zergRangeWeapons1 + "," +
				zergGroundArmor1 + "," +
				zergMeleeWeapons2 + "," +
				zergRangeWeapons2 + "," +
				zergGroundArmor2 + "," +
				zergCreep + "," +
				zergSunken + "," +
				zergSpore;
	}
	
	public static String getFeatureTypes()
	{
		return "" +
			"ZergPool," +
			"ZergOverlord," +
			"ZergZergling," +
			"ZergZerglingSpeed," +
			"ZergCrackling," +
			"ZergBurrow," +
			"ZergGas," +
			"ZergSecondGas," +
			"ZergSecondHatch," +
			"ZergThirdHatch," +
			"ZergFourthHatch," +
			"ZergFifthHatch," +
			"ZergSixthHatch," +
			"ZergSeventhHatch," +
			"ZergHydraDen," +
			"ZergHydra," +
			"ZergHydraRange," +
			"ZergHydraSpeed," +
			"ZergLurker," +
			"ZergLair," +
			"ZergSpire," +
			"ZergMutalisk," +
			"ZergScourge," +
			"ZergAirAttack," +
			"ZergAirArmor," +
			"ZergQueenNest," +
			"ZergQueen," +
			"ZergHive," +
			"ZergGreaterSpire," +
			"ZergGardian," +
			"ZergDevour," +
			"ZergUltraliskCavern," +
			"ZergUltralisk," +
			"ZergUltraliskArmor," +
			"ZergUltraliskSpeed," +
			"ZergDefilerMound," +
			"ZergDefiler," +
			"ZergConsume," +
			"ZergevoDen," +
			"ZergMeleeWeapons1," +
			"ZergRangeWeapons1," +
			"ZergGroundArmor1," +
			"ZergMeleeWeapons2," +
			"ZergRangeWeapons2," +
			"ZergGroundArmor2," +
			"ZergCreep," +
			"ZergSunken," +
			"ZergSpore";
		//return "secondHatch,thirdHatch,lair,spire,hydraden,lurkers,zerglingspeed,pool,path";
	}
	
	
	
	public void initialiseEISFeatures(LinkedList<ReplayEvent> events, String repPath_in)
	{
		repPath = repPath_in;
		for(ReplayEvent evt : events)
		{
			if(evt.getEventType() == ReplayEvent.Event_Morph)
			{

				MorphEvent mevt = (MorphEvent)evt;
				
				if(mevt.getUnitType().equals("Zerg Spawning Pool"))
				{
					zergPool.setInitial(mevt.getFrame());
				}
				else if(mevt.getUnitType().equals("Zerg Overlord"))
				{
					zergOverlord.setInitial(mevt.getFrame());
				}
				else if(mevt.getUnitType().equals("Zerg Zergling"))
				{
					zergZergling.setInitial(mevt.getFrame());
				}
				else if(mevt.getUnitType().equals("Zerg Extractor"))
				{
					if(!zergGas.hasBeenPlaced())
					{
						zergGas.setInitial(mevt.getFrame());
					}
					else if(!zergSecondGas.hasBeenPlaced())
					{
						zergSecondGas.setInitial(mevt.getFrame());
					}
				}
				else if(mevt.getUnitType().equals("Zerg Hatchery"))
				{
					if(!zergSecondHatch.hasBeenPlaced())
					{
						zergSecondHatch.setInitial(mevt.getFrame());
					}
					else if(!zergThirdHatch.hasBeenPlaced())
					{
						zergThirdHatch.setInitial(mevt.getFrame());
					}
					else if(!zergFourthHatch.hasBeenPlaced())
					{
						zergFourthHatch.setInitial(mevt.getFrame());
					}
					else if(!zergFifthHatch.hasBeenPlaced())
					{
						zergFifthHatch.setInitial(mevt.getFrame());
					}
					else if(!zergSixthHatch.hasBeenPlaced())
					{
						zergSixthHatch.setInitial(mevt.getFrame());
					}
					else
					{
						zergSeventhHatch.setInitial(mevt.getFrame());
					}
				}
				else if(mevt.getUnitType().equals("Zerg Hydralisk Den"))
				{
					zergHydraDen.setInitial(mevt.getFrame());
				}
				else if(mevt.getUnitType().equals("Zerg Hydralisk"))
				{
					zergHydra.setInitial(mevt.getFrame());
				}
				else if(mevt.getUnitType().equals("Zerg Lurker"))
				{
					zergLurker.setInitial(mevt.getFrame());
				}
				else if(mevt.getUnitType().equals("Zerg Lair"))
				{
					zergLair.setInitial(mevt.getFrame());
				}
				else if(mevt.getUnitType().equals("Zerg Spire"))
				{
					zergSpire.setInitial(mevt.getFrame());
				}
				else if(mevt.getUnitType().equals("Zerg Mutalisk"))
				{
					zergMutalisk.setInitial(mevt.getFrame());
				}
				else if(mevt.getUnitType().equals("Zerg Queens Nest"))
				{
					zergQueenNest.setInitial(mevt.getFrame());
				}
				else if(mevt.getUnitType().equals("Zerg Queen"))
				{
					zergQueen.setInitial(mevt.getFrame());
				}
				else if(mevt.getUnitType().equals("Zerg Hive"))
				{
					zergHive.setInitial(mevt.getFrame());
				}
				else if(mevt.getUnitType().equals("Zerg Greater Spire"))
				{
					zergGreaterSpire.setInitial(mevt.getFrame());
				}
				else if(mevt.getUnitType().equals("Zerg Guardian"))
				{
					zergGardian.setInitial(mevt.getFrame());
				}
				else if(mevt.getUnitType().equals("Zerg Devourer"))
				{
					zergDevour.setInitial(mevt.getFrame());
				}
				else if(mevt.getUnitType().equals("Zerg Ultralisk Cavern"))
				{
					zergUltraliskCavern.setInitial(mevt.getFrame());
				}
				else if(mevt.getUnitType().equals("Zerg Ultralisk"))
				{
					zergUltralisk.setInitial(mevt.getFrame());
				}
				else if(mevt.getUnitType().equals("Zerg Defiler Mound"))
				{
					zergDefilerMound.setInitial(mevt.getFrame());
				}
				else if(mevt.getUnitType().equals("Zerg Defiler"))
				{
					zergDefiler.setInitial(mevt.getFrame());
				}
				else if(mevt.getUnitType().equals("Zerg Evolution Chamber"))
				{
					zergevoDen.setInitial(mevt.getFrame());
				}
				else if(mevt.getUnitType().equals("Zerg Creep Colony"))
				{
					zergCreep.setInitial(mevt.getFrame());
				}
				else if(mevt.getUnitType().equals("Zerg Sunken Colony"))
				{
					zergSunken.setInitial(mevt.getFrame());
				}
				else if(mevt.getUnitType().equals("Zerg Spore Colony"))
				{
					zergSpore.setInitial(mevt.getFrame());
				}
			}
			else if(evt.getEventType() == ReplayEvent.Event_StartUpgrade)
			{
				
			}
			else if(evt.getEventType() == ReplayEvent.Event_FinishUpgrade)
			{
				
			}
		}
	}
	
	public ZergEISFeatures(LinkedList<ReplayEvent> events, String repPath_in)
	{
		initialiseEISFeatures(events, repPath_in);
	}
	
}
