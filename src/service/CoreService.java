package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import message.resp.TextMessage;
import model.News;
import model.Paper;
import util.MessageUtil;
import util.SolrConstant;

public class CoreService 
{
	 public static String processRequest(HttpServletRequest request) 
	 {  
		 String respMessage = null;  
		 try 
		 {  
			 String respContent = "请求处理异常，请稍候尝试！";  
	         Map<String, String> requestMap = MessageUtil.parseXml(request);  
	         String fromUserName = requestMap.get("FromUserName");  
	         String toUserName = requestMap.get("ToUserName");  
	         String msgType = requestMap.get("MsgType");
	         String reqContent = requestMap.get("Content").trim();
	         TextMessage textMessage = new TextMessage();  
	         textMessage.setToUserName(fromUserName);  
	         textMessage.setFromUserName(toUserName);  
	         textMessage.setCreateTime(new Date().getTime());  
	         textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
	         textMessage.setFuncFlag(0);
	         SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	         NewsDAO newsDAO = new NewsDAO();
	         PaperDAO paperDAO = new PaperDAO();
	         List<News> newsList = newsDAO.getNewsByTimeRange(SolrConstant.TODAY);
	         List<Paper> paperList = paperDAO.getNewsByTimeRange(SolrConstant.YEAR);
	         String newstitle=newsList.get(0).getTitle();
	         String newsurl=newsList.get(0).getUrl();
	         Date newsupdatetime=newsList.get(0).getUpdateTime();
	         //String newscont=newsList.get(0).getContent();
	         String papertitle=paperList.get(0).getTitle();
	         List<String> paperauthor=paperList.get(0).getAuthor();
	         Date paperupdatetime=paperList.get(0).getUpdateTime();
	         String paperpath=paperList.get(0).getPath();
	         if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) 
	         {
	        	 if(reqContent.equals("1"))
	        	 {
	        		 respContent = "<a href=\""+newsurl+"\">"+newstitle+"</a>。更新时间："+newsupdatetime;
	        	 }
	        	 else if(reqContent.equals("2"))
	        	 {
	        		 respContent = "<a href=\"http://lib.cqvip.com"+paperpath+"\">"+papertitle+"</a>。作者："+paperauthor+"。更新时间："+paperupdatetime;
	        	 }
	        	 else
	        	 {
	        		 respContent = "请发送1选择新闻，输入2选择论文。";
	        	 }
	         }  
	         else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE))
	         {  
	        	 respContent = "图片";  
	         } 	  
	         textMessage.setContent(respContent);  
	         respMessage = MessageUtil.textMessageToXml(textMessage);
	         try {
	             Connection con = null; 
	             Class.forName("com.mysql.jdbc.Driver").newInstance(); 
	             con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/push", "push", "push");
	             Statement stmt; 
	             stmt = con.createStatement();
	             stmt.executeUpdate("INSERT INTO log (request, response, reqtime) VALUES ('"+reqContent+"', '"+respContent+"', '"+time.format(new Date())+"')");
	         } catch (Exception e) {
	             System.out.print("MYSQL");
	         }
		 } 
	     catch (Exception e) 
	     {  
	    	 e.printStackTrace();  
	     }  
	     return respMessage;  
	 }  
}
