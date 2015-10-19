package barqsoft.footballscores;

/**
 * Created by olgakuklina on 2015-10-18.
 */
public class WidgetMatchData {

    private final String homeTeamName;
    private final String awayTeamName;
    private final String score;
    private final String date;

    public WidgetMatchData(String homeTeamName, String awayTeamName, String score, String date) {
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.score = score;
        this.date = date;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public String getScore() {
        return score;
    }

    public String getDate() {
        return date;
    }
}
