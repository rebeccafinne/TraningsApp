package kandidat.trainingapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import kandidat.trainingapp.Fragments.FavoritesFragment;
import kandidat.trainingapp.Fragments.LeaderboardFragment;
import kandidat.trainingapp.Fragments.ProfileFragment;
import kandidat.trainingapp.R;
import kandidat.trainingapp.Fragments.TrainingFragment;


/**
 * Created by rebeccafinne on 2018-02-22.
 */

public class AppMainActivity extends AppCompatActivity {


    private TextView toolText;
    private ImageButton settingsButton;
    private FirebaseAuth mAuth;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_running);
        mAuth = FirebaseAuth.getInstance();

        BottomNavigationView mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_fragment);
        toolText = (TextView) toolbar.findViewById(R.id.toolbar_text);
        settingsButton = (ImageButton) toolbar.findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(AppMainActivity.this, settingsButton);
                popup.getMenuInflater()
                        .inflate(R.menu.the_main_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                       switch (item.getTitle().toString()){
                           case "Log Out":
                               signOut();
                               break;
                           case "Account Settings":
                               settings(view);
                               break;
                           case "Add New Favorite":
                               addFavorite(view);
                               break;
                       }
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        }); //closing the setOnClickListener method





        toolText.setText("Workout");


        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()){
                    case R.id.action_workout:
                        toolText.setText("Workout");

                        selectedFragment = TrainingFragment.newInstance();
                        break;
                    case R.id.action_me:
                        toolText.setText("Me");

                        selectedFragment = ProfileFragment.newInstance();

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

        mBottomNav.setSelectedItemId(R.id.action_workout);

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

    public void signOut(){
        AuthUI.getInstance().signOut(AppMainActivity.this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            startActivity(MainActivity.createIntent(AppMainActivity.this));
                            finish();
                        } else {
                            // Signout failed
                        }
                    }
                });
    }





}
