package kandidat.trainingapp.Models;

/**
 * Created by rasti on 2018-04-12.
 */

public class LeaderboardModel {
    String userName,points,UID;

    public LeaderboardModel(String userName, String points, String UID) {
        this.userName = userName;
        this.points = points;
    }

    public String getUserName() {
        return userName;
    }

    public String getPoints() {
        return points;
    }

    public String getUID(){
        return UID;
    }
}
