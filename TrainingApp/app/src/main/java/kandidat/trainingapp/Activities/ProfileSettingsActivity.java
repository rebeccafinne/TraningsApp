package kandidat.trainingapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kandidat.trainingapp.R;

public class ProfileSettingsActivity extends AppCompatActivity {

    private Button save;
    private EditText editDisplayName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity);
        TextView textView = (TextView) toolbar.findViewById(R.id.activity_text);

        textView.setText(R.string.settings);

        editDisplayName = (EditText) findViewById(R.id.edit_username);
        save = (Button)findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            String theNameWanted = editDisplayName.getText().toString();
            changeDisplayName(theNameWanted);
        }
    });

    }

   private void changeDisplayName(String name){
       FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

       UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
               .setDisplayName(name)
               .build();

       user.updateProfile(profileUpdates)
               .addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       if (task.isSuccessful()) {
                           Toast.makeText(
                                   ProfileSettingsActivity.this,
                                   "You have changed your displayname",
                                   Toast.LENGTH_SHORT
                           ).show();
                           FirebaseUser me = FirebaseAuth.getInstance().getCurrentUser();
                           String UID = me.getUid();
                           FirebaseDatabase db = FirebaseDatabase.getInstance();
                           DatabaseReference ref = db.getReference().child("users").child(UID).child("displayName");
                           ref.setValue(me.getDisplayName());
                       }
                   }
               });

       finish();
   }

   
}
