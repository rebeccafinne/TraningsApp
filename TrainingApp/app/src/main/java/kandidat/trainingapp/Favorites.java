package kandidat.trainingapp;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rebeccafinne on 2018-03-21.
 */

public class Favorites {


    //Singleton class right now, might want to change that? 

    private static volatile  Favorites fav = new Favorites();

    private Favorites(){

    }

    public static Favorites getInstance(){
        return fav;
    }

    //Add the new value depending to the list
    public void addNewFavorite(Integer newValue, List<Integer> list){
            if(list.contains(newValue)){
                System.out.println("New Favorite not added: " + newValue);
                System.out.println("The list now: " + list);

            }else{
                list.add(newValue);
                System.out.println("New Favorite added: " + newValue);
                System.out.println("The list now: " + list);
            }


        }
    }





