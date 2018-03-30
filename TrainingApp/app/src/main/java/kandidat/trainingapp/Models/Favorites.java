package kandidat.trainingapp.Models;

import android.content.Context;
import android.widget.Toast;

import com.firebase.ui.auth.ui.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kandidat.trainingapp.Repositories.FavoriteData;
import kandidat.trainingapp.Repositories.UserInformation;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by rebeccafinne on 2018-03-21.
 */

public class Favorites {


    private FirebaseDatabase db;


    public  Favorites(){

        db = FirebaseDatabase.getInstance();

    }



    //Add the new value depending to the list
    public boolean addNewFavorite(Integer newValue, List<Integer> list, String key){

        Context context = getApplicationContext();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = db.getReference("favorites");
        DatabaseReference myRef = ref.child(user.getUid()).child(key).push();
        DatabaseReference parentRef = ref.child("favorites");


        parentRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String currentKey = null;


                if(dataSnapshot.hasChild(user.getUid())){
                    for(DataSnapshot ds : dataSnapshot.child(user.getUid()).getChildren()){
                        Integer dsVal = (int) (long) ds.getValue();
                        if(dsVal.equals(newValue))
                            currentKey = ds.getKey();
                        break;
                    }
                }


               for(DataSnapshot ds : dataSnapshot.child(user.getUid()).getChildren()){
                    Integer dsVal = (int) (long) ds.getValue();
                    if(dsVal.equals(newValue) && !currentKey.equals(ds.getKey())){
                        dataSnapshot.getRef().child(ds.getKey()).removeValue();


                    }
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





