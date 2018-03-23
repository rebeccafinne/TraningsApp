package kandidat.trainingapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import kandidat.trainingapp.FavoriteData;
import kandidat.trainingapp.Fragments.FavoritesFragment;
import kandidat.trainingapp.Fragments.LeaderboardFragment;
import kandidat.trainingapp.Fragments.ProfileFragment;
import kandidat.trainingapp.R;
import kandidat.trainingapp.Fragments.TrainingFragment;

/**
 * Created by rebeccafinne on 2018-02-22.
 */

public class AppMainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNav;

    private Toolbar toolbar;
    private TextView toolText;
    private ImageButton settingsButton, addFavoritesButton;
    private FirebaseAuth mAuth;
   // private FavoriteData favoriteData;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_running);
        mAuth = FirebaseAuth.getInstance();


      /*  if(favoriteData == null){
            favoriteData = new FavoriteData();
        }*/
      final FavoriteData favoriteData = (FavoriteData) getApplicationContext();


        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);

        toolbar = (Toolbar) findViewById(R.id.toolbar_fragment);
        toolText = (TextView) toolbar.findViewById(R.id.toolbar_text);
        settingsButton = (ImageButton) toolbar.findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settings(view);
            }
        });
        addFavoritesButton = (ImageButton) toolbar.findViewById(R.id.add_favorites_button);
        addFavoritesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                addFavorite(view);
            }
        });


        toolText.setText("Workout");
        settingsButton.setImageResource(R.drawable.settings_small);
        
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

    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
            Intent welcomeIntent = new Intent(AppMainActivity.this,MainActivity.class);
            startActivity(welcomeIntent);
            finish();
        }
    }


    private void settings(View view){
        Intent intent = new Intent(this, ProfileSettingsActivity.class);
        startActivity(intent);
    }
    private void addFavorite(View view){
        Intent intent = new Intent(this, AddFavoritesActivity.class);
        startActivity(intent);
    }

    public static Intent createIntent(Context context, IdpResponse idpResponse) {
        Intent in = IdpResponse.getIntent(idpResponse);
        in.setClass(context, AppMainActivity.class);
        return in;
    }

    public static Intent createIntent(Context context) {
        Intent in = new Intent();
        in.setClass(context, AppMainActivity.class);
        return in;
    }

   /* public FavoriteData getFavoriteData(){
        return this.favoriteData;
    }*/



}
