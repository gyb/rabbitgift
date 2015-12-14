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
<jsp:include page="../top.jsp"/>
<div id="header">
  <input size="30" name="goods" value=""/> <input type="button" value="搜索本店商品"/>
</div>
<div id="main">
<%@include file="nav.jsp" %>
<div id="content">
<div class="title">${goods.name}</div>
<div class="content">
<div id="goods_pic"><img src="<c:url value="${goods.picUrl}" />" height="130" width="130"/></div>
<div>
<p>
类别：${category}<br/>
价格：${goods.price}元<br/>
数量：${goods.availableNumber}个
</p>
<p><input type="button" value="上架" onclick="location.href='<c:url value="/myshop/putOnline/"/>${goods.id}'"/></p>
</div>
<div id="goods_description">${goods.description}</div>
</div>

</div>
</div>
<%@include file="../footer.jsp" %>
</div>
</body>
</html>