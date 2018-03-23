package kandidat.trainingapp.Repositories;

import android.app.Application;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rebeccafinne on 2018-03-21.
 */

public class FavoriteData extends Application{

    private List<Integer> busList;
    private List<Integer> stairsList;
    private List<Integer> standingList;



    public FavoriteData(){
        busList = new ArrayList<>();
        stairsList = new ArrayList<>();
        standingList = new ArrayList<>();
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
}
