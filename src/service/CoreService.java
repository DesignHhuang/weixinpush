package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.SolrServerException;
import message.resp.Article;
import message.resp.NewsMessage;
import message.resp.TextMessage;
import message.resp.VoiceMessage;
import model.News;
import model.Paper;
import model.Patent;
import util.MessageUtil;
import util.SolrConstant;

public class CoreService {
	SolrDAOImpl solrDAO = new SolrDAOImpl();

	public String getCustomizeMenu()
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("您好，请回复数字开始定制：").append("\n\n");
		buffer.append("0  首次定制").append("\n");
		buffer.append("1  已经定制").append("\n");
		buffer.append("2  修改定制").append("\n");
		buffer.append("已经定制直接回复1。").append("\n\n");
		return buffer.toString();
	}
	
	public String getFirstCustomize()
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("您好，请回复“00”+“关注的关键词”。（如：00计算机，00机械）").append("\n\n");
		return buffer.toString();
	}
	public String getchangeCustomize()
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("您好，请回复“02”+“关注的关键词”来修改订制内容。（如：02计算机，02机械）").append("\n\n");
		return buffer.toString();
	}
	
	public String getMainMenu() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("您好，请回复数字选择服务：").append("\n\n");
		//buffer.append("已开通语音服务,试试发送语音消息！").append("\n");
		buffer.append("10  最新新闻动态").append("\n");
		//buffer.append("11  昨日新闻").append("\n");
		buffer.append("12  图片新闻").append("\n");
		buffer.append("20  最新论文动态").append("\n");
		//buffer.append("21  今日论文").append("\n");
		buffer.append("30  最新专利动态").append("\n");
		//buffer.append("31  今日专利").append("\n\n");
		return buffer.toString();
	}
	
	public String getSecTodayMenu() //今日新闻的二级菜单
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("100 语音").append("\n");
		buffer.append("101 文字").append("\n");
		return buffer.toString();	
	}

	public String getTodayNewsVoice() //语音消息
	{
		return "读取语音";
	}
	
	public String getMainMenuWeb() // web
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("您好，请回复数字选择服务：").append("\n\n").append("</br>");
		buffer.append("10  今日新闻").append("\n").append("</br>");
		buffer.append("11  昨日新闻").append("\n").append("</br>");
		buffer.append("12  图片新闻").append("\n").append("</br>");
		buffer.append("20  最近论文").append("\n").append("</br>");
		buffer.append("21  今日论文").append("\n").append("</br>");
		buffer.append("30  最近专利").append("\n").append("</br>");
		buffer.append("31  今日专利").append("\n\n").append("</br>");
		return buffer.toString();
	}

	public String getTodayNews(String content)
	{
		StringBuffer buffer = new StringBuffer();
		List<News> newsList = null;
		try {
			newsList = solrDAO.getResults(content, 0, 5,
					"news", News.class);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		buffer.append("关于"+content+"的最新新闻动态：").append("\n\n");
		for (int i = 0; i < newsList.size(); i++) {
			String newstitle = newsList.get(i).getTitle();
			String newsurl = newsList.get(i).getUrl();
			Date newsupdatetime = newsList.get(i).getUpdateTime();
			String str = "<a href=\"" + newsurl + "\">" + newstitle
					+ "</a>。更新时间：" + newsupdatetime;
			buffer.append(i + 1).append(".").append(str).append("\n");
		}
		return buffer.toString();
	}

	public String getRecPatent(String content)
	{
		StringBuffer buffer = new StringBuffer();
		List<Patent> patentList = null;
		try {
			patentList = solrDAO.getResults(content, 0, 5,
					"patent", Patent.class);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		buffer.append("关于"+content+"的最新专利动态：").append("\n\n");
		for (int i = 0; i < patentList.size(); i++) {
			String patenttitle = patentList.get(i).getTitle();
			List<String> patentinventor = patentList.get(i).getInventor();
			List<String> patentapplicant = patentList.get(i).getApplicant();
			String patentabstract = patentList.get(i).getAbstract();
			Date newsupdatetime = patentList.get(i).getUpdateTime();
			String str = "标题：" + patenttitle
					+"。发明者：" + patentinventor
					+"。申请者：" + patentapplicant
					+ "。摘要：" + patentabstract
					+ "</a>。更新时间：" + newsupdatetime;
			buffer.append(i + 1).append(".").append(str).append("\n");
		}
		return buffer.toString();
	}
	
	public String getTodayPatent() throws SolrServerException 
	{
		StringBuffer buffer = new StringBuffer();
		List<Patent> patentList = solrDAO.getResultsByTimeRange(
				SolrConstant.TODAY, 0, 5, "patent", Patent.class);
		if (patentList.isEmpty()) {
			String str = "今日无专利更新！";
			buffer.append(str);
		} else {
			buffer.append("今日专利：").append("\n\n");
			for (int i = 0; i < patentList.size(); i++) {
				String patenttitle = patentList.get(i).getTitle();
				List<String> patentinventor = patentList.get(i).getInventor();
				List<String> patentapplicant = patentList.get(i).getApplicant();
				String patentabstract = patentList.get(i).getAbstract();
				Date newsupdatetime = patentList.get(i).getUpdateTime();
				String str = "标题：" + patenttitle
						+"发明者：" + patentinventor
						+"申请者：" + patentapplicant
						+ "摘要：" + patentabstract
						+ "</a>。更新时间：" + newsupdatetime;
				buffer.append(i + 1).append(".").append(str).append("\n");
			}
		}
		return buffer.toString();
	}

	public String getTodayNewsWeb() // web
	{
		StringBuffer buffer = new StringBuffer();
		List<News> newsList = null;
		try {
			newsList = solrDAO.getResultsByTimeRange(SolrConstant.TODAY, 0, 5,
					"news", News.class);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		buffer.append("今日新闻：").append("\n\n").append("</br>");
		for (int i = 0; i < newsList.size(); i++) {
			String newstitle = newsList.get(i).getTitle();
			String newsurl = newsList.get(i).getUrl();
			Date newsupdatetime = newsList.get(i).getUpdateTime();
			String str = "<a href=\"" + newsurl + "\">" + newstitle
					+ "</a>。更新时间：" + newsupdatetime + "</br>";
			buffer.append(i + 1).append(".").append(str).append("\n");
		}
		return buffer.toString();
	}

	public String getYesNews() throws SolrServerException {
		StringBuffer buffer = new StringBuffer();
		List<News> newsList = solrDAO.getResultsByTimeRange(
				SolrConstant.YESTERDAY, 0, 5, "news", News.class);
		buffer.append("昨日新闻：").append("\n\n");
		for (int i = 0; i < newsList.size(); i++) {
			String newstitle = newsList.get(i).getTitle();
			String newsurl = newsList.get(i).getUrl();
			Date newsupdatetime = newsList.get(i).getUpdateTime();
			String str = "<a href=\"" + newsurl + "\">" + newstitle
					+ "</a>。更新时间：" + newsupdatetime;
			buffer.append(i + 1).append(".").append(str).append("\n");
		}
		return buffer.toString();
	}

	public String getYesNewsWeb() throws SolrServerException // web
	{
		StringBuffer buffer = new StringBuffer();
		List<News> newsList = solrDAO.getResultsByTimeRange(
				SolrConstant.YESTERDAY, 0, 5, "news", News.class);
		buffer.append("昨日新闻：").append("\n\n").append("</br>");
		for (int i = 0; i < newsList.size(); i++) {
			String newstitle = newsList.get(i).getTitle();
			String newsurl = newsList.get(i).getUrl();
			Date newsupdatetime = newsList.get(i).getUpdateTime();
			String str = "<a href=\"" + newsurl + "\">" + newstitle
					+ "</a>。更新时间：" + newsupdatetime + "</br>";
			buffer.append(i + 1).append(".").append(str).append("\n");
		}
		return buffer.toString();
	}

	public String getRecPaper(String content) throws SolrServerException {
		StringBuffer buffer = new StringBuffer();
		List<Paper> paperList = solrDAO.getResults(
				content, 0, 5, "papers", Paper.class);
		buffer.append("关于"+content+"的最新论文动态：").append("\n\n");
		for (int i = 0; i < paperList.size(); i++) {
			String papertitle = paperList.get(i).getTitle();
			List<String> paperauthor = paperList.get(i).getAuthor();
			Date paperupdatetime = paperList.get(i).getUpdateTime();
			String paperpath = paperList.get(i).getPath();
			String str = "<a href=\"http://lib.cqvip.com" + paperpath + "\">"
					+ papertitle + "</a>。作者：" + paperauthor + "。更新时间："
					+ paperupdatetime;
			buffer.append(i + 1).append(".").append(str).append("\n");
		}
		return buffer.toString();
	}

	public String getRecPaperWeb() throws SolrServerException // web
	{
		StringBuffer buffer = new StringBuffer();
		List<Paper> paperList = solrDAO.getResultsByTimeRange(
				SolrConstant.YEAR, 0, 5, "papers", Paper.class);
		buffer.append("最近论文：").append("\n\n").append("</br>");
		for (int i = 0; i < paperList.size(); i++) {
			String papertitle = paperList.get(i).getTitle();
			List<String> paperauthor = paperList.get(i).getAuthor();
			Date paperupdatetime = paperList.get(i).getUpdateTime();
			String paperpath = paperList.get(i).getPath();
			String str = "<a href=\"http://lib.cqvip.com" + paperpath + "\">"
					+ papertitle + "</a>。作者：" + paperauthor + "。更新时间："
					+ paperupdatetime + "</br>";
			buffer.append(i + 1).append(".").append(str).append("\n");
		}
		return buffer.toString();
	}

	public String getTodayPaper() throws SolrServerException {
		StringBuffer buffer = new StringBuffer();
		List<Paper> paperList = solrDAO.getResultsByTimeRange(
				SolrConstant.TODAY, 0, 5, "papers", Paper.class);
		if (paperList.isEmpty()) {
			String str = "今日无论文更新！";
			buffer.append(str);
		} else {
			buffer.append("今日论文：").append("\n\n");
			for (int i = 0; i < paperList.size(); i++) {
				String papertitle = paperList.get(i).getTitle();
				List<String> paperauthor = paperList.get(i).getAuthor();
				Date paperupdatetime = paperList.get(i).getUpdateTime();
				String paperpath = paperList.get(i).getPath();
				String str = "<a href=\"http://lib.cqvip.com" + paperpath
						+ "\">" + papertitle + "</a>。作者：" + paperauthor
						+ "。更新时间：" + paperupdatetime;
				buffer.append(i + 1).append(".").append(str).append("\n");
			}
		}
		return buffer.toString();
	}

	public String getTodayPaperWeb() throws SolrServerException // web
	{
		StringBuffer buffer = new StringBuffer();
		List<Paper> paperList = solrDAO.getResultsByTimeRange(
				SolrConstant.TODAY, 0, 5, "papers", Paper.class); 
		if (paperList.isEmpty()) {
			String str = "今日无论文更新！";
			buffer.append(str).append("</br>");
		} else {
			buffer.append("今日论文：").append("\n\n").append("</br>");
			for (int i = 0; i < paperList.size(); i++) {
				String papertitle = paperList.get(i).getTitle();
				List<String> paperauthor = paperList.get(i).getAuthor();
				Date paperupdatetime = paperList.get(i).getUpdateTime();
				String paperpath = paperList.get(i).getPath();
				String str = "<a href=\"http://lib.cqvip.com" + paperpath
						+ "\">" + papertitle + "</a>。作者：" + paperauthor
						+ "。更新时间：" + paperupdatetime + "</br>";
				buffer.append(i + 1).append(".").append(str).append("\n");
			}
		}
		return buffer.toString();
	}
	public static String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			String respContent = "请求处理异常，请稍候尝试！";
			String respWeb = null;
			String picurl = null;
			String resp = null;
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			String fromUserName = requestMap.get("FromUserName");
			String toUserName = requestMap.get("ToUserName");
			String msgType = requestMap.get("MsgType");
			
			TextMessage textMessage = new TextMessage();
			List<Article> articleList = new ArrayList<Article>();  
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			
			NewsMessage newsMessage = new NewsMessage();  
            newsMessage.setToUserName(fromUserName);  
            newsMessage.setFromUserName(toUserName);  
            newsMessage.setCreateTime(new Date().getTime());  
            newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);  
            newsMessage.setFuncFlag(0);  
            
            VoiceMessage voiceMessage = new VoiceMessage();
            voiceMessage.setToUserName(fromUserName);  
            voiceMessage.setFromUserName(toUserName);  
            voiceMessage.setCreateTime(new Date().getTime());  
            voiceMessage.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_VOICE);  
            voiceMessage.setFuncFlag(2);  
            
			SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) 
			{
				String reqContent = requestMap.get("Content").trim();
				if(reqContent.equals("0"))//首次定制
				{
					CoreService strgetFirstCustomize = new CoreService();
					respContent = strgetFirstCustomize.getFirstCustomize();
					//respWeb = strgetFirstCustomize.getMainMenuWeb();
					textMessage.setContent(respContent);
					respMessage = MessageUtil.textMessageToXml(textMessage);
				}
				else if(reqContent.startsWith("00"))
				{
					String repTran = reqContent.substring(2);
					respContent = "您所输入的关键词为“" + repTran + "”。已完成定制。输入1可查看定制的信息。";   
					textMessage.setContent(respContent);
					respMessage = MessageUtil.textMessageToXml(textMessage);
					Connection con = null;
    				Class.forName("com.mysql.jdbc.Driver").newInstance();
    				con = DriverManager.getConnection(
    						"jdbc:mysql://127.0.0.1:3306/push", "push", "push");
    				Statement stmt;
    				stmt = con.createStatement();
    				stmt.executeUpdate("INSERT INTO cus (openid, content, reqtime) VALUES ('"
    						+ fromUserName
    						+ "', '"
    						+ repTran
    						+ "', '"
    						+ time.format(new Date()) + "')");	
				}
				else if(reqContent.equals("1")) //已经定制
				{
					CoreService strMainMenu = new CoreService();
					respContent = strMainMenu.getMainMenu();
					respWeb = strMainMenu.getMainMenuWeb();
					textMessage.setContent(respContent);
					respMessage = MessageUtil.textMessageToXml(textMessage);
				}
				else if(reqContent.equals("2"))
				{
					CoreService strgetchangeCustomize = new CoreService();
					respContent = strgetchangeCustomize.getchangeCustomize();
					//respWeb = strgetFirstCustomize.getMainMenuWeb();
					textMessage.setContent(respContent);
					respMessage = MessageUtil.textMessageToXml(textMessage);
				}
				else if(reqContent.startsWith("02")) //修改定制 
				{
					String repTran = reqContent.substring(2);
					respContent = "您修改后的关键词为“" + repTran + "”。已完成修改。输入1可查看定制的信息。";   
					textMessage.setContent(respContent);
					respMessage = MessageUtil.textMessageToXml(textMessage);
					Connection con = null;
    				Class.forName("com.mysql.jdbc.Driver").newInstance();
    				con = DriverManager.getConnection(
    						"jdbc:mysql://127.0.0.1:3306/push", "push", "push");
    				Statement stmt;
    				stmt = con.createStatement();
    				stmt.executeUpdate("INSERT INTO cus (openid, content, reqtime) VALUES ('"
    						+ fromUserName
    						+ "', '"
    						+ repTran
    						+ "', '"
    						+ time.format(new Date()) + "')");	
				}
				
				else if(reqContent.equals("10")||reqContent.equals("今日新闻。")) 
				{
					CoreService strSecTodayMenu = new CoreService();
					respContent = strSecTodayMenu.getSecTodayMenu();
					respWeb = strSecTodayMenu.getTodayNewsWeb(); //网页
					textMessage.setContent(respContent);
					respMessage = MessageUtil.textMessageToXml(textMessage);
				} 
				else if(reqContent.equals("100")) //语音
				{
					CoreService strTodayNews = new CoreService();
					respContent = strTodayNews.getTodayNewsVoice();
					respWeb = strTodayNews.getTodayNewsWeb(); //网页
					textMessage.setContent(respContent);
					respMessage = MessageUtil.textMessageToXml(textMessage);
				}
				else if(reqContent.equals("101")) //文字
				{
					CoreService strTodayNews = new CoreService();
					Connection con = null;
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/push", "push", "push");
					Statement stmt; //创建声明
			        stmt = con.createStatement();
					String selectSql = "SELECT * FROM cus where openid = '"+fromUserName+"' order by id desc " ;  
					ResultSet selectRes = stmt.executeQuery(selectSql);
					selectRes.next();
					String content = selectRes.getString("content");
					respContent = strTodayNews.getTodayNews(content);
					respWeb = strTodayNews.getTodayNewsWeb(); //网页
					textMessage.setContent(respContent);
					respMessage = MessageUtil.textMessageToXml(textMessage);
				}
				
				
				
				
				
				
				
				
				else if (reqContent.equals("11")||reqContent.equals("昨日新闻。")) 
				{
					CoreService strYesNews = new CoreService();
					respContent = strYesNews.getYesNews();
					respWeb = strYesNews.getYesNewsWeb();
					textMessage.setContent(respContent);
					respMessage = MessageUtil.textMessageToXml(textMessage);
				} 
				else if (reqContent.equals("20")||reqContent.equals("最近论文。")) 
				{
					CoreService strRecPaper = new CoreService();
					Connection con = null;
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/push", "push", "push");
					Statement stmt; //创建声明
			        stmt = con.createStatement();
					String selectSql = "SELECT * FROM cus where openid = '"+fromUserName+"'";  
					ResultSet selectRes = stmt.executeQuery(selectSql);
					selectRes.next();
					String content = selectRes.getString("content");
					respContent = strRecPaper.getRecPaper(content);
					//respWeb = strRecPaper.getRecPaperWeb();
					textMessage.setContent(respContent);
					respMessage = MessageUtil.textMessageToXml(textMessage);
				}
				else if (reqContent.equals("21")||reqContent.equals("今日论文。"))
				{
					CoreService strTodayPaper = new CoreService();
					respContent = strTodayPaper.getTodayPaper();
					respWeb = strTodayPaper.getTodayPaperWeb();
					textMessage.setContent(respContent);
					respMessage = MessageUtil.textMessageToXml(textMessage);
				} 
				else if(reqContent.equals("30")) //最近专利
				{
					CoreService strRecPatent = new CoreService();
					Connection con = null;
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/push", "push", "push");
					Statement stmt; //创建声明
			        stmt = con.createStatement();
					String selectSql = "SELECT * FROM cus where openid = '"+fromUserName+"'";  
					ResultSet selectRes = stmt.executeQuery(selectSql);
					selectRes.next();
					String content = selectRes.getString("content");
					respContent = strRecPatent.getRecPatent(content);
					//respWeb = strRecPatent.getRecPatent();
					textMessage.setContent(respContent);
					respMessage = MessageUtil.textMessageToXml(textMessage);
				}
				else if(reqContent.equals("31")) //今日专利
				{
					CoreService strRecPatent = new CoreService();
					respContent = strRecPatent.getTodayPatent();
					respWeb = strRecPatent.getTodayPatent();
					textMessage.setContent(respContent);
					respMessage = MessageUtil.textMessageToXml(textMessage);
				}
				else if (reqContent.equals("12")||reqContent.equals("图片新闻。"))
				{   
					  Article article1 = new Article();  
	                    List<News> newsList = null;
	                    newsList = NewsDAO.getNewsByTimeRange(SolrConstant.TODAY);
						article1.setTitle("新闻一\n"+newsList.get(0).getTitle());  
	                    article1.setDescription("");  
	                    article1.setPicUrl("http://i2.sinaimg.cn/IT/2014/0325/U5388P2DT20140325183816.jpg");  
	                    article1.setUrl(newsList.get(0).getUrl()); 
	                    String str1 = article1.getPicUrl();
	                    String strT1 = article1.getTitle();
	                    Article article2 = new Article();  
	                    article2.setTitle("新闻二\n"+newsList.get(1).getTitle());  
	                    article2.setDescription("");  
	                    article2.setPicUrl("http://i0.sinaimg.cn/IT/2014/0330/U4672P2DT20140330113904.jpg");  
	                    article2.setUrl(newsList.get(1).getUrl());  
	                    String str2 = article2.getPicUrl();
	                    String strT2 = article2.getTitle();
	                    Article article3 = new Article();  
	                    article3.setTitle("新闻三\n"+newsList.get(2).getTitle());  
	                    article3.setDescription("");  
	                    article3.setPicUrl("http://i1.sinaimg.cn/IT/2014/0330/U4672P2DT20140330104231.jpg");  
	                    article3.setUrl(newsList.get(2).getUrl()); 
	                    String str3 = article3.getPicUrl();
	                    String strT3 = article3.getTitle();
						articleList.add(article1);  
	                    articleList.add(article2);  
	                    articleList.add(article3);  
	                    picurl =  str1 + "</br>"  + str2+ "</br>" + str3+ "</br>";
	                    resp = "图片新闻" + "</br>" + strT1 + "</br>"  + strT2+ "</br>" + strT3+ "</br>";
						newsMessage.setArticleCount(articleList.size());  
	                    newsMessage.setArticles(articleList);  
	                    respMessage = MessageUtil.newsMessageToXml(newsMessage);  
	                   /* Connection con = null;
	    				Class.forName("com.mysql.jdbc.Driver").newInstance();
	    				con = DriverManager.getConnection(
	    						"jdbc:mysql://127.0.0.1:3306/push", "push", "push");
	    				Statement stmt;
	    				stmt = con.createStatement();
	    				stmt.executeUpdate("INSERT INTO pic (picurl, response, reqtime) VALUES ('"
	    						+ picurl
	    						+ "', '"
	    						+ resp
	    						+ "', '"
	    						+ time.format(new Date()) + "')");*/
				}
				else 
				{
					CoreService strCustomize = new CoreService();
					respContent = strCustomize.getCustomizeMenu();
					//respWeb = strSecTodayMenu.getTodayNewsWeb(); //网页
					textMessage.setContent(respContent);
					respMessage = MessageUtil.textMessageToXml(textMessage);
				}
				if(reqContent.equals("12")||reqContent.equals("图片新闻。"))
				{
					 Connection con = null;
	    				Class.forName("com.mysql.jdbc.Driver").newInstance();
	    				con = DriverManager.getConnection(
	    						"jdbc:mysql://127.0.0.1:3306/push", "push", "push");
	    				Statement stmt;
	    				stmt = con.createStatement();
	    				stmt.executeUpdate("INSERT INTO pic (request, picurl, response, reqtime) VALUES ('"
	    						+ reqContent
	    						+ "', '"
	    						+ picurl
	    						+ "', '"
	    						+ resp
	    						+ "', '"
	    						+ time.format(new Date()) + "')");
				}
				else
				{
				Connection con = null;
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				con = DriverManager.getConnection(
						"jdbc:mysql://127.0.0.1:3306/push", "push", "push");
				Statement stmt;
				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO log (request, response, reqtime) VALUES ('"
						+ reqContent
						+ "', '"
						+ respWeb
						+ "', '"
						+ time.format(new Date()) + "')");
				}
			} 
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) 
			{  
				String reqContent = requestMap.get("Recognition").trim();
				respContent = reqContent; 
				voiceMessage.setRecognition(respContent);
				respMessage = MessageUtil.voiceMessageToXml(voiceMessage);
	        }  
			else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT))
			{
				String eventType = requestMap.get("Event");
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) 
				{
					respContent = "欢迎关注GetImpo！发送点东西来了解GetImpo!@黄丽民";
					respWeb = respContent + "</br>";
				} else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) 
				{
					respWeb = "此用户已取消关注!</br>";		
				}
				Connection con = null;
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				con = DriverManager.getConnection(
						"jdbc:mysql://127.0.0.1:3306/push", "push", "push");
				Statement stmt;
				stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO user (username, huifu) VALUES ('"
						+ fromUserName 
						+ "', '"
						+ respWeb + "')");
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}
			else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE))
			{
				respContent = "您发送的是图片！请发送正确的内容。";
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}
			//textMessage.setContent(respContent);
			//respMessage = MessageUtil.textMessageToXml(textMessage);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMessage;
	}
}
