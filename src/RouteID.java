
public class RouteID {
	private String source_airport_ID;
	private String destination_airport_ID;
	
	public RouteID(String id1, String id2) {
		this.source_airport_ID = id1;
		this.destination_airport_ID = id2;
	}
	
	public String getSourceID() {
		return this.source_airport_ID;
	}
	
	public String getDestinationID() {
		return this.destination_airport_ID;
	}
}
