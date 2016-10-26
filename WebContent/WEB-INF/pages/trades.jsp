<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<center>
		<br>
		<br>
		<h4>User: ${user.username }</h4>
		<br>
		<br>
		<table>
			<tr>
				<td>
					<table cellpadding="10" border="1" cellspacing="0">
						<c:forEach items="${user.trades }" var="trade">
							<tr><td colspan="3">TradeTime: ${trade.tradeTime }</td></tr>
							
			
							<c:forEach items="${trade.items }" var="item">
								<tr>
									<td>${item.book.title } </td>
									<td>${item.book.price }</td>
									<td>${item.quantity }</td>
								</tr>
							</c:forEach>
						</c:forEach>
					</table>

				</td>
			</tr>
		</table>
	</center>
</body>
</html>