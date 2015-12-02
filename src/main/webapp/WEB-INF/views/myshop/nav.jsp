<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="navigation">
<p>我的店铺 &gt;&gt;</p>
<ul>
<li><a href="<c:url value="/myshop/onlinePage" />">热卖中商品</a></li>
<li><a href="<c:url value="/myshop/createdPage" />">未上架商品</a></li>
<li><a href="<c:url value="/myshop/offlinePage" />">已下架商品</a></li>
<li><a href="<c:url value="/myshop/upload" />">上传新商品</a></li>
<li><a href="<c:url value="/myshop/orders" />">我的订单</a></li>
</ul>
</div>