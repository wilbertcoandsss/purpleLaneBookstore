package connect;



import java.sql.*;

public class Connect {
	private final String USERNAME = "root";
	private final String PASSWORD = "";
	private final String DATABASE = "purplelanebookstore";
	private final String HOST = "localhost:3306";
	private final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);
	
	public ResultSet rs;
	public ResultSetMetaData rsm;
	
	private Connection con;
	private Statement st;
	private static Connect connect;
	
	
	public static Connect getInstance() {
		if(connect == null) {
			return new Connect();
		}
		return connect;
	}
	
	public Connect() {
		try {
			/* Karena saya memakai sql connector java yang versi 8 
			 * (berbeda dengan yang dikasih), jadi classnya juga berbeda yakni com.mysql.cj.jdbc.Driver
			 * Jika ingin memakai yang versi 5 seperti yang dikasih, cukup didelete saja cj nya.
			 * Jika memakai mysql-connector-java-j-8.0xx = com.mysql.cj.jdbc.Driver
			 * Sedangkan jika memakai mysql-connector-java-j-5.0xx = com.mysql.jdbc.Driver
			 * Terima kasih.
			*/
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
			
			st = con.createStatement();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet execQuery(String query) {
		try {
			rs = st.executeQuery(query);
			rsm = rs.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public void execUpdate(String query) {
		try {
			st.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public PreparedStatement prepareStatement(String query) {
    	PreparedStatement ps = null;
    	try {
			ps = con.prepareStatement(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return ps;
    }
}
