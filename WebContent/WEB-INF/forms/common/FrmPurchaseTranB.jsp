<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加商品</title>
</head>
<body>
	<h1 style="text-align: center;">进货单详情</h1>
	<hr>
	<div>
		<form action="FrmPurchase.saveUpDateTranH" method="post">
			<div style="float:left;width: 40%;">
				单据编号 ：<input type="text" name="tbNo" value="${tbNo}" readonly="readonly"><br>
				建档人员 ：<input type="text" name="appUser" value="${appUser}" readonly="readonly">
				<br>
			</div>
			<div style="float:left;width: 40%;">
				单据日期 ：<input type="text" name="tbDate" value="${tbDate}"><br>
				厂商名称 ：<input type="text" name="supName" value="${supName}">
				<br>
			</div>
			<div style="float:right;">
				${msg}<input type="submit" value="保存"> 
			</div>
		</form>
	</div>

	<table width=100% border=1 cellpadding=10 cellspacing=0 borderColor=red
		style="border-collapse: collapse">
		<thead>
			<tr>
				<th>单序</th>
				<th>商品编号</th>
				<th>品名</th>
				<th>规格</th>
				<th>单位</th>
				<th>数量</th>
				<th>查看详情</th>
				
			</tr>
		</thead>
		<tbody>
			
			<c:forEach items="${dataSet}" var="item">
				<tr align=center>
					<td>${item.items.It_}</td>
					<td>${item.items.Code_}</td>
					<td>${item.items.Desc_}</td>
					<td>${item.items.Spec_}</td>
					<td>${item.items.Unit_}</td>
					<td>${item.items.Num_}</td>
					<td>                           
						<a href="FrmPurchase.modifyTranB?tbNo=${tbNo }&code=${item.items.Code_}">详细内容</a>             
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div align="left">
		<a href="FrmPurchase.addTranB?tbNo=${tbNo }">添加商品</a>
		<a href="FrmPurchase">返回</a>
	</div>

</body>
</html>