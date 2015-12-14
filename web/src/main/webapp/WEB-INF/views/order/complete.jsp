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
<script src="<c:url value="/resources/js/jquery-1.7.1.min.js" />" type="text/javascript"></script>
</head>
<body>
<div id="all">
<jsp:include page="../top.jsp"/>
<div id="header">
  <input size="30" name="goods" value=""/> <input type="button" value="搜索商品"/>
</div>
<div id="main">
<div class="title"><h2 class="process">确认订单 &gt;&gt; 付款 &gt;&gt; <span class="strong">确认收货</span> &gt;&gt; 评价</h2></div>
<div class="content">
<fieldset>
<legend>订单信息</legend>
<div class="content">
<table>
<tr><th>发货时间</th><th colspan="2">商品</th><th>单价</th><th>数量</th><th>合计</th><th>收货地址</th></tr>
<tr>
<td><fmt:formatDate value="${order.lastUpdateTime}" type="both"/></td>
<td><a href="<c:url value="/goods/${goods.id}" />"/>${goods.name}</a></td>
<td><img height="130" width="130" src="<c:url value="${goods.picUrl}" />"/></td>
<td>${goods.price}元</td>
<td>${order.num}个</td>
<td>${order.money}元</td>
<td>${order.address} ${order.receiverName} ${order.phone}</td>
</tr>
</table>
</div>
</fieldset>
<br/>
<c:url value="/order/complete/${order.id}" var="complete"/>
<form method="post" action="${complete}">
<input type="submit" value="确认收货" />
注意：如果您还没收到货，请不要点击确认，否则可能财货两空！
</form>
</div>
</div>
<%@include file="../footer.jsp" %>
</div>
</body>
</html>