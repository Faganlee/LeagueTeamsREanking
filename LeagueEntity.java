public class LeagueEntity {

    private String teamName;
    private int points;

    public LeagueEntity(String teamName, int points) {
        this.teamName = teamName;
        this.points = points;
    }

    public LeagueEntity(String s) {
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void addPoints(int points){

        this.points += points;
    }
}
