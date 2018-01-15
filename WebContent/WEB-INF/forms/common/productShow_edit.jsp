<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>商品修改</title>
</head>



<body>
	<div align="center">
	<h1>商品修改</h1>

	
		<form action="" method="post" >
			<input type="hidden" name="uid" value="${uid} ">
			商品编号：<input type="text" name="code" value="${code} " readonly="readonly">
			<br> 
			品名：         <input type="text" name="desc" value="${desc}">
			<br> 
			规格：         <input type="text" name="spec" value="${spec}">
			<br> 
			单位：          <input type="text" name="unit" value="${unit}">
			<br> 
			<input type="submit" value="保存" formaction="FrmProductShow.updateProduct"> 
			<input type="submit"  value="返回" formaction="FrmProductShow">
		</form>
		${msg}

</div>
</body>
</html>
