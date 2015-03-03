package DijkstrasAlgorithmPackage;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

import DijkstrasAlgorithmPackage.DijkstrasAlgorithmDriver.CONVERGE;

public class DijkstrasAlgorithmReducer extends Reducer<LongWritable, Text, LongWritable, Text> {

	boolean firstIteration;
	
	public DijkstrasAlgorithmReducer()
	{
		firstIteration=true;
	}
	
	public void reduce(LongWritable _key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		// process values
		
		int dmin=100000;
		int distance=0;
		int previousDistance=0;
		String adjacencyList="";
		
		for (Text val : values) {
			
			String valueString=val.toString();
			if(valueString.contains("Node_"))
			{
				//valueString.replace("Node_", "");				
				String[] nodeDetails=(valueString.split("_")[1]).split(" ");
				previousDistance=Integer.parseInt(nodeDetails[0]);
				adjacencyList=nodeDetails[1];
				
			}
			else if(valueString.contains("Value_"))
			{
				String[] temp=valueString.split("_");
				//valueString.replace("Value_", "");
				//distance=Integer.parseInt(valueString);
				distance=Integer.parseInt(temp[1]);
				if(dmin>distance)
				{
					dmin=distance;
				}
			}
		}
		
		if(firstIteration==true)
		{
			context.getCounter(CONVERGE.COUNTER).setValue(0);
			firstIteration=false;
		}
		
		if(dmin!=previousDistance)
		{
			context.getCounter(CONVERGE.COUNTER).increment(1);
		}
		
		Text word=new Text();
		String temp=dmin+" "+ adjacencyList;
		word.set(temp);
		context.write(_key, word);
		word.clear();
	}


}
