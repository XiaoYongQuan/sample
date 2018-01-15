<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>盘点单</title>
</head>
<body>
	<h1 style="text-align: center;">盘点单列表</h1>
	<hr>
	<div>
		<h4>查询条件</h4>
		<form action="FrmInventory" method="post">
			<div style="float:left;width: 30%;">
				单据编号<input type="text" name="tbNo" value="${tbNo}"><br>
				起始时间<input type="date" name="dateStart" value="${dateStart}"><br>
				<br>
			</div>
			<div style="float:left;width: 40%;">
				建档人员<input type="text" name="appUser" value="${appUser}"><br>
				结束时间<input type="date" name="dateEnd" value="${dateEnd}">&nbsp;&nbsp;<input type="submit" value="查询"> ${msg}<br>
				<br>
			</div>
		</form>
	</div>
	<table width=100% border=1 cellpadding=10 cellspacing=0 borderColor=red
		style="border-collapse: collapse">
		<thead>
			<tr>
				<th>单据类别</th>
				<th>单据编号</th>
				<th>单据日期</th>
				<th>建档人员</th>
				<th>建档日期</th>
			</tr>
		</thead>
		<tbody>

			<c:forEach items="${dataSet}" var="item">
				<tr align=center>
					<td>${item.items.TB_}</td>
					<td><a href="FrmInventory.ShowTranB?tbNo=${item.items.TBNo_}">${item.items.TBNo_}</a></td>
					<td>${item.items.TBDate_}</td>
					<td>${item.items.AppUser_}</td>
					<td>${item.items.AppDate_}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div align="left">
		<a href="FrmInventory.addTranH">新增</a>
		<a href="FrmInvoicing">返回</a>
	</div>

</body>
</html>