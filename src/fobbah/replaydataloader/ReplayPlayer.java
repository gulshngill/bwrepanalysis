package fobbah.replaydataloader;

public class ReplayPlayer 
{
	private int startLoc;
	private String playerName;
	private String race;
	private int playerNum;
	
	public void printPlayer()
	{
		System.out.println(playerNum + "," + playerName + "," + race + "," + startLoc);
	}
	
	public static ReplayPlayer loadFromLine(String datain)
	{
		String dataline = new String(datain);
		int pnum = Integer.parseInt(dataline.substring(0, dataline.indexOf(',')));
		dataline = dataline.substring(dataline.indexOf(',') + 2, dataline.length());
		String pname = dataline.substring(0, dataline.indexOf(','));
		dataline = dataline.substring(dataline.indexOf(',') + 2, dataline.length());
		String prace = dataline.substring(0, dataline.indexOf(','));
		dataline = dataline.substring(dataline.indexOf(',') + 2, dataline.length());
		int psloc = Integer.parseInt(dataline.substring(0, dataline.length() - 1));
		return new ReplayPlayer(pnum, pname, prace, psloc);
	}
	
	public ReplayPlayer(int playerNum_in, String playerName_in, String race_in, int startLoc_in)
	{
		playerNum = playerNum_in;
		playerName = playerName_in;
		race = race_in;
		startLoc = startLoc_in;
	}
	public int getStartLocation()
	{
		return startLoc;
	}
	public String getPlayerName()
	{
		return playerName;
	}
	public int getPlayerNum()
	{
		return playerNum;
	}
	public String getRace()
	{
		return race;
	}
	
}
