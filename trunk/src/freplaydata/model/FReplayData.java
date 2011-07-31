package freplaydata.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;



public class FReplayData {
	
//	public static final byte RACE_ZERG    = (byte) 0x00;
//	public static final byte RACE_TERRAN  = (byte) 0x01;
//	public static final byte RACE_PROTOSS = (byte) 0x02;

	public static final int INIT_HQ    = 0;  // Command Center, Nexus, Hive
	public static final int INIT_WORKER  = 1; //  Worker, Drone, Probe, SCV
	public static final int INIT_LARVA = 2; // Larva 
	public static final int INIT_OVERLORD = 3; // Larva 
	
	public int      gameFrames;
	public int      startingPointsCount;
	public String   mapName;
	public String   replayName;

	public String[] playerNames       		= {"","","","","","","","","","","",""};
	public String[] playerRaces       		= new String[ 12 ];
	public int[]    playerStartingPointIds 		= {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
	public int[]    replayPlayerIndex        	= {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};  // just for simplicity
	public int[] 	bwapiPlayerIndex        	= {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};;

	public ArrayList<int[]> ressource = new ArrayList<int[]>();  // int[] = {UnitID, PosX, PosY}
	public ArrayList<int[][]> initUnitsIDs = new ArrayList<int[][]>();  // int[][] ={initsCount} {UnitID, TYPE}   TYPE of INIT_HQ,INIT_WORKER
	public ArrayList<ArrayList<Integer>> initLarvaIDs = new ArrayList<ArrayList<Integer>>();
 	 
	public HashMap<Integer, Integer> neutralUnits = new HashMap<Integer, Integer>();
	public int playerCount;
	
	public FReplayData(String replayName) {		
		this.replayName = replayName;
	}
	
	/**
	 * Returns the index of a player specified by his/her name.
	 * @param playerName name of player to be searched
	 * @return the index of a player specified by his/her name; or -1 if player name not found
	 */
	public int getPlayerIndexByName( final String playerName ) {
		for ( int i = 0; i < playerNames.length; i++ )
			if ( playerNames[ i ] != null && playerNames[ i ].equals( playerName ) )
				return i;
		return -1;
	}

}
