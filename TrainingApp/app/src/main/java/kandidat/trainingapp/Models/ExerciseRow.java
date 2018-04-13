package kandidat.trainingapp.Models;

import java.util.LinkedHashMap;

/**
 * Created by Anna on 2018-04-13.
 */

public class ExerciseRow {
    private int sets;
    private int reps;
    private int weight;

    public ExerciseRow(int set, int reps, int weigth){
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
