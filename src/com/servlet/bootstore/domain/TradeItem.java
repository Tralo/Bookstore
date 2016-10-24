package com.servlet.bootstore.domain;

public class TradeItem {
	private Integer itemId;
	//和TradeItem关联的Book
	private Book book;
	
	private int quantity;
	//和 TradeItem 关联的 Book 的 bookId
	private Integer bookId;
	
	private Integer tradeId;

	

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getTradeId() {
		return tradeId;
	}

	public void setTradeId(Integer tradeId) {
		this.tradeId = tradeId;
	}

	public TradeItem(Integer itemId,  Integer bookId,int quantity,
			Integer tradeId) {
		super();
		this.itemId = itemId;
		this.quantity = quantity;
		this.bookId = bookId;
		this.tradeId = tradeId;
	}

	public TradeItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "TradeItem [itemId=" + itemId + ", quantity="
				+ quantity + ", bookId=" + bookId + "]";
	}
	
	
}
