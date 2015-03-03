package sample;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class IntSumReducer1 
extends Reducer<Text,IntWritable,Text,DoubleWritable> {
	private DoubleWritable result = new DoubleWritable();

	public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
		
		Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);   
        //Reader
        HashMap<String , String> AllKeyCount= new HashMap<String, String>();
        String line;
        DataInputStream d = new DataInputStream(fs.open(new Path("/output1/part-r-00000")));
        BufferedReader reader = new BufferedReader(new InputStreamReader(d));
        while ((line = reader.readLine()) != null){

               String[] a= line.split(",");
               String RFkey= a[0].toString().replace("*_", "");
               AllKeyCount.put(RFkey, a[1].toString().trim());
        }
        reader.close();
		
		
		
		int  sum = 0 ;
		for (IntWritable val : values) {
			sum += val.get();
		}
		//sum=sum/2;
		//result.set(sum);
		String[] coOccuringKey= key.toString().split("_");
		try
		{
		Double dbValue=Double.parseDouble(String.valueOf(sum))/Double.parseDouble(AllKeyCount.get(coOccuringKey[0].toString()));
		//sum=sum/Double.parseDouble(AllKeyCount.get(key));
		context.write(key, new DoubleWritable(dbValue));
		}
		catch(NullPointerException e)
		{}
	}
}
