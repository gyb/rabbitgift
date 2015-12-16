<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
  <input size="30" name="goods" value=""/> <input type="button" value="搜索商品"/>
</div>
<div id="main">
<div class="title">订单支付完成</div>
<div class="content">
由于我们采用了异步和事件驱动的架构，所以现在不知道你支付成功了没有。<br/>
请检查一下订单状态，如果没有支付成功，请再次支付。<br/>
<a href="<c:url value="/myttt/orders"/>">我的订单</a>
</div>
</div>
<%@include file="../footer.jsp" %>
</div>
</body>
</html>