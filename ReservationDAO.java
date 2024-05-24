import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
    public void insertIntoReservationData() throws SQLException {
    	String clearQuery = "DELETE FROM ReservationData";
        String query = "INSERT INTO ReservationData (origin, destenation, departureTime, capacity) " +
                       "SELECT r.origin, r.destenation, r.departureTime, b.capacity " +
                       "FROM RouteData r " +
                       "JOIN BusData b ON r.plate = b.plate";
        

        try {
        	 Connection connection = ConnectionDB.getconnection();
             Statement statement = connection.createStatement(); 
             statement.executeUpdate(clearQuery); 
             statement.executeUpdate(query);
        } 
        catch (SQLException e) {
            e.printStackTrace();
            
        }
    }

    
    public List<Reservations> getAllReservation() throws SQLException {
        List<Reservations> reservations = new ArrayList<>();
        String query = "SELECT origin, destenation, departureTime, capacity FROM ReservationData";

        try {
        	 Connection connection = ConnectionDB.getconnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query); 

            while (resultSet.next()) {
                String origin = resultSet.getString("origin");
                String destenation = resultSet.getString("destenation");
                String departureTime = resultSet.getString("departureTime");
                int capacity = resultSet.getInt("capacity");
                reservations.add(new Reservations(origin, destenation, departureTime, capacity));
            }
        } catch (SQLException e) {
            e.printStackTrace();
           
        }
        return reservations;
    }
   
}

