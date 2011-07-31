package belicza.andras.bwhf.model;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Class modeling the actions of a replay.
 * 
 * @author Andras Belicza
 */
public class ReplayActions {
	
	/** The players action map. The key is the player name, the value is the player's action list. */
	public final Map< String, List< Action > > playerNameActionListMap;
	
	/** Players of the replay. */
	public final PlayerActions[] players;
	
	/**
	 * Creates a new ReplayActions.
	 * 
	 * @param playerNameActionListMap map containing the actions of the players of the replay
	 */
	public ReplayActions( final Map< String, List< Action > > playerNameActionListMap ) {
		this.playerNameActionListMap = playerNameActionListMap;
		
		players = new PlayerActions[ playerNameActionListMap.size() ];
		
		int i = 0;
		for ( final Map.Entry< String, List< Action > > playerNameActionListEntry : playerNameActionListMap.entrySet() ) {
			final String         playerName       = playerNameActionListEntry.getKey();
			final List< Action > playerActionList = playerNameActionListEntry.getValue();
			final Action[]       playerActions    = playerActionList.toArray( new Action[ playerActionList.size() ] );
			
			players[ i++ ] = new PlayerActions( playerName, playerActions );
		}
	}
	/**
	 * Prints the replay header information into the specified output writer.
	 * @param output output print writer to be used
	 */
	public void printActions( final PrintWriter output) {		
		for (String player : playerNameActionListMap.keySet()){
			for (Action action : playerNameActionListMap.get(player)) {
				if (action.actionNameIndex == Action.ANI_SELECT){
					
				}
				
//				if (!action.toString( player, true ).equals(action.toStringMy( player, true ))){		
//				if (frames.contains(action.iteration + 2) | frames.contains(action.iteration + 1)| frames.contains(action.iteration)){
//					output.println(action.toString( player, false ));
					output.println(action.toString( player, false ));
//				}
//					output.println("M:" + action.toStringMy( player, true ));
//				}
			}
		}
		output.flush();
	}
}
