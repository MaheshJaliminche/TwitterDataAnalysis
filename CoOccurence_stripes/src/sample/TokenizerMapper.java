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

public class TokenizerMapper extends Mapper<Object, Text, Text, MapWritable>{

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
					MapWritable CoOccuringHash =new MapWritable();
					for (int i = 0; i < Tokens.length; i++) {
						if(Tokens[i].toString().trim().startsWith("#")&&(!Tokens[i].toString().trim().equalsIgnoreCase(a.getKey())))
						{
							if(CoOccuringHash.containsKey(word.toString()))
							{
									
							CoOccuringHash.put(new Text(Tokens[i]),  new IntWritable(Integer.parseInt(CoOccuringHash.get(Tokens[i]).toString().trim())+1));
							}
							else
							{
								
							 	CoOccuringHash.put(new Text(Tokens[i]), new IntWritable(1) );
							}
							
						}
					}
					context.write(new Text(a.getKey().toString())  , CoOccuringHash);
					
				}
			}
			
			
		}
		
	}
}