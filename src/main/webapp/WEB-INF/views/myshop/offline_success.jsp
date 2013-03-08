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
<jsp:include page="../top.jsp"/>
<div id="header">
  <input size="30" name="goods" value=""/> <input type="button" value="搜索本店商品"/>
</div>
<div id="main">
<%@include file="nav.jsp" %>
<div id="content">
<div class="title">${goods.name} 已下架</div>
<div class="content"><img src="<c:url value="${goods.picUrl}"/>"/></div>
<a href="<c:url value="/myshop" />">回到店铺</a>
</div>
</div>
<%@include file="../footer.jsp" %>
</div>
</body>
</html>