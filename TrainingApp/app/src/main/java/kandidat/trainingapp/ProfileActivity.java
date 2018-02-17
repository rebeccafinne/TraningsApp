package kandidat.trainingapp;

import android.content.Intent;
import android.icu.util.TimeZone;
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

public class ProfileActivity extends AppCompatActivity{
    private final String TAG = "FB_PROFILE";

    private String usernameCap;
    private TextView welcomeText,pointsText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //A reference to Authentication in Firebase
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        createWelcome( FirebaseDatabase.getInstance().getReference("users").child(mAuth.getUid()));

        findViewById(R.id.logOut_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent loginIntent = new Intent(ProfileActivity.this, MainActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loginIntent);
            }
        });

        welcomeText = (TextView) findViewById(R.id.profile_text);
        pointsText = (TextView) findViewById(R.id.profile_points);

    }

    // Create customised welcomemessage with username
    public void createWelcome(DatabaseReference userRef){

       userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation user = dataSnapshot.getValue(UserInformation.class);

                //Capitalize username
                usernameCap = user.getUsername().substring(0,1).toUpperCase() + user.getUsername().substring(1);
                welcomeText.setText("Welcome " +  usernameCap + "!");
                pointsText.setText("You have collected " + user.getPoints() + " points." );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
