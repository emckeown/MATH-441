import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Team {
	
	private int homeIndex;
	private String teamName;
	private Location location;
	private List<Team> teamSchedule;
	private List<Team> newSchedule;
	private int numberHomeGames = 41;
	private int numberGames = 8;
	private int days;
	private int maxDaysOnRoad = 3;
	private int maxroadGames = 2;

	
	double c = 0.0174603175;
	double R = 6371000; // meters
	double p1;
	double p2;
	double p;
	double q;
	double a;
	double c2;
	double distance = 0;
	


	public Team(String team, int homeIndex, int numberDays) {
		this.teamName = team;
		this.homeIndex = homeIndex;	
		days = numberDays;
		teamSchedule = new ArrayList<Team>(days);
		newSchedule = new ArrayList<Team>(days);

	
	}


	
	public Team(String status) {
		teamName = status;
		homeIndex = -1;
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
	
	public void addGames(Team homeTeam, int dayIndex) {
		teamSchedule.add(dayIndex, homeTeam);
		newSchedule.add(dayIndex, homeTeam);
	}
	
	public void setTeamSchedule(Team homeTeam, int dayIndex) {
		teamSchedule.set(dayIndex, homeTeam);
		newSchedule.set(dayIndex, homeTeam);
	}
	
	public void setNewSchedule(Team homeTeam, int dayIndex){
		newSchedule.set(dayIndex, homeTeam);
	}
	
	public List<Team> getNewSchedule() {
		return newSchedule;
	}
	
	public List<Team> getTeamSchedule() {
		return teamSchedule;
	}
	
	public void setScheduleToNew() {
		for (int i=0; i<newSchedule.size(); i++) {
			teamSchedule.set(i, newSchedule.get(i));
		}
	}
	
	public void resetSchedule(List<Integer> allStarBreak) {
		for (int i=0; i<teamSchedule.size(); i++) {
			newSchedule.set(i, teamSchedule.get(i));
		}
		teamScheduleDistance(teamSchedule, allStarBreak);
	}
	
	public Team getElement(List<Team> schedule, int dayIndex) {
		return schedule.get(dayIndex);
	}
	
	public void teamScheduleDistance(List<Team> schedule, List<Integer> allStarBreak) {
		distance = 0;
		int size = schedule.size();
		List<Team> travelSchedule = new ArrayList<Team>();
		for (int i = 0; i<size; i++) {
			if (schedule.get(i).getHomeIndex() >=0) {
				travelSchedule.add(schedule.get(i));
			}
			
			//fake a trip home at the beginning of the all star break
			else if (i == allStarBreak.get(0)) {
				travelSchedule.add(this);
			}
		}
		
		// get the distance from home arena to first game
		distance = distanceBetweenCities(this, travelSchedule.get(0));
		
		for (int i=0; i<travelSchedule.size() -1; i++) {
			distance = distance + this.distanceBetweenCities(travelSchedule.get(i), travelSchedule.get(i+1));
		}
		
	}

	private double distanceBetweenCities(Team team1, Team team2) {
//			double c = 0.0174603175;
//			double R = 6371000; // meters
			
			p1 = team1.getLocation().getLat()*c;
			p2 = team2.getLocation().getLat()*c;
			p = (team2.getLocation().getLat() - team1.getLocation().getLat())*c;
			q = (team2.getLocation().getLon() - team1.getLocation().getLon())*c;

			a = Math.sin(p/2) * Math.sin(p/2) +
			       Math.cos(p1) * Math.cos(p2) *
			       Math.sin(q/2) * Math.sin(q/2);
			c2 = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

			double betweenCities = R * c2; 
			betweenCities = betweenCities*.001;
			return betweenCities; 
	}
	
	public void printTeamSchedule() {
		
		for (int i = 0; i<teamSchedule.size(); i++) {
			System.out.println(teamSchedule.get(i).getTeamName());
		}
		
		
	}
	
	public boolean isValidSchedule(List<Integer> allStarBreak) {
		int size = newSchedule.size();
		
		//Check that they're not on the road for more than 14 days
		int daysOnRoad = 0;
		for (int i = 0; i<size; i++) {
			
			if (allStarBreak.contains(i)) {
				daysOnRoad = 0;	
			}
			// reset to zero after home game
			else if (newSchedule.get(i).getHomeIndex() == homeIndex) {
				daysOnRoad = 0;
			}
			// increment for every away game
			else if (newSchedule.get(i).getHomeIndex() >= 0) {
				daysOnRoad++;
				if (daysOnRoad > maxDaysOnRoad) {
					return false;
				}
			}
			// increment on days off if they're away from home
			else if (daysOnRoad > 0) {
				daysOnRoad++;
				if (newSchedule.get(i).getHomeIndex() >= 0 && daysOnRoad > maxDaysOnRoad) {
					return false;
				}
			}
		}
		
		
		int fiveDayCount = 0;
		int daysOffInARow = 0;
		int numberGamesInARow = 0;
		for (int i = 0; i<size; i++) {
			
			if (newSchedule.get(i).getHomeIndex() >= 0) {
				numberGamesInARow++;
				if (numberGamesInARow > 2) {
					return false;
				}
				daysOffInARow = 0;
				fiveDayCount++;
				if (fiveDayCount > 5) {
					return false;
				}
			} 
			else {
				daysOffInARow++;
				if (numberGamesInARow < 2 || daysOffInARow >=2) {
					fiveDayCount = 0;
				}
				numberGamesInARow = 0;
			}
		}
		
		List<Team> travelSchedule = new ArrayList<Team>();
		//Check that they're playing 82 games
		for (int i = 0; i<size; i++) {
			if (newSchedule.get(i).getHomeIndex() >=0) {
				travelSchedule.add(newSchedule.get(i));
			}
			//fake a trip home at the beginning of the all star break
			else if (i == allStarBreak.get(0)) {
				travelSchedule.add(this);
			}
		}
		
		//add 1 to number of games to account for 'fake' home game
		if (travelSchedule.size() != numberGames) {
			return false;
		}
		
		//Check that they don't play more than 7 games a row away
		int roadGames = 0;
		for (int i = 0; i < travelSchedule.size(); i++) {
			if (travelSchedule.get(i).getHomeIndex() == homeIndex) {
				roadGames = 0;
			}
			else {
				roadGames++;
				if (roadGames > maxroadGames) {
					return false;
				}
			}
		}
		
		
		return true;
	}
	
	public boolean checkNumberGames() {
		List<Team> travelSchedule = new ArrayList<Team>();
		//Check that they're playing 82 games
		for (int i = 0; i<newSchedule.size(); i++) {
			if (newSchedule.get(i).getHomeIndex() >=0) {
				travelSchedule.add(newSchedule.get(i));
			}
		}

		if (travelSchedule.size() != numberGames ) {
			return false;
		}
		return true;
	}
	
	public double getDistance() {
		return distance;
	}
	
	

}

