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
	$("form").attr("action", "<c:url value="/order/create"/>"); //I can't understand spring mvc form action attribute. Here is a hack method
	$("#num").keyup(function() {
		$("#total").text($("#num").val() * <c:out value="${goods.price}"/>); 
	});
	$("input[name=address]:radio").click(function() {
		$("#receiverName").val($(this).data("receiverName"));
		$("#address").val($(this).data("address"));
		$("#phone").val($(this).data("phone"));
	});
	<c:forEach items="${addressList}" var="address">
	var address = $("#address_<c:out value="${address.id}"/>");
	address.data("receiverName", "<c:out value="${address.receiverName}"/>");
	address.data("address", "<c:out value="${address.address}"/>");
	address.data("phone", "<c:out value="${address.phone}"/>");
	</c:forEach>
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
<div class="title"><h2 class="process"><span class="strong">确认订单</span> &gt;&gt; 付款 &gt;&gt; 确认收货 &gt;&gt; 评价</h2></div>
<div class="content">
<fieldset>
<legend>选择一个地址</legend>
<div class="content">
<c:forEach items="${addressList}" var="address">
<input id="address_${address.id}" name="address" type="radio"/> ${address.address} ${address.receiverName} ${address.phone}<br/>
</c:forEach>
<p><a href="<c:url value="/myttt/address"/>">添加新的地址</a></p>
</div>
</fieldset>
<br/>
<form:form modelAttribute="order" method="post">
<form:hidden path="buyer.id"/>
<form:hidden path="goods.id"/>
<form:hidden path="receiverName"/>
<form:hidden path="address"/>
<form:hidden path="phone"/>
<form:errors path="" />
<fieldset>
<legend>确认订单信息</legend>
<div class="content">
<table width="80%">
<tr><th colspan="2">商品</th><th>单价</th><th>数量</th><th>合计</th></tr>
<tr>
<td><a href="<c:url value="/goods/${goods.id}" />"/>${goods.name}</a></td>
<td><img height="130" width="130" src="<c:url value="${goods.picUrl}" />"/></td>
<td>${goods.price}元</td>
<td><form:input path="num" id="num" size="5"/>个</td>
<td><span id="total">${goods.price}</span>元</td>
</tr>
</table>
</div>
</fieldset>
<br/>
<input type="submit" value="确认订单" />
</form:form>
</div>
</div>
<%@include file="../footer.jsp" %>
</div>
</body>
</html>