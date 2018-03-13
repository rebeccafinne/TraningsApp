package kandidat.trainingapp;

import java.util.ArrayList;

/**
 * Created by Anna on 2018-03-13.
 */

public class Workout {

    static ArrayList allAvailableExercises = new ArrayList<Exercise>(); //TODO borde vara i firebase

    private String name;
    private ArrayList<Exercise> ExerciseList;

    public Workout(String name){
        this.name = name;
        ExerciseList = new ArrayList<Exercise>();
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
}
