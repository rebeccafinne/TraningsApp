package kandidat.trainingapp.Models;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by rebeccafinne on 2018-03-21.
 */

public class Favorites {


    private FirebaseDatabase db;
    private CharSequence text = null;
    private Integer val;


    public  Favorites(){

        db = FirebaseDatabase.getInstance();

    }



    //Add the new value depending to the list
    public boolean addNewFavorite(Integer newValue, String key){

        Context context = getApplicationContext();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = db.getReference("favorites");
        DatabaseReference myRef = ref.child(user.getUid()).child(key).push();
        DatabaseReference parentRef = ref.child(user.getUid());



        parentRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String currentKey = null;


                if(dataSnapshot.getKey().equals(key)) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Integer dsVal = (int) (long) ds.getValue();
                        if (dsVal.equals(newValue)) {
                            currentKey = ds.getKey();
                            break;
                        }

                    }


                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Integer dsVal = (int) (long) ds.getValue();
                        if (dsVal.equals(newValue) && !currentKey.equals(ds.getKey())) {
                            text = "Favorite already added!";

                            dataSnapshot.getRef().child(ds.getKey()).removeValue();
                        }
                    }

                    if(text == null){
                        text = "New favorite added!";
                    }

                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }


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


        myRef.setValue(newValue);







        return true;
    }

}





