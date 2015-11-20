
public class Location {
	private double lattitude;
	private double longitude;
	
	public Location (double lat, double lon) {
		lattitude = lat;
		longitude = lon;
	}
	
	public double getLat() {
		return lattitude;
	}
	
	public double getLon() {
		return longitude;
	}
	
}
