package kandidat.trainingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG = "FB_PROFILE";
    private FirebaseAuth mAuth;
    private DatabaseReference mrefUser;
    private DatabaseReference mrefUsername;
    private DatabaseReference mrefPoints;
    private String usernameCap;
    private TextView welcomeText,pointsText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        findViewById(R.id.logOut_btn).setOnClickListener(this);
        welcomeText = (TextView) findViewById(R.id.profile_text);
        pointsText = (TextView) findViewById(R.id.profile_points);
        mAuth = FirebaseAuth.getInstance();
        createWelcome( FirebaseDatabase.getInstance().getReference("users").child(mAuth.getUid()));
    }

    // Create customised welcomemessage with username
    public void createWelcome(DatabaseReference userNameRef){
        mrefUsername = userNameRef.child("username");
        mrefPoints = userNameRef.child("points");

        mrefUsername.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String username = dataSnapshot.getValue(String.class);
                //Capitalize username
                usernameCap = username.substring(0,1).toUpperCase() + username.substring(1);
                welcomeText.setText("Welcome " +  usernameCap + "!");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mrefPoints.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer points = dataSnapshot.getValue(Integer.class);
                pointsText.setText("You have collected " + points + " points." );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logOut_btn:
                mAuth.signOut();
                Intent loginIntent = new Intent(this, MainActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loginIntent);
                break;
        }
    }
}
