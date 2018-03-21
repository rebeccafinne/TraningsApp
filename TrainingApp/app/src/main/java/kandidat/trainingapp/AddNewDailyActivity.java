package kandidat.trainingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AddNewDailyActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolText;
    Button addStairs, addBus, addStand;
    private View rootView;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_daily);
        toolbar = (Toolbar) findViewById(R.id.toolbar_activity);
        toolText = (TextView) toolbar.findViewById(R.id.activity_text);
        toolText.setText("Favorite daily exercise");
        addStairs = (Button) findViewById(R.id.add_stairs);
        addStairs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStairsClicked(view);
            }
        });
        addBus = (Button) findViewById(R.id.add_bus);
        addBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBusClicked(view);
            }
        });
        addStand = (Button) findViewById(R.id.add_stand);
        addStand.setOnClickListener(new View.OnClickListener() {
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
