package belicza.andras.bwhf.control;

import belicza.andras.bwhf.model.Action;
import belicza.andras.bwhf.model.MapData;
import belicza.andras.bwhf.model.Replay;
import belicza.andras.bwhf.model.ReplayActions;
import belicza.andras.bwhf.model.ReplayHeader;

import java.io.File;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import sun.misc.Cleaner;

/**
 * Replay parser to produce a {@link Replay} java object from a binary replay file.
 * 
 * @author Andras Belicza
 */
public class BinRepParser {
	
//	public static Vector<Integer> frames = new Vector<Integer>();
//	public static Vector<Action> trained = new Vector<Action>();

	public static ArrayList<HashSet<Integer>> playerUnitIDs = new ArrayList<HashSet<Integer>>();
	public static ArrayList<HashSet<Integer>> playerBuildingIDs = new ArrayList<HashSet<Integer>>();
	public static ArrayList<HashSet<Integer>> selectedIDs = new ArrayList<HashSet<Integer>>();
	public static ArrayList<HashSet<Integer>> initHQID = new ArrayList<HashSet<Integer>>();
	public static ArrayList<HashSet<Integer>> initWorkerIDs = new ArrayList<HashSet<Integer>>();
	public static ArrayList<ArrayList<Integer>> initLarvaIDs = new ArrayList<ArrayList<Integer>>();

	public static ArrayList<HashSet<Integer>> posBadIteration = new ArrayList<HashSet<Integer>>();
	public static ArrayList<HashMap<Integer,Action>> badIteration = new ArrayList<HashMap<Integer,Action>>();
	
	
	
	public static HashSet<Integer> union = new HashSet<Integer>();
	public static int[] initUnitsCounter = {0,0,0,0,0,0,0,0,0,0,0,0};
	public static int[] initUnitsCount = {0,0,0,0,0,0,0,0,0,0,0,0};
	public static int[] initWorkerCounter = {0,0,0,0,0,0,0,0,0,0,0,0};
	public static int[] initLarvaCounter = {0,0,0,0,0,0,0,0,0,0,0,0};
	public static boolean[] initHQFlag = {false,false,false,false,false,false,false,false,false,false,false,false};
	
	
	
	
	
	/**
	 * For testing purposes only.
	 * @param arguments
	 */
	public static void main( final String[] arguments ) {
//		final String[] replayNames = new String[] { "c:/rep/4 - hack.rep" };
//		final String[] replayNames = new String[] { "C:/Data(Port)/Masterarbeit/Replays/LFRepPack/1360 vhrvndtjftP qqrftT.rep" };
//		final String[] replayNames = new String[] { "C:/Data(Port)/Masterarbeit/Replays/LFRepPack/clazzvslf.rep" };
		final String[] replayNames = new String[] { "C:/Data(Port)/Masterarbeit/DATA/zvzReps/ZvZ_020_008.rep" };
//		final String[] replayNames = new String[] { "C:/Data(Port)/Masterarbeit/Replays/LFRepPack/What The Fuck Exploding Rin.rep" };
//		final String[] replayNames = new String[] { "C:/Data(Port)/Masterarbeit/Replays/LFRepPack/1950 JambiP LuckyFoolT.rep" };
//		final String[] replayNames = new String[] { "C:/Program Files (x86)/StarCraft/Maps/replays/LastReplay.rep" };
//		final String[] replayNames = new String[] { "C:/Program Files (x86)/StarCraft/Maps/replays/tester.rep" };
		
//		final String[] replayNames = new String[] { "C:/Program Files (x86)/StarCraft/Maps/replays/iCCup  Match _TvsP_100510.rep" };
//		final String[] replayNames = new String[] { "C:/Program Files (x86)/StarCraft/Maps/replays/iCCup  Match _TvsZ_102404.rep" };
//		final String[] replayNames = new String[] { "C:/Program Files (x86)/StarCraft/Maps/replays/Road War_TvsP_125038.rep" };

		//		final String[] replayNames = new String[] { "C:/Program Files (x86)/StarCraft/Maps/replays/Untitled Scenari_Tvs_105015.rep" };
//		final String[] replayNames = new String[] { "C:/Program Files (x86)/StarCraft/Maps/replays/Untitled Scenari_Tvs_110623.rep" };
//		final String[] replayNames = new String[] { "C:/Program Files (x86)/StarCraft/Maps/replays/Untitled Scenari_TZvs_213702.rep" };
//		final String[] replayNames = new String[] { "C:/Program Files (x86)/StarCraft/Maps/replays/iCCup  Match _PvsT_130835.rep" };
		
		
		
		for ( final String replayName : replayNames ) {
			final Replay replay = parseReplay( new File( replayName ), true, false, true, false );
			if ( replay != null ){
				
				for (int playerId = 0; playerId < replay.replayHeader.playerIds.length && replay.replayHeader.playerIds[playerId]!=255 ; playerId++){
					System.out.print(replay.replayHeader.playerNames[playerId] + " PlayerIDs[" + playerUnitIDs.get(playerId).size() +"]: (" );
					for (Integer id : playerUnitIDs.get(playerId)) {
						System.out.print(id + " ");
					}
					System.out.println(")");
					System.out.print(replay.replayHeader.playerNames[playerId] + " BuildingIDs[" + playerBuildingIDs.get(playerId).size() +"]: (" );
					for (Integer id : playerBuildingIDs.get(playerId)) {
						System.out.print(id + " ");
					}
					System.out.println(")");
				}
				
				
				System.out.print("From both selected [" + union.size() +"]: (");
				for (Integer id : replay.playerOneUnitIDs) {
					if (replay.playerTwoUnitIDs.contains(id))
					System.out.print(id + " ");
				}
				System.out.println(")");
				
				replay.replayActions.printActions( new PrintWriter( System.out ));
//				for (Action action : trained) {
//					System.out.println(action.toString());
//				}
//				for (int i = 0; i < trained.size(); i++) {
////					System.out.println("dasd: " + trained.size());
//					trained.get(i).toString("qqrft", false);
//					trained.get(i).toString("vhrvndtjftk", false);
////					action.toString("qqrft", false);
//					
//				}
				replay.replayHeader.printHeaderInformation( new PrintWriter( System.out ) );
			}
			else
				System.out.println( "Could not parse " + replayName + "!" );
			System.out.println();
		}
	}
	
	/** Size of the header section */
	public static final int HEADER_SIZE = 0x279;
	
	/** Mask for mapping of units index */
	public static final int INDEX_MASK = 0x7FF;
	
	/**
	 * Wrapper class to build the game chat.
	 * @author Andras Belicza
	 */
	private static class GameChatWrapper {
		
		/** <code>StringBuilder</code> for the game chat.  */
		public final StringBuilder          gameChatBuilder;
		/** Map from the player IDs to their name.         */
		public final Map< Integer, String > playerIndexNameMap;
		/** Message buffer to be used to extract messages. */
		public final byte[]                 messageBuffer = new byte[ 80 ];
		
		/**
		 * Creates a new GameChatWrapper.
		 */
		public GameChatWrapper( final String[] playerNames, final int[] playerIds ) {
			gameChatBuilder = new StringBuilder();
			
			playerIndexNameMap = new HashMap< Integer, String >();
			for ( int i = 0; i < playerNames.length; i++ )
				if ( playerNames[ i ] != null && playerIds[ i ] != 0xff ) // Computers are listed with playerId values of 0xff.
					playerIndexNameMap.put( i, playerNames[ i ] );
		}
	}
	
	/**
	 * Parses a binary replay file.
	 * 
	 * @param replayFile           replay file to be parsed
	 * @param parseCommandsSection tells if player actions have to be parsed from the commands section 
	 * @param parseGameChat        tells if game chat has to be parsed
	 * @param parseMapDataSection  tells if map data section has to be parsed
	 * @param parseMapTileData     tells if map tile data section has to be parsed
	 * @return a {@link Replay} object describing the replay; or <code>null</code> if replay cannot be parsed 
	 */
	@SuppressWarnings("unchecked")
	public static Replay parseReplay( final File replayFile, final boolean parseCommandsSection, final boolean parseGameChat, final boolean parseMapDataSection, final boolean parseMapTileData ) {
		playerUnitIDs = new ArrayList<HashSet<Integer>>();
		playerBuildingIDs = new ArrayList<HashSet<Integer>>();
		selectedIDs = new ArrayList<HashSet<Integer>>();
		initHQID = new ArrayList<HashSet<Integer>>();
		initWorkerIDs = new ArrayList<HashSet<Integer>>();
		initLarvaIDs = new ArrayList<ArrayList<Integer>>();

		posBadIteration = new ArrayList<HashSet<Integer>>();
		badIteration = new ArrayList<HashMap<Integer,Action>>();
			
		union = new HashSet<Integer>();
		
		for ( int i = 0;i< 12;i++ ){
			initUnitsCounter[i] 	= 0;
			initUnitsCount[i] 		= 0;
			initWorkerCounter[i] 	=0;
			initLarvaCounter[i] 	= 0;
			initHQFlag[i] 		= false;
		}
		
		BinReplayUnpacker unpacker = null;
		try {
			unpacker = new BinReplayUnpacker( replayFile );
			
			// Replay ID section
			if ( Integer.reverseBytes( ByteBuffer.wrap( unpacker.unpackSection( 4 ) ).getInt() ) != 0x53526572 )
				return null;  // Not a replay file
			
			// Replay header section
			final byte[] headerData = unpacker.unpackSection( HEADER_SIZE );
			final ByteBuffer headerBuffer = ByteBuffer.wrap( headerData );
			headerBuffer.order( ByteOrder.LITTLE_ENDIAN );

			final ReplayHeader replayHeader = new ReplayHeader();
			replayHeader.gameEngine  = headerData[ 0x00 ];
			
			replayHeader.gameFrames  = headerBuffer.getInt( 0x01 );
			replayHeader.saveTime    = new Date( headerBuffer.getInt( 0x08 ) * 1000l );
			
			replayHeader.gameName    = getZeroPaddedString( headerData, 0x18, 28 );
			
			replayHeader.mapWidth    = headerBuffer.getShort( 0x34 );
			replayHeader.mapHeight   = headerBuffer.getShort( 0x36 );
			
			replayHeader.gameSpeed   = headerBuffer.getShort( 0x3a );
			replayHeader.gameType    = headerBuffer.getShort( 0x3c );
			replayHeader.gameSubType = headerBuffer.getShort( 0x3e );
			
			replayHeader.creatorName = getZeroPaddedString( headerData, 0x48, 24 );
			
			replayHeader.mapName     = getZeroPaddedString( headerData, 0x61, 26 );
			
			replayHeader.playerRecords = Arrays.copyOfRange( headerData, 0xa1, 0xa1 + 432 );
			for ( int i = 0; i < replayHeader.playerColors.length; i++ )
				replayHeader.playerColors[ i ] = headerBuffer.getInt( 0x251 + i * 4 );
			replayHeader.playerSpotIndices = Arrays.copyOfRange( headerData, 0x271, 0x271 + 8 );
			
			// Derived data from player records:
			for ( int i = 0; i < 12; i++ ) {
				final String playerName = getZeroPaddedString( replayHeader.playerRecords, i * 36 + 11, 25 );
				if ( playerName.length() > 0 )
					replayHeader.playerNames[ i ] = playerName;
				replayHeader.playerRaces[ i ] = replayHeader.playerRecords[ i * 36 + 9 ];
				initUnitsCount[i] = ReplayHeader.INIT_UNITS_COUNT;					
				replayHeader.playerIds  [ i ] = replayHeader.playerRecords[ i * 36 + 4 ] & 0xff;
				playerUnitIDs.add(i, new HashSet<Integer>());
				playerBuildingIDs.add(i, new HashSet<Integer>());
				selectedIDs.add(i, new HashSet<Integer>());
				initHQID.add(i,new HashSet<Integer>());
				initLarvaIDs.add(i,new ArrayList<Integer>());
				initWorkerIDs.add(i,new HashSet<Integer>());
				posBadIteration.add(i,new HashSet<Integer>());
				badIteration.add(i,new HashMap<Integer,Action>());
			}
			
			if ( !parseCommandsSection )
				return new Replay( replayHeader, null, null, null );
			
			// Player commands length section
			final int playerCommandsLength = Integer.reverseBytes( ByteBuffer.wrap( unpacker.unpackSection( 4 ) ).getInt() );
			
			// Player commands section
			final ByteBuffer commandsBuffer = ByteBuffer.wrap( unpacker.unpackSection( playerCommandsLength ) );
			//System.out.print("Buffer " + commandsBuffer.toString());
			commandsBuffer.order( ByteOrder.LITTLE_ENDIAN );
			
			List< Action >[] playerActionLists = null;
			GameChatWrapper  gameChatWrapper   = null;
			if ( parseGameChat )
				gameChatWrapper   = new GameChatWrapper( replayHeader.playerNames, replayHeader.playerIds );
			if ( parseCommandsSection ){
				playerActionLists = new ArrayList[ replayHeader.playerNames.length ]; // This will be indexed by playerId!
				for ( int i = 0; i < playerActionLists.length; i++ )
					playerActionLists[ i ] = new ArrayList< Action >();
			}
			
			while ( commandsBuffer.position() < playerCommandsLength ) {
				final int frame               = commandsBuffer.getInt();
				int       commandBlocksLength = commandsBuffer.get() & 0xff;
				final int commandBlocksEndPos = commandsBuffer.position() + commandBlocksLength;
				
				while ( commandsBuffer.position() < commandBlocksEndPos ) {
					final int playerId = commandsBuffer.get() & 0xff;
					final Action action = readNextAction( frame, commandsBuffer, commandBlocksEndPos, gameChatWrapper, playerId );					if ( action != null ) {
						replayHeader.playerIdActionsCounts  [ playerId ]++; // If playerId is outside the index range, throw the implicit exception and fail to parse replay, else it may contain incorrect actions which may lead to false hack reports!
						if ( frame < ReplayHeader.FRAMES_IN_TWO_MINUTES )
							replayHeader.playerIdActionsCountBefore2Mins[ playerId ]++;
						if ( playerActionLists != null )
							playerActionLists[ playerId ].add( action );
						for (int plID=0;plID<playerId;plID++)
							if (posBadIteration.get(plID).contains(frame)){
								badIteration.get(playerId).put(frame,action);
								for (Action action2: playerActionLists[plID])
									badIteration.get(plID).put(frame, action2);
							}
					}
				}
			}
			
			// Fill the last action frames array
			if ( playerActionLists != null )
				for ( int i = 0; i < playerActionLists.length; i++ ) {
					final List< Action > playerActionList = playerActionLists[ i ];
					if ( !playerActionList.isEmpty() )
						replayHeader.playerIdLastActionFrame[ i ] = playerActionList.get( playerActionList.size() - 1 ).iteration;
				}
			
			ReplayActions replayActions = null;
			if ( parseCommandsSection ) {
				// Now create the ReplayActions object
				final Map< String, List< Action > > playerNameActionListMap = new HashMap< String, List< Action > >();
				for ( int i = 0; i < replayHeader.playerNames.length; i++ )
					if ( replayHeader.playerNames[ i ] != null )
						if ( replayHeader.playerIds[ i ] != 0xff )  // Computers are listed with playerId values of 0xff, but no actions are recorded from them.
							playerNameActionListMap.put( replayHeader.playerNames[ i ], playerActionLists[ replayHeader.playerIds[ i ] ] );
				replayActions = new ReplayActions( playerNameActionListMap );
			}
			
			MapData mapData = parseMapTileData ? new MapData() : null;
			if ( parseMapDataSection ) {
				// Map data length section
				final int mapDataLength = Integer.reverseBytes( ByteBuffer.wrap( unpacker.unpackSection( 4 ) ).getInt() );
				
				// Map data section
				final ByteBuffer mapDataBuffer = ByteBuffer.wrap( unpacker.unpackSection( mapDataLength ) );
				mapDataBuffer.order( ByteOrder.LITTLE_ENDIAN );
				
				final byte[] sectionNameBuffer = new byte[ 4 ];
				final String SECTION_NAME_DIMENSION = "DIM "; // Name of the dimension section in the map data replay section.
				final String SECTION_NAME_MTXM      = "MTXM"; // Name of the tile section in the map data replay section.
				final String SECTION_NAME_ERA       = "ERA "; // Name of the tile set section in the map data replay section.
				final String SECTION_NAME_UNIT      = "UNIT"; // Name of the unit section in the map data replay section.
				while ( mapDataBuffer.position() < mapDataLength ) {
					mapDataBuffer.get( sectionNameBuffer );
					final String sectionName   = new String( sectionNameBuffer, "US-ASCII" );
					final int    sectionLength = mapDataBuffer.getInt();
					final int    sectionEndPos = mapDataBuffer.position() + sectionLength;
					
					if ( sectionName.equals( SECTION_NAME_UNIT ) ) {
						if ( parseMapTileData ) {
							while ( mapDataBuffer.position() < sectionEndPos ) {
								final int unitEndPos = mapDataBuffer.position() + 36; // 36 bytes each unit
								mapDataBuffer.getInt(); // unknown
								final short x    = mapDataBuffer.getShort();
								final short y    = mapDataBuffer.getShort();
								final short type = mapDataBuffer.getShort();
								mapDataBuffer.getShort(); // unknown
								mapDataBuffer.getShort(); // special properties flag
								mapDataBuffer.getShort(); // valid elements flag
								final byte owner = mapDataBuffer.get();
								
								if ( type == Action.UNIT_NAME_MINERAL_FIELD_1 || type == Action.UNIT_NAME_MINERAL_FIELD_2 || type == Action.UNIT_NAME_MINERAL_FIELD_3 ) {
									mapData.mineralFieldList.add( new short[] { x, y } );
								}
								else if ( type == Action.UNIT_NAME_VESPENE_GEYSER ) {
									mapData.geyserList.add( new short[] { x, y } );
								}
								else if ( type == Action.UNIT_NAME_START_LOCATION ) {
									mapData.startLocationList.add( new int[] { x, y, owner } );
								}
								
								if ( mapDataBuffer.position() < unitEndPos ) // We might not processed all unit data
									mapDataBuffer.position( unitEndPos < mapDataLength ? unitEndPos : mapDataLength );
							}
						}
					}
					else if ( sectionName.equals( SECTION_NAME_DIMENSION ) ) {
						// If map has a non-standard size, the replay header contains invalid map size, this is the correct one
						final short newWidth  = mapDataBuffer.getShort();
						final short newHeight = mapDataBuffer.getShort();
						// Sometimes newWidth and newHeight is 0, we don't want to overwrite the size with wrong values!
						// And sometimes it contains some insane values, we just ignore them
						if ( newWidth <= 256 && newHeight <= 256 ) {
							if ( newWidth > replayHeader.mapWidth )
								replayHeader.mapWidth = newWidth;
							if ( newHeight > replayHeader.mapHeight )
								replayHeader.mapHeight= newHeight;
						}
						if ( !parseMapTileData )
							break; // We only needed the dimension section
					}
					else if ( sectionName.equals( SECTION_NAME_MTXM ) ) {
						if ( parseMapTileData ) {
							final int maxI = sectionLength/2; // This is map_width*map_height
							// Sometimes map is broken into multiple sections. The first one is the biggest (whole map size), but the beginning of map is empty
							// The subsequent MTXM sections will fill the whole at the beginning. 
							if ( mapData.tiles == null )
								mapData.tiles = new short[ maxI ];
							for ( int i = 0; i < maxI; i++ )
								mapData.tiles[ i ] = mapDataBuffer.getShort();
						}
					}
					else if ( sectionName.equals( SECTION_NAME_ERA ) ) {
						if ( parseMapTileData )
							mapData.tileSet = mapDataBuffer.getShort();
					}
					
					if ( mapDataBuffer.position() < sectionEndPos ) // Part or all the section might be unprocessed, skip the unprocessed bytes
						mapDataBuffer.position( sectionEndPos < mapDataLength ? sectionEndPos : mapDataLength );
				}
				
				if ( mapDataBuffer.position() < mapDataLength ) // We might have skipped some parts of map data, so we position to the end
					mapDataBuffer.position( mapDataLength );
			}
			Replay rReplay = new Replay( replayHeader, replayActions, gameChatWrapper == null ? null : gameChatWrapper.gameChatBuilder.toString(), mapData ); 
			rReplay.playerOneUnitIDs = playerUnitIDs.get(0);
			rReplay.playerTwoUnitIDs = playerUnitIDs.get(1);
			rReplay.initHQID = initHQID;
			rReplay.badIteration = badIteration;
			rReplay.initLarvaIDs = initLarvaIDs;
			rReplay.initWorkerIDs = initWorkerIDs;
			return rReplay;
		}
		catch ( final Exception e ) {
			e.printStackTrace();
			return null;
		}
		finally {
			if ( unpacker != null )
				unpacker.close();
		}
	}
	
	/**
	 * Returns a string from a "C" style buffer array.<br>
	 * That means we take the bytes of a string form a buffer until we find a 0x00 terminating character.
	 * @param data   data to read from
	 * @param offset offset to read from
	 * @param length max length of the string
	 * @return the zero padded string read from the buffer
	 */
	private static String getZeroPaddedString( final byte[] data, final int offset, final int length ) {
		String string = new String( data, offset, length );
		
		int firstNullCharPos = string.indexOf( 0x00 );
		if ( firstNullCharPos >= 0 )
			string = string.substring( 0, firstNullCharPos );
		
		return string;
	}
	
	/**
	 * Reads the next action in the commands buffer.<br>
	 * Only parses actions which are important in hack detection.
	 * @param frame               frame of the action
	 * @param commandsBuffer      commands buffer to be read from
	 * @param commandBlocksEndPos end position of the current command blocks
	 * @param gameChatWrapper     game chat wrapper to be used if game chat is desired
	 * @return the next action object
	 */
	private static Action readNextAction( final int frame, final ByteBuffer commandsBuffer, final int commandBlocksEndPos, final GameChatWrapper gameChatWrapper, final int playerId ) {
		final byte blockId  = commandsBuffer.get();
		
		Action action    = null;
		int    skipBytes = 0;
		
		switch ( blockId ) {
			case (byte) 0x09 :   // Select units
			case (byte) 0x0a :   // Shift select units
			case (byte) 0x0b : { // Shift deselect units
				selectedIDs.get(playerId).clear();
				int unitsCount = commandsBuffer.get() & 0xff;
				HashSet<Integer> selectedUnits = new HashSet<Integer>();
				final StringBuilder parametersBuilder = new StringBuilder();
				for ( ; unitsCount > 0; unitsCount-- ) {
					short unitID = commandsBuffer.getShort();
//					byte unitType = commandsBuffer.get();
//					byte unitID = commandsBuffer.get();
					try {
						int clearUnitID = getJniBwapiID(unitID);
						parametersBuilder.append(clearUnitID);
						selectedIDs.get(playerId).add(new Integer(clearUnitID));
						selectedUnits.add(clearUnitID);						
					} catch (UnitIDException e) {
						e.printStackTrace();
					}
//					parametersBuilder.append( commandsBuffer.getShort() );
//					System.out.print(frame + " Select: " + unitID);
//					System.out.print(" Bitstring : " + Integer.toBinaryString(unitID));
//					System.out.println(" Select Byte: " + Integer.toHexString(unitID));
//					System.out.println(" Select Byte: " + convertToHexString(toBytes(unitID)[1],(byte) toBytes(unitID)[0]));
//					
//					System.out.print("JNIBWAPI: 1700 - [" + ((unitID & 0x7FF) - 1) + "]");
//					System.out.println(" = [" + (1700 - ((unitID & 0x7FF) - 1)) + "]");
//					System.out.println("fromIndex(1): [" + ((((unitID & 0x7FF) - 1) + (1 << 11) + 1)) + "]");
//					System.out.print("  >>11: " + convertToHexString(toBytes((0x0a5)<<11)[2],toBytes((0x0a5)<<11)[1],(byte) toBytes((0x0a5)<<11)[0]));
//					System.out.println(" >>11: " + ((0x0a5)<<11));
				
//					System.out.println("Build: " + Action.UNIT_ID_NAME_MAP.get(unitId));
//					System.out.print(" Select uShort1: " + (short)((byte)unitID & 0x0f ) + " " + Action.UNIT_ID_NAME_MAP.get((short)(byte) unitID & 0x0f));
//					System.out.print(" Select uShort2: " + (short)((byte) unitID & 0xf0) + " " + Action.UNIT_ID_NAME_MAP.get((short)((byte) unitID & 0xf0)));
//					System.out.println(" Select uShort3: " + (short)((byte) unitID & 0x0f));
//					System.out.println("--------------------------------------------------------------");
					if ( unitsCount > 1 )
						parametersBuilder.append( ',' );
				}
				action = new Action( frame, parametersBuilder.toString(), blockId, selectedUnits);
//				trained.add(action);
				break;
			}
			case (byte) 0x0c : { // Build
				/*final byte  type   = *///commandsBuffer.get();
				final byte  type   = commandsBuffer.get();
				final short posX   = commandsBuffer.getShort();
				final short posY   = commandsBuffer.getShort();
				final short unitId = commandsBuffer.getShort();
//				System.out.println("Build: " + Action.UNIT_ID_NAME_MAP.get(unitId));
				action = new Action( frame, posX + "," + posY + ","  + Action.UNIT_ID_NAME_MAP.get( unitId ), blockId, Action.UNIT_NAME_INDEX_UNKNOWN, unitId, posX, posY );
//				trained.add(action);
				posBadIteration.get(playerId).add(frame);
				break;
			}
			case (byte) 0x0d : { // Vision
				final byte data1 = commandsBuffer.get();
				final byte data2 = commandsBuffer.get();
				action = new Action( frame, convertToHexString( data1, data2 ), blockId );
				break;
			}
			case (byte) 0x0e : { // Ally
				final byte data1 = commandsBuffer.get();
				final byte data2 = commandsBuffer.get();
				final byte data3 = commandsBuffer.get();
				final byte data4 = commandsBuffer.get();
				action = new Action( frame, convertToHexString( data1, data2, data3, data4 ), blockId );
				break;
			}
			case (byte) 0x0f : { // Change game speed
				final byte speed = commandsBuffer.get();
				action = new Action( frame, Action.GAME_SPEED_MAP.get( speed ), blockId );
				break;
			}
			case (byte) 0x13 : { // Hotkey
				selectedIDs.get(playerId).clear();
				final byte type = commandsBuffer.get();
				action = new Action( frame, ( type == (byte) 0x00 ? Action.HOTKEY_ACTION_PARAM_NAME_ASSIGN : Action.HOTKEY_ACTION_PARAM_NAME_SELECT ) + "," + commandsBuffer.get(), blockId, type );
				break;
			}
			case (byte) 0x14 : { // Move
				final short posX   = commandsBuffer.getShort();
				final short posY   = commandsBuffer.getShort();
				/*final short unitId = *///commandsBuffer.getShort(); 
				// Move to (posX;posY) if this is 0xffff, or move to this unit if it's a valid unit id (if it's not 0xffff)
				final short unitId = commandsBuffer.getShort(); 
				// Move to (posX;posY) if this is 0xffff, or move to this unit if it's a valid unit id (if it's not 0xffff)
				skipBytes = 3;
//				System.out.println(frame + " Skiped " + skipBytes + " Caused by id[" + blockId+ "] Move " + unitId + " ");
//				System.out.println(unitId + " "+(unitId == 0) );
				if (unitId == 0){
					action = new Action( frame, posX + "," + posY, Action.ANI_MOVE, (int) posX, (int) posY );
				} else {					
					try {
						action = new Action( frame, getJniBwapiID(unitId ) + "," + posX + "," + posY, Action.ANI_MOVE, (int) posX, (int) posY, getJniBwapiID(unitId ));
						if (initWorkerCounter[playerId] < ReplayHeader.INIT_WORKER_COUNT){
							for (Integer id : selectedIDs.get(playerId)){
								if (!initWorkerIDs.get(playerId).contains(id)){
									initWorkerIDs.get(playerId).add(id);
									initWorkerCounter[playerId]++;
								}
							}
						}
					} catch (UnitIDException e) {
						e.printStackTrace();
					}
				}
				break;
			}
			case (byte) 0x15 : { // Attack/Right Click/Cast Magic/Use ability
				final short posX   = commandsBuffer.getShort();
				final short posY   = commandsBuffer.getShort();
				final short unitId = commandsBuffer.getShort(); // (posX;posY) if this is 0xffff, or target this unit if it's a valid unit id (if it's not 0xffff)
				final short unknownYet = commandsBuffer.getShort(); // Unknown  // values 228, 154, 110, 124, 
				final byte type    = commandsBuffer.get();

//				System.out.println("unitType: " + Action.UNIT_ID_NAME_MAP.get(unitId));
				byte actionNameIndex;
				switch ( type ) {
				case (byte) 0x00 : case (byte) 0x06 :  // Move with right click or Move by click move icon
					actionNameIndex = Action.ANI_MOVE       ; 
					playerUnitIDs.get(playerId).addAll(selectedIDs.get(playerId));
					break;
				case (byte) 0x09 : case (byte) 0x4f : case (byte) 0x50 :
					actionNameIndex = Action.ANI_GATHER     ; 
//					playerUnitIDs.get(playerId).addAll(selectedIDs.get(playerId));
					break;
				case (byte) 0x0e : // Attack move
					actionNameIndex = Action.ANI_ATTACK_MOVE; 
//					playerUnitIDs.get(playerId).addAll(selectedIDs.get(playerId));
					break;
				case (byte) 0x27 :
					actionNameIndex = Action.ANI_SET_RALLY  ; 
					break;

				case (byte) 0x28 :
					actionNameIndex = Action.ANI_SET_RALLY  ; 
					break;
				default :
//					frames.add(frame);
//					try {
//						System.out.println(frame + " unitID "+  getJniBwapiID(unitId )  + "ANI: " + (byte)type + " = 0x"+  Integer.toHexString(type) + " : " + test);
//					} catch (UnitIDException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} 
					actionNameIndex = Action.ANI_UNKNOWN    ; break;
				}
//				System.out.println("ANI" + type + " " + test); 
				
				final byte type2 = commandsBuffer.get(); // Type2: 0x00 for normal attack, 0x01 for shift attack
				action = new Action( frame, posX + "," + posY, actionNameIndex, type, Action.UNIT_NAME_INDEX_UNKNOWN, Action.BUILDING_NAME_INDEX_NON_BUILDING, type );
				break;
			}
			case (byte) 0x1f : { // Train
				final short unitId = commandsBuffer.getShort();
				action = new Action( frame, Action.UNIT_ID_NAME_MAP.get( unitId ), blockId, unitId, Action.BUILDING_NAME_INDEX_NON_BUILDING );
//				trained.add(action);
//				System.out.println("new Unit: " +  Action.UNIT_ID_NAME_MAP.get(unitId));
				playerBuildingIDs.get(playerId).addAll(selectedIDs.get(playerId));
				posBadIteration.get(playerId).add(frame);
				if (initUnitsCounter[playerId] < initUnitsCount[playerId]){	
					for (Integer id : selectedIDs.get(playerId)){
						if (initHQID.get(playerId).add(id)){
							initUnitsCounter[playerId]++;
						}
					}
				}
				break;
			}
			case (byte) 0x20 : { // Cancel train
				skipBytes = 2;
//				System.out.println(frame + " Skiped " + skipBytes + " Caused by id[" + blockId+ "] Cancel train" );
				action = new Action( frame, "", blockId );
				break;
			}
			case (byte) 0x23 : { // Hatch
				final short unitId = commandsBuffer.getShort();
				action = new Action( frame, Action.UNIT_ID_NAME_MAP.get( unitId ), blockId, unitId, Action.BUILDING_NAME_INDEX_NON_BUILDING );
				// TODO:
				for (Integer id : selectedIDs.get(playerId)){
					if (initLarvaIDs.get(playerId).contains(id)){
						// set the element at the end
						initLarvaIDs.get(playerId).remove(id);
						initLarvaIDs.get(playerId).add(id);
					}
					if (initLarvaCounter[playerId] < ReplayHeader.INIT_LARVA_COUNT){	
						if (initLarvaIDs.get(playerId).add(id)){
							initLarvaCounter[playerId]++;
						}
					}
				}
				break;
			}
			case (byte) 0x30 : { // Research
				final byte researchId = commandsBuffer.get();
				action = new Action( frame, Action.RESEARCH_ID_NAME_MAP.get( researchId ), blockId );
//				System.out.println("Babab " + blockId + " " + frame + " " + Action.RESEARCH_ID_NAME_MAP.get(researchId));
				break;
			}
			case (byte) 0x32 : { // Upgrade
				final byte upgradeId = commandsBuffer.get();
				action = new Action( frame, Action.UPGRADE_ID_NAME_MAP.get( upgradeId ), blockId );
				break;
			}
			case (byte) 0x1e :    // Return chargo
			case (byte) 0x21 :    // Cloack
			case (byte) 0x22 :    // Decloack
			case (byte) 0x25 :    // Unsiege
			case (byte) 0x26 :    // Siege
			case (byte) 0x28 :    // Unload all
			case (byte) 0x2b :    // Hold position
			case (byte) 0x2c :    // Burrow
			case (byte) 0x2d :    // Unburrow
			case (byte) 0x1a : {  // Stop
				final byte data = commandsBuffer.get();
				final String params = blockId == (byte) 0x1a || blockId == (byte) 0x1e || blockId == (byte) 0x28  || blockId == (byte) 0x2b ? ( data == 0x00 ? "Instant" : "Queued" ) : "";
				action = new Action( frame, params, blockId );
				break;
			}
			case (byte) 0x35 : {  // Morph
				final short unitId = commandsBuffer.getShort();
				action = new Action( frame, Action.UNIT_ID_NAME_MAP.get( unitId ), blockId, unitId, unitId );
				break;
			}
			case (byte) 0x57 : {  // Leave game
				final byte reason = commandsBuffer.get();
				action = new Action( frame, reason == (byte) 0x01 ? "Quit" : ( reason == (byte) 0x06 ? "Dropped" : "" ), blockId );
				break;
			}
			case (byte) 0x29 : { // Unload
				skipBytes = 2;
//				System.out.println(frame + " Skiped " + skipBytes + " Caused by id[" + blockId+ "] Unload" );
				action = new Action( frame, "", blockId );
				break;
			}
			case (byte) 0x58 : { // Minimap ping
				final short posX = commandsBuffer.getShort();
				final short posY = commandsBuffer.getShort();
				action = new Action( frame, "(" + posX + "," + posY + ")", blockId );
				break;
			}
			case (byte) 0x12 :   // Use Cheat
			case (byte) 0x2f : { // Lift
				final byte data1 = commandsBuffer.get();
				final byte data2 = commandsBuffer.get();
				final byte data3 = commandsBuffer.get();
				final byte data4 = commandsBuffer.get();
				action = new Action( frame, convertToHexString( data1, data2, data3, data4 ), blockId );
				break;
			}
			case (byte) 0x18 :   // Cancel
			case (byte) 0x19 :   // Cancel hatch
			case (byte) 0x27 :   // Build interceptor/scarab
			case (byte) 0x2a :   // Merge archon
			case (byte) 0x2e :   // Cancel nuke
			case (byte) 0x31 :   // Cancel research
			case (byte) 0x36 :   { // Stim
				action = new Action( frame, "", blockId );
				break;
				}
			case (byte) 0x5a : { // Merge dark archon
				// No additional data
				action = new Action( frame, "", blockId );
				break;
			}
			case (byte) 0x5c : { // Game Chat (as of 1.16)
				if ( gameChatWrapper == null ){
					skipBytes = 81;  // 1 byte for player index, and 80 bytes of message characters
//					System.out.println(frame + " Skiped " + skipBytes + " Caused by id[" + blockId+ "] Game Chat (as of 1.16)" );
				}
				else {
					if ( gameChatWrapper.gameChatBuilder.length() > 0 )
						gameChatWrapper.gameChatBuilder.append( "\r\n" );
					ReplayHeader.formatFrames( frame, gameChatWrapper.gameChatBuilder, false );
					gameChatWrapper.gameChatBuilder.append( " - " ).append( gameChatWrapper.playerIndexNameMap.get( commandsBuffer.get() & 0xff ) );
					commandsBuffer.get( gameChatWrapper.messageBuffer );
					gameChatWrapper.gameChatBuilder.append( ": " ).append( getZeroPaddedString( gameChatWrapper.messageBuffer, 0, gameChatWrapper.messageBuffer.length ) );
				}
				break;
			}
			default: { // We don't know how to handle actions, we have to skip the whole time frame which means we might lose some actions!

//				System.out.println(((byte) 0x1e)==blockId);
//				System.out.println("!!!!!!!!!Re default" + + blockId + " " + frame);
				skipBytes = commandBlocksEndPos - commandsBuffer.position();
//				System.out.println(frame + " Skiped " + skipBytes + " Caused by id[" + blockId+ "]  ÄÄÄÄHH" );
				
				break;
			}
		}
		
		if ( skipBytes > 0 ) {
			commandsBuffer.position( commandsBuffer.position() + skipBytes );
//			System.out.println(frame + " Skiped " + skipBytes + " Caused by id[frameend] " );
		}
		
		if ( blockId == (byte) 0x5c ) // Game chat is not a "real" action
			return null;
		
		if ( action == null )
			action = new Action( frame, "", Action.ANI_UNKNOWN );
		
		return action;
	}
	
	/**
	 * Converts bytes to hex string separating bytes with spaces. 
	 * @return the bytes converted to string separated with spaces
	 */
	private static String convertToHexString( final byte... data ) {
    	final StringBuilder sb = new StringBuilder( data.length * 2 );
    	
    	for ( int i = 0; i < data.length; ) {
    	    sb.append( Integer.toHexString( ( data[ i ] >> 4 ) & 0x0f ).toUpperCase() );
    	    sb.append( Integer.toHexString( data[ i ] & 0x0f ).toUpperCase() );
    	    
    	    if ( ++i < data.length )
    	    	sb.append( ' ' );
    	}
    	
    	return sb.toString();
	}

	/**
	 * Converts short code to id of JNIBWAPI.
	 * @param the code which is to be converted
	 * @return the JNIBWAPI id. Hope so.
	 */

	public static short getJniBwapiID(short code) throws UnitIDException {
		int id = (code & 0x7FF) - 1;
		if (id <= 1700){
			id = 1700 - id;
			return (short) id;
		}
		else {
			throw new UnitIDException(code,id);
		}
	}
	
}
