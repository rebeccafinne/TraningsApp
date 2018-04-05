package kandidat.trainingapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import kandidat.trainingapp.Models.Favorites;
import kandidat.trainingapp.R;


public class FavoriteStairsActivity extends AppCompatActivity {


    private Spinner spinner;
    private Favorites favorites;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_stairs);

        favorites = new Favorites();
        //Set toolbar and the text
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity);
        TextView toolText = (TextView) toolbar.findViewById(R.id.activity_text);
        toolText.setText("Stairs");


        //Initialize the spinner
        spinner = (Spinner) findViewById(R.id.stairs_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.amount_of_stairs, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        Button saveStairs = (Button) findViewById(R.id.save_stairs_button);
        saveStairs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveStairsClicked(view);
            }
        });

    }

    private void saveStairsClicked(View view){
        String newValueString = spinner.getSelectedItem().toString();
        Integer newValueInteger = Integer.parseInt(newValueString);
        if(favorites.addNewFavorite(newValueInteger, "Stairs")){
              finish();
          }


    }


}
