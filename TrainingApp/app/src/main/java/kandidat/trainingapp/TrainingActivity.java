package kandidat.trainingapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TrainingActivity extends AppCompatActivity {

    private TextView timerText;
    private Button btnTimerStart;
    private Button btnTimerStop;
    private Button btnTimerPaus;

    private Context context;
    private Timer timer;
    private Thread timerThread;

    private final String TAG = "FB_TRAINING";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        timerText = (TextView) findViewById(R.id.timer_text);
        btnTimerStart = (Button) findViewById(R.id.btn_timer_start);
        btnTimerStop = (Button) findViewById(R.id.btn_timer_stop);
        btnTimerPaus = (Button) findViewById(R.id.btn_timer_paus);

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
