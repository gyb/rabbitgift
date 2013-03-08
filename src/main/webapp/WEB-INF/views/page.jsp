<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>
function gotoPage() {
	var url = "<c:url value="${page.pageUrl}" />";
	location.href=url + document.getElementById("currentPageNo").value;
}
</script>
<div id="page">
<c:if test="${page.hasPrev}"><a href="<c:url value="${page.pageUrl}${page.prevNo}" />">&lt;&lt;上页</a></c:if>
 总${page.total}页 第<input value="${page.pageNo}" id="currentPageNo"/>页 <input type="button" value="go" onclick="gotoPage();"/>
<c:if test="${page.hasNext}"><a href="<c:url value="${page.pageUrl}${page.nextNo}" />">下页&gt;&gt;</a></c:if>
</div>