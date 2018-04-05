package kandidat.trainingapp.Models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Anna on 2018-03-
 *
 *
 * This class contains stuff that are the same for cardio and gym workouts.
 */

public class BasicWorkout {

    //**********************************************************************************************
    //***************************** Variables ******************************************************
    //**********************************************************************************************
    private int duration;
    private String name;



    //**********************************************************************************************
    //***************************** Constructors ***************************************************
    //**********************************************************************************************

    public BasicWorkout(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
        this.name = dateFormat.format(date).toString();
        duration = 0;
    }
    public BasicWorkout(String name){
        this(name, 0);
    }
    public BasicWorkout(int dur){
        this();
        this.duration = dur;
    }
    public BasicWorkout(String name, int dur){
        this.duration = dur;
        this.name = name;
    }

    //**********************************************************************************************
    //***************************** Get - Methods **************************************************
    //**********************************************************************************************

    public String getName(){
        return name;
    }
    public int getDuration() {
        return duration;
    }

    //**********************************************************************************************
    //***************************** Set - Methods **************************************************
    //**********************************************************************************************

    public void setName(String name){
        this.name = name;
    }
    public void setDuration(int time) {
        this.duration = time;
    }

}
