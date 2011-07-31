package freplaydata.parser;

import java.awt.Rectangle;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import belicza.andras.bwhf.control.BinRepParser;
import belicza.andras.bwhf.model.Action;
import belicza.andras.bwhf.model.Replay;

import freplaydata.model.FReplayData;



/**
 * Creates a player actions log of the sc bw replay.
 *
 */
public class ReplayLogForBWAPI {

	public static final int RACE_INITUNITS_COUNT 				 = 5;
	public static final int RACE_ZERG_INITUNITS_COUNT   		 = 9;
	public static final int RACE_ZERG_LARVA_COUNT   			 = 3;
	public static final int RACE_WORKER_COUNT			   		 = 4;
	public static final int MINERALS_X_POS_OFFSET		   		 = 32;
	public static final int MINERALS_Y_POS_OFFSET		   		 = 16;
	public static final int MINERALS_Y_GRAFICS_OFFSET	   		 = 16;
	public static final int MINERALS_WIDTH				   		 = 64;
	public static final int MINERALS_HEIGHT				   		 = 32;
	
	
	Replay replay;
	FReplayData fReplay;
	public int[] initUnitsCounter = {0,0,0,0,0,0,0,0,0,0,0,0};  // Max count of player is 12
	

	FileInputStream input;
	DataInputStream dis;
	BufferedInputStream bis;
	BufferedReader br;
	final int STARTING_FRAME = 0;
	

	public ReplayLogForBWAPI(File replayRGD, File replayFile) {				
		replay = BinRepParser.parseReplay( replayFile, true, false, true, false );
		if (replayRGD.isDirectory() || !replayRGD.exists()){
			System.out.println("File is direcotry or not existent!");
		} 
		else {
			try {
				input = new FileInputStream( replayRGD );
				dis = new DataInputStream(input);
				br = new BufferedReader(new InputStreamReader(dis));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}	
			try {
				String line;
				boolean isData = false;
				boolean playerSection = false;
				fReplay = new FReplayData(replayRGD.getName());
				while ((line = br.readLine()) != null) {
					if (!isData){  // Header
						if (line.contains("MapName")) fReplay.mapName = line.split(":")[1];
						if (line.contains("NumStartPositions")) fReplay.startingPointsCount = Integer.parseInt(line.split(":")[1].trim());
						if (line.contains("Begin replay data:")) {
							fReplay.playerCount = 0;
							for (int id: fReplay.bwapiPlayerIndex) {
								if (id >= 0) fReplay.playerCount++;
							}
							playerSection = false;
							isData = true;
						}
						if (playerSection) {
//							System.out.println(line);
							String[] fields = line.split(",");
							int playerID = Integer.parseInt(fields[0]);
							fReplay.playerNames[playerID] = fields[1].substring(1);
							fReplay.playerRaces[playerID] = fields[2];
							fReplay.playerStartingPointIds[playerID] = Integer.parseInt(fields[3].trim());
							fReplay.replayPlayerIndex[playerID] = replay.replayHeader.getPlayerIndexByName(fReplay.playerNames[playerID]);  // just for simplicity
							fReplay.bwapiPlayerIndex[playerID] = playerID;  // just for simplicity
							if (fReplay.playerRaces[playerID].contains("Zerg"))
								fReplay.initUnitsIDs.add(playerID, new int[RACE_ZERG_INITUNITS_COUNT][2]);								
							else 
								fReplay.initUnitsIDs.add(playerID, new int[RACE_INITUNITS_COUNT][2]);
							fReplay.initLarvaIDs.add(playerID,new ArrayList<Integer>());
						}
						if (line.contains("The following players are in this replay:"))
							playerSection = true;
					}
					else if (!line.equals("[EndGame]")) {		  // Frame data
						String[] fields = line.split(",");
						if (Integer.parseInt(fields[0]) == STARTING_FRAME && fields[2].equals("Created") && !fields[2].equals("R")){
							if (Integer.parseInt(fields[1]) == -1){
								int[] res = new int[3];
								res[0] = Integer.parseInt(fields[3]);
								res[1] = Integer.parseInt(fields[5].substring(1));
								res[2] = Integer.parseInt(fields[6].substring(0, fields[6].length()-1));
								fReplay.ressource.add(res);
							} 
							else {
								fReplay.initUnitsIDs.get(Integer.parseInt(fields[1]))[initUnitsCounter[Integer.parseInt(fields[1])]][0] = Integer.parseInt(fields[3].trim());
								if (fields[4].contains("Drone")) {
									fReplay.initUnitsIDs.get(Integer.parseInt(fields[1]))[initUnitsCounter[Integer.parseInt(fields[1])]][1] = FReplayData.INIT_WORKER;
								} 
								else if (fields[4].contains("Hatchery")){
									fReplay.initUnitsIDs.get(Integer.parseInt(fields[1]))[initUnitsCounter[Integer.parseInt(fields[1])]][1] = FReplayData.INIT_HQ;
								} 
								else if (fields[4].contains("Overlord")) {
									fReplay.initUnitsIDs.get(Integer.parseInt(fields[1]))[initUnitsCounter[Integer.parseInt(fields[1])]][1] = FReplayData.INIT_OVERLORD;
								} 
								else  {
									fReplay.initUnitsIDs.get(Integer.parseInt(fields[1]))[initUnitsCounter[Integer.parseInt(fields[1])]][1] = FReplayData.INIT_LARVA;
								}
								initUnitsCounter[Integer.parseInt(fields[1])]++;
							}
						} 
						else {
//							System.out.println(line);
							if (fields[2].contains("Morph") && Integer.parseInt(fields[1].trim()) != -1){
								for (int[] unit : fReplay.initUnitsIDs.get(Integer.parseInt(fields[1]))){
									if (unit[0] == Integer.parseInt(fields[3].trim()) 
											&& unit[1] == FReplayData.INIT_LARVA
											&& !fReplay.initLarvaIDs.get(Integer.parseInt(fields[1])).contains(Integer.parseInt(fields[3].trim()))){
										fReplay.initLarvaIDs.get(Integer.parseInt(fields[1])).add(Integer.parseInt(fields[3].trim()));
									}
								}
							}
						}
					}
				}
				if (fReplay.playerCount < 3) {
					initUnitIDMap();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}
	
	/**
	 * Searches the mineralfield by the position (x,y). 
	 * @param x x-coordinate 
	 * @param y y-coordinate
	 * @return a ArrayList of one or two minerals
	 */
	public ArrayList<int[]> getNearestMinerals(int x, int y){
		ArrayList<int[]> ret = new ArrayList<int[]>();
		for (int[] minerals : fReplay.ressource) {
			//calculate the map difference distance
			Rectangle minRect = new Rectangle(minerals[1] - MINERALS_X_POS_OFFSET, 
												minerals[2] - MINERALS_Y_POS_OFFSET-MINERALS_Y_GRAFICS_OFFSET, 
												MINERALS_WIDTH, 
												MINERALS_HEIGHT + MINERALS_Y_GRAFICS_OFFSET);
			if (minRect.contains(x, y)) ret.add(minerals);			
		}
		return ret;
	}	

	private void initUnitIDMap(){
		for (int playerID : fReplay.bwapiPlayerIndex) {
			if (playerID != -1 ) {
				// rPlayerId needed because the IDs are miss matched TODO: fix that by replay parsing
				int rPlayerId = replay.replayHeader.playerIds[fReplay.replayPlayerIndex[playerID]]; 
				replay.unitIDsMap.add(playerID, new HashMap<Integer,Integer>());
				int[] oIDs = new int[replay.initHQID.get(rPlayerId).size() + replay.initWorkerIDs.get(rPlayerId).size() + replay.initLarvaIDs.get(rPlayerId).size()];
				
				int i = 0;
				boolean hqFound = false;
				boolean overlordFound = false;
				for(Integer unit : replay.initHQID.get(rPlayerId)){
					for (int[] fUnit : fReplay.initUnitsIDs.get(playerID)){
						if (fUnit[1] == FReplayData.INIT_HQ && !replay.unitIDsMap.get(playerID).containsValue(fUnit[0])){
							replay.unitIDsMap.get(playerID).put(unit, fUnit[0]);
							hqFound = true;
						}
					}
					oIDs[i++] = unit;
				}
				for(Integer unit : replay.initWorkerIDs.get(rPlayerId)){
					for (int[] fUnit : fReplay.initUnitsIDs.get(playerID)){
						if (fUnit[1] == FReplayData.INIT_WORKER && !replay.unitIDsMap.get(playerID).containsValue(fUnit[0])){
							replay.unitIDsMap.get(playerID).put(unit, fUnit[0]);
						}
					}
					oIDs[i++] = unit;
				}
				for (int j	= 0 ; j < replay.initLarvaIDs.get(rPlayerId).size(); j++){
					try {
						int larvaId = replay.initLarvaIDs.get(rPlayerId).get(j);
						replay.unitIDsMap.get(playerID).put(larvaId, fReplay.initLarvaIDs.get(playerID).get(j));
						oIDs[i++] = larvaId;
					} catch (Exception e) {
						System.out.println("Larva matching is wrong!");
						e.printStackTrace();						
					}
				}
				Arrays.sort(oIDs);
				if (!hqFound || !overlordFound){
					int inbetweenID = -1;
					for (int j = 1; j < oIDs.length-1; j++) {
						if ((oIDs[j-1] + 1) != oIDs[j]){
							inbetweenID = (oIDs[j-1] + 1);
						}
					}					
					for (int[] fUnit : fReplay.initUnitsIDs.get(playerID)){						
						if (fUnit[1] == FReplayData.INIT_HQ && !hqFound){
							replay.unitIDsMap.get(playerID).put(oIDs[0]-1, fUnit[0]);
						}				
						if (fUnit[1] == FReplayData.INIT_OVERLORD && !overlordFound){
							if (inbetweenID == -1)
								replay.unitIDsMap.get(playerID).put(oIDs[oIDs.length-1]+1, fUnit[0]);
							else
								replay.unitIDsMap.get(playerID).put(inbetweenID, fUnit[0]);
						}
					}

				}
			}
		}
	}
	
	/**
	 * Prints the replay header information into the specified output writer.
	 * @param output output print writer to be used
	 */
	public void printActions( PrintWriter output) {
		for (String player : replay.replayActions.playerNameActionListMap.keySet()){
			int playerId = fReplay.getPlayerIndexByName(player);
			if (playerId != -1) {
				int rPlayerId = replay.replayHeader.getPlayerIndexByName(player);
				for (Action action : replay.replayActions.playerNameActionListMap.get(player)) {
					if (action.actionNameIndex == Action.ANI_SELECT || action.actionNameIndex == Action.ANI_SHIFT_DESELECT || action.actionNameIndex == Action.ANI_SHIFT_SELECT){
						String selUnits = "";
						for (int id : action.selectedUnits){
							if (replay.unitIDsMap.get(playerId).containsKey(id))
								selUnits += id + ">" + replay.unitIDsMap.get(playerId).get(id) + ",";
							else 
								selUnits += id + ">" + id +",";
						}
						if (!selUnits.equals("")) output.println(action.toString( player, false, "[" + selUnits.substring(0, selUnits.length()-1) +"]" ));
					} 
					else if (action.actionNameIndex == Action.ANI_ATTACK_MOVE || action.actionNameIndex == Action.ANI_MOVE) {
						if (replay.unitIDsMap.get(playerId).containsKey(action.targetUnitID))
							output.println(action.toString( player, false, "[" + action.targetUnitID + ">" + replay.unitIDsMap.get(playerId).get(action.targetUnitID) +"]" ));
						else if (action.targetUnitID != Action.TARGET_UNIT_ID_NOT_SET) {
							ArrayList<int []> mins = getNearestMinerals(action.getTargetX(),action.getTargetY());
							if (mins.isEmpty()) {
								output.println(action.toString( player, false, "[" + action.targetUnitID + ">" + action.targetUnitID +"]" ));
							} 
							else {
								if (mins.size() == 2) {
									if (mins.get(0)[2] > action.getTargetY()) {
										output.println(action.toString( player, false, "[" + action.targetUnitID + ">" + mins.get(0)[0] +"]" ));
									}
									else {
										output.println(action.toString( player, false, "[" + action.targetUnitID + ">" + mins.get(1)[0] +"]" ));
									}
								}
								else {
									output.println(action.toString( player, false, "[" + action.targetUnitID + ">" + mins.get(0)[0] +"]" ));
									replay.unitIDsMap.get(playerId).put(action.targetUnitID,mins.get(0)[0]);
								}
							}
						}
						else 
							output.println(action.toString( player, false ));
					} 
					else {
						output.println(action.toString( player, false ));
					}
				}
			}
		}
		output.flush();
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Path to the replay files
		String dataPath = "C:/Data(Port)/Masterarbeit/DATA";
		
		File[] replayNames = new File[] { new File(dataPath + "/zvzReps/" + "ZvZ_020_008.rep") };
		File logfile = new File(dataPath + "/zvzRepLogs/" + "logFile.log");
		File repsFiles = new File(dataPath + "/zvzReps" );
		PrintWriter logout = null;
		try {
			logout = new PrintWriter(new PrintWriter(logfile));
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		
		try {
			logfile.createNewFile();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		for (File file : repsFiles.listFiles()){
//		for (File file : replayNames){
			
			System.out.print("Working at " + file.getName());
			logout.print("Working at " + file.getName());
			
			ReplayLogForBWAPI repLog = new ReplayLogForBWAPI(new File(dataPath + "/zvzAnalysis/" + file.getName() + ".rgd"), file);
			if (repLog.fReplay.playerCount < 3) {
				File outputFile = new File(dataPath + "/zvzRepLogs/" + file.getName() + ".log");
				try {
					outputFile.createNewFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					PrintWriter out = new PrintWriter(outputFile);
					repLog.replay.replayHeader.printHeaderInformation(out);
					repLog.printActions(out);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				System.out.println("  finisched with " + file.getName());
				logout.println("  finisched with " + file.getName());
			}
			else {
				System.out.println("  skipped " + file.getName());
				logout.println("  skipped " + file.getName());
			}
		}
		logout.flush();
	}

}
