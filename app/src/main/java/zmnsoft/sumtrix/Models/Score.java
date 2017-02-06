package zmnsoft.sumtrix.Models;

import static android.R.attr.name;

/**
 * Created by Zaid on 1/30/2017.
 */

public class Score { // This is POJO class represent score for player

    private String ID;
    private int score;
    private int timeFinished;

    public Score() { // empty constructor
    }

    public Score(String ID, int score, int timeFinished) {
        this.ID = ID;
        this.score = score;
        this.timeFinished = timeFinished;
    }

    public String getName() {
        return ID;
    }

    public void setName(String ID) {
        this.ID = ID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTimeFinished() {
        return timeFinished;
    }

    public void setTimeFinished(int timeFinished) {
        this.timeFinished = timeFinished;
    }

    @Override
    public String toString() {
        return "Score{" +
                "name=" + name +
                ", score=" + score +
                ", timeFinished=" + timeFinished +
                '}';
    }
}
