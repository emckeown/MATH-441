import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InitializeSchedule {

	
	public InitializeSchedule() {
		setUpTeams();
		setUpSchedule();
	}

	private void setUpSchedule() {
		try {
			BufferedReader in = new BufferedReader(new FileReader("2014_2015_NHL_Schedule.csv"));
		    String str;
		    String[] parse = null;
		    int day = 0;
		    while ((str = in.readLine()) != null) {
		    	
		    }
		    in.close();
			
		} catch(IOException e) {
			
		}
		
	}

	private void setUpTeams() {
		try {
			BufferedReader in = new BufferedReader(new FileReader("teams.txt"));
		    String str;
		    String[] parse = null;
		    int teamIndex = 0;
		    while ((str = in.readLine()) != null) {
		    	parse = str.split(",");
		    	Team t = new Team(parse[0], teamIndex);
		    	t.setLocation(Double.parseDouble(parse[1]), Double.parseDouble(parse[2]));
		        System.out.println(str);
		    }
		    in.close();
			
		} catch(IOException e) {
			
		}	
	}
	
	
}
