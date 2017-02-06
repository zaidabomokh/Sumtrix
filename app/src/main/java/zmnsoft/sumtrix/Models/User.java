package zmnsoft.sumtrix.Models;

/**
 * Created by ANDROID on 14/12/2016.
 */

//pojo

public class User {

    private String ID;
    private String Name;
    private int highScore;

    public User() {
    }

    public User(String ID, String name) {
        this.ID = ID;
        Name = name;
        highScore = -1;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    @Override
    public String toString() {
        return "User{" +
                "ID='" + ID + '\'' +
                ", Name='" + Name + '\'' +
                '}';
    }
}
