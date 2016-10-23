<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="script/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('.delete').click(function(){
			var $tr = $(this).parent().parent();
			var title = $.trim($tr.find("td:first").text());
			var flag = confirm("确定要删除" + title + "的信息吗?");
			if(flag){
				return true;
			}
			
			return false;
		});
		$('.clear').click(function(){
		
			var flag = confirm("确定要清空购物车吗?");
			if(flag){
				return true;
			}
			
			return false;
		});
		// ajax 修改单个商品的数量
		//1. 获取页面中所有的 text ,并为其添加 onchange 响应函数, 弹出确认对话框： 确定要修改吗?
		$(":text").change(function(){
			var $tr = $(this).parent().parent();
			var title = $.trim($tr.find("td:first").text());
			var flag = confirm("确定要修改"+ title +"的数量吗?");
			if(!flag){
				return;
			}
			//2. 请求地址为: BookServlet
			var url = "bookServlet";
			
			//3. 请求参数为: method:updateItemQuantity, id:name属性值, quantity:val, time:new Date();
			var idVal = $.trim(this.name);
			var quantityVal = $.trim(this.value);
			var args = {"method":"updateItemQuantity","id":idVal, "quantity":quantityVal,"time":new Date()};
			
			
			
			//6. 更新当前页面的 bookNumber 和 totalMoney
			$.post(url,args,function(data){
				var bookNumber = data.bookNumber;
				var totalMoney = data.totalMoney;
				$("#totalMoney").text("总金额: ￥"+ totalMoney);
				$("#bookNumber").text("您的购物车中共有 "+ bookNumber +" 本书");
			},"JSON");
			
		});
		
		
		
	})
</script>
<title>Insert title here</title>
<%@ include file="/commons/queryCondition.jsp" %>
</head>
<body>
	<center>
		<br>
		<br> <div id="bookNumber">您的购物车中共有 ${sessionScope.ShoppingCart.bookNumber } 本书</div>
		<table cellpadding="10">
			<tr>
				<td>Title</td>
				<td>Price</td>
				<td>Quantity</td>
				<td>&nbsp;</td>
			</tr>
			<c:forEach items="${sessionScope.ShoppingCart.items }" var="item">
				<tr>
					<td>${item.book.title }</td>
					<td>${item.book.price }</td>
					<td><input type="text" size="1" value="${item.quantity }" name="${item.book.id }" /></td>
					<td><a href="bookServlet?method=remove&pageNo=${param.pageNo }&id=${item.book.id }"  class="delete">删除</a></td>
				</tr>

			</c:forEach>
			<tr>
				<td colspan="4" id="totalMoney">总金额: ￥${sessionScope.ShoppingCart.totalMoney }</td>
			</tr>
			<tr>
				<td colspan="4">
					<a href="bookServlet?method=getBooks&pageNo=${param.pageNo }">继续购物</a>
					&nbsp;&nbsp;
					<a href="bookServlet?method=clear">清空购物车</a>
					&nbsp;&nbsp;
					<a href="" class="clear">结帐</a>
				</td>
			</tr>
		</table>
	</center>


</body>
</html>