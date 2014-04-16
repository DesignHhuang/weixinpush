package util;

import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import message.resp.VoiceMessage;
import message.resp.Article;
import message.resp.NewsMessage;
import message.resp.TextMessage;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class MessageUtil 
{
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";  
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";  
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";  
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice"; 
    @SuppressWarnings("unchecked") 
    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception
    {  
        Map<String, String> map = new HashMap<String, String>();  
        InputStream inputStream = request.getInputStream();  
        SAXReader reader = new SAXReader();  
        Document document = reader.read(inputStream);  
        Element root = document.getRootElement();  
        List<Element> elementList = root.elements();     
        for (Element e : elementList)  
            map.put(e.getName(), e.getText());  
        inputStream.close();  
        inputStream = null;  
        return map;  
    }
    private static XStream xstream = new XStream(new XppDriver()
    {  
        public HierarchicalStreamWriter createWriter(Writer out) 
        {  
            return new PrettyPrintWriter(out) 
            {  
                boolean cdata = true;  
                @SuppressWarnings("rawtypes")
				public void startNode(String name, Class clazz)
                {  
                    super.startNode(name, clazz);  
                }  
                protected void writeText(QuickWriter writer, String text) 
                {  
                    if (cdata) 
                    {  
                        writer.write("<![CDATA[");  
                        writer.write(text);  
                        writer.write("]]>");  
                    } 
                    else 
                    {  
                        writer.write(text);  
                    }  
                }  
            };  
        }  
    });
    public static String textMessageToXml(TextMessage textMessage) 
    {  
        xstream.alias("xml", textMessage.getClass());  
        return xstream.toXML(textMessage);  
    }  
    public static String newsMessageToXml(NewsMessage newsMessage) 
    {  
        xstream.alias("xml", newsMessage.getClass());  
        xstream.alias("item", new Article().getClass());  
        return xstream.toXML(newsMessage);  
    }   
    public static String voiceMessageToXml(VoiceMessage voiceMessage)
    {
    	xstream.alias("xml", voiceMessage.getClass());
    	return xstream.toXML(voiceMessage);
    }
}
