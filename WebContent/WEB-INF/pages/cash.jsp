<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<center>
		<br><br>
		您一共买了 ${sessionScope.ShoppingCart.bookNumber } 本书
		<br>
		应付: ￥ ${sessionScope.ShoppingCart.totalMoney }
		<br><br>
		<form action="bookServlet?method=cash" method="post">
			<table cellpadding="10">
				<tr>
					<td>信用卡姓名:  </td>
					<td><input type="text" name="username"/></td>
				</tr>
				<tr>
					<td>信用卡帐号:  </td>
					<td><input type="text" name="accountId"/></td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit" value="Submit"/></td>
				</tr>
			</table>
		</form>
	</center>

</body>
</html>