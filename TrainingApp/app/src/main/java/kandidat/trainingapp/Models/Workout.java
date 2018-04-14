package kandidat.trainingapp.Models;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import kandidat.trainingapp.Models.BasicWorkout;
import kandidat.trainingapp.Models.Exercise;

/**
 * Created by Anna on 2018-03-13.
 */

public class Workout extends BasicWorkout {

    //**********************************************************************************************
    //***************************** Variables ******************************************************
    //**********************************************************************************************

    /**
     * unsavedWorkouts will not appear in the "select workout"-list.
     */
    private static ArrayList<Workout> unsavedWorkouts = new ArrayList<>();
    /**
     * savedWorkouts will appear in the "select workout"-list.
     */
    private static ArrayList<Workout> savedWorkouts = new ArrayList<>();

    private String name;
    private LinkedHashMap<Exercise, ArrayList<ExerciseRow>> exerciseList;

    //**********************************************************************************************
    //***************************** Constructors ***************************************************
    //**********************************************************************************************
    public Workout(){
        super();
        exerciseList = new LinkedHashMap<Exercise, ArrayList<ExerciseRow>>();
        unsavedWorkouts.add(this);
    }
    public Workout(String name){
        super(name);
    }
    public Workout(Workout workout){
        super(workout.getName());
        this.exerciseList = new LinkedHashMap<>(workout.exerciseList);
        // there already exist one with the same name -> not saved in any list.
    }

    //**********************************************************************************************
    //***************************** Methods, creating exercises ************************************
    //**********************************************************************************************

    public void addNewExercise(){
        addNewExercise(new Exercise("NoName"));
    }
    public void addNewExercise(Exercise exercise){
        exerciseList.put(exercise, new ArrayList<>());
        newRow(exercise);
    }
    public void addNewExercise(String name){
        Exercise newEx = new Exercise(name);
        addNewExercise(newEx);
    }
    public void addNewExercise(String name, String description){
        Exercise newEx = new Exercise(name, description);
        addNewExercise(newEx);
    }
    public void addNewExercise(Exercise exercise, ArrayList<ExerciseRow> rows){
        exerciseList.put(exercise, rows);
    }


    public void addExercise(String name){
        Exercise exercise = Exercise.getExercise(name);
        newRow(Exercise.getExercise(exercise),0, 0,0 );
        exerciseList.put(exercise, new ArrayList<ExerciseRow>());
    }
    public void addExercise(Exercise exercise){
        String name = exercise.getName();
        addExercise(name);
    }

    //**********************************************************************************************
    //***************************** Methods, exercises *********************************************
    //**********************************************************************************************
    public Exercise getExercise(int i){
        ArrayList<Exercise> exercises = new ArrayList<>(exerciseList.keySet());
        return exercises.get(i);
    }
    public int nbrOfExercises(){
        return exerciseList.size();
    }


    //**********************************************************************************************
    //***************************** Methods, rows **************************************************
    //**********************************************************************************************

    public void newRow(Exercise exercise){
        newRow(exercise, 0,0,0);
    }
    public void newRow(Exercise exercise, int set, int rep, int weight){
        ExerciseRow newRow = new ExerciseRow(set, rep, weight);
        exerciseList.get(exercise).add(newRow);
    }
    public int nbrOfRows(Exercise exercise){
        return exerciseList.get(exercise).size();
    }
    public void setRow(Exercise exercise, int row, int set, int rep, int weight){
        exerciseList.get(exercise).get(row).setRow(set, rep, weight);
    }

    //**********************************************************************************************
    //***************************** Methods, getting row-values ************************************
    //**********************************************************************************************
    public int getSets(Exercise exercise, int rownumber){
        return exerciseList.get(exercise).get(rownumber).getSets();
    }
    public int getReps(Exercise exercise, int rownumber){
        return exerciseList.get(exercise).get(rownumber).getReps();
    }
    public int getWeight(Exercise exercise, int rownumber){
        return exerciseList.get(exercise).get(rownumber).getWeight();
    }

}
