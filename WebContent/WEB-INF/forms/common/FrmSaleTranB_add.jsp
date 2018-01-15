<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd 

">
<html>
<head>
<title>添加商品</title>
</head>
<body>


<div align="center">
	<h1>添加商品</h1>
	<form action="FrmSale.saveTranB" method="post">
		<input type="hidden" name="tbNo" value="${tbNo}" readonly="readonly"><br>
		商品编号：<input type="text" name="code" value="${code}">
		<br> 
		商品数量：<input type="text" name="num" value="${num}">
		<br> 
		<input type="submit" value="添加"> 
		<input type="button"  onclick="javascript:history.back(-1);" value="返回">
	</form>
	${msg }
</div>
</body>
</html>
