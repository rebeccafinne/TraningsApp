package kandidat.trainingapp;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by Anna on 2018-03-13.
 */

public class Workout {

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
    private LinkedHashMap<Exercise, ArrayList<Row>> exerciseList;
    private int duration;

    //**********************************************************************************************
    //***************************** Constructors ***************************************************
    //**********************************************************************************************
    public Workout(){
        this.name = "No Name";
        exerciseList = new LinkedHashMap<Exercise, ArrayList<Row>>();
        unsavedWorkouts.add(this);
    }

    public Workout(String name){
        this();
        this.name = name;
    }

    public Workout(Workout workout){
        this(workout.getName());
        this.exerciseList = new LinkedHashMap<>(workout.exerciseList);
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

    public void addExercise(String name){
        Exercise exercise = Exercise.getExercise(name);
        newRow(Exercise.getExercise(exercise),0, 0,0 );
        exerciseList.put(exercise, new ArrayList<Row>());
    }
    public void addExercise(Exercise exercise){
        String name = exercise.getName();
        addExercise(name);
    }

    //**********************************************************************************************
    //***************************** Methods, exercises *********************************************
    //**********************************************************************************************

    public ArrayList<Row> getRowsOfExercise(Exercise exercise){
        return exerciseList.get(exercise);
    }
    public Exercise getExercise(int i){
        ArrayList<Exercise> exercises = new ArrayList<>(exerciseList.keySet());
        return exercises.get(i);
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

    public void newRow(Exercise exercise){
        newRow(exercise, 0,0,0);
    }
    public void newRow(Exercise exercise, int set, int rep, int weight){
        Row newRow = new Row(set, rep, weight);
        exerciseList.get(exercise).add(newRow);
    }
    public int nbrOfRows(Exercise exercise){
        return exerciseList.get(exercise).size();
    }
    public void setRow(Exercise exercise, int row, int set, int rep, int weight){
        exerciseList.get(exercise).get(row).setRow(set, rep, weight);
    }

    //**********************************************************************************************
    //***************************** Methods, editing rows ******************************************
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

    public void setSet(Exercise exercise, int rownbr, int value){
        exerciseList.get(exercise).get(rownbr).setSets(value);
    }

    class Row{
        private int sets;
        private int reps;
        private int weight;

        private Row(int set, int reps, int weigth){
            this.sets = set;
            this.reps = reps;
            this.weight = weigth;
        }


        public void setRow(int set, int rep, int weight){
            setSets(set); setReps(rep); setWeight(weight);
        }
        public LinkedHashMap<String, Integer> getRow(){
            LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
            map.put("Sets", sets);
            map.put("Reps", reps);
            map.put("Weight", weight);
            return map;

        }

        public int getSets(){
            return sets;
        }
        public void setSets(int i){
            sets = i;
        }

        public int getReps(){
            return reps;
        }
        public void setReps(int i){
            reps = i;
        }

        public int getWeight(){
            return weight;
        }
        public void setWeight(int i){
            weight = i;
        }
    }

    public void setDuration(int time){
        duration = time;
    }
}
