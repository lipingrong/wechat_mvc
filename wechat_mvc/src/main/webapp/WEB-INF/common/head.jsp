<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
	function createUrl(url){
		return "<%=path%>" + "/" + url;
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../js/jquery-3.0.0.js"></script>
<title>Insert title here</title>
</head>
<body>
中文
</body>
</html>