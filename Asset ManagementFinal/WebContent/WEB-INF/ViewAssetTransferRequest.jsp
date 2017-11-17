<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<%@page import="beans.UserAssetRequest,java.util.List" %>
<body>
<p1 align="right">
 <form action="./empop" method="post">
<input type="hidden" value="${user}" name="userid" /> 
<input type="submit" value="Home" name="op" />
<input type="submit" value="Logout" name="op" />
</form>
</p1>

<center>
<h1>View Asset Transfer Request</h1>
<table border="3">
<tr><th>RequestId</th><th>AssetId</th><th>AssetName</th><th>FromEmp</th><th>ToEmp</th><th>Status</th></tr>
<%
List<UserAssetRequest> ar=(List)request.getAttribute("User");
for(UserAssetRequest cc:ar)
{

%>
<tr>
           <td><%=(cc.getRequestid())%></td>
           <td> <%=(cc.getAssetid())%></td>
           <td><%=(cc.getAssetname())%></td>
           <td><%=(cc.getFromemp())%></td>
           <td><%=(cc.getToemp())%></td>
          <td> <%  int st=0;
      String status="";
      st=cc.getStatus(); 
      if(st==0)
      {
    	  status="Pending With Manager";
      }
      else if(st==1)
      {
    	  status="Approved By Manager";
      }
      else if(st==2)
      {
    	  status="Cancelled By Manager";
      }
      else if(st==3)
      {
    	  status="Accept By "+(cc.getToemp());
      }
      else if(st==4)
      {
    	  status="Cancelled By "+(cc.getToemp());
      }
      %>
    <%=status%></td> 
 
</tr>

<%

}

%>



</table>
</center>
</body>
</html>