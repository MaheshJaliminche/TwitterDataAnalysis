package sample;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class CustomPartitioner extends Partitioner<Text, IntWritable> {

	public int getPartition(Text key, IntWritable value, int numReduceTask) {
		// TODO Auto-generated method stub
		
		String atoe="abcde";
		String ftoj="fghij";
		String ktop="klmnop";
		String qtoz="qrstuvwxyz";
		char startChar;
		String Data=key.toString().toLowerCase();
		startChar=Data.charAt(1);
		
		
		
			if(atoe.indexOf(startChar)!=-1)
			{
				return (5 % numReduceTask);
			}
			if(ftoj.indexOf(startChar)!=-1)
			{
				return (1 % numReduceTask);
			}
			if(ktop.indexOf(startChar)!=-1)
			{
				return (2 % numReduceTask);
			}
			if(qtoz.indexOf(startChar)!=-1)
			{
				return (3 % numReduceTask);
			}
			return (4 % numReduceTask);
	
	}

}
