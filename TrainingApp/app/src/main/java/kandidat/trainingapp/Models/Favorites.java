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

    private DatabaseReference ref;
    private UserInformation userInformation;
    private FavoriteData favoriteData;
    //Singleton class right now, might want to change that? 

//    private static volatile  Favorites fav = new Favorites();

    public  Favorites(){

        db = FirebaseDatabase.getInstance();

        favoriteData = (FavoriteData) getApplicationContext();

       // userInformation = UserInformation.class;
    }

   /* public static Favorites getInstance(){
        return fav;
    }*/

    //Add the new value depending to the list
    public boolean addNewFavorite(Integer newValue, List<Integer> list, String key){

        Context context = getApplicationContext();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ref = db.getReference("favorites");
        DatabaseReference myRef = ref.child(user.getUid()).child(key).push();
        DatabaseReference parentRef = ref.child("favorites");


        parentRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //  System.out.println(s);

           /*     if(dataSnapshot.exists()){
                    System.out.println(dataSnapshot.getValue());
                }*/
                String currentKey = null;

                /*Map<String, Object> map = (Map<String, Object>) dataSnapshot.child(key).getValue();
                if(map.containsValue(newValue)){
                    dataSnapshot.getRef().removeValue();
                }
                */
                if(dataSnapshot.hasChild(user.getUid())){
                    for(DataSnapshot ds : dataSnapshot.child(user.getUid()).getChildren()){
                        Integer dsVal = (int) (long) ds.getValue();
                        if(dsVal.equals(newValue))
                            currentKey = ds.getKey();
                        break;
                    }
                }


               for(DataSnapshot ds : dataSnapshot.child(user.getUid()).getChildren()){
                   // Integer dsVal = Integer.valueOf(ds.getValue().toString());
                    Integer dsVal = (int) (long) ds.getValue();
                    System.out.println(dsVal == newValue);
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

     /*   parentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //  System.out.println(s);

                if(dataSnapshot.exists()){
                    System.out.println(dataSnapshot.getValue());
                }

           /*     for(DataSnapshot ds : dataSnapshot.getChildren()){
                   // Integer dsVal = Integer.valueOf(ds.getValue().toString());
                    Integer dsVal = (int) (long) ds.getValue();
                    System.out.println(dsVal == newValue);
                    if(dsVal == newValue){
                        dataSnapshot.getRef().child(ds.getKey()).removeValue();


                    }
                }*/
         /*   }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/



        //Denna rad lägger till värdet på databasen, behöver check om värdet redan finns.
        myRef.setValue(newValue);


        //Väldigt oklart hur detta funkar
  /*      ChildEventListener eventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (!dataSnapshot.exists()) {
                    CharSequence text = "New favorite is added!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    CharSequence text = "This is already a favorite!";
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
            public void onCancelled(DatabaseError databaseError) {}
        };
*/
        //myRef.addChildEventListener(eventListener);


        //Gammal kod som funkade när jag hade det lokalt och listorna blev tomma efter varje körning.
        //Kanske kan man lägga in värdena från databasen i listorna (som ursprungligen finns i FavoriteData)
        //För att kolla om värdet redan finns på databasen? Inte hittat hur man kan göra det än.

     /*   if(list.contains(newValue)){
                System.out.println("New Favorite not added: " + newValue);
                System.out.println("The list now: " + list);
                CharSequence text = "This is already a favorite!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                return false;

            }else {
            CharSequence text = "New favorite is added!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();








        }*/

     //Också gammal kod till ett försök att lägga till på rätt ställe i databasen, tror inte detta är aktuellt längre men har kvar ändå
       /* favoriteData.addNewTotalFavorite(key, newValue.toString());
        System.out.println(favoriteData.getTotalFavorites());
             /*   if(key.equals("busStop")){
                    myRef = ref.child(mAuth.getUid()).child("favorites").child(key);
                    myRef.setValue(favoriteData.getBusList());
                }else if(key.equals("stairs")){
                    myRef = ref.child(mAuth.getUid()).child("favorites").child(key);
                    myRef.setValue(favoriteData.getStairsList());
                }else{
                    myRef = ref.child(mAuth.getUid()).child("favorites").child(key);
                    myRef.setValue(favoriteData.getStandingList());
                }*/
          /*    myRef.setValue(newValue);*/


        return true;
    }
    }





