package kandidat.trainingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddFavoritesActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolText;
    Button gymButton, cardioButton, dailyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_favorites);

        toolbar = (Toolbar) findViewById(R.id.toolbar_activity);
        toolText = (TextView) toolbar.findViewById(R.id.activity_text);
        toolText.setText("Add Favorite Workout");
        gymButton = (Button) findViewById(R.id.add_gym);
        gymButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        cardioButton = (Button) findViewById(R.id.add_gym);
        cardioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        dailyButton = (Button) findViewById(R.id.add_gym);
        dailyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
