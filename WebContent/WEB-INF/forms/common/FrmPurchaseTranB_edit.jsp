<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>详细内容</title>
</head>



<body>
	<div align="center">
	<h1>商品详情</h1>
		<form action="" method="post" >
			单据编号：<input type="text" name="tbNo" value="${tbNo} " readonly="readonly">
			<br> 
			<input type="hidden" name="code" value="${code} " >
			品名：         <input type="text" name="desc" value="${desc}" readonly="readonly">
			<br>
			规格：         <input type="text" name="spec" value="${spec}" readonly="readonly">
			<br> 
			单位：          <input type="text" name="unit" value="${unit}" readonly="readonly">
			<br>
			数量：         <input type="text" name="num" value="${num}">
			<br> 
			<input type="submit" value="保存" formaction="FrmPurchase.saveUpDateTranB" />
			<input type="submit" value="删除" formaction="FrmPurchase.deleteTranB" />
		</form>
		${msg}
</div>
</body>
</html>
