package kandidat.trainingapp;

import java.util.ArrayList;

/**
 * Created by rasti on 2018-02-12.
 */

public class UserInformation {

    public String userId;
    public String username;
    public String email;
    public int points;
    public ArrayList<UserInformation> friends;



    public UserInformation() {
    }

    public UserInformation(String userId, String username, String email){
        this.username = username;
        this.email = email;
        this.userId = userId;
        this.points = 0;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public int getPoints() {
        return points;
    }
}
