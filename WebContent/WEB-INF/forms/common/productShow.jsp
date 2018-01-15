<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品展示</title>
</head>
<body>
	<h1 style="text-align: center;">商品列表</h1>
	<hr>
	<div>
		<form action="FrmProductShow" method="post">
			<div style="float:left;width: 30%;">
				<h4>查询条件</h4><input type="text" name="searchText" value="${searchText}">&nbsp;&nbsp;<input type="submit" value="查询"> ${msg}<br>
				<br>
			</div>
		</form>
	</div>
	
	<table width=100% border=1 cellpadding=10 cellspacing=0
			borderColor=red style="border-collapse: collapse">
			<thead>
				<tr>
					<th>商品编号</th>
					<th>品名</th>
					<th>规格</th>
					<th>单位</th>
					<th>库存</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>

				<c:forEach items="${dataSet}" var="item">
				     <tr align=center >
						<td>${item.items.Code_}</td>
						<td>${item.items.Desc_}</td>
						<td>${item.items.Spec_}</td> 
						<td>${item.items.Unit_}</td>
						<td>${item.items.Stock_}</td>
						<td>                           
						<a href="FrmProductShow.deleteProduct?uid=${item.items.UID_}" onclick=" return confirm('你确定要删除此数据吗?')">删除</a>&nbsp;&nbsp;
						<a href="FrmProductShow.modify?uid=${item.items.UID_}">修改</a>             
						</td>	
					  </tr>
				</c:forEach>
			</tbody>
		</table>
		<div align="left">
			<a href="FrmProductShow.addProduct">添加</a>
			<a href="FrmInvoicing">返回</a>
		</div>
	
</body>
</html>