package kandidat.trainingapp;

import java.util.ArrayList;

/**
 * Created by rasti on 2018-02-12.
 */

public class UserInformation {

    private String userId;
    private String displayName;
    private String email;
    private int points;
    public ArrayList<UserInformation> friends;



    public UserInformation() {
    }

    public UserInformation(String userId, String displayName, String email){
        this.displayName = displayName;
        this.email = email;
        this.userId = userId;
        this.points = 0;
    }

    public String getDisplayName() {
        return displayName;
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
