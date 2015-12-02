<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
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
<jsp:include page="top.jsp"/>
<div id="header">
  <input size="30" name="goods" value=""/> <input type="button" value="搜索商品"/>
</div>
<div id="main">
<div id="navigation">
<p>商品分类 &gt;&gt;</p>
<ul>
<c:forEach items="${category}" var="cat">
<!-- 
<li><a href="#">${cat.key} - ${cat.value}</a></li>
 -->
 <li>${cat.key} - ${cat.value}</li>
</c:forEach>
</ul>

</div>
<div id="content">
<div class="title">商品精选</div>
<ol id="goodslist">
<c:forEach items="${page.content}" var="goods">
	<li><a href="<c:url value="/goods/${goods.id}" />"><img src="<c:url value="${goods.picUrl}" />" height="130" width="130"/><br/><span>${goods.name}</span></a></li>
</c:forEach>
</ol>
<jsp:include page="page.jsp" />
</div>
</div>
<%@include file="footer.jsp" %>
</div>
</body>
</html>