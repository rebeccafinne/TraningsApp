package kandidat.trainingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        welcomeText = (TextView) findViewById(R.id.profile_text);
        mAuth =FirebaseAuth.getInstance();
        welcomeText.setText("Welcome " + mAuth.getCurrentUser().getEmail());
    }
}
