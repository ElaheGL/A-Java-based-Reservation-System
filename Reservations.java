import java.time.LocalDateTime;

public class Reservations {
	String origin,destenation,plate;
	String departureTime;
	int capacity;

	
	
	public Reservations(String porigin,String pdestenation,String pdepartureTime, int pcapacity) {
		this.origin = porigin;
		this.destenation = pdestenation;
		this.departureTime = pdepartureTime;
		 this.capacity = pcapacity;
		
		
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
	 public String getDepartureTime() {
	        return departureTime;
	    }
	    
	    public void setDepartureTime(String departureTime) {
	        this.departureTime = departureTime;
	    }
	    public void setcapacity(int capacity) {
			 this.capacity = capacity;
		 }
		 public int getcapacity() {
			 return capacity;
		 }
		

}


