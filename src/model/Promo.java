package model;

public class Promo {
	private Integer PromoID;
	private String PromoCode;
	private Integer PromoDiscount;
	private String PromoNotes;
	
	public Promo(Integer promoID, String promoCode, Integer promoDiscount, String promoNotes) {
		super();
		PromoID = promoID;
		PromoCode = promoCode;
		PromoDiscount = promoDiscount;
		PromoNotes = promoNotes;
	}
	
	public Promo(){
		
	}
	
	public Integer getPromoID() {
		return PromoID;
	}
	public void setPromoID(Integer promoID) {
		PromoID = promoID;
	}
	public String getPromoCode() {
		return PromoCode;
	}
	public void setPromoCode(String promoCode) {
		PromoCode = promoCode;
	}
	public Integer getPromoDiscount() {
		return PromoDiscount;
	}
	public void setPromoDiscount(Integer promoDiscount) {
		PromoDiscount = promoDiscount;
	}
	public String getPromoNotes() {
		return PromoNotes;
	}
	public void setPromoNotes(String promoNotes) {
		PromoNotes = promoNotes;
	}
	
	

}
