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

public class CoreService {
	SolrDAOImpl solrDAO = new SolrDAOImpl();

	public String getMainMenu() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("���ã���ظ�����ѡ�����").append("\n\n");
		buffer.append("10  ��������").append("\n");
		buffer.append("11  ��������").append("\n");
		buffer.append("20  �������").append("\n");
		buffer.append("21  ��������").append("\n\n");
		return buffer.toString();
	}

	public String getMainMenuWeb() // web
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("���ã���ظ�����ѡ�����").append("\n\n").append("</br>");
		buffer.append("10  ��������").append("\n").append("</br>");
		buffer.append("11  ��������").append("\n").append("</br>");
		buffer.append("20  �������").append("\n").append("</br>");
		buffer.append("21  ��������").append("\n\n").append("</br>");
		return buffer.toString();
	}

	public String getTodayNews() {
		StringBuffer buffer = new StringBuffer();
		List<News> newsList = null;
		try {
			newsList = solrDAO.getResultsByTimeRange(SolrConstant.TODAY, 0, 5,
					"news", News.class);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		buffer.append("�������ţ�").append("\n\n");
		for (int i = 0; i < newsList.size(); i++) {
			String newstitle = newsList.get(i).getTitle();
			String newsurl = newsList.get(i).getUrl();
			Date newsupdatetime = newsList.get(i).getUpdateTime();
			String str = "<a href=\"" + newsurl + "\">" + newstitle
					+ "</a>������ʱ�䣺" + newsupdatetime;
			buffer.append(i + 1).append(".").append(str).append("\n");
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
		buffer.append("�������ţ�").append("\n\n").append("</br>");
		for (int i = 0; i < newsList.size(); i++) {
			String newstitle = newsList.get(i).getTitle();
			String newsurl = newsList.get(i).getUrl();
			Date newsupdatetime = newsList.get(i).getUpdateTime();
			String str = "<a href=\"" + newsurl + "\">" + newstitle
					+ "</a>������ʱ�䣺" + newsupdatetime + "</br>";
			buffer.append(i + 1).append(".").append(str).append("\n");
		}
		return buffer.toString();
	}

	public String getYesNews() throws SolrServerException {
		StringBuffer buffer = new StringBuffer();
		List<News> newsList = solrDAO.getResultsByTimeRange(
				SolrConstant.YESTERDAY, 0, 5, "news", News.class);
		buffer.append("�������ţ�").append("\n\n");
		for (int i = 0; i < newsList.size(); i++) {
			String newstitle = newsList.get(i).getTitle();
			String newsurl = newsList.get(i).getUrl();
			Date newsupdatetime = newsList.get(i).getUpdateTime();
			String str = "<a href=\"" + newsurl + "\">" + newstitle
					+ "</a>������ʱ�䣺" + newsupdatetime;
			buffer.append(i + 1).append(".").append(str).append("\n");
		}
		return buffer.toString();
	}

	public String getYesNewsWeb() throws SolrServerException // web
	{
		StringBuffer buffer = new StringBuffer();
		List<News> newsList = solrDAO.getResultsByTimeRange(
				SolrConstant.YESTERDAY, 0, 5, "news", News.class);
		buffer.append("�������ţ�").append("\n\n").append("</br>");
		for (int i = 0; i < newsList.size(); i++) {
			String newstitle = newsList.get(i).getTitle();
			String newsurl = newsList.get(i).getUrl();
			Date newsupdatetime = newsList.get(i).getUpdateTime();
			String str = "<a href=\"" + newsurl + "\">" + newstitle
					+ "</a>������ʱ�䣺" + newsupdatetime + "</br>";
			buffer.append(i + 1).append(".").append(str).append("\n");
		}
		return buffer.toString();
	}

	public String getRecPaper() throws SolrServerException {
		StringBuffer buffer = new StringBuffer();
		List<Paper> paperList = solrDAO.getResultsByTimeRange(
				SolrConstant.YEAR, 0, 5, "papers", Paper.class);
		buffer.append("������ģ�").append("\n\n");
		for (int i = 0; i < paperList.size(); i++) {
			String papertitle = paperList.get(i).getTitle();
			List<String> paperauthor = paperList.get(i).getAuthor();
			Date paperupdatetime = paperList.get(i).getUpdateTime();
			String paperpath = paperList.get(i).getPath();
			String str = "<a href=\"http://lib.cqvip.com" + paperpath + "\">"
					+ papertitle + "</a>�����ߣ�" + paperauthor + "������ʱ�䣺"
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
		buffer.append("������ģ�").append("\n\n").append("</br>");
		for (int i = 0; i < paperList.size(); i++) {
			String papertitle = paperList.get(i).getTitle();
			List<String> paperauthor = paperList.get(i).getAuthor();
			Date paperupdatetime = paperList.get(i).getUpdateTime();
			String paperpath = paperList.get(i).getPath();
			String str = "<a href=\"http://lib.cqvip.com" + paperpath + "\">"
					+ papertitle + "</a>�����ߣ�" + paperauthor + "������ʱ�䣺"
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
			String str = "���������ĸ��£�";
			buffer.append(str);
		} else {
			buffer.append("�������ģ�").append("\n\n");
			for (int i = 0; i < paperList.size(); i++) {
				String papertitle = paperList.get(i).getTitle();
				List<String> paperauthor = paperList.get(i).getAuthor();
				Date paperupdatetime = paperList.get(i).getUpdateTime();
				String paperpath = paperList.get(i).getPath();
				String str = "<a href=\"http://lib.cqvip.com" + paperpath
						+ "\">" + papertitle + "</a>�����ߣ�" + paperauthor
						+ "������ʱ�䣺" + paperupdatetime;
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
			String str = "���������ĸ��£�";
			buffer.append(str).append("</br>");
		} else {
			buffer.append("�������ģ�").append("\n\n").append("</br>");
			for (int i = 0; i < paperList.size(); i++) {
				String papertitle = paperList.get(i).getTitle();
				List<String> paperauthor = paperList.get(i).getAuthor();
				Date paperupdatetime = paperList.get(i).getUpdateTime();
				String paperpath = paperList.get(i).getPath();
				String str = "<a href=\"http://lib.cqvip.com" + paperpath
						+ "\">" + papertitle + "</a>�����ߣ�" + paperauthor
						+ "������ʱ�䣺" + paperupdatetime + "</br>";
				buffer.append(i + 1).append(".").append(str).append("\n");
			}
		}
		return buffer.toString();
	}
	public static String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			String respContent = "�������쳣�����Ժ��ԣ�";
			String respWeb = null;
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			String fromUserName = requestMap.get("FromUserName");
			String toUserName = requestMap.get("ToUserName");
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
				String reqContent = requestMap.get("Content").trim();
				if (reqContent.equals("10")) {
					CoreService strTodayNews = new CoreService();
					respContent = strTodayNews.getTodayNews();
					respWeb = strTodayNews.getTodayNewsWeb();
				} else if (reqContent.equals("11")) {
					CoreService strYesNews = new CoreService();
					respContent = strYesNews.getYesNews();
					respWeb = strYesNews.getYesNewsWeb();
				} else if (reqContent.equals("20")) {
					CoreService strRecPaper = new CoreService();
					respContent = strRecPaper.getRecPaper();
					respWeb = strRecPaper.getRecPaperWeb();
				} else if (reqContent.equals("21")) {
					CoreService strTodayPaper = new CoreService();
					respContent = strTodayPaper.getTodayPaper();
					respWeb = strTodayPaper.getTodayPaperWeb();
				} else {
					CoreService strMainMenu = new CoreService();
					respContent = strMainMenu.getMainMenu();
					respWeb = strMainMenu.getMainMenuWeb();
				}
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
			else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT))
			{
				String eventType = requestMap.get("Event");
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) 
				{
					respContent = "��ӭ��עGetImpo�����͵㶫�����˽�GetImpo!@������";
					respWeb = respContent + "</br>";
				} else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) 
				{
					respWeb = "���û���ȡ����ע!</br>";		
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
			}
			else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE))
			{
				respContent = "�����͵���ͼƬ���뷢����ȷ�����ݡ�";
			}
			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessage);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMessage;
	}
}
