package kandidat.trainingapp.Repositories;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by rasti on 2018-02-12.
 */

public class UserInformation {

    private String userId;
    private String displayName;
    private String email;
    private int points;
    private int negPoints;


    public UserInformation() {
    }

    public UserInformation(String userId, String displayName, String email){
        this.userId = userId;
        this.displayName = displayName;
        this.email = email;
        this.points = 0;
        //Used to sort users from highetst points to lowest
        this.negPoints = points * -1;

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

    public int getNegPoints() {return negPoints;}




}
