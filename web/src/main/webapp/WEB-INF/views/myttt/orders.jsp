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
  <input size="30" name="goods" value=""/> <input type="button" value="搜索商品"/>
</div>
<div id="main">
<%@include file="nav.jsp" %>
<div id="content">
<div class="title">我买到的商品</div>
<div class="content">
<table>
<thead><tr><th>编号</th><th>状态</th><th>时间</th><th>卖家</th><th colspan="2">商品</th><th>单价</th><th>个数</th><th>总计</th><th>收货信息</th><th>操作</th></tr></thead>
<tbody>
<c:forEach items="${page.content}" var="order">
<tr>
<td><a href="<c:url value="/order/history/${order.id}"/>">${order.id}</a></td>
<td><c:choose><c:when test="${order.state=='CREATED'}">待确认</c:when>
<c:when test="${order.state=='CONFIRMED'}">待支付</c:when>
<c:when test="${order.state=='PAYED'}">待发货</c:when>
<c:when test="${order.state=='DELIVERED'}">已发货</c:when>
<c:when test="${order.state=='RECEIVED'}">已收货</c:when>
<c:when test="${order.state=='COMPLETED'}">已完成</c:when>
<c:when test="${order.state=='CANCELED'}">已取消</c:when>
<c:when test="${order.state=='REFUNDED'}">已退款</c:when></c:choose></td>
<td><fmt:formatDate value="${order.lastUpdateTime}" type="both"/></td>
<td>${order.seller.login}</td>
<td><c:url value="${order.goods.picUrl}" var="picUrl"/><img src="${picUrl}" height="65" width="65"/></td>
<td><c:url value="/goods/${order.goods.id}" var="goodsUrl"/><a href="${goodsUrl}"/>${order.goods.name}</a></td>
<td>${order.goods.price}元</td>
<td>${order.num}个</td>
<td>${order.money}元</td>
<td>${order.address} ${order.receiverName} ${order.phone}</td>
<td>
<c:choose><c:when test="${order.state=='CONFIRMED'}"><input type="button" value="支付" onclick="location.href='<c:url value="/order/pay/${order.id}"/>'"/></c:when>
<c:when test="${order.state=='DELIVERED'}"><input type="button" value="确认收货" onclick="location.href='<c:url value="/order/receive/${order.id}"/>'"/></c:when>
<c:when test="${order.state=='RECEIVED'}"><input type="button" value="评价" onclick="location.href='<c:url value="/order/rate/${order.id}"/>'"/></c:when>
</c:choose>
</td>
</tr>
</c:forEach>
</tbody>
</table>
<jsp:include page="../page.jsp" />
</div>
</div>
</div>
<%@include file="../footer.jsp" %>
</div>
</body>
</html>