import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Route {
	
	String origin,destenation,plate;
	LocalDateTime departureTime;
	
	
	public Route(String porigin,String pdestenation,LocalDateTime pdepartureTime) {
		this.origin = porigin;
		this.destenation = pdestenation;
		this.departureTime = pdepartureTime;
		
		
	}
	public void setorigin(String setorigin) {
		this.origin=setorigin;
	}
	public String getorigin() {
		return origin;
	}
	public void setdestenation(String setdestenation) {
		this.destenation = destenation;	
	}
	public String getdestenation() {
		return destenation;
	}
	 public LocalDateTime getDepartureTime() {
	        return departureTime;
	    }
	    
	    public void setDepartureTime(LocalDateTime departureTime) {
	        this.departureTime = departureTime;
	    }
}
