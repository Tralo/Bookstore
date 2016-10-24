package com.servlet.bootstore.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.servlet.bootstore.domain.Account;
import com.servlet.bootstore.domain.Book;
import com.servlet.bootstore.domain.ShoppingCart;
import com.servlet.bootstore.domain.ShoppingCartItem;
import com.servlet.bootstore.domain.User;
import com.servlet.bootstore.service.AccountService;
import com.servlet.bootstore.service.BookService;
import com.servlet.bootstore.service.UserService;
import com.servlet.bootstore.web.BookStoreWebUtils;
import com.servlet.bootstore.web.CriteriaBook;
import com.servlet.bootstore.web.Page;

/**
 * Servlet implementation class BookServlet
 */
@WebServlet("/bookServlet")
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BookService bookService = new BookService();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String methodName = request.getParameter("method");
		try {
			Method method = getClass().getDeclaredMethod(methodName,
					HttpServletRequest.class, HttpServletResponse.class);
			method.setAccessible(true);
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void getBooks(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String pageNoStr = request.getParameter("pageNo");
		String minPriceStr = request.getParameter("minPrice");
		String maxPriceStr = request.getParameter("maxPrice");
		int pageNo = 1;
		int minPrice = 0;
		int maxPrice = Integer.MAX_VALUE;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (Exception e) {
		}
		try {
			minPrice = Integer.parseInt(minPriceStr);
		} catch (Exception e) {
		}
		try {
			maxPrice = Integer.parseInt(maxPriceStr);
		} catch (Exception e) {
		}
		CriteriaBook cb = new CriteriaBook(minPrice, maxPrice, pageNo);
		Page<Book> page = bookService.getPage(cb);
		request.setAttribute("bookpage", page);
		request.getRequestDispatcher("/WEB-INF/pages/books.jsp").forward(
				request, response);
	}

	protected void getBook(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int id = -1;
		boolean flag = true;
		Book book = null;
		try {
			id = Integer.parseInt(idStr);
		} catch (Exception e) {
		}
		if (id > 0) {
			book = bookService.getBook(id);

		}
		if (book == null) {
			response.sendRedirect(request.getContextPath() + "error-1.jsp");
		} else {
			request.setAttribute("book", book);
			request.getRequestDispatcher("/WEB-INF/pages/book.jsp").forward(
					request, response);
		}
	}

	protected void addToCart(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int id = -1;
		boolean flag = false;

		try {
			id = Integer.parseInt(idStr);
		} catch (Exception e) {
		}

		if (id > 0) {
			ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);

			flag = bookService.addToCart(id, sc);
		}
		if (flag) {
			getBooks(request, response);
			return;
		}
		response.sendRedirect(request.getContextPath() + "error-1.jsp");
	}

	/*
	 * protected void toCartPage(HttpServletRequest request, HttpServletResponse
	 * response) throws ServletException, IOException {
	 * request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request,
	 * response); }
	 */

	protected void forwardPage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = request.getParameter("page");
		request.getRequestDispatcher("/WEB-INF/pages/" + page + ".jsp")
				.forward(request, response);
	}

	protected void remove(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int id = -1;
		try {
			id = Integer.parseInt(idStr);
		} catch (Exception e) {
		}
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		bookService.removeItemFromShoppingCart(sc, id);
		if (sc.isEmpty()) {
			request.getRequestDispatcher("/WEB-INF/pages/emptycart.jsp")
					.forward(request, response);
			return;
		}
		request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(
				request, response);

	}

	protected void clear(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		bookService.clearShoppingCart(sc);
		request.getRequestDispatcher("/WEB-INF/pages/emptycart.jsp").forward(
				request, response);
	}

	protected void updateItemQuantity(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 4. 在updateItemQuantity 方法中，获取quantity,id, 再获取购物车对象，调用 service 的方法修改
		String idStr = request.getParameter("id");
		String quantityStr = request.getParameter("quantity");
		int id = -1;
		int quantity = -1;
		try {
			id = Integer.parseInt(idStr);
			quantity = Integer.parseInt(quantityStr);
		} catch (Exception e) {
		}
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);

		if (id > 0 && quantity > 0) {
			bookService.updateItemQuantity(sc, id, quantity);
		}
		// 5. 传回 json 数据: bookNumber: xxx,totalMoney: xxx
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("bookNumber", sc.getBookNumber());
		result.put("totalMoney", sc.getTotalMoney());
		String jsonStr = new Gson().toJson(result);
		response.setContentType("text/javascript");
		response.getWriter().write(jsonStr);
	}

	protected void cash(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1. 简单验证： 验证表单域是否符合基本的规范,是否可以转为 int 类型，是否是一个 eamil, 不需要进行查询
		// 数据库或调用任何的业务方法
		String username = request.getParameter("username");
		String accountId = request.getParameter("accountId");
		String errors = validateFormField(username, accountId);
//		System.out.println("错误信息:   "+errors.toString());
		if(errors.equals("")){//表单验证通过
			errors = validateUser(username, accountId);
			if(errors.equals("")){//用户名帐号通过
				errors = validateBookStoreNumber(request);
				if(errors.equals("")){//库存验证通过
					errors = validateBalance(request,accountId);
					if(errors.equals("")){//余额通过
						
					}
					
				}
			}
		}
		if(!errors.equals("")){
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/WEB-INF/pages/cash.jsp").forward(request, response);
			return;
		}
		
	}
	
	
	
	private UserService userService = new UserService();
	
	/**
	 * 验证余额是否充足
	 * @param accountId
	 * @return
	 */
	private String validateBalance(HttpServletRequest request,String accountId){
		
		StringBuffer errors = new StringBuffer("");
		ShoppingCart cart = BookStoreWebUtils.getShoppingCart(request);
		Account account = accountService.getAccount(Integer.parseInt(accountId));
		if(cart.getTotalMoney() > account.getBalance()){
			errors.append("余额不足!");
		}
		return errors.toString();
	}
	
	private AccountService accountService = new AccountService();
	
	/**
	 * 验证库存是否充足
	 * @param request
	 * @return
	 */
	private String validateBookStoreNumber(HttpServletRequest request){
		ShoppingCart cart = BookStoreWebUtils.getShoppingCart(request);
		StringBuffer errors = new StringBuffer("");
		for(ShoppingCartItem sci : cart.getItems()){
			int quantity = sci.getQuantity();
			int storeNumber = bookService.getBook(sci.getBook().getId()).getStoreNumber();
			if(quantity > storeNumber){
				errors.append(sci.getBook().getTitle() + "库存不足<br>");
			}
			
		}
		return errors.toString();
	}
	
	
	/**
	 * 验证用户名和帐号是否匹配
	 * @param username
	 * @param accountId
	 * @return
	 */
	private String validateUser(String username,String accountId){
		boolean flag = false;
		User user = userService.getUserByUserName(username);
		if(user != null){
			int myAccountId = user.getAccountId();
			if(accountId.trim().equals(String.valueOf(myAccountId))){
				flag = true;
			}
		} 
		StringBuffer errors = new StringBuffer("");
		if(!false){
			errors.append("用户名和帐号不匹配");
			
		}
		return errors.toString();
	}
	/**
	 * 验证表单域是否符合基本的规则： 验证是否为空
	 * @param username
	 * @param accountId
	 * @return
	 */
	private String validateFormField(String username,String accountId){
		StringBuffer errors = new StringBuffer("");
		if (username == null || username.trim().equals("")) {
			errors.append("用户名不能为空<br>");
		}
		if (accountId == null || accountId.trim().equals("")) {
			errors.append("帐号不能为空");
		}
		return errors.toString();
	}
	
}
