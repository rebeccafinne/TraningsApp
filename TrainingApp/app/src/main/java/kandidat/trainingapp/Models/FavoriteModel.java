package kandidat.trainingapp.Models;

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
        return 7*this.getActivity().hashCode()+this.getValue();
    }

  /*  @Override
    public boolean equals(FavoriteModel fav){
        if(fav.getValue().equals(this.getValue()) && fav.getActivity().equals(this.getActivity())){
            return true;
        }else{
            return false;
        }
    }*/
}
