package zmnsoft.sumtrix;

/**
 * Created by ANDROID on 14/12/2016.
 */
public class User {

    private String ID;

    public String getID() {
        return ID;
    }

    public User(String ID) {
        this.ID = ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public User() {}

    @Override
    public String toString() {
        return "User name : " + ID;
    }
}
