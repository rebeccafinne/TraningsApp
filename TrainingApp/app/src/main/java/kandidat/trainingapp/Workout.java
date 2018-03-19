package kandidat.trainingapp;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;

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
    private HashMap<Exercise, ArrayList<Row>> exerciseList;

    //**********************************************************************************************
    //***************************** Constructors ***************************************************
    //**********************************************************************************************
    public Workout(){
        this.name = "No Name";
        exerciseList = new HashMap<Exercise, ArrayList<Row>>();
        unsavedWorkouts.add(this);
    }

    public Workout(String name){
        this();
        this.name = name;
    }

    public Workout(Workout workout){
        this(workout.getName());
        this.exerciseList = new HashMap<>(workout.exerciseList);
    }

    //**********************************************************************************************
    //***************************** Methods, creating exercises ************************************
    //**********************************************************************************************

    public void newExercise(){
        addExercise("NoName");
    }
    public void newExercise(String name){
        Exercise newEx = new Exercise(name);
        newRow(newEx,0, 0,0 );
        exerciseList.put(newEx, new ArrayList<Row>());
    }
    public void newExercise(String name, String description){
        Exercise newEx = new Exercise(name, description);
        newRow(newEx,0, 0,0 );
        exerciseList.put(newEx, new ArrayList<Row>());
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

    public ArrayList<Row> getExercise(String exercise){
        if(exerciseList.isEmpty()) return null;
        else return exerciseList.get(exercise);
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

    public void newRow(Exercise exercise, int set, int rep, int weight){
        Row newRow = new Row(set, rep, weight);
        exerciseList.get(exercise).add(newRow);
    }
    public int nbrOfRows(Exercise exercise){
        return exerciseList.get(exercise).size();
    };

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
        return exerciseList.get(exercise).get(rownumber).getReps();
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
        public HashMap<String, Integer> getRow(){
            HashMap<String, Integer> map = new HashMap<>();
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
            sets = i;
        }

        public int getWeight(){
            return weight;
        }
        public void setWeight(int i){
            weight = i;
        }
    }
}
