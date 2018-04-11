package kandidat.trainingapp.Models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by rebeccafinne on 2018-04-06.
 */

public class Points {


    private DatabaseReference pointRef;
    private DatabaseReference negPointRef;



    public Points(){

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = db.getReference("users");

        pointRef = ref.child(user.getUid());
        negPointRef = ref.child(user.getUid()).child("negPoints");

    }


    public void calcualtePoints(Integer newPoint){


        pointRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("points").getValue(Integer.class) == 0){
                    pointRef.child("points").setValue(newPoint);
                    negPointRef.setValue(newPoint*-1);

                }else{
                    Integer currentData = dataSnapshot.child("points").getValue(Integer.class);
                    currentData = currentData + newPoint;
                    pointRef.child("points").setValue(currentData);
                    negPointRef.setValue(currentData*-1);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
