package kandidat.trainingapp.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kandidat.trainingapp.R;
import kandidat.trainingapp.Repositories.UserInformation;

public class AddFriendActivity extends AppCompatActivity {

    private FirebaseDatabase db;
    private DatabaseReference ref;
    private ListView leaderboardList;
    private FirebaseUser theCurrenUser = FirebaseAuth.getInstance().getCurrentUser();
    private TextView toolText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        db = FirebaseDatabase.getInstance();
        ref = db.getReference();
        leaderboardList = (ListView) findViewById(R.id.leaderList);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_fragment);
        toolText = (TextView) toolbar.findViewById(R.id.toolbar_text);
        toolText.setText("Add new friend");

        FirebaseListAdapter<UserInformation> fListAdapter = new FirebaseListAdapter<UserInformation>(
                AddFriendActivity.this,
                UserInformation.class,
                R.layout.alluser_representation,
                ref.child("users").orderByChild("displayName")
        ) {
            TextView name;


            @Override
            protected void populateView(View v, UserInformation model, int position) {

                name = v.findViewById(R.id.txt_name);
                name.setText(String.valueOf(model.getDisplayName()));

                if(theCurrenUser.getUid().equals(model.getUserId())){
                    name.setTextColor(Color.rgb(0,108,0));
                    name.setText(String.valueOf(model.getDisplayName()) + " (You)");
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
