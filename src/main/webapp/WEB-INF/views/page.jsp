<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="page">
<c:if test="${!page.first}"><a href="#" onclick="gotoPage(${page.number-1})">&lt;&lt;上页</a></c:if>
 总${page.totalPages}页 第<input value="${page.number}" id="currentPageNo"/>页 <input type="button" value="go" onclick="gotoCurrentPage();"/>
<c:if test="${!page.last}"><a href="#" onclick="gotoPage(${page.number+1})">下页&gt;&gt;</a></c:if>
</div>
<script>
function gotoCurrentPage() {
	gotoPage($("#currentPageNo").val());
}
function gotoPage(page) {
	changeParameters({"page":page})
}
function changeParameters(params) {
	/*
	 * queryParameters -> handles the query string parameters
	 * queryString -> the query string without the fist '?' character
	 * re -> the regular expression
	 * m -> holds the string matching the regular expression
	 */
	var queryParameters = {}, queryString = location.search.substring(1),
	    re = /([^&=]+)=([^&]*)/g, m;
	 
	// Creates a map with the query string parameters
	while (m = re.exec(queryString)) {
	    queryParameters[decodeURIComponent(m[1])] = decodeURIComponent(m[2]);
	}
	 
	// Add new parameters or update existing ones
	for (key in params) {
		queryParameters[key] = params[key];
	}
	 
	/*
	 * Replace the query portion of the URL.
	 * jQuery.param() -> create a serialized representation of an array or
	 *     object, suitable for use in a URL query string or Ajax request.
	 */
	location.search = $.param(queryParameters); // Causes page to reload
}
</script>