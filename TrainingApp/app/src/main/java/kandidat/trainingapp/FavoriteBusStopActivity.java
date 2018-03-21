package kandidat.trainingapp;

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

import java.util.ArrayList;
import java.util.List;

public class FavoriteBusStopActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolText;
    private List<Integer> busFavorites;
    private Button saveBus;
    private Spinner spinner;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_bus_stop);

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
        mDatabase = FirebaseDatabase.getInstance().getReference();


        busFavorites = new ArrayList<>();
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
        if(busFavorites.contains(newValueInteger)){
            //Göra någon pupup om att den favoriten redan finns
            System.out.println("New Favorite not added: " + newValueInteger);
            System.out.println("The list now: " + busFavorites);
        }else{
            busFavorites.add(newValueInteger);
            System.out.println("New Favorite added: " + newValueInteger);
            System.out.println("The list now: " + busFavorites);
        }


    }
}
