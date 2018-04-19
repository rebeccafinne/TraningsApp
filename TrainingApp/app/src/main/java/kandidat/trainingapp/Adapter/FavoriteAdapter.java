package kandidat.trainingapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kandidat.trainingapp.Models.FavoriteModel;
import kandidat.trainingapp.R;

/**
 * Created by rebeccafinne on 2018-03-22.
 */

public class FavoriteAdapter extends ArrayAdapter<FavoriteModel>{

    private ArrayList<FavoriteModel> data;

    public FavoriteAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull ArrayList<FavoriteModel> objects) {
        super(context, resource, textViewResourceId, objects);
        data = objects;

    }

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        FavoriteModel fav = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_favorite_row, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.activity_name);
        TextView tvHome = (TextView) convertView.findViewById(R.id.value);
        // Populate the data into the template view using the data object
        tvName.setText(fav.getActivity());
        tvHome.setText(fav.getValue().toString());


        // Return the completed view to render on screen
        return convertView;
    }



    public FavoriteModel getItem(int position){
        return data.get(position);
    }
}
