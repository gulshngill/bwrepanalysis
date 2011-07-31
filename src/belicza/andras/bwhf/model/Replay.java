package belicza.andras.bwhf.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Class modeling a replay.
 * 
 * @author Belicza Andras
 */
public class Replay {
	
	/** Header of the replay.                   */
	public final ReplayHeader  replayHeader;
	/** Actions of the replay.                  */
	public final ReplayActions replayActions;
	/** Formatted text of game chat.            */
	public final String        gameChat;
	/** Data of the map.                        */
	public final MapData       mapData;
	public HashSet<Integer> playerOneUnitIDs = new HashSet<Integer>();
	public HashSet<Integer> playerTwoUnitIDs = new HashSet<Integer>();
	public ArrayList<HashMap<Integer,Action>> badIteration = new ArrayList<HashMap<Integer,Action>>();
	/** init units fields 									*/
	public ArrayList<HashSet<Integer>> initHQID = new ArrayList<HashSet<Integer>>();
	public ArrayList<ArrayList<Integer>> initLarvaIDs = new ArrayList<ArrayList<Integer>>();
	public ArrayList<HashSet<Integer>> initWorkerIDs = new ArrayList<HashSet<Integer>>();
	public ArrayList<HashMap<Integer, Integer>> unitIDsMap = new ArrayList<HashMap<Integer, Integer>>();
	
	/**
	 * Creates a new Replay.
	 * @param replayHeader  header of the replay
	 * @param replayActions actions of the replay
	 * @param gameChat      formatted text of game chat
	 */
	public Replay( final ReplayHeader replayHeader, final ReplayActions replayActions, final String gameChat, final MapData mapData ) {
		this.replayHeader  = replayHeader;
		this.replayActions = replayActions;
		this.gameChat      = gameChat;
		this.mapData       = mapData;
	}
	
}
