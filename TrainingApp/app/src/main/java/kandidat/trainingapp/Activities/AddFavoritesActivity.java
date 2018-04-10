package kandidat.trainingapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import kandidat.trainingapp.R;

public class AddFavoritesActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolText;
    Button stairsButton, busButton, standingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_favorites);

        toolbar = (Toolbar) findViewById(R.id.toolbar_fragment);
        toolText = (TextView) toolbar.findViewById(R.id.toolbar_text);
        toolText.setText("Add Favorite Workouts");

        stairsButton = (Button) findViewById(R.id.add_stairs);
        stairsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStairsClicked(view);
            }
        });
        busButton = (Button) findViewById(R.id.add_bus);
        busButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBusClicked(view);
            }
        });
        standingButton = (Button) findViewById(R.id.add_stand);
        standingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStandClicked(view);
            }
        });

    }

    private void addStairsClicked(View view){
        Intent intent = new Intent(this, FavoriteStairsActivity.class);
        startActivity(intent);
    }

    private void addBusClicked(View view){
        Intent intent = new Intent(this, FavoriteBusStopActivity.class);
        startActivity(intent);
    }

    private void addStandClicked(View view){
        Intent intent = new Intent(this, FavoriteTimeStandingActivity.class);
        startActivity(intent);
    }
}
