package bankingSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Conn {

	
	Connection c;
	Statement s;
	public Conn() {
		try {
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankmanagementSystem","root", "123456789");
			s = c.createStatement();
		}catch (Exception e) {
			System.out.println(e);
		}
	}
	 public  PreparedStatement prepareStatement(String sql) throws SQLException {
	        return c.prepareStatement(sql);
	    }
	
	
	
}

		