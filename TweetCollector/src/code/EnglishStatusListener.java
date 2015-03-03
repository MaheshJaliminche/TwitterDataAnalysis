package code; 
 
import java.util.Arrays; 
import java.util.List; 
 
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 
 
import twitter4j.StallWarning; 
import twitter4j.Status; 
import twitter4j.StatusDeletionNotice; 
import twitter4j.StatusListener; 
 
public class EnglishStatusListener implements StatusListener{ 
 
    private static final transient Logger LOG = LoggerFactory.getLogger(EnglishStatusListener.class); 
     
    @Override 
    public void onException(Exception arg0) { 
        // TODO Auto-generated method stub 
         
    } 
 
    @Override 
    public void onDeletionNotice(StatusDeletionNotice arg0) { 
        // TODO Auto-generated method stub 
         
    } 
 
    @Override 
    public void onScrubGeo(long arg0, long arg1) { 
        // TODO Auto-generated method stub 
         
    } 
 
    @Override 
    public void onStallWarning(StallWarning arg0) { 
        // TODO Auto-generated method stub 
         
    } 
 
    /** 
     * Use log4j to write the Tweet Text to disk. Note that  
     * we only collect Tweet Text content. If you want to collect 
     * additional data, the Status object has several methods including 
     * getUser(), getGeolocation(), and so on. Twitter4j has documentation on 
     * their website on these. 
     */ 
    @Override 
    public void onStatus(Status status) { 
        String totalTweetText = status.getText(); 
        String userName=status.getUser().getName(); 
        String Date= status.getCreatedAt().toString().replaceAll("\\s", "_");  
        if(isEnglish(totalTweetText)&& totalTweetText!=null && (userName !="" | userName != null | userName != " "))  
        { 
             
            String[] Stopwords={"a","about","above","after","again","against","all","am","an", 
                    "and","any","are","as","at","be","by","com","for","from","how","in","it","of", 
                    "on","or","that","the","this","to","was","what","when","where","who","will","with", 
                    "is","do","not","of","I"}; 
                    List<String> stopwordlist=Arrays.asList(Stopwords); 
                    String [] str = totalTweetText.split(" "); 
                    StringBuilder newText=new StringBuilder(); 
                    for(int i=0;i<str.length;i++) 
                    { 
                    if(!stopwordlist.contains(str[i])&&!str[i].contains("http")) 
                    newText.append(str[i]).append(" "); 
                    } 
                    totalTweetText=newText.toString(); 
 
     
     
                    totalTweetText=totalTweetText.replaceAll("[^\\s0-9a-zA-Z @ # : ]", " "); 
                    totalTweetText=totalTweetText.replaceAll("\\n", " ");
     
                    userName= userName.replaceAll("\\s", "_"); 
 
                    LOG.info(totalTweetText.toString() + " , " + Date + " , " +status.getRetweetCount() + " , " +userName + " , " +status.getUser().getFollowersCount()+" ; "); 
             
             
             
        } 
         
         
    } 
 
    @Override 
    public void onTrackLimitationNotice(int arg0) { 
        // TODO Auto-generated method stub 
         
    } 
     
    /** 
     * Our hack for filtering English tweets. While we could get the language of the user 
     * object returned by status.getUser(), people with an English language set still tweet 
     * in other languages. We simply check if the Tweet text contains non-ASCII characters; 
     * if it does, we do not collect it. 
     * @param tweetText 
     * @return true if tweetText contains no non-ASCII characters, false otherwise 
     */ 
    public static boolean isEnglish(String tweetText) { 
        for(int i = 0;i < tweetText.length();i++) { 
            int c = tweetText.charAt(i); 
            if(c > 127) { 
                return false; 
            } 
        } 
        return true; 
    } 
 
}