package model;

public class TransactionDetail {
	private Integer thID;
	private Integer bookID;
	private String bookName;
	private Integer bookPrice;
	private Integer qty;
	private Integer subTotal;
	
	public TransactionDetail(Integer thID, Integer bookID, String bookName, Integer bookPrice, Integer qty,
			Integer subTotal) {
		super();
		this.thID = thID;
		this.bookID = bookID;
		this.bookName = bookName;
		this.bookPrice = bookPrice;
		this.qty = qty;
		this.subTotal = subTotal;
	}
	
	public TransactionDetail() {
		
	}
	public Integer getThID() {
		return thID;
	}
	public void setThID(Integer thID) {
		this.thID = thID;
	}
	public Integer getBookID() {
		return bookID;
	}
	public void setBookID(Integer bookID) {
		this.bookID = bookID;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public Integer getBookPrice() {
		return bookPrice;
	}
	public void setBookPrice(Integer bookPrice) {
		this.bookPrice = bookPrice;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public Integer getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(Integer subTotal) {
		this.subTotal = subTotal;
	}
	
	
	

}
