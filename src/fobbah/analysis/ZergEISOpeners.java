package fobbah.analysis;
import fobbah.replaydataloader.*;
import java.util.LinkedList;

public class ZergEISOpeners 
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
	
	private int playerID = -1;
	
	public void setPlayerID(int id)
	{
		playerID = id;
	}
	
	public boolean isKnownOpener()
	{
		return(	openerType.equals(ninepool) || 
				openerType.equals(overpool) ||
				openerType.equals(twelvehatch) ||
				openerType.equals(ninepoolgas) ||
				openerType.equals(twelvepool));
	}
	
	
	public void initialiseOpenerFeatures(LinkedList<ReplayEvent> events)
	{
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
	}
	
	public static String getOpenerAndPlayerAndPathType()
	{
		return "opener,player,path";
	}
	
	
	public String getOpenerAndPlayerAndPath()
	{
		return openerType + "," + playerID + "," + repPath.substring(17);
	}
	
	public String getOpener()
	{
		return openerType;
	}
	
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
				zergSpore + "," +
				openerType;
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
			"ZergSpore," +
			"OpenerType";
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
				else if(mevt.getUnitType().equals("Zerg Scourge"))
				{
					zergScourge.setInitial(mevt.getFrame());
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
				StartUpgradeEvent ugevt = (StartUpgradeEvent)evt;
				if(ugevt.getUpgradeType().equals("Metabolic Boost"))
				{
					zergZerglingSpeed.setInitial(ugevt.getFrame());
				}
				else if(ugevt.getUpgradeType().equals("Adrenal Glands"))
				{
					zergCrackling.setInitial(ugevt.getFrame());
				}
				else if(ugevt.getUpgradeType().equals("Grooved Spines"))
				{
					zergHydraRange.setInitial(ugevt.getFrame());
				}
				else if(ugevt.getUpgradeType().equals("Muscular Augments"))
				{
					zergHydraSpeed.setInitial(ugevt.getFrame());
				}
				else if(ugevt.getUpgradeType().equals("Zerg Flyer Attacks"))
				{
					if(ugevt.getUpgradeLevel() == 1)
					{
						zergAirAttack.setInitial(ugevt.getFrame());
					}
				}
				else if(ugevt.getUpgradeType().equals("Zerg Flyer Carapace"))
				{
					//ugevt.printEvent();
					if(ugevt.getUpgradeLevel() == 1)
					{
						zergAirArmor.setInitial(ugevt.getFrame());
					}
				}
				else if(ugevt.getUpgradeType().equals("Chitinous Plating"))
				{
					zergUltraliskArmor.setInitial(ugevt.getFrame());
				}
				else if(ugevt.getUpgradeType().equals("Anabolic Synthesis"))
				{
					zergUltraliskSpeed.setInitial(ugevt.getFrame());
				}
				else if(ugevt.getUpgradeType().equals("Zerg Melee Attacks"))
				{
					if(ugevt.getUpgradeLevel() == 1)
					{
						zergMeleeWeapons1.setInitial(ugevt.getFrame());
					}
					else if(ugevt.getUpgradeLevel() == 2)
					{
						zergMeleeWeapons2.setInitial(ugevt.getFrame());
					}
				}
				else if(ugevt.getUpgradeType().equals("Zerg Missile Attacks"))
				{
					if(ugevt.getUpgradeLevel() == 1)
					{
						zergRangeWeapons1.setInitial(ugevt.getFrame());
					}
					else if(ugevt.getUpgradeLevel() == 2)
					{
						zergRangeWeapons2.setInitial(ugevt.getFrame());
					}
				}
				else if(ugevt.getUpgradeType().equals("Zerg Carapace"))
				{
					if(ugevt.getUpgradeLevel() == 1)
					{
						zergGroundArmor1.setInitial(ugevt.getFrame());
					}
					else if(ugevt.getUpgradeLevel() == 2)
					{
						zergGroundArmor2.setInitial(ugevt.getFrame());
					}
				}
			}
			else if(evt.getEventType() == ReplayEvent.Event_StartResearch)
			{
				StartResearchEvent revt = (StartResearchEvent)evt;
				if(revt.getResearchType().equals("Burrowing"))
				{
					zergBurrow.setInitial(revt.getFrame());
				}
				else if(revt.getResearchType().equals("Consume"))
				{
					zergConsume.setInitial(revt.getFrame());
				}
			}
		}
	}
	
	public ZergEISOpeners(LinkedList<ReplayEvent> events, String repPath_in)
	{
		initialiseEISFeatures(events, repPath_in);
		initialiseOpenerFeatures(events);
	}
	
}
