package kandidat.trainingapp.Repositories;

import android.support.annotation.NonNull;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by rasti on 2018-02-12.
 */

public class UserInformation {

    private String userId;
    private String displayName;
    private String email;
    //private List<String> friends;
    private int points;
    private int level;



    public UserInformation() {
    }

    public UserInformation(String userId, String displayName, String email){
        this.userId = userId;
        this.displayName = displayName;
        this.email = email;
        this.points = points;
        this.level = 1;
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

    public int getLevel() {
        return level;
    }


}
