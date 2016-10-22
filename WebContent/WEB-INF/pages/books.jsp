<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="script/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
	$(function(){
		
		$("#pageNo").change(function(){
			//得到当前的页码
			var pageNo = $(this).val();
			//正则判断
			var reg = /^\d+$/;
			if(!reg.test(pageNo)){
				$(this).val("");
				alert("输入的页码不合法");
				return;
			}
			//判断输入的页码是否在合法的范围内
			var pageNo2 = parseInt(pageNo);
			if(pageNo2 < 1 || pageNo2 > parseInt("${bookpage.totalPageNumber}")){
				$(this).val("");
				alert("输入的页码不合法");
				return;
			}
			//翻页
			var criteria = $(":hidden").serialize();
			window.location.href = "bookServlet?method=getBooks&pageNo=" + pageNo2 + "&" + criteria;
		});
	})
</script>
<%@ include file="/commons/queryCondition.jsp" %>
<title>Insert title here</title>
</head>
<body>
	<center>
		<c:if test="${param.title != null }">
			您已经将 ${param.title } 放入到购物车中.
			<br><br>
		</c:if>
		<c:if test="${empty session.ShoppingCart }">
			您的购物车中有 ${sessionScope.ShoppingCart.bookNumber } 本书,<a href="cart.jsp?pageNo=${bookpage.pageNo }">查看购物车</a>
		</c:if>
	
		<br></br>
		<form action="bookServlet?method=getBooks" method="post">
			Price: <input type="text" size="10" name="minPrice"/> - <input type="text" size="10" name="maxPrice"/>
			<input type="submit" value="Submit"/>
			
		</form>
		<br><br>
		<table cellpadding="10">
			<c:forEach items="${bookpage.list }" var="book">
				<tr>
					<td><a href="bookServlet?method=getBook&pageNo=${bookpage.pageNo }&id=${book.id }">${book.title }</a>
					<br>${book.author }</td>
					
					<td>${book.price }</td>
					<td><a href="bookServlet?method=addToCart&pageNo=${bookpage.pageNo }&id=${book.id }&title=${book.title }">加入购物车</a></td>
				</tr>
			</c:forEach>
		</table>
		<br><br>
		共${bookpage.totalPageNumber }页
		&nbsp;&nbsp;
		当前第${bookpage.pageNo }页
		&nbsp;&nbsp;
		<c:if test="${bookpage.hasPrev }">
			<a href="bookServlet?method=getBooks&pageNo=1">首页</a>
			&nbsp;&nbsp;
			<a href="bookServlet?method=getBooks&pageNo=${bookpage.prevPage }">上一页</a>
		</c:if>
		<c:if test="${bookpage.hasNext }">
			<a href="bookServlet?method=getBooks&pageNo=${bookpage.nextPage }">下一页</a>
			&nbsp;&nbsp;
			<a href="bookServlet?method=getBooks&pageNo=${bookpage.totalPageNumber }">末页</a>
		</c:if>
		&nbsp;&nbsp;
		转到<input type="text" size="1" id="pageNo" name="pageNo"/>页
	</center>

</body>
</html>