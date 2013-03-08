<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="<c:url value="/resources/css/main.css" />" />
<title>淘淘兔</title>
</head>
<body>
<div id="all">
<div id="top">
<div id="top-left">欢迎小兔子 ${user.login}</div>
<div id="top-right"></div>
<div id="top-clear"></div>
</div>
<div id="header">
</div>
<div id="main">
<h1>${user.login} 登录成功</h1>
<a href="<c:url value="/" />">回首页</a>
</div>
<%@include file="../footer.jsp" %>
</div>
</body>
</html>