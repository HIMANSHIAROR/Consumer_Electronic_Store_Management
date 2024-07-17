package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Database {
	
	private String user = "root";
	private String pass = "Himanshi@123";
	private String url = "jdbc:mysql://localhost/electronicstore";
	private Statement statement;
	
	public Database() {
		try {
			Connection connection = DriverManager.getConnection(url, user, pass);
			statement = connection.createStatement();
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	public Statement getStatement() {
		return statement;
	}

}
