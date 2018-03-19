package kandidat.trainingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class FavoriteStairsActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_stairs);
        toolbar = (Toolbar) findViewById(R.id.toolbar_activity);
        toolText = (TextView) toolbar.findViewById(R.id.activity_text);
        toolText.setText("Stairs");

        Spinner spinner = (Spinner) findViewById(R.id.stairs_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.amount_of_stairs, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }
}
