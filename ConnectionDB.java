import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDB {

	private  final static String DBURL = "jdbc:sqlite:Jbdc.db";
	
	public static Connection getconnection() throws SQLException {
		
		return DriverManager.getConnection(DBURL );
		
	}
	public static void  closeConnection(Connection connection) {
		if(connection != null) {
			try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
	}
		

}
