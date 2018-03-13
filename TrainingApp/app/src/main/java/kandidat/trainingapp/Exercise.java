package kandidat.trainingapp;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Anna on 2018-03-13.
 */

public class Exercise {

    private String name;
    private String description;

    private ArrayList<Row> rows;

    public Exercise(String name){
        this.name = name;
        this.description = "no description available.";

        this.rows = new ArrayList<>();
    }

    public String getName(){
        return name;
    }
    public void reName(String name){
        this.name = name;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public void newRow(int set, int rep, int weight){
        Row newRow = new Row(set, rep, weight);
        rows.add(newRow);
    }

    //TODO fix with different rows
    public int getSets(int rownumber){
        return rows.get(rownumber).getSets();
    }
    public int getReps(int rownumber){
        return rows.get(rownumber).getReps();
    }
    public int getWeight(int rownumber){
        return rows.get(rownumber).getReps();
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
