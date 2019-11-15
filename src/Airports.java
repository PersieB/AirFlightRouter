
public class Airports {
	private String airport_id;
	private String name;
	private String city;
	private String country;
	private String iata_code;
	private String icao_code;
	private double latitude;
	private double longitude;
	private double timezone;
	private double dst;
	private String tz_database_time;
	
	public Airports(String airport_id, String name, String city, String country, String iata_code, String icao_code, double latitude, 
			double longitude, double timezone, double dst, String tz_database_time) {
		this.airport_id = airport_id;
		this.name = name;
		this.city = city;
		this.country = country;
		this.iata_code = iata_code;
		this.icao_code = icao_code;
		this.latitude = latitude;
		this.longitude = longitude;
		this.timezone = timezone;
		this.dst = dst;
		this.tz_database_time = tz_database_time;
		
	}
	
	
	
	public String getAirport_id() {
		return this.airport_id;
		
	}
	public String getName() {
		return this.name;		
	}
	
	public String getCity() {
		return this.city;
	}
	
	public String getCountry() {
		return this.country;
	}
	public String getIata_code() {
		return this.iata_code;
	}
	
	public String getIcao_code() {
		return this.icao_code;
	}
	public double getLatitude() {
		return this.latitude;
	}
	public double getLongitude() {
		
		return this.longitude;
	}
	public double getTimezone() {
		return this.timezone;
		
	}
	public double getDst() {
		return this.dst;		
	}
	
	public String getTz_database_time() {
		return this.tz_database_time;
	}
	
	
	
	public void SetAirport_id(String airport_id) {
		this.airport_id = airport_id;
		
	}
	
	public void setName(String name) {
		this.name = name;		
	}
	
	public void setCity(String city) {
		 this.city = city;
	}
	
	public void setCountry(String country) {
		 this.country = country;
	}
	
	public void setIata_code(String iata_code) {
		 this.iata_code = iata_code;
	}
	
	public void setIcao_code(String icao_code) {
		this.icao_code = icao_code;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public void setLongitude(double longitude) {
		
		 this.longitude = longitude;
	}
	
	public void setTimezone(double timezone) {
		this.timezone = timezone;	
	}
	
	public void setDst(double dst) {
		this.dst = dst;		
	}
	
	public void setTz_database_time(String tz_database_time) {
		 this.tz_database_time = tz_database_time;
	}
	
	
}
