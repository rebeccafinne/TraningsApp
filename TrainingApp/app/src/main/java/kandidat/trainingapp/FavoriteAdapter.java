package kandidat.trainingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        // let the adapter bind the name to the list
     /*   View v = super.getView(position, convertView, parent);
        // find the counter TextView so we can update it's value
        TextView counterTv = (TextView) v.findViewById(R.id.value);
        //get the data from the list for this row.
        FavoriteModel obj = data.get(position);
        //set the counter value for this row
        counterTv.setText(String.valueOf(obj.value));
        return v;
*/

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
        tvName.setText(fav.activity);
        tvHome.setText(fav.value.toString());
        // Return the completed view to render on screen
        return convertView;
    }
}
