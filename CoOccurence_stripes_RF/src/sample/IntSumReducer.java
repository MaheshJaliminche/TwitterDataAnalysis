package sample;

import java.io.IOException;
import java.util.Map.Entry;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Reducer;

public class IntSumReducer 
extends Reducer<Text,MapWritable,Text,DoubleWritable> {
	private IntWritable result = new IntWritable();

	public void reduce(Text key, Iterable<MapWritable> values, Context context) throws IOException, InterruptedException {
		int sum = 0;
		MapWritable finalCollection= new MapWritable();
		int Count =0;
		
		for (MapWritable val : values) {
			
			for(Entry<Writable, Writable> everyVal:val.entrySet())
			{
				if(finalCollection.containsKey(everyVal.getKey()))
				{
					//int asd=Integer.parseInt(CoOccuringHash.get(word).toString().trim());
					int finaladd= Integer.parseInt(finalCollection.get(everyVal.getKey()).toString()) + Integer.parseInt(everyVal.getValue().toString());
					finalCollection.put(everyVal.getKey(),new IntWritable(finaladd));
					Count=Count+Integer.parseInt(everyVal.getValue().toString());
				}
				else
				{
					finalCollection.put(everyVal.getKey(),everyVal.getValue());
					Count=Count+Integer.parseInt(everyVal.getValue().toString());
				}
			}
			
		}
		
		
		String firstKey=key.toString();
		for(Entry<Writable, Writable> displayCoOccurence:finalCollection.entrySet())
		{
			String KeyToDisplay=firstKey+"_"+displayCoOccurence.getKey().toString();
			double db=   Double.parseDouble(displayCoOccurence.getValue().toString())/Count;
			
			context.write(new Text(KeyToDisplay), new DoubleWritable(db));
		}
		//result.set(sum);
		
	}
}
