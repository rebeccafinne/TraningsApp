package kandidat.trainingapp;

import java.util.ArrayList;

/**
 * Created by Anna on 2018-03-13.
 */

public class Workout {

    static ArrayList allAvailableExercises = new ArrayList<Exercise>(); //TODO borde vara i firebase

    private String name;
    private ArrayList<Exercise> exerciseList;

    public Workout(){
        this.name = "No Name";
        exerciseList = new ArrayList<Exercise>();
    }

    public Workout(String name){
        this();
        this.name = name;
    }

    //Should make a dialog which let the user create a new exercise.
    public void addExercise(){
        addExercise("NoName");
    }
    public void addExercise(String name){
        Exercise newEx = new Exercise(name);
        newEx.newRow(0, 0,0 );
        exerciseList.add(newEx);
    }
    public Exercise getExercise(int i){
        if(exerciseList.isEmpty()) return null;
        else return exerciseList.get(i);
    }
    public int nbrOfExercises(){
        return exerciseList.size();
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
}
