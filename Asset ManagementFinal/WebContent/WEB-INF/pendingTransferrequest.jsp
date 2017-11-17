<p1 align="right">
 <form action="./managerop" method="post">
<input type="hidden" value="${user}" name="eid" /> 
<input type="submit" value="Home" name="op" />
<input type="submit" value="Logout" name="op" />
</form>
</p1>
<h1>Asset Requests</h1>
<h2>${msg1}</h2>
<h2>${msg2}</h2>
<table border="5">
<%@page import="java.util.ArrayList,beans.UserAssetRequest" %>
<tr><th>Request ID </th><th>Request From</th><th>Request To</th><th>Asset Id</th><th>Asset Name</th><th>Request date</th><th>Request Status</th><th>Action</th></tr>
<%
ArrayList<UserAssetRequest> ar=(ArrayList<UserAssetRequest>)request.getAttribute("LIST");
for(UserAssetRequest cc:ar)
{
%>
<form action="./MApproveTransferReq" method="post">
<tr>
<td><%=(cc.getRequestid())%></td>
<td><%=(cc.getFromemp())%></td>
<td><%=(cc.getToemp())%></td>
<td><input type="text" value="<%=(cc.getAssetid())%>" name="assetid" readonly /></td>
<input type="hidden" value="<%=(cc.getRequestid())%>" name="requestid" readonly />
<input type="hidden" value="<%=(cc.getFromemp())%>" name="fromemp" readonly />
<input type="hidden" value="<%=(cc.getToemp())%>" name="toemp" readonly />

<td><%=(cc.getAssetname())%></td>
<td><%=(cc.getRequestdate())%></td>
<%  int st=0;
      String status="";
      st=cc.getStatus(); 
      if(st==0)
      {
    	  status="New Request";
      }
      %>
    <td><%=status%></td>  
<td><input type="submit" value="Approve" name="op" /><input type="submit" value="Reject" name="op" />
</td>
</tr>
</form>
</tr>
<% 
}
%>
</table>
</pre>
</center>
</body>
</html>