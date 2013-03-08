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
<script>
$(function() {
	$(".ratingwidget label").hover(function() {
		$(this).parent().attr("class", "ratingwidget stars stars-" + $(this).children("input").val()); 
	}, function() {
		var select = $(this).parent().children().children(":checked").val();
		if (!select) select = 0;
		$(this).parent().attr("class", "ratingwidget stars stars-" + select); 
	});
	$(".ratingwidget label").click(function() {
		$(this).children("input").select();
	});
});
</script>
</head>
<body>
<div id="all">
<jsp:include page="../top.jsp"/>
<div id="header">
  <input size="30" name="goods" value=""/> <input type="button" value="搜索商品"/>
</div>
<div id="main">
<div class="title"><h2 class="process">确认订单 &gt;&gt; 付款 &gt;&gt; 确认收货 &gt;&gt; <span class="strong">评价</span></h2></div>
<div class="content">
<fieldset>
<legend>订单信息</legend>
<div class="content">
<table>
<tr><th>收货时间</th><th colspan="2">商品</th><th>单价</th><th>数量</th><th>合计</th><th>收货地址</th></tr>
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
<p>请对您购买的商品进行评价</p>
<c:url value="/order/rate" var="rateUrl"/>
<form:form modelAttribute="rating" method="post" action="${rateUrl}">
<form:errors path="" />
<form:hidden path="userId" />
<form:hidden path="orderId" />
<form:hidden path="goodsId" />
<p>
<label for="number">星级</label><br/>
<span class="ratingwidget stars stars-0"><label>1 星<input type="radio" value="1" name="number"></label><label>2 星<input type="radio" value="2" name="number"></label><label>3 星<input type="radio" value="3" name="number"></label><label>4 星<input type="radio" value="4" name="number"></label><label>5 星<input type="radio" value="5" name="number"></label></span>
<form:errors path="number" />
</p>
<p>
<label for="comment">对商品说点什么吧……</label><br/>
<form:textarea path="comment" />
</p>
<input type="submit" value="评价" />
</form:form>
</div>
</div>
<%@include file="../footer.jsp" %>
</div>
</body>
</html>