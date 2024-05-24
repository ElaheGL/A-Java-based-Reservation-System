import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BusDAO {
	
	public List<Bus> getAllbuses(){
		List<Bus> buses = new ArrayList<Bus>();
		try {
		Connection connection = ConnectionDB.getconnection();
		Statement statement = connection.createStatement();
		ResultSet resultset = statement.executeQuery("SELECT * FROM BusData");
		while (resultset.next()) {
            String plate = resultset.getString("plate");
            int capacity = resultset.getInt("capacity");
            buses.add(new Bus(plate, capacity));
           
	}
		}
		catch (Exception e) {
			
		}
		return buses;
	}
	public void addBus(Bus bus) {
		try {
			Connection connection = ConnectionDB.getconnection();
			PreparedStatement prepStatement = connection.prepareStatement
					("INSERT INTO BusData (plate,capacity) VALUES (?,?)") ;
			prepStatement.setString(1,bus.getplate());
			prepStatement.setInt(2, bus.getcapacity());		
			prepStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }		
		}
	public void deleteBus(Bus bus) {
		try {
			Connection connection = ConnectionDB.getconnection();
			PreparedStatement prepStatement = connection.prepareStatement
					("DELETE FROM BusData WHERE PLATE = ?");
			prepStatement.setString(1,bus.getplate());
			prepStatement.executeUpdate();
			prepStatement = connection.prepareStatement("DELETE FROM RouteData WHERE plate = ?");
	        prepStatement.setString(1, bus.getplate());
	        prepStatement.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
		
	}
		
	}
	}
	
