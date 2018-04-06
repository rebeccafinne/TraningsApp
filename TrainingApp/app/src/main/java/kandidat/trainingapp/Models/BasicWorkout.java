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
    private int points;
    private Difficulty difficulty;
    private String comment;

    final double easyFactor = 0.8;
    final double mediumFactor = 1;
    final double hardFactor = 1.2;

    //**********************************************************************************************
    //***************************** Constructors ***************************************************
    //**********************************************************************************************

    public BasicWorkout(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        this.name = dateFormat.format(date).toString();
        duration = 0;
        points = 0;
        difficulty = Difficulty.MEDIUM;
        comment = "";
    }
    public BasicWorkout(String name){
        this(name, 0);
    }
    public BasicWorkout(int dur){
        this();
        this.duration = dur;
        setPoints();
    }
    public BasicWorkout(String name, int dur){
        this(dur);
        this.name = name;
    }
    public BasicWorkout(String name, int dur, int points, Difficulty diff){
        this(name, dur);
        this.points = points;
        this.difficulty = diff;
    }
    public BasicWorkout(BasicWorkout workout){
        this(workout.getName(), workout.getDuration(), workout.getPoints(), workout.getDifficulty());
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
    public int getPoints(){ return points; }
    public Difficulty getDifficulty(){
        return difficulty;
    }
    public String getComment(){return comment; }

    //**********************************************************************************************
    //***************************** Set - Methods **************************************************
    //**********************************************************************************************

    public void setName(String name){
        this.name = name;
    }
    public void setDuration(int time) {
        this.duration = time;
        setPoints();
    }
    public void setPoints(){
        switch (difficulty){
            case EASY:
                points = (int) (getDuration() *easyFactor);
                break;
            case MEDIUM:
                points = (int) (getDuration()*mediumFactor);
                break;
            case HARD:
                points = (int) (getDuration()*hardFactor);
                break;
        }
    }
    public void setPoints(int n){
        this.points = n;
    }
    public void setDifficulty(String str){
        switch (str.toLowerCase()) {
            case "hard":
                setDifficulty(Difficulty.HARD);
            case "medium":
                setDifficulty(Difficulty.MEDIUM);
            case "easy":
                setDifficulty(Difficulty.EASY);
        }
        setPoints();
    }
    public void setDifficulty(Difficulty diff){
        this.difficulty = diff;
    }
    public void setComment(String comment){
        this.comment = comment;
    }

    public enum Difficulty {
        EASY, MEDIUM, HARD
    }

}
