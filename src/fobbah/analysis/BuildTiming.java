package fobbah.analysis;

public class BuildTiming 
{
	private int time = 0;
	public void setInitial(int time_in)
	{
		if(time == 0)
		{
			time = time_in;
		}
	}
	
	@Override
	public String toString()
	{
		return Integer.toString(time);
	}
	
	public boolean hasBeenPlaced()
	{
		return time != 0;
	}
}
