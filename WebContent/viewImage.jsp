<%@page import="com.deepak.hibernate.entity.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View Image page</title>
</head>
<body>
<%! Files file; String path; %>
<% file=(Files)request.getAttribute("file");
	path=(String)request.getAttribute("path");
	
	
%>
File Id: <%=file.getId() %>| FileName:  <%=file.getFileName() %>
<%
if(file.getLabel()!=null)
{
	out.print("| label : "+file.getLabel());
}
%>
<%
if(file.getCaption()!=null)
{
	out.print("| Caption :  "+file.getCaption());
}
%>

| <a href="${pageContext.request.contextPath}">  Home   </a>
| <a href="${pageContext.request.contextPath}/ImageUpload?action=listingImages">  List Avilable Images </a>
<hr/>
<img  src="<%=path+file.getFileName()%>">

</body>
</html>