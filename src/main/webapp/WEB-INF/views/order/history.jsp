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
<div class="title">订单处理过程</div>
<div class="content">
<fieldset>
<legend>订单信息</legend>
<div class="content">
<table>
<tr><th>编号</th><th>订单状态</th><th>最后处理时间</th><th>买家</th><th>卖家</th><th colspan="2">商品</th><th>单价</th><th>数量</th><th>合计</th><th>收货地址</th></tr>
<tr>
<td>${order.id}</td>
<td><c:choose><c:when test="${order.state=='CREATED'}">未支付</c:when>
<c:when test="${order.state=='PAYED'}">已支付</c:when>
<c:when test="${order.state=='DELIVERED'}">已发货</c:when>
<c:when test="${order.state=='RECEIVED'}">已收货</c:when>
<c:when test="${order.state=='COMPLETED'}">已完成</c:when>
<c:when test="${order.state=='CANCELED'}">已取消</c:when>
<c:when test="${order.state=='REFUNDED'}">已退款</c:when></c:choose></td>
<td><fmt:formatDate value="${order.lastUpdateTime}" type="both"/></td>
<td>${order.buyer.login}</td>
<td>${order.seller.login}</td>
<td><a href="<c:url value="/goods/${order.goods.id}" />"/>${order.goods.name}</a></td>
<td><img height="130" width="130" src="<c:url value="${order.goods.picUrl}" />"/></td>
<td>${order.goods.price}元</td>
<td>${order.num}个</td>
<td>${order.money}元</td>
<td>${order.address} ${order.receiverName} ${order.phone}</td>
</tr>
</table>
</div>
</fieldset>
<br/>
<fieldset>
<legend>处理过程</legend>
<c:forEach items="${history}" var="history">
<p><fmt:formatDate value="${history.time}" type="both" /> ${history.user.login}
<c:choose>
<c:when test="${history.type=='CREATE'}">下单</c:when>
<c:when test="${history.type=='PAY'}">支付了订单</c:when>
<c:when test="${history.type=='DELIVER'}">发货</c:when>
<c:when test="${history.type=='RECEIVE'}">确认收货</c:when>
<c:when test="${history.type=='COMPLETE'}">评价商品，订单完成</c:when>
<c:when test="${history.type=='CANCEL'}">取消交易，关闭了订单</c:when>
<c:when test="${history.type=='REFUND'}">取消交易，退款给买家</c:when>
</c:choose>
</p>
</c:forEach>
</fieldset>
</div>
</div>
<%@include file="../footer.jsp" %>
</div>
</body>
</html>