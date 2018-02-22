package kandidat.trainingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.app.TabActivity;

/**
 * Created by rebeccafinne on 2018-02-22.
 */

public class AppMainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNav;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_running);
        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);
        
        mBottomNav.setSelectedItemId(R.id.action_workout);

        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()){
                    case R.id.action_me:
                        selectedFragment = ProfileFragment.newInstance();
                        break;
                    case R.id.action_workout:
                        selectedFragment = TrainingFragment.newInstance();
                        break;
                    case R.id.action_favorites:

                        selectedFragment = FavoritesFragment.newInstance();

                        break;
                    case R.id.action_leaderboard:
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

}
