package kandidat.trainingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FavoriteTimeStandingActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView toolText;
    private List<Integer> favoriteStanding;
    private Button saveButton;
    private Favorites favorites;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_time_standing);

        favorites = Favorites.getInstance();
        toolbar = (Toolbar) findViewById(R.id.toolbar_activity);
        toolText = (TextView) toolbar.findViewById(R.id.activity_text);
        toolText.setText("Time standing");

        Spinner spinner = (Spinner) findViewById(R.id.stairs_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.amount_of_time_standing, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        favoriteStanding = new ArrayList<>();
        saveButton = (Button) findViewById(R.id.save_standing_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newValueString = spinner.getSelectedItem().toString();
                Integer newValueInteger = Integer.parseInt(newValueString);
                if(favorites.addNewFavorite(newValueInteger, favoriteStanding)){
                    favoriteStanding.add(newValueInteger);
                    //finnish();
                }
            }
        });

    }
}
