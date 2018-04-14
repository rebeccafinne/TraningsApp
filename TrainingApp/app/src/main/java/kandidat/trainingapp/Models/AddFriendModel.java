package kandidat.trainingapp.Models;

import android.widget.ArrayAdapter;

/**
 * Created by rasti on 2018-04-14.
 */

public class AddFriendModel  {
    String userName,UID;

    public AddFriendModel(String userName, String UID) {
        this.userName = userName;
        this.UID = UID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUID() {
        return UID;
    }
}
