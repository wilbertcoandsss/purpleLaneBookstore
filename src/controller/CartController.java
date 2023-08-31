package controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connect.Connect;
import model.Cart;
import model.User;

public class CartController {

	private Connect connect = Connect.getInstance();

	public boolean addToCart(Integer bookID, String bookName, String bookAuthor, Integer bookQty, Integer bookPrice, Integer subTotal, Integer userID) {
		String query = "INSERT INTO carts (`BookID`, `BookName`, `BookQty`, `BookAuthor`, `BookPrice`, `SubTotal`, `user_ID`) VALUES (?,?,?,?,?,?,?)";
		PreparedStatement ps = connect.prepareStatement(query);
		
		String query1 = "SELECT * FROM carts";
		connect.rs = connect.execQuery(query1);

		int flag = 0;
		Integer newQty = 0;
		Integer newSubTotal = 0;
		
		try {
			while(connect.rs.next()){
				if(connect.rs.getInt("BookID") == bookID) {
					newQty = bookQty + connect.rs.getInt("BookQty");
					newSubTotal = newQty * connect.rs.getInt("BookPrice");
					String query2 = "UPDATE carts\n"
							+ "SET bookQty = ?, subTotal = ?\n"
							+ "WHERE bookID = ?";
					PreparedStatement ps1 = connect.prepareStatement(query2);
					
					ps1.setInt(1, newQty);
					ps1.setInt(2, newSubTotal);
					ps1.setInt(3, connect.rs.getInt("BookID"));
					ps1.executeUpdate();
					flag = 1;
					break;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (flag != 1) {
			try {
				ps.setInt(1, bookID);
				ps.setString(2, bookName);
				ps.setInt(3, bookQty);
				ps.setString(4, bookAuthor);
				ps.setInt(5, bookPrice);
				ps.setInt(6, subTotal);
				ps.setInt(7, userID);
				ps.executeUpdate();
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				return false;
			}		
		}

		return true;


	}

	public boolean removeCart(Integer bookID) {
		String query = "DELETE FROM carts WHERE BookID = ?";
		PreparedStatement ps = connect.prepareStatement(query);
		try {
			ps.setInt(1, bookID);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}

}
