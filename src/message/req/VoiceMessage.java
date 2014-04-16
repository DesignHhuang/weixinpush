package message.req;

public class VoiceMessage extends BaseMessage
{
	  private   String Recognition;
	  private String MediaId;  
	  private String Format;  
	  public String getMediaId() 
	  {  
		  return MediaId;  
	  }  
	  public void setMediaId(String mediaId)
	  {  
		  MediaId = mediaId;  
	  }  
	  public String getFormat() 
	  {  
		  return Format;  
	  }  
	  public void setFormat(String format)
	  {  
		  Format = format;  
	  }  
	  public String getRecognition()
	  {
		  return Recognition;
	  }
	  public void setRecognition(String recognition)
	  {  
		  //recognition = "今日新闻";
		  Recognition = recognition;  
	  }  
}
