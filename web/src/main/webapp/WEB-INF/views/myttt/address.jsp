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
<script src="<c:url value="/resources/js/jquery-1.7.1.min.js" />" type="text/javascript"></script>
<script>
$(function() {
	$("#add_show").click(function() {
		$(this).hide();
		$("#add_field").show();
	});
	$("#add_cancel").click(function() {
		$("#add_field").hide();
		$("#add_show").show();
	});
	$(".del_addr").click(function() {
		if (confirm("确定要删除这个地址吗？")) {
			location.href="<c:url value="/myttt/deleteAddress/" />" + $(this).next().val();
		}
	});
});
</script>
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
<div class="title">地址管理</div>
<div class="content">
<input id="add_show" type="button" value="添加新地址"/>
<fieldset id="add_field" class="address" style="display:none;">
<legend>添加新地址</legend>
<div class="content">
<form:form modelAttribute="address" action="address" method="post">
<input type="hidden" name="userId" value="${sessionScope.user.id}"/>
<form:errors path="" />
<p>
<form:label for="receiverName" path="receiverName" cssErrorClass="error">姓名</form:label>
<form:input path="receiverName" size="15"/><br/><form:errors path="receiverName" />
</p>
<p>
<form:label for="address" path="address" cssErrorClass="error">地址</form:label>
<form:input path="address" size="60"/><br/><form:errors path="address" />
</p>
<p>
<form:label for="phone" path="phone" cssErrorClass="error">手机</form:label>
<form:input path="phone" size="15"/><br/><form:errors path="phone" />
</p>
<p><input type="submit" value="添加地址"/> <input id="add_cancel" type="button" value="取消"/></p>
</form:form>
</div>
</fieldset>
<c:forEach items="${addressList}" var="address" varStatus="status">
<fieldset class="address">
<legend>地址${status.count}</legend>
<div class="content">
<input class="del_addr" type="button" value="删除地址" />
<input type="hidden" value="${address.id}"/>
<p>姓名: ${address.receiverName}</p>
<p>地址: ${address.address}</p>
<p>手机: ${address.phone}</p>
</div>
</fieldset>
</c:forEach>
</div>
</div>
</div>
<%@include file="../footer.jsp" %>
</div>
</body>
</html>