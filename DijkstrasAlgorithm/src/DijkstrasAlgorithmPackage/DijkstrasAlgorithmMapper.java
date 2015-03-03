package DijkstrasAlgorithmPackage;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class DijkstrasAlgorithmMapper extends Mapper<LongWritable, Text, LongWritable, Text> {

	public void map(LongWritable ikey, Text ivalue, Context context)
	 {
		
		try
		{
		Text word =new Text();
		String line=ivalue.toString();//1 0 2:3:
		String[] graphDetails=line.split(" ");//1 and 0 and 2:3:
		
		String node=graphDetails[0];
		int distance=Integer.parseInt(graphDetails[1]);
		String distanceString=String.valueOf(distance+1);
		String[] adjacencyList=graphDetails[2].split(":");
		int adjacencyListLength=adjacencyList.length;
		
		word.set("Value_"+distance);
		context.write(new LongWritable(Integer.parseInt(node)), word);
		
		word.set("Value_"+distanceString);
		for(int i=0; i<adjacencyListLength;i++)
		{
			context.write(new LongWritable(Integer.parseInt(adjacencyList[i])),word);
		}
		word.clear();
		
		String nodeDetails=graphDetails[1] + " " + graphDetails[2];
		word.set("Node_"+nodeDetails);
		context.write(new LongWritable(Integer.parseInt(node)), word);
		}
		catch(Exception e)
		{
			System.out.println("Value : "+ ivalue.toString());
			e.printStackTrace();
		}
	}

}
