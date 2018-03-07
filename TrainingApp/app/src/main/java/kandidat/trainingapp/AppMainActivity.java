package kandidat.trainingapp;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.app.TabActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by rebeccafinne on 2018-02-22.
 */

public class AppMainActivity extends AppCompatActivity implements View.OnClickListener {

    private BottomNavigationView mBottomNav;

    private Toolbar toolbar;
    private TextView toolText;
    private ImageButton settingsButton, addFavoritesButton;
    ActionBar actionBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_running);
        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);

        toolbar = (Toolbar) findViewById(R.id.toolbar_fragment);
        toolText = (TextView) toolbar.findViewById(R.id.toolbar_text);
        settingsButton = (ImageButton) toolbar.findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(this);
        addFavoritesButton = (ImageButton) toolbar.findViewById(R.id.add_favorites_button);
        addFavoritesButton.setOnClickListener(this);


        toolText.setText("Workout");
        settingsButton.setImageResource(R.drawable.settings_small);

        // setSupportActionBar(toolbar);
        // toolbar = getSupportActionBar(); // or getActionBar();
        //getSupportActionBar().setTitle("BetterTogether");
        //getSupportActionBar().setLogo(R.drawable.settings);
        
        mBottomNav.setSelectedItemId(R.id.action_workout);

        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()){
                    case R.id.action_me:
                        toolText.setText("Me");

                        selectedFragment = ProfileFragment.newInstance();

                        break;
                    case R.id.action_workout:
                        toolText.setText("Workout");

                        selectedFragment = TrainingFragment.newInstance();
                        break;
                    case R.id.action_favorites:

                        toolText.setText("Favorites");

                        selectedFragment = FavoritesFragment.newInstance();

                        break;
                    case R.id.action_leaderboard:
                        toolText.setText("Friends");

                        selectedFragment = LeaderboardFragment.newInstance();

                        break;
                }

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();
                return true;
            }


        });
        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, TrainingFragment.newInstance());
        transaction.commit();






        //Used to select an item programmatically
        //bottomNavigationView.getMenu().getItem(2).setChecked(true);

    }

    @Override
    public void onClick(View view) {

        Intent intent;
        if(view.getId() == R.id.settings_button){
            intent = new Intent(this, ProfileSettingsActivity.class);
        } else{
            intent = new Intent(this, AddFavoritesActivity.class);
        }
        startActivity(intent);

    }
}
