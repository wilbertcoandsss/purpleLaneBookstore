package controller;

import java.sql.PreparedStatement;

import java.sql.SQLException;
import java.util.ArrayList;

import connect.Connect;
import model.User;


public class AuthController {

	private Connect connect = Connect.getInstance();
	public static ArrayList<User> userData;
	public static User authUser = new User();
	public static Integer tempUserID;
	public static String tempUserRole;

	public boolean insertUser(String name, String email, String username, String password) {
		String query = "INSERT INTO users (`name`, `email`, `username`, `password`) VALUES (?,?,?,?)";
		PreparedStatement ps = connect.prepareStatement(query);
		try {
			ps.setString(1, name);
			ps.setString(2, email);
			ps.setString(3, username);
			ps.setString(4, password);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}

	public boolean login(String email, String password) {
		String query = "SELECT * FROM users WHERE email = '"+email+"' AND password = '"+password+"'";

		connect.rs = connect.execQuery(query);
		
		String tempEmail, tempPassword;

		try {
			while(connect.rs.next()) {
				tempEmail = connect.rs.getString("email");
				tempPassword = connect.rs.getString("password");
				if (tempEmail.equals(email) && tempPassword.equals(password)) {
					authUser.setID(connect.rs.getInt("ID"));
					authUser.setRole(connect.rs.getString("role"));
					return true;

				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	public void fetchDataUser() {
		String query = "SELECT * FROM users";
		connect.execQuery(query);
		userData = new ArrayList<>();
		try {
			while(connect.rs.next()) {
				Integer tempID = connect.rs.getInt("ID");
				String tempName = connect.rs.getString("name");	
				String tempEmail = connect.rs.getString("email");	
				String tempUsername = connect.rs.getString("username");
				String tempPassword = connect.rs.getString("password");
				String tempRole = connect.rs.getString("role");	
				User us = new User(tempID, tempName, tempEmail, tempUsername, tempPassword, tempRole);
				userData.add(us);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getCurrentUser() {
		String query = "SELECT * FROM users WHERE ID = ?";
		connect.execQuery(query);
		try {
			while(connect.rs.next()) {
				//				Integer currentID
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
