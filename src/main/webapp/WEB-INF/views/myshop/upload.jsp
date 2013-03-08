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
</head>
<body>
<div id="all">
<jsp:include page="../top.jsp"/>
<div id="header">
  <input size="30" name="goods" value=""/> <input type="button" value="搜索本店商品"/>
</div>
<div id="main">
<%@include file="nav.jsp" %>
<div id="content">
<div class="title">上传新商品</div>
<div class="content">
<form:form modelAttribute="goods" action="upload" method="post" enctype="multipart/form-data">
<form:errors path="" />
<p>
<form:label for="categoryId" path="categoryId" cssErrorClass="error">商品分类</form:label><br/>
<form:select path="categoryId" items="${category}">
</form:select>

</p>
<p>
<form:label for="name" path="name" cssErrorClass="error">商品名称</form:label><br/>
<form:input path="name" /><form:errors path="name" />
</p>
<p>
<form:label for="description" path="description" cssErrorClass="error">商品描述</form:label><br/>
<form:textarea path="description"/><form:errors path="description" />
</p>
<p>
<form:label for="price" path="price" cssErrorClass="error">商品价格</form:label><br/>
<form:input path="price"/> 元 <form:errors path="price" />
</p>
<p>
<form:label for="availableNumber" path="availableNumber" cssErrorClass="error">库存数量</form:label><br/>
<form:input path="availableNumber"/><form:errors path="availableNumber" />
</p>
<p>
<label for="image">上传图片</label><br/>
<input name="image" type="file"/>
</p>
<form:hidden path="userId"/>
<input type="submit" value="确定"/>
</form:form>
</div>
</div>
</div>
<%@include file="../footer.jsp" %>
</div>
</body>
</html>