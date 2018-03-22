package kandidat.trainingapp;

/**
 * Created by rebeccafinne on 2018-03-22.
 */

public class FavoriteModel {

    String activity;
    Integer value;

    public FavoriteModel(String a, Integer v){
        this.activity = a;
        this.value = v;
    }

    @Override
    public String toString(){
        return activity;
    }
}
