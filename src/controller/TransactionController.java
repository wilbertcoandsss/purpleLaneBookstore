package controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import connect.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.TransactionHeader;

public class TransactionController {
	
	static Connect con = Connect.getInstance();
	
//	public static ObservableList<TransactionHeader> getUserTransaction(Integer userID) {
//		// TODO Auto-generated method stub
//		ObservableList<TransactionHeader> transactionHeaderList = FXCollections.observableArrayList();
//		AuthController ac = new AuthController();
//		
//		String query = "SELECT * FROM trheader th JOIN users us ON th.userID = us.ID JOIN promo pm ON th.PromoCode = pm.PromoID WHERE us.ID = '"+userID+"'";
//		Connect.getInstance();
//		con.execQuery(query);
//		try {
//			while(con.rs.next()) {
//				TransactionHeader th = new TransactionHeader();
//				th.setThID(con.rs.getInt("thID"));
//				th.setUserID(con.rs.getInt("userID"));
//				th.setTrDate(con.rs.getString("trDate"));
//				th.setTotalItem(con.rs.getInt("totalItem"));
//				th.setPaymentType(con.rs.getString("PaymentType"));
//				th.setCardNumber(con.rs.getString("CardNumber"));
//				th.setPromoCode(con.rs.getInt("PromoCode"));
//				System.out.println(th.getThID());
//				System.out.println("Apakah masuk");
//				transactionHeaderList.add(th);
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return transactionHeaderList;
//	
//		
//	}


	
}
