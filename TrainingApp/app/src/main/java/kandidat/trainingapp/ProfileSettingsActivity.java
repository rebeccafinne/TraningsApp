package kandidat.trainingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ProfileSettingsActivity extends AppCompatActivity {

    private Button save;
    private ImageButton backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        save = (Button)findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            save(view);
        }
    });

    }

    private void save(View view) {
        //Intent intent = new Intent(this, AppMainActivity.class);
        //startActivity(intent);
        finish();
    }

   
}
