package kandidat.trainingapp.Models;

import android.support.annotation.NonNull;

/**
 * Created by rebeccafinne on 2018-03-22.
 */

public class FavoriteModel implements Comparable{

    private String activity;
    private Integer value;

    public FavoriteModel(String a, Integer v){
        this.activity = a;
        this.value = v;
    }

    @Override
    public String toString(){
        return activity;
    }

    public String getActivity(){
        return this.activity;
    }

    public Integer getValue(){
        return this.value;
    }

    @Override
    public boolean equals(Object o) {

        if(o == this){
            return true;
        }
       if(!(o instanceof FavoriteModel)){
            return false;
        }
        FavoriteModel fav = (FavoriteModel) o;

        if(fav.getValue().equals(this.getValue()) && fav.getActivity().equals(this.getActivity())){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int hashCode(){
        return 191 * this.getValue() + this.getActivity().hashCode();
    }


    @Override
    public int compareTo(@NonNull Object o) {
        FavoriteModel fav = (FavoriteModel) o;


        return this.getValue().compareTo(fav.getValue());
    }
}
