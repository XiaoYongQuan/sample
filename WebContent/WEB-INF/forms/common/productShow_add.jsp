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
	<form action="" method="post">
		商品编号：<input type="text" name="code" value="${code}">
		<br> 
		品名：<input type="text" name="desc" value="${desc}">
		<br> 
		规格：<input type="text" name="spec" value="${spec}">
		<br> 
		单位：<input type="text" name="unit" value="${unit}">
		<br> 
		<input type="submit" value="保存" formaction="FrmProductShow.saveProduct"> 
		<input type="submit"  value="返回" formaction="FrmProductShow">
	</form>
	${msg }
</div>
</body>
</html>
