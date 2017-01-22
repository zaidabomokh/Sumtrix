package zmnsoft.sumtrix;

/**
 * Created by ANDROID on 14/12/2016.
 */
public class User {

    private String ID;
    private String Name;

    public void setName(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public User(String user, String id) {
        setID(id);
        setName(user);
    }

    @Override
    public String toString() {
        return "User name : " + ID;
    }
}
