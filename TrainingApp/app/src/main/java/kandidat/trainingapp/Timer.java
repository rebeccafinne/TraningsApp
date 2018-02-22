package kandidat.trainingapp;

import android.content.Context;

/**
 * Created by Anna on 2018-02-22.
 */

public class Timer implements Runnable{

    public static final long MILLIS_TO_MINUTES = 60000;
    public static final long MILLIS_TO_HOURS  = 3600000;

    private Context timerContext;
    private long timerStartTime;
    private long timeWhenPaused;
    private long timePaused;
    private long totalTimePaused;
    private Boolean paused;

    private boolean isRunning;

    public Timer(Context timerContext) {
        this.timerContext = timerContext;
    }

    public void startTimer() {
        if (paused == null){
            timerStartTime = System.currentTimeMillis();
            totalTimePaused = 0;
            timePaused = 0;
            isRunning = true;
        }
        paused = false;
        totalTimePaused = totalTimePaused + timePaused;
    }

    public void pausTimer() {
        if (!paused) {
            timeWhenPaused = System.currentTimeMillis();
            paused = true;
        }
    }

    public void stopTimer(){
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning){
            if (!paused) {
                long since = System.currentTimeMillis() - timerStartTime - totalTimePaused;

                int seconds = (int) (since / 1000) % 60;
                int minutes = (int) (since / MILLIS_TO_MINUTES) % 60;
                int hours = (int) (since / MILLIS_TO_HOURS) % 24;

                ((GymActivity) timerContext).updateTimerText(String.format("%02d:%02d:%02d", hours, minutes, seconds));

            }else{
                timePaused = System.currentTimeMillis() - timeWhenPaused;
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
