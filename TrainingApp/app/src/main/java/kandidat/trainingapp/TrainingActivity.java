package kandidat.trainingapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrainingActivity extends AppCompatActivity {

    private TextView timerText;
    private Button btnTimerStart;
    private Button btnTimerStop;
    private Button btnTimerPaus;

    private Context context;
    private Timer timer;
    private Thread timerThread;

    private List<String> exercises;
    private Button btnAddExercise;

    private final String TAG = "FB_TRAINING";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        exercises = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.exercises)));

        final ArrayAdapter adapter = new ArrayAdapter(this,
                R.layout.activity_listview, exercises);

        ListView lstExercises = findViewById(R.id.list_view_gym_exercies);
        lstExercises.setAdapter(adapter);

        lstExercises.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                exercises.set(i, exercises.get(i) + " I");
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Clickade", Toast.LENGTH_SHORT).show(); //TODO remove when working
            }
        });

        timerText = findViewById(R.id.timer_text);
        btnTimerStart = findViewById(R.id.btn_timer_start);
        btnTimerStop = findViewById(R.id.btn_timer_stop);
        btnTimerPaus = findViewById(R.id.btn_timer_paus);
        btnAddExercise = findViewById(R.id.btn_add_ex);

        context = this;

        btnTimerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Clicked start " , Toast.LENGTH_SHORT).show(); //TODO remove when everything works.
                if(timer == null){
                    timer = new Timer(context);
                    timerThread = new Thread(timer);
                    timerThread.start();
                }
                timer.startTimer();
            }
        });

        btnTimerStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Clicked stop" , Toast.LENGTH_SHORT).show();
                if(timer != null){
                    timer.stopTimer();
                    timerThread.interrupt();
                    timerThread = null;

                    timer = null;
                }
            }
        });

        btnTimerPaus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Clicked Paus", Toast.LENGTH_SHORT).show();
                if(timer != null) timer.pausTimer();
            }
        });

        btnAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exercises.add("Exercise");
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Klickade Add Exercise", Toast.LENGTH_SHORT).show(); //TODO remove when working
            }
        });

    }

    public void updateTimerText(final String time){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                timerText.setText(time);
            }
        });
    }
}
