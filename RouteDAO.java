import java.security.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

public class RouteDAO {
	
	
	public List<Route> getAllRoute() throws SQLException{
		List<Route> routes = new ArrayList<Route>();
		try {
			Connection connection = ConnectionDB.getconnection();
			Statement statement = connection.createStatement();
			ResultSet resultset = statement.executeQuery("SELECT * FROM RouteData");

			while(resultset.next()) {
				String origin = resultset.getString("origin");
				String destenation = resultset.getString("destenation");
				LocalDateTime departureTime = LocalDateTime.parse(resultset.getString("departureTime"),
						DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

				
				routes.add(new Route(origin,destenation,departureTime));
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return routes;
	}
	public void addRoute(Route routes, String plate) {
	    Connection connection = null;
	    PreparedStatement prepstate = null;
	    ResultSet resultSet = null;
	    LocalDateTime departureTime = routes.getDepartureTime();

	    try {
	        connection = ConnectionDB.getconnection();
	        String checkQuery = "SELECT MAX(departureTime) FROM RouteData WHERE plate = ?";
	        prepstate = connection.prepareStatement(checkQuery);
	        prepstate.setString(1, plate);
	        resultSet = prepstate.executeQuery();

	        boolean canAddRoute = true;

	        if (resultSet.next()) {
	            String lastDeparture = resultSet.getString(1);
	            if (lastDeparture != null) {
	                LocalDateTime lastDepartureTime = LocalDateTime.parse(lastDeparture, 
	                		DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	                LocalDateTime nextAllowedDeparture = lastDepartureTime.plusHours(3);

	                if (departureTime.isBefore(nextAllowedDeparture)) {
	                    JOptionPane.showMessageDialog
	                    (null, "There must be at least a 3-hour gap for the same bus.",
	                    		"Warning", JOptionPane.WARNING_MESSAGE);
	                    canAddRoute = false;
	                }
	            }
	        }

	        if (prepstate != null) prepstate.close();
	        if (resultSet != null) resultSet.close();

	        if (canAddRoute) {
	            // Insert the new route
	            String insertQuery = "INSERT INTO RouteData "
	            		+ "(origin, destenation, plate, departureTime) VALUES (?, ?, ?, ?)";
	            prepstate = connection.prepareStatement(insertQuery);
	            prepstate.setString(1, routes.getorigin());
	            prepstate.setString(2, routes.getdestenation());
	            prepstate.setString(3, plate);
	            prepstate.setString(4, departureTime.format
	            		(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

	            prepstate.executeUpdate();
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        
	        try {
	            if (resultSet != null) resultSet.close();
	            if (prepstate != null) prepstate.close();
	            if (connection != null) connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
	public void deleteRwoute(Route routes) {
		try {
			Connection connection = ConnectionDB.getconnection();
			PreparedStatement prepstate = connection.prepareStatement("DELETE FROM RouteData WHERE origin = ? AND destenation = ? AND departureTime =?");
			prepstate.setString(1,routes.getorigin());
			prepstate.setString(2,routes.getdestenation());
			prepstate.setString(3,routes.getDepartureTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
			
			prepstate.executeUpdate();		
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	

}
