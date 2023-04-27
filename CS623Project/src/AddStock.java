import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Sánchez Code adding a transaction to Stock 
 * ACID is implemented
 */

public class AddStock {
	public static void main(String args[]) throws SQLException, IOException, 
	ClassNotFoundException {
		
		// Load the PostgreSQL driver
	    // Connect to the default database with credentials
		Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
		
		// For atomicity
		conn.setAutoCommit(false);
		
		// For isolation
		conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				
		Statement stmt1 = null;
		try {
			// Create statement object
			stmt1 = conn.createStatement();
			
			// Either the 2 following inserts are executed, or none of them are. This is
			// atomicity
			stmt1.executeUpdate("insert into stock values ('p100', 'd2', 50)");
		} catch (SQLException e) {
			System.out.println("An exception was thrown");
			e.printStackTrace();
			
			// For atomicity
			conn.rollback();
			stmt1.close();
			conn.close();
			return;
		}
		conn.commit();
		stmt1.close();
		conn.close();
	}

}
