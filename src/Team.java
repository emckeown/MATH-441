import java.util.List;

public class Team {

	private List<String> teamSchedule;
	private int homeIndex;
	private String teamName;
	private Location location;
	
	public Team(String team, int homeIndex) {
		this.teamName = team;
		this.homeIndex = homeIndex;
	}
	
	//Creates 'bye'
	public Team(String status) {
		teamName = status;
	}
	
	public List<String> getTeamSchedule() {
		return teamSchedule;
	}
	
	public String getTeamName() {
		return teamName;
	}
	
	public int getHomeIndex() {
		return homeIndex;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(double lat, double lon) {
		location = new Location(lat, lon);
	}
	
	public void setTeamSchedule(int day, String team) {
		//TODO: not sure if this will take a String or a team object...
	}
	
	
}