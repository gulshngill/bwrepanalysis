package fobbah.replaydataloader;

import java.io.*;
import java.util.LinkedList;
import fobbah.analysis.*;

public class ReplayLoader 
{
	public static void main(String args[])
	{
		//loadReplay("zvz/ZvZ_015_004.rep");
		exampleQuery();
	}
	
	public static void exampleQuery()
	{
		String dir = "zvz";
		File directory = new File(dir);
		FilenameFilter filefilter = new FilenameFilter() 
		{
			public boolean accept(File dir, String name) {
				// if the file extension is .txt return true, else false
				return name.endsWith(".rep");
			}
		};
		String[] filenames = directory.list(filefilter);

		LinkedList<ZergOpenersExtractor> featureList = new LinkedList<ZergOpenersExtractor>();
		//LinkedList<Integer> spawnPoolTimings = new LinkedList<Integer>();
		//LinkedList<Integer> hatcheryTimings = new LinkedList<Integer>();
		
		int idx = 0;
		for (String name : filenames) 
		{
			if(idx % 50 == 0)
			{
				System.out.println(idx);
			}
			Replay r = loadReplay("zvz/" + name);
			for (ReplayPlayer p : r.players)
			{
				int pid = p.getPlayerNum();
				//LinkedList<ReplayEvent> eventList = r.playerRepEvents.get(pid);
				ZergOpenersExtractor feats = new ZergOpenersExtractor(r, r.repPath, pid);
				//feats.setPlayerID(pid);
				featureList.add(feats);
			}
			idx++;
		}
		try
		{
			FileWriter fstream = new FileWriter("zvzopeners.csv");
			BufferedWriter fout = new BufferedWriter(fstream);
			
			
			fout.write(ZergOpenersExtractor.getFeatureTypes() + "\n");
			
			for (ZergOpenersExtractor f : featureList)
			{
				fout.write(f.getFeatures() + "\n");	
			}
			fout.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
	
	public static Replay loadReplay(String repPath)
	{
		try
		{
			Replay newReplay = new Replay();
			//LOAD Replay Game Data
			StringBuffer fileData = new StringBuffer(1000);
			BufferedReader reader = new BufferedReader(new FileReader(repPath + ".rgd"));
			char[] buf = new char[1024];
			int numRead = 0;
			while ((numRead = reader.read(buf)) != -1) {
				String readData = String.valueOf(buf, 0, numRead);
				fileData.append(readData);
				buf = new char[1024];
			}
			reader.close();
			String RGD = fileData.toString();
			
			newReplay.loadRGD(RGD);
			
			
			//LOAD Replay Location Data
			fileData = new StringBuffer(1000);
			reader = new BufferedReader(new FileReader(repPath + ".rld"));
			buf = new char[1024];
			numRead = 0;
			while ((numRead = reader.read(buf)) != -1) {
				String readData = String.valueOf(buf, 0, numRead);
				fileData.append(readData);
				buf = new char[1024];
			}
			reader.close();
			String RLD = fileData.toString();
			
			newReplay.loadRLD(RLD);
			
			//LOAD Replay Orders Data
			fileData = new StringBuffer(1000);
			reader = new BufferedReader(new FileReader(repPath + ".rod"));
			buf = new char[1024];
			numRead = 0;
			while ((numRead = reader.read(buf)) != -1) {
				String readData = String.valueOf(buf, 0, numRead);
				fileData.append(readData);
				buf = new char[1024];
			}
			reader.close();
			String ROD = fileData.toString();
			
			newReplay.loadROD(ROD);
			
			return newReplay;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
