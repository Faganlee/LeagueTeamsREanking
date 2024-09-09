import java.io.IOException;
import java.util.List;

public class LeagueApplication {

    public static void main(String[] args) {
        LeagueService leagueService = new LeagueService();

        List<String> matches;
        try {
            if (args.length > 0) {
                String filename = args[0];
                matches = leagueService.readFromFile(filename);
            } else {
                matches = leagueService.readFromStdIn();
            }

            for (String match : matches) {
                leagueService.processMatch(match);
            }

            leagueService.rankCalculation();
        } catch (IOException e) {
            System.out.println("Error reading file or stdin input: " + e.getMessage());
        }
    }
}
