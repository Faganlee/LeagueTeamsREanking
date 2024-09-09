import java.io.*;
import java.nio.file.*;
import java.util.*;

public class LeagueService {
    private Map<String, LeagueEntity> leagueTeams;
    public LeagueService() {
        this.leagueTeams = new HashMap<>();
    }
    public void matchProcessing(String match) {
        String[] parts = match.split(", ");

        if (parts.length != 2) {
            System.err.println("Invalid input format: " + match);
            return;
        }

        String[] team1Data = parts[0].split(" ");
        String[] team2Data = parts[1].split(" ");

        if (team1Data.length < 2 || team2Data.length < 2) {
            System.err.println("Invalid team data: " + match);
            return;
        }

        String team1Name = getTeamName(team1Data);
        String team2Name = getTeamName(team2Data);

        int team1Score, team2Score;

        try {
            team1Score = Integer.parseInt(team1Data[team1Data.length - 1]);
            team2Score = Integer.parseInt(team2Data[team2Data.length - 1]);
        } catch (NumberFormatException e) {
            System.err.println("Invalid score format: " + match);
            return;
        }

        LeagueEntity team1 = leagueTeams.computeIfAbsent(team1Name, LeagueEntity::new);
        LeagueEntity team2 = leagueTeams.computeIfAbsent(team2Name, LeagueEntity::new);

        team1.setTeamName(team1Name);
        team2.setTeamName(team2Name);

        if (team1Score > team2Score) {
            team1.addPoints(3);
        } else if (team1Score < team2Score) {
            team2.addPoints(3);
        } else {
            team1.addPoints(1);
            team2.addPoints(1);
        }
    }

    public void rankCalculation() {
        List<LeagueEntity> ranking = new ArrayList<>(leagueTeams.values());
        ranking.sort((team1, team2) -> {
            if (team1.getPoints() != team2.getPoints()) {
                return Integer.compare(team2.getPoints(), team1.getPoints());
            }
            return team1.getTeamName().compareTo(team2.getTeamName());
        });

        int rank = 1;
        int prevPoints = -1;
        for (int i = 0; i < ranking.size(); i++) {
            LeagueEntity team = ranking.get(i);
            if (team.getPoints() != prevPoints) {
                rank = i + 1;
            }
            System.out.printf("%d. %s, %d pt%s\n", rank, team.getTeamName(), team.getPoints(), team.getPoints() == 1 ? "" : "s");
            prevPoints = team.getPoints();
        }
    }

    private String getTeamName(String[] teamData) {
        return String.join(" ", Arrays.copyOfRange(teamData, 0, teamData.length - 1));
    }

    public List<String> readFromStdIn() throws IOException {
        List<String> inputLines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter the match results:");
        System.out.println("(Please press enter on an empty line to finish)");
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            inputLines.add(line);
        }
        return inputLines;
    }

    public List<String> readFromFile(String filename) throws IOException {
        return Files.readAllLines(Paths.get(filename));
    }
}
