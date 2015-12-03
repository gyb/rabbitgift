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
<script src="<c:url value="/resources/js/jquery-1.7.1.min.js" />" type="text/javascript"></script>
<script>
$(function() {
	getRating("<c:url value="/goods/${goods.id}/rating/page"/>", 1);
});
var getRating = function(url, pageNo) {
	$.getJSON(url + "?page=" + (pageNo-1), function(data) {
		$("#rating_list").empty();
		$.each(data.content, function(k, v) {
			var buydate = new Date(v.buyTime).toLocaleDateString();
			var ratedate = new Date(v.ratingTime).toLocaleDateString();
			var rating = "<tr>" + "<td><span class=\"stars stars-" + v.number + "\"></span></td><td>" + ratedate + "</td><td>" + v.user.login + "</td><td>" + v.comment + "</td><td>" + v.buyPrice + "元</td><td>" + buydate + "</td></tr>";
			$("#rating_list").append(rating);
		});
		$("#page").empty();
		var page = " 总" + data.totalPages + "页 第<input id='currentPageNo' value='" + (data.number+1) + "'/>页 <input type='button' value='go' onclick='gotoPage()'/> ";
		if (!data.first) $("#page").append("<a href='#' onclick='getRating(\"" + url + "\"," + data.number + ")'>&lt;&lt;上页</a>");
		$("#page").append(page);
		if (!data.last) $("#page").append("<a href='#' onclick='getRating(\"" + url + "\"," + (data.number+2) + ")'>下页&gt;&gt;</a>");
	});
};
var gotoPage = function() {
	var gotoNo = document.getElementById("currentPageNo").value;
	getRating("<c:url value="/goods/${goods.id}/rating/page/"/>", gotoNo);
};
</script>
</head>
<body>
<div id="all">
<jsp:include page="../top.jsp"/>
<div id="header">
  <input size="30" name="goods" value=""/> <input type="button" value="搜索商品"/>
</div>
<div id="main">
<div id="navigation">
<div>
<p>卖家：${seller.login} <a href="#">发消息</a></p>
<p><input size="15" name="goods" value=""/> <input type="button" value="搜索店内商品"/></p>
</div>
</div>
<div id="content">
<div class="title">${goods.name}</div>
<div class="content">
<div id="goods_pic"><img src="<c:url value="${goods.picUrl}" />" height="130" width="130"/></div>
<div id="rating_div">
<c:choose>
<c:when test="${goods.averageRating>4.7}"><span class="stars large" title="5星">5星</span></c:when>
<c:when test="${goods.averageRating>4.2}"><span class="stars large stars-4h" title="4星半">4星半</span></c:when>
<c:when test="${goods.averageRating>3.7}"><span class="stars large stars-4" title="4星">4星</span></c:when>
<c:when test="${goods.averageRating>3.2}"><span class="stars large stars-3h" title="3星半">3星半</span></c:when>
<c:when test="${goods.averageRating>2.7}"><span class="stars large stars-3" title="3星">3星</span></c:when>
<c:when test="${goods.averageRating>2.2}"><span class="stars large stars-2h" title="2星半">2星半</span></c:when>
<c:when test="${goods.averageRating>1.7}"><span class="stars large stars-2" title="2星">2星</span></c:when>
<c:when test="${goods.averageRating>1.2}"><span class="stars large stars-1h" title="1星半">1星半</span></c:when>
<c:when test="${goods.averageRating>0.7}"><span class="stars large stars-1" title="1星">1星</span></c:when>
<c:otherwise><span class="stars large stars-0" title="0星">0星</span></c:otherwise>
</c:choose>
评分 ${goods.averageRating}（${goods.ratingTimes}人评价）
  <ul data-total="${goods.ratingTimes}" class="grouped_ratings">
    <li class="c">
      <span title="评分 5，满分5星" class="stars stars-5">评分 5，满分5星</span>
      <div data-num-reviews="${goods.ratingStars5}" class="rating_bar">
        <span style="width:${goods.ratingStars5*100/goods.ratingTimes}%" class="bar">
          <span class="num_ratings">${goods.ratingStars5}</span>
        </span>
      </div>
    </li>
    <li class="c">
      <span title="评分 4，满分5星" class="stars stars-4">评分 4，满分5星</span>
      <div data-num-reviews="${goods.ratingStars4}" class="rating_bar">
        <span style="width:${goods.ratingStars4*100/goods.ratingTimes}%" class="bar">
          <span class="num_ratings">${goods.ratingStars4}</span>
        </span>
      </div>
    </li>
    <li class="c">
      <span title="评分 3，满分5星" class="stars stars-3">评分 3，满分5星</span>
      <div data-num-reviews="${goods.ratingStars3}" class="rating_bar">
        <span style="width:${goods.ratingStars3*100/goods.ratingTimes}%" class="bar">
          <span class="num_ratings">${goods.ratingStars3}</span>
        </span>
      </div>
    </li>
    <li class="c">
      <span title="评分 2，满分5星" class="stars stars-2">评分 2，满分5星</span>
      <div data-num-reviews="${goods.ratingStars2}" class="rating_bar">
        <span style="width:${goods.ratingStars2*100/goods.ratingTimes}%" class="bar">
          <span class="num_ratings">${goods.ratingStars2}</span>
        </span>
      </div>
    </li>
    <li class="c">
      <span title="评分 1，满分5星" class="stars stars-1">评分 1，满分5星</span>
      <div data-num-reviews="${goods.ratingStars1}" class="rating_bar">
        <span style="width:${goods.ratingStars1*100/goods.ratingTimes}%" class="bar">
          <span class="num_ratings">${goods.ratingStars1}</span>
        </span>
      </div>
    </li>
  </ul>
</div>
<div>
<p>
类别：${category}<br/>
价格：${goods.price}元<br/>
剩余数量：${goods.availableNumber}个<br/>
已售出：${goods.selledNumber}个
<c:if test="${goods.state!='CREATED'}">
<br/>上架时间: <fmt:formatDate value="${goods.onlineTime}" type="both"/>
</c:if>
</p>
<p>
<c:choose>
<c:when test="${goods.state=='ONLINE'}">
<input type="button" value="我要购买" onclick="location.href='<c:url value="/order/buy/"/>${goods.id}'"/>
</c:when>
<c:when test="${goods.state=='OFFLINE'}">
已经在<fmt:formatDate value="${goods.offlineTime}" type="both"/>下架
</c:when>
<c:otherwise>
还没上架呢
</c:otherwise>
</c:choose>
</p>
</div>
<div id="goods_description">${goods.description}</div>
</div>
<hr/>
<div class="title">商品评价</div>
<table>
<tr><th>星级</th><th>评价日期</th><th>用户</th><th>评论</th><th>购买价格</th><th>购买日期</th></tr>
<tbody id="rating_list">
</tbody>
</table>
<div id="page"></div>
</div>
</div>
<%@include file="../footer.jsp" %>
</div>
</body>
</html>