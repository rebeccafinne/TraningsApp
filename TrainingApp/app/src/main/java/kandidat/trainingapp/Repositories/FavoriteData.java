package kandidat.trainingapp.Repositories;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by rebeccafinne on 2018-03-21.
 */

public class FavoriteData extends Application{

    //Do not think this class is needed anymore

    private List<Integer> busList;
    private List<Integer> stairsList;
    private List<Integer> standingList;

    FirebaseDatabase db;

    DatabaseReference ref;

    private HashMap<String, String> totalFavorites;


    public FavoriteData(){
        busList = new ArrayList<>();
        stairsList = new ArrayList<>();
        standingList = new ArrayList<>();
        totalFavorites = new HashMap<>();

    }


    public List<Integer> getBusList(){
        return this.busList;
    }
    public void addBusList(Integer val){
        this.busList.add(val);
    }

    public List<Integer> getStairsList(){
        return this.stairsList;
    }

    public void addStairsList(Integer val){
        this.stairsList.add(val);
    }

    public List<Integer> getStandingList(){
        return this.standingList;
    }

    public void addStandingList(Integer val){
        standingList.add(val);
    }

    public HashMap<String, String> getTotalFavorites(){
        return this.totalFavorites;
    }

    public void addNewTotalFavorite(String thing, String value){

        this.totalFavorites.put(thing, value);
    }

}
