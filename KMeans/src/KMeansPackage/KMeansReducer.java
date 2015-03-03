package KMeansPackage;

import java.io.IOException;
import java.util.Hashtable;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import KMeansPackage.KMeansDriver.CONVERGE;

public class KMeansReducer extends Reducer<LongWritable, Text, Text, Text> {
	
	int[] previousCentroids;
	FileHandler fileHandler=new FileHandler();
	
	public KMeansReducer() {
		
		previousCentroids=fileHandler.GetCentroidFromFile(3);
	}
	
	
	public void reduce(LongWritable _key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		// process values
		int sum =0;
		int count=0;
		int followers=0;
		int newCentroid=0;
		long clusterId=_key.get();
		String user=null;
		Hashtable<String, Integer> ht= new Hashtable<String, Integer>();
		
		for (Text val : values) {
			//context.write(_key, val);
			String[] splitArray=val.toString().split(" ");
			user=splitArray[0];
			followers=Integer.parseInt(splitArray[1]);
			ht.put(user, followers);
			sum+=followers;
			count++;
		}
		
		newCentroid=sum/count;
		
		for (String key : ht.keySet()) {
			
			user=key;
			followers=ht.get(key);
			
			Text id =new Text();
			id.set(user);
			Text value=new Text();
			value.set(followers +" " + _key);
			
			context.write(id, value);
		}
		
		int iterations=(int) context.getCounter(CONVERGE.ITERATIONS).getValue();
		
		if(iterations%3==0)
		{
			context.getCounter(CONVERGE.COUNTER).setValue(0);
		}
		
		if(previousCentroids[iterations%3]!=newCentroid)
		{
			context.getCounter(CONVERGE.COUNTER).increment(1);
		}
		
		context.getCounter(CONVERGE.ITERATIONS).increment(1);
		fileHandler.Write(GlobalVariables.clusterFilePath, newCentroid+":");
		
	}
	

}
