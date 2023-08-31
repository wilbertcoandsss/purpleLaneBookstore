package model;

public class Checkout {
	private Integer BookID;
	private String BookName;
	private String BookAuthor;
	private Integer BookPrice;
	private Integer BookQty;
	private Integer SubTotal;
	
	public Checkout(Integer bookID, String bookName, String bookAuthor, Integer bookPrice, Integer bookQty,
			Integer subTotal) {
		super();
		this.BookID = bookID;
		this.BookName = bookName;
		this.BookAuthor = bookAuthor;
		this.BookPrice = bookPrice;
		this.BookQty = bookQty;
		this.SubTotal = subTotal;
	}
	public Integer getBookID() {
		return BookID;
	}
	public void setBookID(Integer bookID) {
		BookID = bookID;
	}
	public String getBookName() {
		return BookName;
	}
	public void setBookName(String bookName) {
		BookName = bookName;
	}
	public String getBookAuthor() {
		return BookAuthor;
	}
	public void setBookAuthor(String bookAuthor) {
		BookAuthor = bookAuthor;
	}
	public Integer getBookPrice() {
		return BookPrice;
	}
	public void setBookPrice(Integer bookPrice) {
		BookPrice = bookPrice;
	}
	public Integer getBookQty() {
		return BookQty;
	}
	public void setBookQty(Integer bookQty) {
		BookQty = bookQty;
	}
	public Integer getSubTotal() {
		return SubTotal;
	}
	public void setSubTotal(Integer subTotal) {
		SubTotal = subTotal;
	}
	
	
	
}
