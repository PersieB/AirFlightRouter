
public class Airline {
	
	private String airline_id;
	private String name;
	private String alias;
	private String iata_code;
	private String icao_code;
	private String callsign ;
	private String country;
	
	public Airline(String airline_id, String name, String alias, String iata_code, String icao_code, String callsign, String country) {
		this.airline_id = airline_id;
		this.name = name;
		this.alias = alias;
		this.iata_code = iata_code;
		this.icao_code = icao_code;
		this.callsign = callsign;
		this.country = country;
	}

	// Getters
	public String getAirline_id() {
		return this.airline_id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getAlias() {
		return this.alias;
	}
	
	public String getIata_code() {
		return this.iata_code;
	}
	
	public String getIcao_code() {
		return this.icao_code;
	}
	
	public String getCallsign() {
		return this.callsign;
	}
	
	public String getCountry() {
		return this.country;
	}
	
	// Setters
	public void setAirline_id(String airlineID) {
		this.airline_id = airlineID;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public void setIata_code(String iata_code) {
		this.iata_code = iata_code;
	}
	
	public void setIcao_code(String icao_code) {
		this.icao_code = icao_code;
	}
	
	public void setCallsign(String callsign) {
		this.callsign = callsign;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
}
