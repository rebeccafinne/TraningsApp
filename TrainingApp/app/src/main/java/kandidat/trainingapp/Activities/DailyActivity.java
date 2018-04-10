package kandidat.trainingapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import kandidat.trainingapp.Models.Points;
import kandidat.trainingapp.R;

public class DailyActivity extends AppCompatActivity {

    private Spinner spinnerType, spinnerHowMany;
    private Points points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity);
        TextView toolText = (TextView) toolbar.findViewById(R.id.activity_text);
        toolText.setText("Daily");

        points = new Points();

        spinnerType = (Spinner) findViewById(R.id.choose_daily_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        //Using amount of stairs since it contains the same values.
        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(this,
                R.array.type_of_daily, R.layout.layout_spinner);
        // Specify the layout to use when the list of choices appears
        adapterType.setDropDownViewResource(R.layout.layout_spinner);
        // Apply the adapter to the spinner
        spinnerType.setAdapter(adapterType);

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerHowMany = (Spinner) findViewById(R.id.how_many_spinner);
                // Create an ArrayAdapter using the string array and a default spinner layout
                //Using amount of stairs since it contains the same values.
                if (spinnerType.getSelectedItem().toString().equals("Standing up")) {
                    ArrayAdapter<CharSequence> adapterHowMany = ArrayAdapter.createFromResource(getApplicationContext(),
                            R.array.amount_of_time_standing, R.layout.layout_spinner);
                    // Specify the layout to use when the list of choices appears
                    adapterHowMany.setDropDownViewResource(R.layout.layout_spinner);
                    // Apply the adapter to the spinner
                    spinnerHowMany.setAdapter(adapterHowMany);
                } else {
                    ArrayAdapter<CharSequence> adapterHowMany = ArrayAdapter.createFromResource(getApplicationContext(),
                            R.array.amount_of_stairs, R.layout.layout_spinner);
                    // Specify the layout to use when the list of choices appears
                    adapterHowMany.setDropDownViewResource(R.layout.layout_spinner);
                    // Apply the adapter to the spinner
                    spinnerHowMany.setAdapter(adapterHowMany);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });


        Button saveButton = (Button) findViewById(R.id.save_daily_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String selectedString = spinnerHowMany.getSelectedItem().toString();
                Integer selectedInteger = Integer.parseInt(selectedString);
                points.calcualtePoints(selectedInteger);

                finish();

            }
        });



    }
}




