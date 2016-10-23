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
import com.servlet.bootstore.domain.Book;
import com.servlet.bootstore.domain.ShoppingCart;
import com.servlet.bootstore.service.BookService;
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
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			method.setAccessible(true);
			method.invoke(this, request,response);
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
		request.getRequestDispatcher("/WEB-INF/pages/books.jsp").forward(request, response);
	}
	
	protected  void getBook(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int id = -1;
		boolean flag = true;
		Book book = null;
		try {
			id = Integer.parseInt(idStr);
		} catch (Exception e) {}
		if(id > 0){
			book = bookService.getBook(id);
			
		}
		if(book == null){
			response.sendRedirect(request.getContextPath() + "error-1.jsp");
		} else {
			request.setAttribute("book", book);
			request.getRequestDispatcher("/WEB-INF/pages/book.jsp").forward(request, response);
		}
	}
	
	
	protected  void addToCart(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int id = -1;
		boolean flag = false;
		
		try {
			id = Integer.parseInt(idStr);
		} catch (Exception e) {}
		
		if(id > 0){
			ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
			
			flag = bookService.addToCart(id,sc);
		}
		if(flag){
			getBooks(request, response);
			return;
		}
		response.sendRedirect(request.getContextPath() + "error-1.jsp");
	}
	
	protected  void toCartPage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
	}
	
	protected  void remove(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int id = -1;
		try {
			id = Integer.parseInt(idStr);
		} catch (Exception e) {
		}
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		bookService.removeItemFromShoppingCart(sc, id);
		if(sc.isEmpty()){
			request.getRequestDispatcher("/WEB-INF/pages/emptycart.jsp").forward(request, response);
			return;
		}
		request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
		
	}
	protected  void clear(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		bookService.clearShoppingCart(sc);
		request.getRequestDispatcher("/WEB-INF/pages/emptycart.jsp").forward(request, response);
	}
	
	protected  void updateItemQuantity(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//4. 在updateItemQuantity 方法中，获取quantity,id, 再获取购物车对象，调用 service 的方法修改
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
		
		if(id > 0 && quantity > 0){
			bookService.updateItemQuantity(sc,id,quantity);
		}
		//5. 传回 json 数据: bookNumber: xxx,totalMoney: xxx
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("bookNumber", sc.getBookNumber());
		result.put("totalMoney", sc.getTotalMoney());
		String jsonStr = new Gson().toJson(result);
		response.setContentType("text/javascript");
		response.getWriter().write(jsonStr);
	}
}
