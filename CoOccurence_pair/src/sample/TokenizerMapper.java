package sample;

import java.io.IOException;
import java.security.KeyStore.Entry;
import java.util.HashMap;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.join.Parser.Token;
import org.apache.hadoop.mapreduce.Mapper;

public class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable>{

	private final static IntWritable one = new IntWritable(1);
	private Text word = new Text();
	

	@SuppressWarnings("null")
	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		String[] getText= value.toString().split(";");
		for (String string : getText) {
			String[] getTweetText=string.split(",");
			
			HashMap<String, String> hashTagCollection= new HashMap<>();
			String[] Tokens= getTweetText[0].toString().trim().split(" ");
			
			for (int i = 0; i < Tokens.length; i++) {
				if(Tokens[i].toString().trim().startsWith("#"))
				{
					hashTagCollection.put(Tokens[i], "1");
				}
			}
			
			if(hashTagCollection.size()>1)
			{
				for(java.util.Map.Entry<String, String> a :hashTagCollection.entrySet())
				{	
					for (int i = 0; i < Tokens.length; i++) {
						if(Tokens[i].toString().trim().startsWith("#")&&(!Tokens[i].toString().trim().equalsIgnoreCase(a.getKey())))
						{
							String finalKey;
							if(a.getKey().toString().trim().compareTo(Tokens[i].toString().trim())<0)
							{
								if(!a.getKey().toString().trim().equalsIgnoreCase("#"))
								{
								finalKey= a.getKey().toString().trim()+"_"+Tokens[i].toString().trim();
								context.write(new Text(finalKey), one);
								}
							}							
							
						}
					}
					
					
				}
			}
			
		}
		
	}
}