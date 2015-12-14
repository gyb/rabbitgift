<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="navigation">
<p>我的淘淘兔&gt;&gt;</p>
<ul>
<li><a href="<c:url value="/myttt/address" />">我的地址</a></li>
<li><a href="<c:url value="/myttt/orders" />">我买到的商品</a></li>
</ul>
</div>