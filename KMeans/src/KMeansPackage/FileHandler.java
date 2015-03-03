package KMeansPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class FileHandler {
	
	public void Write(String filePath, String content) throws IOException
	{
		Path path=null;
		FileSystem fs=null;
		OutputStreamWriter writer=null;
		BufferedWriter buffer=null;
		
		try
		{
			path=new Path(filePath);
			fs = FileSystem.get(new Configuration());
			
			writer = new OutputStreamWriter(fs.append(path));
			buffer=new BufferedWriter(writer);
			
			buffer.write(content);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			buffer.close();
			writer.close();
		}	
		
	}

	public String Read(String filePath) throws IOException
	{
		FileSystem fs=null;
		InputStreamReader reader=null;
		BufferedReader br=null;
		String line=null;
		
		try
		{
			Path path = new Path(filePath);
			fs = FileSystem.get(new Configuration());
			reader=new InputStreamReader(fs.open(path));
			br=new BufferedReader(reader);
			
			line=br.readLine();
			
			/*while(line!=null)
			{
				line=br.readLine();
			}*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			br.close();
			reader.close();
		}
		return line;
		
	}
	
	public void Create(Configuration conf,String filePath) throws IOException 
	{
		Path path =null;
		FileSystem fs=null;
		
		try
		{
			path=new Path(filePath);
			fs=FileSystem.get(conf);
			
			/*if(fs.exists(path))
			{
				fs.delete(path, true);
			}*/
			
			fs.createNewFile(path);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			//fs.close();
			path=null;
		}
		
	}
	
	public void CreateFolder(Configuration conf, String folderPath ) throws IOException 
	{
		FileSystem fs = FileSystem.get(conf);
		Path path = new Path(folderPath);
		if(fs.exists(path)) 
		{
			fs.delete(path,true);
		}
		fs.mkdirs(path);
	}
	
	public int[] GetCentroidFromFile(int noOfValuesToRead) 
	{
		String line=null;
		try {
			line = Read(GlobalVariables.clusterFilePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] KValues=line.split(":");
		int KValueLength=KValues.length;
		int[] KValuesArray=new int[noOfValuesToRead];
		
		for(int i=KValueLength-noOfValuesToRead,index=0;i<KValueLength;i++,index++)
		{
			KValuesArray[index]=Integer.parseInt(KValues[i]);
		}
		
		return KValuesArray;
	}

	public void DeleteFolder(Configuration conf, String folderPath ) throws IOException 
	{
		FileSystem fs = FileSystem.get(conf);
		Path path = new Path(folderPath);
		if(fs.exists(path)) 
		{
			fs.delete(path,true);
		}
	}
	
}
