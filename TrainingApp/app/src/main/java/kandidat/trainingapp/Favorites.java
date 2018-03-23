package kandidat.trainingapp;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.firebase.ui.auth.ui.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by rebeccafinne on 2018-03-21.
 */

public class Favorites {


    UserInformation userInformation;
    //Singleton class right now, might want to change that? 

    private static volatile  Favorites fav = new Favorites();

    private Favorites(){

    }

    public static Favorites getInstance(){
        return fav;
    }

    //Add the new value depending to the list
    public boolean addNewFavorite(Integer newValue, List<Integer> list){
        Context context = getApplicationContext();
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        DatabaseReference ref = db.getReference("users");

        ref.child(userInformation.getUserId()).child("favorites");

        if(list.contains(newValue)){
                System.out.println("New Favorite not added: " + newValue);
                System.out.println("The list now: " + list);
                CharSequence text = "This is already a favorite!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                return false;

            }else{
                CharSequence text = "New favorite is added!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                System.out.println("New Favorite added: " + newValue);
                System.out.println("The list now: " + list);
                return true;

            }


        }
    }





