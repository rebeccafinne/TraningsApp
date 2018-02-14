package kandidat.trainingapp;

/**
 * Created by rasti on 2018-02-12.
 */

public class UserInformation {

    public String username;
    public String email;
    public int points;



    public UserInformation() {
    }

    public UserInformation(String username, String email){
        this.username = username;

        this.email = email;
        this.points = 0;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getPoints() {
        return points;
    }
}
