import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Schedule {

	public Team[][] schedule;
	private int days;
	private int teams;
	private List<String> homeSchedule;
	private int numberAwayGamesAllowed = 2;
	private int numberDaysAway = 4;
	
	
	public Schedule() {
		this.days = 11;
		this.teams = 5;
		
		this.schedule = new Team[days][teams];		
	}
	
//	public void setAllToFree() {
//		for (int i=0; i<=days; i++) {
//			for (int j=0; j<teams; j++){
//				schedule[i][j] = "FREE";
//			}
//		}
//	}
	

	
	public int getNumberTeams() {
		return teams;
	}
	
	public int getNumberDays() {
		return days;
	}
//	
//	public Team getElement(int day, int team){
//		return schedule[day][team];
//	}
//	
//	public boolean checkValidSchedule(){
//		//make sure the team is not playing against itself
//		for (int j = 0; j<teams; j++){
//			for (int i = 1; i<=days; i++){
//				if (this.getElement(i, j) == this.getElement(0, j)){
//					return false;
//				}
//			}
//		}
//		//Check that every team hosts every other team once
//		List<String> opponents = new ArrayList<String>();
//		for (int j = 0; j<teams; j++){
//			opponents.clear();
//			for (int i = 0; i<=days; i++){
//				if (!this.getElement(i, j).equals("X") && !this.getElement(i, j).equals("FREE")){
//					opponents.add(this.getElement(i, j));
//				}
//			}
//			if (opponents.size() != (teams)){
//			return false;
//			}
//			if (!opponents.contains("VAN") || !opponents.contains("CAL")
//					|| !opponents.contains("EDM")){
//				
//				return false;
//			}
//		
//		}
//		for (int j = 0; j < teams; j++) {
//			String homeTeam = this.getElement(0, j);
//			homeSchedule.clear();
//			for (int i=0; i <= days; i++) {
//				//Case where they're playing a home game
//				if (!this.getElement(i, j).equals("FREE") && !this.getElement(i, j).equals("X")) {
//					homeSchedule.add(homeTeam);
//				}
//				//Case where they're playing an away game
//				else if (!this.getElement(i,j).equals("FREE")) {
//					for (int h=0; h< teams; h++) {
//						if (this.getElement(i, h).equals(homeTeam)) {
//							homeSchedule.add(this.getElement(0, h));
//						}
//					}
//				}	
//			}
//			int countAwayGames = 0;
//			for (int i = 0; i < homeSchedule.size(); i++) {
//				if (!homeSchedule.get(i).equals(homeTeam)){
//					countAwayGames++;
//					if (countAwayGames > numberAwayGamesAllowed) {
//						return false;
//					}
//				}
//				else countAwayGames = 0;
//			}
//			
//		}
//		
//		return true;
//	}
//	
//	public int distanceBetweenCities(String cityA, String cityB) {
//		if (cityA.equals(cityB)){
//			return 0;
//		}
//		else if (cityA.equals("CAL") || cityB.equals("CAL") && cityA.equals("VAN") || cityB.equals("VAN")) {
//			return 970;
//		}
//		else if (cityA.equals("CAL") || cityB.equals("CAL") && cityA.equals("EDM") || cityB.equals("EDM")) {
//			return 299;
//		}
//		else if (cityA.equals("VAN") || cityB.equals("VAN") && cityA.equals("EDM") || cityB.equals("EDM")) {
//			return 1159;
//		}
//		
//		return 0;
//		
//	}
//	
//	public int teamScheduleDistance(List<String> travel) {
//		int size = travel.size();
//		int distance = 0;
//		for (int i=0; i<size - 1; i++) {
//			distance = distance + this.distanceBetweenCities(travel.get(i), travel.get(i+1));
//		}
//		return distance;
//		
//	}
//	
//	public int distanceTravelled() {
//		homeSchedule = new ArrayList<String>();
//		int distance = 0;
//		for (int j = 0; j < teams; j++) {
//			String homeTeam = this.getElement(0, j);
//			homeSchedule.clear();
//			for (int i=0; i <= days; i++) {
//				//Case where they're playing a home game
//				if (!this.getElement(i, j).equals("FREE") && !this.getElement(i, j).equals("X")) {
//					homeSchedule.add(homeTeam);
//				}
//				//Case where they're playing an away game
//				else if (!this.getElement(i,j).equals("FREE")) {
//					for (int h=0; h< teams; h++) {
//						if (this.getElement(i, h).equals(homeTeam)) {
//							homeSchedule.add(this.getElement(0, h));
//						}
//					}
//				}
//			}
//			distance = distance + this.teamScheduleDistance(homeSchedule);
//			
//		}
//		return distance;
//	}
//	
	public void printSchedule() {
		System.out.println(Arrays.deepToString(this.schedule));
	}
	
}
