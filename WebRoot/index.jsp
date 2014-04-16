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
	<style type="text/css">
		.right{margin-left:665px;}
 		.left{width:665px;float:left;border-style: solid;border-color:white;border-right-color: black;}
 		.right .button{float:button;border-style: solid;border-color:white;border-top-color: black;}
  	</style>
  </head>
  
  <body>
  <div>
  <div class="left">
       <%
	try 
	{
   		Connection con = null; 
   		Class.forName("com.mysql.jdbc.Driver").newInstance(); 
    	con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/push", "push", "push");
    	Statement stmt; 
    	stmt = con.createStatement();
      	String selectSql = "SELECT * FROM log order by id desc";
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
  </div>
   <div class="right">
   <div class="top">
       <%
	try 
	{
   		Connection con = null; 
   		Class.forName("com.mysql.jdbc.Driver").newInstance(); 
    	con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/push", "push", "push");
    	Statement stmt; 
    	stmt = con.createStatement();
      	String selectSql = "SELECT * FROM user order by id desc";
        ResultSet selectWel = stmt.executeQuery(selectSql);
        while (selectWel.next())
        { 
        	String username = selectWel.getString("username");
           	String wel = selectWel.getString("huifu");
            out.write("<strong><font color=red>用户open_ID:</font></strong>"+username);
            out.write("</br>");
            out.write(wel);
            out.write("</br>");
       	}
	} 
   	catch (Exception e) 
   	{
		System.out.print("MYSQL");
   	}
  %>
  </div>
  <div class="button">
        <%
	try 
	{
   		Connection con = null; 
   		Class.forName("com.mysql.jdbc.Driver").newInstance(); 
    	con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/push", "push", "push");
    	Statement stmt; 
    	stmt = con.createStatement();
      	String selectSql = "SELECT * FROM pic order by id desc";
        ResultSet selectpic = stmt.executeQuery(selectSql);
        while (selectpic.next())
        { 
        	String req = selectpic.getString("request");
        	String picurl = selectpic.getString("picurl");
           	String resp = selectpic.getString("response");
           	Date reqtime = selectpic.getDate("reqtime");
           	out.write("<strong><font color=red>请求:</font></strong>"+req+"。<strong><font color=red>时间:</font></strong>"+reqtime);
            out.write("</br>");
            out.write("<strong><font color=blue>图片地址:</font></strong>"+"</br>"+picurl);
            out.write("<strong><font color=blue>内容：</font></strong>"+resp);
            out.write("</br>");
       	}
	} 
   	catch (Exception e) 
   	{
		System.out.print("MYSQL");
   	}
  %>
  </div>
   </div>
 </div>

  </body>
</html>
