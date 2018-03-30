package kandidat.trainingapp.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import kandidat.trainingapp.R;

public class AnotherUserActivity extends AppCompatActivity {
    public TextView textV;
    public DatabaseReference dbRef;
    Toolbar toolbar;
    TextView toolText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another_user);
        toolbar = (Toolbar) findViewById(R.id.toolbar_activity);
        toolText = (TextView) toolbar.findViewById(R.id.activity_text);

        textV = (TextView) findViewById(R.id.theDisplayName);
        String userId = getIntent().getStringExtra("userId");

        dbRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String theUsersName = dataSnapshot.child("displayName").getValue().toString();
                toolText.setText(theUsersName + "'s Profile");
                textV.setText(theUsersName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        textV.setText(userId);

    }

}
