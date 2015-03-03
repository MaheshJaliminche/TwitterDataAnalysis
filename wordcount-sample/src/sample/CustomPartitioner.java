package sample;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class CustomPartitioner extends Partitioner<Text, IntWritable> {

	public int getPartition(Text key, IntWritable value, int numReduceTask) {
		// TODO Auto-generated method stub
			if(key.toString().startsWith("#"))
			{
				return (1 % numReduceTask);
			}
			if(key.toString().startsWith("@"))
			{
				return (2 % numReduceTask);
			}
			else if(!key.toString().equals(""))
			{
				return (3 % numReduceTask);
			}
			return 1;
	
	}

}
