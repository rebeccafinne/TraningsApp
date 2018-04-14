package kandidat.trainingapp.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

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


      /*  userRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String userName = dataSnapshot.child("displayName").getValue().toString();
                String UID = "Just something";
                dataModels.add(new AddFriendModel(userName,UID));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/


        adapter = new AddFriendAdapter(dataModels,getApplicationContext());


    }

    private void searchForUsers(String theSearch) {

        Query searchQuery = userRef.orderByChild("email").startAt(theSearch)
                .endAt(theSearch + "\uf8ff");

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
                    name.setTextColor(Color.rgb(0,108,0));
                    name.setText(String.valueOf(model.getDisplayName()) + " (You)");
                }else{
                    name.setTextColor(Color.rgb(0,0,108));
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
}
