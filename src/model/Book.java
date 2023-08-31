package model;

public class Book {
	private Integer BookID;
	private String BookName;
	private Integer BookStock;
	private String BookAuthor;
	private Integer BookPrice;
	
	public Book(Integer bookID, String bookName, Integer bookStock, String bookAuthor, Integer bookPrice) {
		super();
		this.BookID = bookID;
		this.BookName = bookName;
		this.BookStock = bookStock;
		this.BookAuthor = bookAuthor;
		this.BookPrice = bookPrice;
	}
	
	public Book() {
		// TODO Auto-generated constructor stub
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
	public Integer getBookStock() {
		return BookStock;
	}
	public void setBookStock(Integer bookStock) {
		BookStock = bookStock;
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
	
	
	
	
}
