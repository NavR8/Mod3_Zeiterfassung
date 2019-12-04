package com.nt28.modulo3_zeiterfassung.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.nt28.modulo3_zeiterfassung.R;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class HomeFragment extends Fragment {

    //from coding with flow:
    private static final long START_TIME_IN_MILLIS = 28800000; // 480000; 28800000 millisec != 8 hrs (60/60/60) >> 1min = 60.000 Millisec, 6 sec = 6.000 millisec!!
    private static final long START_BREAK_TIME = 3600000; // 3600000 millisec = 1 hr
    private TextView mTextViewCountDown;
    private TextView mTextViewPauseCountDown;
    private Button mButtonStart;
    private Button mButtonPause;
    private Button mButtonEnde;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private long mBreakTimeLeftInMillis = START_BREAK_TIME;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        mTextViewCountDown = v.findViewById(R.id.arbeitsZeitVerbleibend);
        mTextViewPauseCountDown = v.findViewById(R.id.pauseZeitVerbleibend);
        mButtonStart = v.findViewById(R.id.startButton);
        mButtonPause = v.findViewById(R.id.pauseButton);
        mButtonEnde = v.findViewById(R.id.endeButton);

        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWorkTimer();
                mButtonStart.setEnabled(false);
                mButtonStart.setTextColor(Color.GRAY);
                mBreakTimeLeftInMillis = START_BREAK_TIME;
            }
        });
        mButtonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning){
                    pauseWorkTimer();
                    startPauseTimer();
                }else{
                    startWorkTimer();
                    pausePauseTimer();
                }
            }
        });
        mButtonEnde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endWorkTimer();
                mButtonStart.setEnabled(true);
                mButtonStart.setTextColor(Color.parseColor("#06AF0C"));
            }
        });
        return v;
    }
        //WorkTimer
        private void startWorkTimer(){
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
            }
        }.start();

        mTimerRunning= true;
//        mButtonStart.setText("pause");
//        mButtonEnde.setVisibility(View.INVISIBLE);
    }
    private void startPauseTimer(){
        //        //BreakTimer:
        mCountDownTimer = new CountDownTimer(mBreakTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mBreakTimeLeftInMillis = millisUntilFinished;
                updateCountDownTextPause();

            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
            }
        }.start(); //BreakTimer code END
    }

    private void pauseWorkTimer(){
        mCountDownTimer.cancel();
        mTimerRunning=false;
    }
    private void pausePauseTimer(){
        mCountDownTimer.cancel();
        mTimerRunning=false;
    }
    private void endWorkTimer(){
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        mCountDownTimer.cancel();
        mBreakTimeLeftInMillis = START_BREAK_TIME;
        updateCountDownText();
    }
    private void pauseTimer(){

    }

    private void updateCountDownText() {

            //stackoverflow:
        int hours = (int) TimeUnit.MILLISECONDS.toHours(mTimeLeftInMillis);
        int minutes = (int) ((int) TimeUnit.MILLISECONDS.toMinutes(mTimeLeftInMillis) % TimeUnit.HOURS.toMinutes(1));
        int seconds = (int) ((int)TimeUnit.MILLISECONDS.toSeconds(mTimeLeftInMillis) % TimeUnit.MINUTES.toSeconds(1));

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
        //stackoverflow end
        }
        private void updateCountDownTextPause(){
            int hours = (int) TimeUnit.MILLISECONDS.toHours(mBreakTimeLeftInMillis);
            int minutes = (int) ((int) TimeUnit.MILLISECONDS.toMinutes(mBreakTimeLeftInMillis) % TimeUnit.HOURS.toMinutes(1));
            int seconds = (int) ((int)TimeUnit.MILLISECONDS.toSeconds(mBreakTimeLeftInMillis) % TimeUnit.MINUTES.toSeconds(1));

            String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
            mTextViewPauseCountDown.setText(timeLeftFormatted);


        }
}

