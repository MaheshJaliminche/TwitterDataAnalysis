package KMeansPackage;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KMeansDriver {

	private static final transient Logger LOG = LoggerFactory.getLogger(KMeansDriver.class);
	private static int count=0;
	FileHandler fileHandler=new FileHandler();
	
	public static enum CONVERGE {
		  COUNTER,
		  ITERATIONS
		};
	
	public int Run()
	{
		int success=0;
		int counter=1;
		
		try
		{
			Configuration conf = new Configuration();		
			
			conf.set("mapred.textoutputformat.separator", " ");
			LOG.info("HDFS Root Path: {}", conf.get("fs.defaultFS"));
			LOG.info("MR Framework: {}", conf.get("mapreduce.framework.name"));
			
			/* Set the Input/Output Paths on HDFS */
			String inputPath = "/input";
			String _output="/output_";
			String outputPath = _output + ++count;
			String outputFile="/part-r-00000";
			String outputFilePath=outputPath + outputFile;
			
			fileHandler.CreateFolder(conf, GlobalVariables.clusterFolderPath);
			fileHandler.Create(conf,GlobalVariables.clusterFilePath);
			fileHandler.Write(GlobalVariables.clusterFilePath,GlobalVariables.defaultCentroids);

			/* FileOutputFormat wants to create the output directory itself.
			 * If it exists, delete it:
			 */
			while(counter>0)
			{		
			
				fileHandler.DeleteFolder(conf,outputPath);
				
				Job job = Job.getInstance(conf);
				job.setJarByClass(KMeansDriver.class);
				job.setMapperClass(KMeansMapper.class);
				job.setReducerClass(KMeansReducer.class);
				job.setOutputKeyClass(LongWritable.class);
				job.setOutputValueClass(Text.class);
				FileInputFormat.addInputPath(job, new Path(inputPath));
				FileOutputFormat.setOutputPath(job, new Path(outputPath));
				
				success=job.waitForCompletion(true) ? 0 : 1;
				counter=(int)job.getCounters().findCounter(CONVERGE.COUNTER).getValue();
				
				inputPath = outputFilePath;
				outputPath = _output + ++count;
				outputFilePath=outputPath + outputFile;
				
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return success;
	}
	
}
