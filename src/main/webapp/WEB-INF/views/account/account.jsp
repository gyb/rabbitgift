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
<title>淘淘兔</title>
<script>
$(function() {
	$("#deposit").click(function() {
		location.href="<c:url value="/account/deposit/"/>" + $("#deposit_num").val();
	});
	$("#withdraw").click(function() {
		location.href="<c:url value="/account/withdraw/"/>" + $("#withdraw_num").val();
	});
});
</script>
</head>
<body>
<div id="all">
<jsp:include page="../top.jsp"/>
<div id="header">
</div>
<div id="main">
<h1>我的账户</h1>
<p>账户余额：${account.totalBalance}元</p>
<p>可用余额：${account.availableBalance}元</p>
<p></p>
<p><input id="deposit_num" size="10"/>元 <input id="deposit" type="button" value="存入"/></p>
<p><input id="withdraw_num" size="10"/>元 <input id="withdraw" type="button" value="取现"/></p>
</div>
<%@include file="../footer.jsp" %>
</div>
</body>
</html>