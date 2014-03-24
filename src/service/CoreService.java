package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.SolrServerException;

import message.resp.TextMessage;
import model.News;
import model.Paper;
import util.MessageUtil;
import util.SolrConstant;

public class CoreService 
{
	public String getMainMenu() 
	{  
	    StringBuffer buffer = new StringBuffer();  
	    buffer.append("您好，请回复数字选择服务：").append("\n\n");  
	    buffer.append("10  今日新闻").append("\n");  
	    buffer.append("11  昨日新闻").append("\n");
	    buffer.append("20  最近论文").append("\n");
	    buffer.append("21  今日论文").append("\n\n");  
	    buffer.append("回复“?”显示此帮助菜单");  
	    return buffer.toString();  
	}
	public String getMainMenuWeb() //web
	{  
	    StringBuffer buffer = new StringBuffer();  
	    buffer.append("您好，请回复数字选择服务：").append("\n\n").append("</br>");  
	    buffer.append("10  今日新闻").append("\n").append("</br>");  
	    buffer.append("11  昨日新闻").append("\n").append("</br>");
	    buffer.append("20  最近论文").append("\n").append("</br>");
	    buffer.append("21  今日论文").append("\n\n").append("</br>");  
	    buffer.append("回复“?”显示此帮助菜单").append("</br>");  
	    return buffer.toString();  
	}
	public String getTodayNews() 
	{
		NewsDAO newsDAO = new NewsDAO();
		StringBuffer buffer = new StringBuffer();  
		List<News> newsList = null;
		try {
			newsList = newsDAO.getNewsByTimeRange(SolrConstant.TODAY);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		buffer.append("今日新闻：").append("\n\n");  
		for(int i=0; i<=4; i++)
		{ 		
			String newstitle=newsList.get(i).getTitle();
			String newsurl=newsList.get(i).getUrl();
			Date newsupdatetime=newsList.get(i).getUpdateTime();
			String str = "<a href=\""+newsurl+"\">"+newstitle+"</a>。更新时间："+newsupdatetime;
			buffer.append(i+1).append(".").append(str).append("\n");
		}
	    return buffer.toString();  	
	}
	public String getTodayNewsWeb() //web
	{
		NewsDAO newsDAO = new NewsDAO();
		StringBuffer buffer = new StringBuffer();  
		List<News> newsList = null;
		try {
			newsList = newsDAO.getNewsByTimeRange(SolrConstant.TODAY);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		buffer.append("今日新闻：").append("\n\n").append("</br>");  
		for(int i=0; i<=4; i++)
		{ 		
			String newstitle=newsList.get(i).getTitle();
			String newsurl=newsList.get(i).getUrl();
			Date newsupdatetime=newsList.get(i).getUpdateTime();
			String str = "<a href=\""+newsurl+"\">"+newstitle+"</a>。更新时间："+newsupdatetime+"</br>";
			buffer.append(i+1).append(".").append(str).append("\n");
		}
	    return buffer.toString();  	
	}
	public String getYesNews() throws SolrServerException 
	{
		NewsDAO newsDAO = new NewsDAO();
		StringBuffer buffer = new StringBuffer();  
		List<News> newsList = newsDAO.getNewsByTimeRange(SolrConstant.YESTERDAY);
		buffer.append("昨日新闻：").append("\n\n");  
		for(int i=0; i<=4; i++)
		{ 		
			String newstitle=newsList.get(i).getTitle();
			String newsurl=newsList.get(i).getUrl();
			Date newsupdatetime=newsList.get(i).getUpdateTime();
			String str = "<a href=\""+newsurl+"\">"+newstitle+"</a>。更新时间："+newsupdatetime;		
			buffer.append(i+1).append(".").append(str).append("\n");
		}
	    return buffer.toString();  	
	}
	public String getYesNewsWeb() throws SolrServerException //web
	{
		NewsDAO newsDAO = new NewsDAO();
		StringBuffer buffer = new StringBuffer();  
		List<News> newsList = newsDAO.getNewsByTimeRange(SolrConstant.YESTERDAY);
		buffer.append("昨日新闻：").append("\n\n").append("</br>");  
		for(int i=0; i<=4; i++)
		{ 		
			String newstitle=newsList.get(i).getTitle();
			String newsurl=newsList.get(i).getUrl();
			Date newsupdatetime=newsList.get(i).getUpdateTime();
			String str = "<a href=\""+newsurl+"\">"+newstitle+"</a>。更新时间："+newsupdatetime+"</br>";		
			buffer.append(i+1).append(".").append(str).append("\n");
		}
	    return buffer.toString();  	
	}
	public String getRecPaper() throws SolrServerException
	{
		PaperDAO paperDAO = new PaperDAO();
		StringBuffer buffer = new StringBuffer();  
		List<Paper> paperList = paperDAO.getNewsByTimeRange(SolrConstant.YEAR);
		buffer.append("最近论文：").append("\n\n");  
		for(int i=0; i<=4; i++)
		{ 		
			String papertitle=paperList.get(i).getTitle();
			List<String> paperauthor=paperList.get(i).getAuthor();
			Date paperupdatetime=paperList.get(i).getUpdateTime();
			String paperpath=paperList.get(i).getPath();
			String str = "<a href=\"http://lib.cqvip.com"+paperpath+"\">"+papertitle+"</a>。作者："+paperauthor+"。更新时间："+paperupdatetime;		
			buffer.append(i+1).append(".").append(str).append("\n");
		}
	    return buffer.toString();  
	}
	public String getRecPaperWeb() throws SolrServerException //web
	{
		PaperDAO paperDAO = new PaperDAO();
		StringBuffer buffer = new StringBuffer();  
		List<Paper> paperList = paperDAO.getNewsByTimeRange(SolrConstant.YEAR);
		buffer.append("最近论文：").append("\n\n").append("</br>");  
		for(int i=0; i<=4; i++)
		{ 		
			String papertitle=paperList.get(i).getTitle();
			List<String> paperauthor=paperList.get(i).getAuthor();
			Date paperupdatetime=paperList.get(i).getUpdateTime();
			String paperpath=paperList.get(i).getPath();
			String str = "<a href=\"http://lib.cqvip.com"+paperpath+"\">"+papertitle+"</a>。作者："+paperauthor+"。更新时间："+paperupdatetime+"</br>";		
			buffer.append(i+1).append(".").append(str).append("\n");
		}
	    return buffer.toString();  
	}
	public String getTodayPaper() throws SolrServerException
	{
		PaperDAO paperDAO = new PaperDAO();
		StringBuffer buffer = new StringBuffer();  
		List<Paper> paperList = paperDAO.getNewsByTimeRange(SolrConstant.TODAY);
		if(paperList == null)
		{
			String str = "今日无论文更新！";
			buffer.append(str);
		}
		else
		{
			buffer.append("今日论文：").append("\n\n");  
			for(int i=0; i<=4; i++)
			{ 		
				String papertitle=paperList.get(i).getTitle();
				List<String> paperauthor=paperList.get(i).getAuthor();
				Date paperupdatetime=paperList.get(i).getUpdateTime();
				String paperpath=paperList.get(i).getPath();
				String str = "<a href=\"http://lib.cqvip.com"+paperpath+"\">"+papertitle+"</a>。作者："+paperauthor+"。更新时间："+paperupdatetime;		
				buffer.append(i+1).append(".").append(str).append("\n");
			}
		}
		return buffer.toString();
	}
	public String getTodayPaperWeb() throws SolrServerException  //web
	{
		PaperDAO paperDAO = new PaperDAO();
		StringBuffer buffer = new StringBuffer();  
		List<Paper> paperList = paperDAO.getNewsByTimeRange(SolrConstant.TODAY);
		if(paperList == null)
		{
			String str = "今日无论文更新！";
			buffer.append(str).append("</br>");
		}
		else
		{
			buffer.append("今日论文：").append("\n\n").append("</br>");  
			for(int i=0; i<=4; i++)
			{ 		
				String papertitle=paperList.get(i).getTitle();
				List<String> paperauthor=paperList.get(i).getAuthor();
				Date paperupdatetime=paperList.get(i).getUpdateTime();
				String paperpath=paperList.get(i).getPath();
				String str = "<a href=\"http://lib.cqvip.com"+paperpath+"\">"+papertitle+"</a>。作者："+paperauthor+"。更新时间："+paperupdatetime+"</br>";		
				buffer.append(i+1).append(".").append(str).append("\n");
			}
		}
		return buffer.toString();
	}
	public static String processRequest(HttpServletRequest request) 
	 {  
		 String respMessage = null;  
		 try 
		 {  
			 String respContent = "请求处理异常，请稍候尝试！";
			 String respWeb = null;
	         Map<String, String> requestMap = MessageUtil.parseXml(request);  
	         String fromUserName = requestMap.get("FromUserName");  
	         String toUserName = requestMap.get("ToUserName"); 
	         String reqContent = requestMap.get("Content").trim();
	         String msgType = requestMap.get("MsgType");
	         TextMessage textMessage = new TextMessage();  
	         textMessage.setToUserName(fromUserName);  
	         textMessage.setFromUserName(toUserName);  
	         textMessage.setCreateTime(new Date().getTime());  
	         textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
	         textMessage.setFuncFlag(0);
	         SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	         if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) 
	         {
	        	 if(reqContent.equals("10"))
	        	 {
	        		 CoreService strTodayNews = new CoreService();
	        		 respContent = strTodayNews.getTodayNews();
	        		 respWeb = strTodayNews.getTodayNewsWeb();
	        	 }
	        	 else if(reqContent.equals("11"))
	        	 {
	        		 CoreService strYesNews = new CoreService();
	        		 respContent = strYesNews.getYesNews();
	        		 respWeb = strYesNews.getYesNewsWeb();
	        	 }
	        	 else if(reqContent.equals("20"))
	        	 {
	        		 CoreService strRecPaper = new CoreService();
	        		 respContent = strRecPaper.getRecPaper();	     
	        		 respWeb = strRecPaper.getRecPaperWeb();
	        	 }
	        	 else if(reqContent.equals("21"))
	        	 {
	        		 CoreService strTodayPaper = new CoreService();
	        		 respContent = strTodayPaper.getTodayPaper();
	        		 respWeb = strTodayPaper.getTodayPaperWeb();
	        	 }
	        	 else 
	        	 {
	        		 CoreService strMainMenu = new CoreService();
	        		 respContent = strMainMenu.getMainMenu();
	        		 respWeb = strMainMenu.getMainMenuWeb();
	        	 }
	         }
	         else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT))
	         {
	        	 String eventType = requestMap.get("Event");  
	             if(eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) 
	             {  
	            	 respContent = "欢迎关注GetImpo！发送“？”了解GetImpo!@黄丽民";
	            	 respWeb = respContent;
	             }  
	             else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) 
	             {  
	             }  
	         }
	         textMessage.setContent(respContent);  
	         respMessage = MessageUtil.textMessageToXml(textMessage);
	         Connection con = null; 
	         Class.forName("com.mysql.jdbc.Driver").newInstance(); 
	         con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/push", "push", "push");
	         Statement stmt; 
	         stmt = con.createStatement();
	         stmt.executeUpdate("INSERT INTO log (request, response, reqtime) VALUES ('"+reqContent+"', '"+respWeb+"', '"+time.format(new Date())+"')");
		 } 
	     catch (Exception e) 
	     {  
	    	 e.printStackTrace();  
	     }  
	     return respMessage;  
	 }  
}
