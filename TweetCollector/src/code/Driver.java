package code;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import auth.ConfigBuilder;


public class Driver {
	
	private static final transient Logger LOG = LoggerFactory.getLogger(Driver.class);
	
	public static void main(String[] args)
	{
		LOG.info("Initializing...");
		LOG.debug("Creating Listener...");
		EnglishStatusListener listener = new EnglishStatusListener();
		LOG.debug("Creating Stream...");
	    TwitterStream twitterStream = new TwitterStreamFactory(ConfigBuilder.getConfig()).getInstance();
	    //twitterStream.addListener(listener);
	    LOG.info("Initialization Complete.");
	    //twitterStream.sample();
	 
	    FilterQuery fq = new FilterQuery();
	       String keywords[] = {"#IPL" ,"#IPL7",
	    		   "#IPL2014","#PepsiIPL" , 
	    		   "#TwitterMirror","#IPLSpotFixing" };
	       
	       String[] language = new String[]{"en"};
	       
	       fq.track(keywords).language(language);
	           twitterStream.addListener(listener);            
	           twitterStream.filter(fq);
	 

	}

}
