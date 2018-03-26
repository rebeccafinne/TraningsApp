package kandidat.trainingapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kandidat.trainingapp.Repositories.FavoriteData;
import kandidat.trainingapp.Models.Favorites;
import kandidat.trainingapp.R;


public class FavoriteStairsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView toolText;
    private DatabaseReference mDatabase;
    private Button saveStairs;
    private Spinner spinner;
    private Favorites favorites;
    private FavoriteData favoriteData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_stairs);

        favoriteData = (FavoriteData) getApplicationContext();
        //favorites = new Favorites();
        favorites = Favorites.getInstance();
        //Set toolbar and the text
        toolbar = (Toolbar) findViewById(R.id.toolbar_activity);
        toolText = (TextView) toolbar.findViewById(R.id.activity_text);
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


        //Vill ha att övningen är key och sen en lista med ints för antal våningar
        mDatabase = FirebaseDatabase.getInstance().getReference();


        saveStairs = (Button) findViewById(R.id.save_stairs_button);
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

        //stairsFavorites = favorites.addNewFavorite(newValueInteger, stairsFavorites);
          if(favorites.addNewFavorite(newValueInteger, favoriteData.getStairsList())){
              favoriteData.addStairsList(newValueInteger);
              finish();
          }


    }


}
