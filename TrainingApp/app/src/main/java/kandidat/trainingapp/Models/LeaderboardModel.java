package kandidat.trainingapp.Models;

import android.support.annotation.NonNull;

/**
 * Created by rasti on 2018-04-12.
 */

public class LeaderboardModel implements Comparable {
    private String userName,points,UID;

    public LeaderboardModel(String userName, String points, String UID) {
        this.userName = userName;
        this.points = points;
        this.UID = UID;
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

    @Override
    public int compareTo(@NonNull Object anotherUser) throws ClassCastException {
        if (!(anotherUser instanceof LeaderboardModel))
            throw new ClassCastException("A User object expected.");

        int anotherPersonAge = Integer.parseInt(((LeaderboardModel) anotherUser).getPoints());
        return Integer.parseInt(this.getPoints()) - anotherPersonAge;
    }
}
