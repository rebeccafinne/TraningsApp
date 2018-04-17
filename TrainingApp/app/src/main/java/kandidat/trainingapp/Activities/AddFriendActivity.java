package kandidat.trainingapp.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.text.Editable;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import kandidat.trainingapp.Adapter.AddFriendAdapter;
import kandidat.trainingapp.Adapter.LeaderboardAdapter;
import kandidat.trainingapp.Models.AddFriendModel;
import kandidat.trainingapp.Models.LeaderboardModel;
import kandidat.trainingapp.R;
import kandidat.trainingapp.Repositories.UserInformation;

import static com.facebook.FacebookSdk.getApplicationContext;

public class AddFriendActivity extends AppCompatActivity {

    private FirebaseDatabase db;
    private DatabaseReference ref;
    private DatabaseReference userRef;
    private ListView leaderboardList;
    private EditText searchFriends;
    private FirebaseUser theCurrenUser = FirebaseAuth.getInstance().getCurrentUser();
    private TextView toolText;
    private ArrayList<AddFriendModel> dataModels;
    private ArrayAdapter<AddFriendModel> adapter;
    private Button enterSearchBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        db = FirebaseDatabase.getInstance();
        ref = db.getReference();
        leaderboardList = (ListView) findViewById(R.id.leaderList);
        searchFriends = (EditText) findViewById(R.id.searchText);
        enterSearchBtn = (Button) findViewById(R.id.findBtn);
        dataModels = new ArrayList<>();
        userRef = ref.child("users");
        enterSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchForUsers(searchFriends.getText().toString().toLowerCase());
            }
        });

    }

    private void searchForUsers(String theSearch) {

        Query searchQuery = userRef.orderByChild("email").startAt(theSearch)
                .endAt(theSearch + "\uf8ff");

        searchQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    Toast.makeText(AddFriendActivity.this,"No users found with that email.",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_fragment);
        toolText = (TextView) toolbar.findViewById(R.id.toolbar_text);
        toolText.setText("Add new friend");



        ImageButton settingsButton = (ImageButton) toolbar.findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(AddFriendActivity.this, settingsButton);
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

        FirebaseListAdapter<UserInformation> fListAdapter = new FirebaseListAdapter<UserInformation>(
                AddFriendActivity.this,
                UserInformation.class,
                R.layout.alluser_representation,
               searchQuery
        ) {
            TextView name;



            @Override
            protected void populateView(View v, UserInformation model, int position) {

                name = v.findViewById(R.id.txt_name);
                if(theCurrenUser.getUid().equals(model.getUserId())){
                    name.setTextColor(getResources().getColor(R.color.colorWhite));
                    name.setText(String.valueOf(model.getDisplayName()) + " (You)");
                }else{
                    name.setTextColor(getResources().getColor(R.color.colorWhite));
                    name.setText(String.valueOf(model.getDisplayName()));
                }



                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent profileIntent = new Intent(AddFriendActivity.this,AnotherUserActivity.class);
                        String userId = getRef(position).getKey();
                        profileIntent.putExtra("userId", userId);
                        startActivity(profileIntent);

                    }
                });

            }
        };

        leaderboardList.setAdapter(fListAdapter);

    }


    public void signOut(){
        AuthUI.getInstance().signOut(AddFriendActivity.this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            startActivity(MainActivity.createIntent(AddFriendActivity.this));
                            finish();
                        } else {
                            // Signout failed
                        }
                    }
                });
    }


    private void settings(View view){
        Intent intent = new Intent(this, ProfileSettingsActivity.class);
        startActivity(intent);
    }
    private void addFavorite(View view){
        Intent intent = new Intent(this, AddFavoritesActivity.class);
        startActivity(intent);
    }
}
