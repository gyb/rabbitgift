<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="top">
<div id="top-left">欢迎小兔子
<c:choose>
<c:when test="${sessionScope.user == null}">
<a href="<c:url value="/user/login" />">请登录</a> <a href="<c:url value="/user/register" />">注册新用户</a>
</c:when>
<c:otherwise>
${user.login} <a href="<c:url value="/user/logout" />">退出</a>
<a href="<c:url value="/account" />">我的账户</a>
<!-- 
<a href="<c:url value="/message" />">我的消息</a>
 -->
</c:otherwise>
</c:choose>
</div>
<div id="top-right"><a href="<c:url value="/myttt" />">我是买家</a> <a href="<c:url value="/myshop" />">我是卖家</a></div>
<div id="top-clear"></div>
</div>