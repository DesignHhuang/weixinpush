<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>显示</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body>
  <%
	try 
	{
   		Connection con = null; 
   		Class.forName("com.mysql.jdbc.Driver").newInstance(); 
    	con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/push", "push", "push");
    	Statement stmt; 
    	stmt = con.createStatement();
      	String selectSql = "SELECT * FROM log";
        ResultSet selectRes = stmt.executeQuery(selectSql);
        while (selectRes.next())
        { 
        	String req = selectRes.getString("request");
           	String resp = selectRes.getString("response");
            Date reqtime = selectRes.getDate("reqtime");
            out.write("<strong><font color=red>请求:</font></strong>"+req+"。<strong><font color=red>时间:</font></strong>"+reqtime);
            out.write("</br>");
            out.write("<strong><font color=blue>回复：</font></strong>"+resp);
            out.write("</br>");
       	}
	} 
   	catch (Exception e) 
   	{
		System.out.print("MYSQL");
   	}
  %>
  </body>
</html>
