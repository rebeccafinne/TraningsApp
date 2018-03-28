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
    private FavoriteData favoriteData;
    private HashMap<String, String> favorites;
    private FirebaseDatabase db;
    private DatabaseReference ref, favRef;





    public UserInformation() {
    }

    public UserInformation(String userId, String displayName, String email){
        this.userId = userId;
        this.displayName = displayName;
        this.email = email;
        this.points = 0;
        //Used to sort users from highetst points to lowest
        this.negPoints = points * -1;
        //this.favorites = new HashMap<>();
        db = FirebaseDatabase.getInstance();
        ref = db.getReference().child("users");
        favRef = db.getReference().child("users");

        //this.favoriteData = (FavoriteData) getApplicationContext();
      /*  ref.orderByChild("points").equalTo(10).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                System.out.println(dataSnapshot.child("points").getValue());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/





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

    public HashMap<String, String> getFavorites(){
        return this.favorites;
    }


}
