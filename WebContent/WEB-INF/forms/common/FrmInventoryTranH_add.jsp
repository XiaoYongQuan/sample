<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd 

">
<html>
<head>
<title>新增表单</title>
</head>
<body>


<div align="center">
	<h1>新增单据</h1>
	<form action="" method="post">
		单据编号：<input type="text" name="tbNo" value="${tbNo}">
		<br> 
		单据日期：<input type="text" name="tbDate" value="${tbDate}" readonly="readonly">
		<br>
		建档人员：<input type="text" name="appUser" value="${appUser}">
		<br> 
		<input type="submit" value="保存" formaction="FrmInventory.saveTranH"> 
		<input type="submit"  value="返回" formaction="FrmInventory">
	</form>
	${msg }
</div>
</body>
</html>
