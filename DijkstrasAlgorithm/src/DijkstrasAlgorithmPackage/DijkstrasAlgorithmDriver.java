package DijkstrasAlgorithmPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DijkstrasAlgorithmDriver {

	private static final transient Logger LOG = LoggerFactory.getLogger(DijkstrasAlgorithmDriver.class);
	private static int count=0;
	public enum CONVERGE {
		  COUNTER
		 }
	
	public static int run() throws IOException, ClassNotFoundException, InterruptedException
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
		
		int counter=1;
		int success=0;
		
		while(counter>0)
		{
		/* FileOutputFormat wants to create the output directory itself.
		 * If it exists, delete it:
		 */
			deleteFolder(conf,outputPath);
			
			Job job = Job.getInstance(conf);
			
			job.setJarByClass(DijkstrasAlgorithmDriver.class);
			job.setMapperClass(DijkstrasAlgorithmMapper.class);
			job.setReducerClass(DijkstrasAlgorithmReducer.class);
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
		
		return success;
	}
	
		public static void main(String[] args) throws Exception {
		
		System.exit(run());
		
	}
	
	/**
	 * Delete a folder on the HDFS. This is an example of how to interact
	 * with the HDFS using the Java API. You can also interact with it
	 * on the command line, using: hdfs dfs -rm -r /path/to/delete
	 * 
	 * @param conf a Hadoop Configuration object
	 * @param folderPath folder to delete
	 * @throws IOException
	 */
	private static void deleteFolder(Configuration conf, String folderPath ) throws IOException {
		FileSystem fs = FileSystem.get(conf);
		Path path = new Path(folderPath);
		if(fs.exists(path)) {
			fs.delete(path,true);
		}
		
	}
}
