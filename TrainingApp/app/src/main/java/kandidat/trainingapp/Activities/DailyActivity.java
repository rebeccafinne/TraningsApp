package kandidat.trainingapp.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import kandidat.trainingapp.Models.Points;
import kandidat.trainingapp.R;

public class DailyActivity extends AppCompatActivity {

    private Spinner spinnerType, spinnerHowMany;
    private Points points;
    private TextView howManyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity);
        TextView toolText = (TextView) toolbar.findViewById(R.id.activity_text);
        toolText.setText(R.string.tool_daily);

        howManyText = (TextView) findViewById(R.id.how_many_text);

        points = new Points();

        spinnerType = (Spinner) findViewById(R.id.choose_daily_spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(this,
                R.array.type_of_daily, R.layout.layout_spinner);

        // Specify the layout to use when the list of choices appears
        adapterType.setDropDownViewResource(R.layout.layout_dropdown_spinner);

        // Apply the adapter to the spinner
        spinnerType.setAdapter(adapterType);

        //Listener of which item in the spinner is selected
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerHowMany = (Spinner) findViewById(R.id.how_many_spinner);
                if (spinnerType.getSelectedItem().toString().equals("Standing up")) {

                    // Create an ArrayAdapter using the string array and a default spinner layout
                    ArrayAdapter<CharSequence> adapterHowMany = ArrayAdapter.createFromResource(getApplicationContext(),
                            R.array.amount_of_time_standing, R.layout.layout_spinner);
                    // Specify the layout to use when the list of choices appears
                    adapterHowMany.setDropDownViewResource(R.layout.layout_dropdown_spinner);
                    // Apply the adapter to the spinner
                    spinnerHowMany.setAdapter(adapterHowMany);
                    howManyText.setText(R.string.how_many_minutes);
                }else if(spinnerType.getSelectedItem().toString().equals("Bus Stops")){

                    // Create an ArrayAdapter using the string array and a default spinner layout
                    ArrayAdapter<CharSequence> adapterHowMany = ArrayAdapter.createFromResource(getApplicationContext(),
                            R.array.amount_of_stairs, R.layout.layout_spinner);
                    // Specify the layout to use when the list of choices appears
                    adapterHowMany.setDropDownViewResource(R.layout.layout_dropdown_spinner);
                    // Apply the adapter to the spinner
                    spinnerHowMany.setAdapter(adapterHowMany);
                    howManyText.setText(R.string.how_many_bus_stops);

                } else {
                    // Create an ArrayAdapter using the string array and a default spinner layout

                    ArrayAdapter<CharSequence> adapterHowMany = ArrayAdapter.createFromResource(getApplicationContext(),
                            R.array.amount_of_stairs, R.layout.layout_spinner);
                    // Specify the layout to use when the list of choices appears
                    adapterHowMany.setDropDownViewResource(R.layout.layout_dropdown_spinner);
                    // Apply the adapter to the spinner
                    spinnerHowMany.setAdapter(adapterHowMany);
                    howManyText.setText(R.string.how_many_stairs);

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

                Context context = getApplicationContext();

                CharSequence text = "You have earned " + selectedString + " points!";


                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                finish();

            }
        });



    }
}




