package sample;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable>{

	private final static IntWritable one = new IntWritable(1);
	private Text word = new Text();

	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		String[] getText= value.toString().split(";");
		for (String string : getText) {
			String[] getTweetText=string.split(",");
			String Tokens[]= getTweetText[0].toString().trim().split(" ");
			
			for (String string2 : Tokens) {
				if (!string2.equalsIgnoreCase("")) {
					
					word.set(string2);
					context.write(word, one);
				}
			}
			
			
//			StringTokenizer itr = new StringTokenizer(getTweetText[0].toString());
//			while (itr.hasMoreTokens()) {
//				word.set(itr.nextToken());
//				context.write(word, one);
//			}
			
		}
		
	}
}