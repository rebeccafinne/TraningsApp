package kandidat.trainingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private TextView welcomeText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        findViewById(R.id.logOut_btn).setOnClickListener(this);
        welcomeText = (TextView) findViewById(R.id.profile_text);
        mAuth = FirebaseAuth.getInstance();
        welcomeText.setText("Welcome " + mAuth.getCurrentUser().getEmail());
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
