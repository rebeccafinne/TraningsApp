package kandidat.trainingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class FavoriteBusStopActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_bus_stop);

        toolbar = (Toolbar) findViewById(R.id.toolbar_activity);
        toolText = (TextView) toolbar.findViewById(R.id.activity_text);
        toolText.setText("Bus Stops");

        Spinner spinner = (Spinner) findViewById(R.id.bus_stop_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        //Using amount of stairs since it contains the same values.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.amount_of_stairs, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }
}
