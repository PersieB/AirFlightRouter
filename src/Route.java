
public class Route {
	private String airline_code;
	private String airline_ID;
	private String source_airport_code;
	private String source_airport_ID;
	private String destination_airport_code;
	private String destination_airport_ID;
	private String codeshare;
	private String stops;
	private String equipment;
	
	public Route(String airline_code, String airline_ID, String source_airport_code, String source_airport_ID, 
			String destination_airport_code, String destination_airport_ID, String codeshare,String stops,String equipment) {
		this.airline_code = airline_code;
		this.airline_ID = airline_ID;
		this.source_airport_code = source_airport_code;
		this.source_airport_ID = source_airport_ID;
		this.destination_airport_code = destination_airport_code;
		this.destination_airport_ID = destination_airport_ID;
		this.codeshare = codeshare;
		this.stops = stops;
		this.equipment = equipment;
	}
	
	public String getAirlineCode() {
		return this.airline_code;
	}
	
	public String getAirlineID() {
		return this.airline_ID;
	}
	
	public String getSourceAirportCode() {
		return this.source_airport_code;
	}
	
	public String getSourceAirportID() {
		return this.source_airport_ID;
	}
	
	public String getDestinationAirportCode() {
		return this.destination_airport_code;
	}
	
	public String getDestinationAirportID() {
		return this.destination_airport_ID;
	}
	
	public String getCodeshare() {
		return this.codeshare;
	}
	
	public String getStops() {
		return this.stops;
	}
	
	public String getEquipment() {
		return this.equipment;
	}
	
	// Setters
	public void setAirlineCode(String airlineCode) {
		this.airline_code = airlineCode;
	}
	
	public void setAirlineID(String airlineID) {
		this.airline_ID = airlineID;
	}
	
	public void setSourceAirportCode(String sourceAirportCode) {
		this.source_airport_code = sourceAirportCode;
	}
	
	public void setSourceAirportID(String sourceAirportID) {
		this.source_airport_ID = sourceAirportID;
	}
	
	public void setDestinationAirportCode(String destinationAirportCode) {
		this.destination_airport_code = destinationAirportCode;
	}
	
	public void setDestinationAirportID(String destinationAirportID) {
		this.destination_airport_ID = destinationAirportID;
	}
	
	public void setCodeShare(String codeShare) {
		this.codeshare = codeShare;
	}
	
	public void setStops(String stops) {
		this.stops = stops;
	}
	
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
}
