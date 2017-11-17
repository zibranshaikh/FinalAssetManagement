<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<p1 align="right">
 <form action="./empop" method="post">
<input type="hidden" value="${user}" name="userid" /> 
<input type="submit" value="Home" name="op" />
<input type="submit" value="Logout" name="op" />
</form>
</p1>
<%@page import="beans.UserAssetRequest,java.util.List" %>
<center>
<h1>View Colleague's Request</h1>
<table border="3">
<tr><th>RequestId</th><th>AssetId</th><th>AssetName</th><th>FromEmp</th><th>Status</th><th>Action</th></tr>
<%
List<UserAssetRequest> ar=(List)request.getAttribute("User");
for(UserAssetRequest cc:ar)
{

%>
<tr>
<form action="./ETransferAssetFromOther" method="post">
           <td><%=(cc.getRequestid())%></td>
           <td> <%=(cc.getAssetid())%></td>
           <td><%=(cc.getAssetname())%></td>
           <td><%=(cc.getFromemp())%></td>
          <td> <%  int st=0;
      String status="";
      st=cc.getStatus(); 
      
      if(st==1)
      {
    	  status="New Request";
      }
       %>
    <%=status%></td> 
 <td><input type="submit" value="Approve" name="op">
 <input type="submit" value="Reject" name="op"></td>
</tr>

<%

}

%>


</form>
</table>

</center>
</body>

</body>
</html>