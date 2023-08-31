package model;

public class TransactionHeader {
	private Integer thID;
	private Integer userID;
	private String userName;
	private String trDate;
	private Integer totalItem;
	private String PaymentType;
	private String CardNumber;
	private String PromoCode;
	private Integer beforeDiscount;
	private Integer PromoDiscount;
	private Integer grandTotal;
	
	
	public TransactionHeader() {
		// TODO Auto-generated constructor stub
	}


	public TransactionHeader(Integer thID, Integer userID, String userName, String trDate, Integer totalItem,
			String paymentType, String cardNumber, String promoCode, Integer beforeDiscount, Integer promoDiscount,
			Integer grandTotal) {
		super();
		this.thID = thID;
		this.userID = userID;
		this.userName = userName;
		this.trDate = trDate;
		this.totalItem = totalItem;
		this.PaymentType = paymentType;
		this.CardNumber = cardNumber;
		this.PromoCode = promoCode;
		this.beforeDiscount = beforeDiscount;
		this.PromoDiscount = promoDiscount;
		this.grandTotal = grandTotal;
	}


	public Integer getThID() {
		return thID;
	}


	public void setThID(Integer thID) {
		this.thID = thID;
	}


	public Integer getUserID() {
		return userID;
	}


	public void setUserID(Integer userID) {
		this.userID = userID;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getTrDate() {
		return trDate;
	}


	public void setTrDate(String trDate) {
		this.trDate = trDate;
	}


	public Integer getTotalItem() {
		return totalItem;
	}


	public void setTotalItem(Integer totalItem) {
		this.totalItem = totalItem;
	}


	public String getPaymentType() {
		return PaymentType;
	}


	public void setPaymentType(String paymentType) {
		PaymentType = paymentType;
	}


	public String getCardNumber() {
		return CardNumber;
	}


	public void setCardNumber(String cardNumber) {
		CardNumber = cardNumber;
	}


	public String getPromoCode() {
		return PromoCode;
	}


	public void setPromoCode(String promoCode) {
		PromoCode = promoCode;
	}


	public Integer getBeforeDiscount() {
		return beforeDiscount;
	}


	public void setBeforeDiscount(Integer beforeDiscount) {
		this.beforeDiscount = beforeDiscount;
	}


	public Integer getPromoDiscount() {
		return PromoDiscount;
	}


	public void setPromoDiscount(Integer promoDiscount) {
		PromoDiscount = promoDiscount;
	}


	public Integer getGrandTotal() {
		return grandTotal;
	}


	public void setGrandTotal(Integer grandTotal) {
		this.grandTotal = grandTotal;
	}
	
	
	
	
}
