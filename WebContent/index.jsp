<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Image upload file</title>
</head>
<body>
<form action="ImageUpload?action=filesUpload" method="post" enctype="multipart/form-data">
Select Image<input type="file" name="files" multiple/>
<input type="submit" value="upload"/>
<br/>
<a href="${pageContext.request.contextPath}/ImageUpload?action=listingImages">View Images</a>
</form>
</body>
</html>