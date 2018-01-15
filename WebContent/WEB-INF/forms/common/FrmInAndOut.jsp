<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>进出明细查询</title>

</head>
<body>
	<h1 style="text-align: center;">进出明细表</h1>
	<hr>
	<div>
		<h4>查询条件</h4>
		<form action="FrmInAndOut" method="post">
			<div style="float:left;width: 30%;">
				商品编号<input type="text" name="code" value="${code}"><br>
				起始时间<input type="date" name="dateStart" value="${dateStart}"><br>
				<br>
			</div>
			<div style="float:left;width: 40%;">
				其他<input type="text" name="searchText" value="${searchText}"><br>
				结束时间<input type="date" name="dateEnd" value="${dateEnd}">&nbsp;&nbsp;<input type="submit" value="查询"> ${msg}<br>
				<br>
			</div>
		</form>
	
	</div>
	<table width=100% border=1 cellpadding=10 cellspacing=0 borderColor=red
		style="border-collapse: collapse">
		<thead>
			<tr>
				<th>序号</th>
				<th>商品编号</th>
				<th>单据类别</th>
				<th>单据编号</th>
				<th>单据日期</th>
				<th>建档人员</th>
				<th>建档日期</th>
				<th>品名</th>
				<th>规格</th>
				<th>单位</th>
				<th>变更数量</th>
			</tr>
		</thead>
		<tbody>

			<c:forEach items="${dataSet}" var="item" varStatus="status">
				<tr align=center>
					<td>${status.index + 1}</td>
					<td>${item.items.Code_}</td>
					<td>${item.items.TB_}</td>
					<td>${item.items.TBNo_}</td>
					<td>${item.items.TBDate_}</td>
					<td>${item.items.AppUser_}</td>
					<td>${item.items.AppDate_}</td>
					<td>${item.items.Desc_}</td>
					<td>${item.items.Spec_}</td>
					<td>${item.items.Unit_}</td>
					<td>${item.items.Num_}</td>
				</tr>
			</c:forEach>
		</tbody>
		
	</table>
	<div align="left">
		<a href="FrmInvoicing">返回</a>
	</div>

</body>
</html>