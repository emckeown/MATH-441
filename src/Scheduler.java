import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Scheduler {
	private static int iterations = 10000;
	private static int curr = 0;
	private static int numberChanges = 1;
	
	private static int withoutChange = 0;
	
	private static Random random;
	
	private static List<Integer> validMoveFromList;
	private static List<Integer> validMoveToList;
	
	private static List<Integer> moveFromList;
	
	private static List<Team> homeTeamList;
	private static List<Team> awayTeamList;
	static List<Team> changedTeams;
	
	private static List<Integer> allStarBreak;
	private static String startOfBreak = "01/22/2015"; 
	private static String endOfBreak = "01/26/2015";
	
	private static List<Team> teamList;
	
	private static String teamFile = "teams.txt";
	private static String scheduleFile = "2014_2015_NHL_Schedule.csv";
	
	private static Team bye;
	private static Team awayTeam2;
	
	private static int days = 190;
	private static int teams = 30;
	
	private static double distance = 0;
	private static double newDistance;
	
	static FileWriter fileWriter = null;
	
	public static double[] dist;

	
	public static void main(String[] args) {
		
		teamList = new ArrayList<Team>(teams);
		setUpTeams();
		
		allStarBreak = new ArrayList<>();
		
		setUpSchedule();
		
		setTeamDistances();
		boolean valid = checkValid();
		setDistance();
		
		teamList.get(0).printTeamSchedule();
				
		validMoveFromList = new ArrayList<>();
		validMoveToList = new ArrayList<>();
		
		moveFromList = new ArrayList<>();
		
		homeTeamList = new ArrayList<Team>();
		awayTeamList = new ArrayList<Team>();
		
		
		
		random = new Random();
//		printmap.print(teamList.get(13));
		
		
		writeToFile("before.txt");
		System.out.println(distance);
		
//		boolean valid = checkValid();
		int test = 0;
		while (!checkValid() && curr < 100000) {
			numberChanges = 1;
			findNewRandom();
			test++;
			if (test == 1000) {
				writeToFile("randomSchedule.txt");
			}
			System.out.println(test);
			curr++;
		}
		numberChanges = 1;
		curr = 0;
		System.out.println(distance);
		writeToFile("randomSchedule.txt");
		
		while (curr<iterations) {
			localSearch();
			
		}
		System.out.println(distance);
		writeToFile("test.txt");
		
//		printgraph.print(dist, (iterations/1000) + 1);
//		printmap.print(teamList.get(13));
		

		
	}
	
	public static void localSearch() {
		curr++;

		removeGames();
		addGames();
		
	
		
//		System.out.println(distanceTravelled());
		//printSchedule();
		boolean isBetter = isDistanceLess();
		boolean valid = checkValid();
		if ((isBetter && valid) )
				//|| (withoutChange>15 && valid)) 
				{
			for (int i = 0; i<teamList.size(); i++) {
				teamList.get(i).setScheduleToNew();
				distance = newDistance;
				withoutChange = 0;
			}
		}
		else {
		for (int i = 0; i<teamList.size(); i++) {
			teamList.get(i).resetSchedule(allStarBreak);
			withoutChange++;
			}
		}
//		System.out.println(distance);

		
	}
	
	public static void findNewRandom() {
		curr++;

		removeGames();
		addGames();
		
	
		if (checkNumberGames()) {
			for (int i = 0; i<teamList.size(); i++) {
				teamList.get(i).setScheduleToNew();
				distance = newDistance;
				withoutChange = 0;
			}
		}
		else {
			for (int i = 0; i<teamList.size(); i++) {
				teamList.get(i).resetSchedule(allStarBreak);
				withoutChange++;
				}
			}
//		System.out.println(distance);

		
	}
	
	
	
	private static boolean checkValid() {
		for (int i = 0; i<teams; i++) {
			if (!teamList.get(i).isValidSchedule(allStarBreak)) {
				return false;
			}
		}
		return true;
	}
	
	private static boolean checkNumberGames() {
		for (int i = 0; i<teams; i++) {
			if (!teamList.get(i).checkNumberGames()) {
				return false;
			}
		}
		return true;
	}

	private static void setValidMoveFromList(Team awayTeam) {
		validMoveFromList.clear();
		for (int i = 0; i<days; i++) {
			if (awayTeam.getElement(awayTeam.getNewSchedule(), i).getHomeIndex() >= 0 &&
					awayTeam.getElement(awayTeam.getNewSchedule(), i).getHomeIndex() != awayTeam.getHomeIndex()) {
				validMoveFromList.add(i);
				
			}		
		}
	}
	
	private static void setValidMoveToList (Team homeTeam, Team awayTeam) {
		validMoveToList.clear();

		for (int i = 0; i<days; i++) {
			if (awayTeam.getElement(awayTeam.getNewSchedule(), i).getHomeIndex() < 0 && 
					homeTeam.getElement(homeTeam.getNewSchedule(), i).getHomeIndex() < 0) {
				validMoveToList.add(i);
			}		
		}
		int size = validMoveToList.size();
		validMoveToList.removeAll(allStarBreak);
		size = validMoveToList.size();
		size = 0;
	}
	
	private static void removeGames() {
		boolean numberGames = checkNumberGames();
		if (numberGames) {
			
		}
		homeTeamList.clear();
		moveFromList.clear();
		awayTeamList.clear();
		for (int i = 0; i < numberChanges; i++) {
			
			awayTeamList.add(teamList.get(random.nextInt(teams)));
			setValidMoveFromList(awayTeamList.get(i));
			int size = validMoveFromList.size();
			
			moveFromList.add(validMoveFromList.get(random.nextInt(validMoveFromList.size())));
			
			int moveFrom = moveFromList.get(i);
			Team awayTeam = awayTeamList.get(i);
			

			setHomeTeam(moveFrom, awayTeam);
			Team homeTeam = homeTeamList.get(i);
			
			awayTeam.setNewSchedule(bye, moveFrom);
			homeTeam.setNewSchedule(bye, moveFrom);
			
			size = 0;

			
			
		}
		int size = awayTeamList.size();
		size = 0;
		
	}
	
	
	private static void setHomeTeam(int moveFrom, Team awayTeam) {
			Team homeTeam = awayTeam.getElement(awayTeam.getNewSchedule(), moveFrom);
			homeTeamList.add(homeTeam);
	}
	
	
	private static void addGames() {

		for (int i = 0; i < numberChanges; i++) {
			Team awayTeam = awayTeamList.get(i);
			int size = awayTeamList.size();
			
			Team homeTeam = homeTeamList.get(i);
			size = homeTeamList.size();
			
					
			setValidMoveToList(homeTeam, awayTeam);
			
			if (validMoveToList.size() > 0){
				int moveTo =validMoveToList.get(random.nextInt(validMoveToList.size()));
			
				homeTeam.setNewSchedule(homeTeam, moveTo);
				awayTeam.setNewSchedule(homeTeam, moveTo);
				
				size = 0;
			}						
		}
	}
	
	private static void setUpNewSchedule() {
		
	    String str;
	    String[] parse = null;
	    String home;
	    String away;
	    String date;
	    int dayIndex = 0;
		try {

			BufferedReader in = new BufferedReader(new FileReader(scheduleFile));		    
		    int game = 1;
		    while ((str = in.readLine()) != null) {
		    	parse = str.split(",");
		    	
		    	away = parse[0].trim();
		    	
		    	home = parse[1].trim();
		    	
		    	date = parse[2];
		    	

		    	dayIndex = Integer.valueOf(date);

		    	
		    	Team homeTeam = null;
		    	Team awayTeam = null;
		    	for (int i = 0; i < teams; i++) {
		    		Team team = teamList.get(i);
		    		if (home.equals(team.getTeamName())) {
		    			homeTeam = team;
		    		}
		    		else if (away.equals(team.getTeamName())) {
		    			awayTeam = team;
		    		}
		    	}
		    	
		    	homeTeam.setTeamSchedule(homeTeam, dayIndex);
		    	awayTeam.setTeamSchedule(homeTeam, dayIndex);
		    		

		    in.close();
		    }	
		} catch(IOException e) {
			
		}
		
	}
	
	private static void setUpSchedule() {
	
	    String str;
	    String[] parse = null;
	    String home;
	    String away;
	    String date;
	    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	    Date gameDate;
	    Date startDate = null;
	    int dayIndex = 0;
		try {

			BufferedReader in = new BufferedReader(new FileReader(scheduleFile));		    
		    int game = 1;
		    while ((str = in.readLine()) != null) {
		    	parse = str.split(",");
		    	
		    	away = parse[0].trim();
		    	
		    	home = parse[1].trim();
		    	
		    	date = parse[2];
		    	
		    	try {
		    		gameDate = df.parse(date);
		    		System.out.println(gameDate);
		    		if (game == 1) {
		    			startDate = gameDate;
		    			
		    		}
		    		else {
		    			dayIndex = daysBetween(startDate, gameDate);
		    			System.out.println(dayIndex);
		    		}
		    	
		    	Team homeTeam = null;
		    	Team awayTeam = null;
		    	for (int i = 0; i < teams; i++) {
		    		Team team = teamList.get(i);
		    		if (home.equals(team.getTeamName())) {
		    			homeTeam = team;
		    		}
		    		else if (away.equals(team.getTeamName())) {
		    			awayTeam = team;
		    		}
		    	}
		    	
		    	homeTeam.setTeamSchedule(homeTeam, dayIndex);
		    	awayTeam.setTeamSchedule(homeTeam, dayIndex);
		    		
		    	} catch (ParseException e) {
		    		
		    	}
		    	game++;
		    }
		    in.close();
			
		} catch(IOException e) {
			
		}
		
		try {
    		
    		int breakStart = daysBetween(startDate, df.parse(startOfBreak));
    		int endBreak = daysBetween(startDate, df.parse(endOfBreak));
    		
    		for (int i = breakStart; i <= endBreak; i++) {
    			allStarBreak.add(i);
    			System.out.println(i);
    			
    		}
		} catch( ParseException e) {
		
		}	
	}

	private static int daysBetween(Date start, Date end) {
		return (int) ( ( end.getTime() - start.getTime()) /(1000*60*60*24) );
	}


	private static void setUpTeams() {
		bye = new Team("BYE");

			try {
				BufferedReader in = new BufferedReader(new FileReader(teamFile));
			    String str;
			    String[] parse = null;
			    int teamIndex = 0;
			    while ((str = in.readLine()) != null) {
			    	parse = str.split(",");
			    	Team t = new Team(parse[0].trim(), teamIndex, days);
			    	t.setLocation(Double.parseDouble(parse[1]), Double.parseDouble(parse[2]));
			    	teamList.add(t.getHomeIndex(), t);
			    	teamIndex++;
			        System.out.println(t.getTeamName() + t.getHomeIndex());
			        for (int i = 0; i<days; i++) {
			        	t.addGames(bye, i);
			        }
			        
			    }
			   
			    in.close();
			    
				
			} catch(IOException e) {
				
			}	
		
	}
	
	private static void printSchedule() {
		for (int i = 0; i<teams; i++) {
			teamList.get(i).printTeamSchedule();
		}
	}
	
	private static void setTeamDistances() {
		for (int i = 0; i<teams; i++) {
			teamList.get(i).teamScheduleDistance(teamList.get(i).getTeamSchedule(), allStarBreak);
		}
	}
	
	private static void setDistance() {
		for (int i = 0; i<teams; i++) {
			distance = distance + teamList.get(i).getDistance();
		}
	}
	
	private static boolean isDistanceLess() {
		newDistance = 0;
		changedTeams = new ArrayList<Team>();
		for (int i = 0; i<awayTeamList.size(); i++) {
			if (!changedTeams.contains(awayTeamList.get(i))) {
				changedTeams.add(awayTeamList.get(i));
			}
		}
		
		for (int i = 0; i<homeTeamList.size(); i++) {
			if (!changedTeams.contains(homeTeamList.get(i))) {
				changedTeams.add(homeTeamList.get(i));
			}
		}
		
		for (int i = 0; i<changedTeams.size(); i++) {
			changedTeams.get(i).teamScheduleDistance(changedTeams.get(i).getNewSchedule(), allStarBreak);
		}
		
		for (int i = 0; i<teams; i++) {
			newDistance = newDistance + teamList.get(i).getDistance();
		}
		
		if (newDistance <= distance) {
			
			return true;
		}
		
		return false;
		
	}
	
	private static void writeToFile(String file) {

		try {
		fileWriter = new FileWriter(file);

			for (int i = 0; i< teams; i++) {
				Team t = teamList.get(i);
				for (int j = 0; j < t.getTeamSchedule().size(); j++) {
					Team home = t.getTeamSchedule().get(j);
					if (home.getHomeIndex() >= 0 && home.getHomeIndex() != t.getHomeIndex()) {
						fileWriter.append(t.getTeamName());
						fileWriter.append(", ");
						fileWriter.append(home.getTeamName());
						fileWriter.append(", ");
						fileWriter.append(String.valueOf(j));
						fileWriter.append("\n");

					}
				}

			}
		} catch (Exception e) {
		            System.out.println("Error in CsvFileWriter !!!");
		            e.printStackTrace();
		        } finally {
		            try {
		                fileWriter.flush();
		                fileWriter.close();
		            } catch (IOException e) {
		                System.out.println("Error while flushing/closing fileWriter !!!");
		                e.printStackTrace();
		            }
		        }

	}
	
	private static String intToDate(int dayIndex) {
		String date = new String();
		return date;
	}
	

}



