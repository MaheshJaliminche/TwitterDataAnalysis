package KMeansPackage;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class KMeansMapper extends Mapper<LongWritable, Text, LongWritable, Text> {

	private static final transient Logger LOG = LoggerFactory.getLogger(KMeansMapper.class);
	
	private Parser parser=new Parser();
		
	public void map(LongWritable ikey, Text ivalue, Context context)
   {
		String[] details=null;
		try
		{
			details=parser.ParseLine(ivalue.toString());
			
			LOG.info("Details Length: {}", details.length);
			
			Text value=new Text();
			String tempValue =details[1] + " " + details[2];
			value.set(tempValue);
		
			LongWritable key=new LongWritable(Integer.parseInt(details[0]));
		
			context.write(key,value);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Details Length : "+ details.length);
		}
		
	}

}
