package KMeansPackage;

public class Parser {
	
	private int[] defaultCentroids={1,2,3};
	private int[] centroids= new int[3];
	private String[] returnString=new String[3];
	
	private String user=null;
	private int followers =0;
	int centroid=0;
	private FileHandler fileHandler=new FileHandler();
	
	public Parser() 
	{
		centroids=fileHandler.GetCentroidFromFile(3);
	}
	
	public String[] ParseLine(String line)
	{
		try
		{
		String[] splitArray=line.split(",");
		int splitArrayLength=splitArray.length;
		
		if(splitArrayLength>1)
		{
			user=splitArray[splitArrayLength-2].trim();
			followers=Integer.parseInt((splitArray[splitArrayLength-1].replace(";", "")).trim());
			centroid=FindCentroid(followers, defaultCentroids);
			
			//fileHandler.Write(, content);
		}
		else
		{
			splitArray=line.split(" ");
			
			user=splitArray[0];
			followers=Integer.parseInt(splitArray[1]);
			//centroids=fileHandler.GetCentroidFromFile(3);	
			
			centroid=FindCentroid(followers, centroids);
		}
		
		returnString[0]=String.valueOf(centroid);
		returnString[1]=user;
		returnString[2]=String.valueOf(followers);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return returnString;
		
	}
	
	public int FindCentroid(int followers, int[] kvalues)
	{
		int centroid;
		
		int[] distanceFromCentroids={Math.abs(kvalues[0]-followers),Math.abs(kvalues[1]-followers),Math.abs(kvalues[2]-followers)};
		
		if(distanceFromCentroids[0]<distanceFromCentroids[1] && distanceFromCentroids[0]<distanceFromCentroids[2])
		{
			centroid=kvalues[0];
		}
		else if(distanceFromCentroids[1]<distanceFromCentroids[0] && distanceFromCentroids[1]<distanceFromCentroids[2])
		{
			centroid=kvalues[1];
		}
		else
		{
			centroid=kvalues[2];
		}
		
		return centroid;
	}
	
	

}
