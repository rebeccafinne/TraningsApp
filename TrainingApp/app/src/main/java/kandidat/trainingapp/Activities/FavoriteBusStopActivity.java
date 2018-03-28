package kandidat.trainingapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kandidat.trainingapp.Repositories.FavoriteData;
import kandidat.trainingapp.Models.Favorites;
import kandidat.trainingapp.R;
import kandidat.trainingapp.Repositories.UserInformation;

public class FavoriteBusStopActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolText;
    private Button saveBus;
    private Spinner spinner;
    private DatabaseReference mDatabase;
    private Favorites favorites;
    FavoriteData favoriteData;
    UserInformation usr;
    private FirebaseDatabase db;
    private DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_bus_stop);

        favoriteData = (FavoriteData) getApplicationContext();


        favorites = new Favorites();
        toolbar = (Toolbar) findViewById(R.id.toolbar_activity);
        toolText = (TextView) toolbar.findViewById(R.id.activity_text);
        toolText.setText("Bus Stops");

         spinner = (Spinner) findViewById(R.id.bus_stop_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        //Using amount of stairs since it contains the same values.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.amount_of_stairs, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //Vill ha att övningen är key och sen en lista med ints för antal våningar
       // mDatabase = FirebaseDatabase.getInstance().getReference();

//        db = FirebaseDatabase.getInstance();

 //       ref = db.getReference("user");




        saveBus = (Button) findViewById(R.id.save_bus_stop_button);
        saveBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveBusClicked(view);
            }
        });
    }

    private void saveBusClicked(View view){

        String newValueString = spinner.getSelectedItem().toString();
        Integer newValueInteger = Integer.parseInt(newValueString);
        if(favorites.addNewFavorite(newValueInteger, favoriteData.getBusList(), "BusStop")){
            favoriteData.addBusList(newValueInteger);
//            ref.child(usr.getUserId()).child("bus").setValue(newValueString);
            finish();
        }


    }
}
