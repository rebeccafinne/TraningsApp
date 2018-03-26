package kandidat.trainingapp.Models;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Created by Anna on 2018-03-13.
 */

public class Exercise {

    private static ArrayList<Exercise> allExercises = new ArrayList<>();

    private String name;
    private String description;


    public Exercise(){
        this("NoName");
    }
    public Exercise(String name){
        this.name = name;
        this.description = "no description available.";
        allExercises.add(this);
    }
    public Exercise(String name, String description){
        this(name);
        this.description = description;
        allExercises.add(this);
    }

    public static Exercise getExercise(String name){
        if(!allExercises.contains(name)) throw new NoSuchElementException("''" + name + "'' is not an existing exercise.");
        else{
            return allExercises.get(allExercises.indexOf(name));
        }
    }
    public static Exercise getExercise(Exercise exercise){
        if (!allExercises.contains(exercise)) throw new NoSuchElementException("''" + exercise + "'' is not an existing exercise.");
        else{
            return allExercises.get(allExercises.indexOf(exercise));
        }
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public boolean equals(Exercise exercise){
        return (this.getName() == exercise.getName() ) & (this.getDescription() == exercise.getDescription());
    }
    public boolean equals(String name){
        return this.getName() == name;
    }

    public String toString(){
        return this.getName();
    }

}
