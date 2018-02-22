package kandidat.trainingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GymActivity extends AppCompatActivity {

    private TextView timerText;
    private Button btnTimerStart;
    private Button btnTimerStop;

    private Context context;
    private Timer timer;
    private Thread timerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        context = this;

        timerText = (TextView) findViewById(R.id.timer_text);
        btnTimerStart = (Button) findViewById(R.id.btn_timer_start);
        btnTimerStop = (Button) findViewById(R.id.btn_timer_stop);

        btnTimerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Clicked start " , Toast.LENGTH_SHORT).show();
                if(timer == null){
                    timer = new Timer(context);
                    timerThread = new Thread(timer);
                    timerThread.start();
                    timer.startTimer();
                }
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
