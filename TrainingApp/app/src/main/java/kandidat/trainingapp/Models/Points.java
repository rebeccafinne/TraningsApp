package kandidat.trainingapp.Models;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import kandidat.trainingapp.Activities.AnotherUserActivity;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by rebeccafinne on 2018-04-06.
 */

public class Points {


    private DatabaseReference userRef;





    public Points(){

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = db.getReference("users");

        userRef = ref.child(user.getUid());


    }


    /**
     * Updates the user's points in the database
     * @param newPoint - the point to be added
     */
    public void calcualtePoints(Integer newPoint){


        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("points").getValue(Integer.class) == 0){
                    Integer currentLevel = dataSnapshot.child("level").getValue(Integer.class);
                    userRef.child("points").setValue(newPoint);
                    while (calculateLevel(newPoint, currentLevel)) {
                        currentLevel += 1;
                        userRef.child("level").setValue(currentLevel);

                    }


                }else{
                    Integer currentData = dataSnapshot.child("points").getValue(Integer.class);
                    Integer currentLevel = dataSnapshot.child("level").getValue(Integer.class);
                    currentData = currentData + newPoint;
                    userRef.child("points").setValue(currentData);
                    while (calculateLevel(currentData, currentLevel)) {
                        currentLevel += 1;
                        userRef.child("level").setValue(currentLevel);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private boolean calculateLevel(Integer points, Integer level) {

        if (points > getNextLevel(level)) {
            try {
                Thread.sleep(Toast.LENGTH_SHORT + 10);
                Context context = getApplicationContext();
                int newLvl = level + 1;
                CharSequence text = "Congratulations! You've reached level " + newLvl;
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            return true;
        }


        return false;
    }

    private int getNextLevel(Integer currentLevel) {
        return ((100 * currentLevel) - 1);
    }
}
