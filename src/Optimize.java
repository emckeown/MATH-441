import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Optimize {

	private static int iterations = 1;
	private static int curr = 0;
	private static int numberChanges = 3;
	
	private static Random random;
	
	private static Schedule s;
	private static Schedule newSchedule;
	
	private static List<Integer> validMoveFromList;
	private static List<Integer> validMoveToList;
	
	private static List<Integer> moveFromList;
	
	private static List<Integer> homeTeamList;
	private static List<Integer> awayTeamList;
	
	private static HashMap<String, Team> teamList;
	
	private static String teamFile = "toyTeams.txt";
	private static String scheduleFile = "toySchedule.csv";
	
	
	public static void main(String[] args) {
		s = new Schedule();
		newSchedule = new Schedule();
		
		//new InitializeSchedule();
		teamList = new HashMap<String, Team>();
		setUpTeams();
		
		setUpSchedule();
		
		validMoveFromList = new ArrayList<>();
		validMoveToList = new ArrayList<>();
		
		moveFromList = new ArrayList<>();
		
		homeTeamList = new ArrayList<>();
		awayTeamList = new ArrayList<>();
		
//		s.setAllToFree();
		
//		s.schedule[0][0] = "VAN";
//		s.schedule[0][1] = "CAL";
//		s.schedule[0][2] = "EDM";
//		s.schedule[0][3] = "LA";
//		s.schedule[0][4] = "SJ";
//		
//		s.schedule[1][0] = "CAL";
//		s.schedule[1][1] = "X";
//		s.schedule[1][2] = "LA";
//		s.schedule[1][3] = "X";
//		
//		s.schedule[2][0] = "X";
//		s.schedule[2][1] = "LA";
//		s.schedule[2][3] = "X";
//		s.schedule[2][4] = "VAN";
//		
//		s.schedule[3][1] = "X";
//		s.schedule[3][2] = "X";
//		s.schedule[3][3] = "EDM";
//		s.schedule[3][4] = "CAL";
//		
//		s.schedule[4][0] = "LA";
//		s.schedule[4][2] = "SJ";
//		s.schedule[4][3] = "X";
//		s.schedule[4][4] = "X";
//		
//		s.schedule[5][0] = "X";
//		s.schedule[5][1] = "VAN";
//		s.schedule[5][3] = "SJ";
//		s.schedule[5][4] = "X";
//		
//		s.schedule[6][1] = "X";
//		s.schedule[6][2] = "X";
//		s.schedule[6][3] = "CAL";
//		s.schedule[6][4] = "EDM";
//		
//		s.schedule[7][0] = "EDM";
//		s.schedule[7][1] = "SJ";
//		s.schedule[7][2] = "X";
//		s.schedule[7][4] = "X";
//		
//		s.schedule[8][0] = "X";
//		s.schedule[8][2] = "VAN";
//		s.schedule[8][3] = "X";
//		s.schedule[8][4] = "LA";
//		
//		s.schedule[9][0] = "X";
//		s.schedule[9][1] = "X";
//		s.schedule[9][2] = "CAL";
//		s.schedule[9][3] = "VAN";
//		
//		s.schedule[10][0] = "SJ";
//		s.schedule[10][1] = "EDM";
//		s.schedule[10][2] = "X";
//		s.schedule[10][4] = "X";		

//		System.out.println(s.distanceTravelled());
//		s.printSchedule();

		random = new Random();
		
//		localSearch(s);
		
		//System.out.println(s.distanceTravelled());
		//s.printSchedule();		
	}
	
	
	private static void setUpSchedule() {

		setAllGamesAsBye();
	
	    String str;
	    String[] parse = null;
	    String home;
	    String away;
	    String date;
	    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	    Date gameDate;
	    Date startDate = null;
	    int dayIndex;
		try {

			BufferedReader in = new BufferedReader(new FileReader(scheduleFile));		    
		    int game = 1;
		    while ((str = in.readLine()) != null) {
		    	parse = str.split(",");
		    	
		    	away = parse[0].trim();
		    	
		    	assert(teamList.containsKey(away));
		    	
		    	home = parse[1].trim();
		    	
		    	assert(teamList.containsKey(home));
		    	
		    	date = parse[2];
		    	
		    	try {
		    		gameDate = df.parse(date);
		    		System.out.println(gameDate);
		    		if (game == 1) {
		    			dayIndex = 0;
		    			startDate = gameDate;
		    		}
		    		else {
		    			dayIndex = daysBetween(startDate, gameDate);
		    			System.out.println(dayIndex);
		    		}
		    		
		    	addGameToSchedule(teamList.get(home), teamList.get(away), dayIndex);
		    		
		    	} catch (ParseException e) {
		    		
		    	}
		    	game++;
		    }
		    in.close();
			
		} catch(IOException e) {
			
		}		
	}


	private static void setAllGamesAsBye() {
		for (int i=0; i<s.getNumberDays(); i++) {
			for (int j=0; j<s.getNumberTeams(); j++){
				s.schedule[i][j] = teamList.get("BYE");
			}
		}
	}
	
	private static int daysBetween(Date start, Date end) {
		return (int) ( ( end.getTime() - start.getTime()) /(1000*60*60*24) );
	}

	private static void setUpTeams() {
			try {
				BufferedReader in = new BufferedReader(new FileReader(teamFile));
			    String str;
			    String[] parse = null;
			    int teamIndex = 0;
			    while ((str = in.readLine()) != null) {
			    	parse = str.split(",");
			    	Team t = new Team(parse[0].trim(), teamIndex);
			    	t.setLocation(Double.parseDouble(parse[1]), Double.parseDouble(parse[2]));
			    	teamList.put(t.getTeamName(), t);
			        System.out.println(t.getTeamName());
			    }
			    in.close();
			    Team bye = new Team("BYE");
			    teamList.put(bye.getTeamName(), bye);
			    Team away = new Team("AWAY");
			    teamList.put(away.getTeamName(), away);
				
			} catch(IOException e) {
				
			}	
		
	}
	
	public Team getTeam(String team) {
		return teamList.get(team);
	}


//	public static void localSearch(Schedule s) {
//		curr++;
//		
//		for (int i=0; i<=s.getNumberDays(); i++) {
//			for (int j=0; j<s.getNumberTeams(); j++){
//				newSchedule.schedule[i][j] = s.schedule[i][j];
//			}
//		}

//		removeGames();
//		addGames();
//		
//		if (newSchedule.distanceTravelled() < s.distanceTravelled() && newSchedule.checkValidSchedule()) {
//			for (int i=0; i<=s.getNumberDays(); i++) {
//				for (int j=0; j<s.getNumberTeams(); j++){
//					s.schedule[i][j] = newSchedule.schedule[i][j];
//				}
//			}	
//		}	
//		
//		System.out.println(s.distanceTravelled());
//		s.printSchedule();
//		
//		if (curr<iterations) {
//			localSearch(s);
//			
//		}
//	}
//	
//	private static void setValidMoveFromList(int homeTeamIndex) {
//		validMoveFromList.clear();
//		for (int i = 1; i<s.getNumberDays() + 1; i++) {
//			if (!s.getElement(i, homeTeamIndex).equals("FREE") && 
//					!s.getElement(i, homeTeamIndex).equals("X")) {
//				validMoveFromList.add(i);
//				
//			}		
//		}
//	}
//	
//	private static void setValidMoveToList (int homeTeamIndex, int awayTeamIndex) {
//		validMoveToList.clear();
//		for (int i = 1; i<s.getNumberDays() + 1; i++) {
//			if (newSchedule.getElement(i, awayTeamIndex).equals("FREE") && newSchedule.getElement(i, homeTeamIndex).equals("FREE")) {
//				validMoveToList.add(i);
//			}		
//		}
//	}
	
//	private static void removeGames() {
//		homeTeamList.clear();
//		moveFromList.clear();
//		awayTeamList.clear();
//		for (int i = 0; i < numberChanges; i++) {
//			
//			homeTeamList.add(random.nextInt(s.getNumberTeams()));
//			setValidMoveFromList(homeTeamList.get(i));
//			
//			moveFromList.add(validMoveFromList.get(random.nextInt(validMoveFromList.size())));
//			
//			int moveFrom = moveFromList.get(i);
//			int homeTeamIndex = homeTeamList.get(i);
//			
//			setAwayTeamIndex(moveFrom, homeTeamIndex);
//			int awayTeamIndex = awayTeamList.get(i);
//			
//			newSchedule.schedule[moveFrom][homeTeamIndex] = "FREE";
//			newSchedule.schedule[moveFrom][awayTeamIndex] = "FREE";
//			
//		}
//		
//	}
//	
//	private static void setAwayTeamIndex(int moveFrom, int homeTeamIndex) {
//			
//			String awayTeam = s.getElement(moveFrom, homeTeamIndex);
//			
//			for (int i = 0; i<s.getNumberTeams(); i++) {
//				if (s.getElement(0, i).equals(awayTeam)) {
//					awayTeamList.add(i);
//					return;
//				}
//			}		
//	}
	
	
//	private static void addGames() {
//
//		for (int i = 0; i < numberChanges; i++) {
//			int awayTeamIndex = awayTeamList.get(i);
//			int moveFrom = moveFromList.get(i);
//			int homeTeamIndex = homeTeamList.get(i);
//					
//			setValidMoveToList(homeTeamIndex, awayTeamIndex);
//			
//			if (validMoveToList.size() > 0){
//				int moveTo =validMoveToList.get(random.nextInt(validMoveToList.size()));
//			
//				newSchedule.schedule[moveTo][homeTeamIndex] = s.schedule[moveFrom][homeTeamIndex];
//				newSchedule.schedule[moveTo][awayTeamIndex] = "X";
//			}						
//		}
//	}

	public static void  addByeToSchedule (Team team, int dayIndex) {
		int homeIndex = team.getHomeIndex();
		s.schedule[dayIndex][homeIndex] = team;
		
	}
	public static void  addGameToSchedule (Team home, Team away, int dayIndex) {
		
		int homeIndex = home.getHomeIndex();
		int awayIndex = away.getHomeIndex();
		
		s.schedule[dayIndex][homeIndex] = away;
		s.schedule[dayIndex][awayIndex] = teamList.get("AWAY");
	}
	
}
