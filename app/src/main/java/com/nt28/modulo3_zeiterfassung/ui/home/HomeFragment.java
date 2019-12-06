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
    private Button mButtonReset;
    private CountDownTimer mCountDownTimer;
    private CountDownTimer mCountDownPauseTimer;
    private boolean mTimerRunning;
    private boolean mPauseTimerRunning;
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
        mButtonReset = v.findViewById(R.id.resetButton);

        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(!mTimerRunning) {//2
                    startWorkTimer();  //2
//                    mCountDownPauseTimer.start(); //2
                    mButtonStart.setEnabled(false);
                    mButtonStart.setTextColor(Color.GRAY);
                    mButtonPause.setTextColor(Color.parseColor("#FF5722"));

                mTimerRunning=true; //2
//                mPauseTimerRunning=false;
                if (mPauseTimerRunning)
                mCountDownPauseTimer.cancel();
                }

//            }
        });
        mButtonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (mTimerRunning) { //2
//                    mCountDownTimer.cancel(); //2
                mPauseTimerRunning=true;
                    mTimerRunning=false; //2
                    pauseWorkTimer(); //2
                    startBreakTimer(); //2
                    mButtonPause.setEnabled(false);
                    mButtonPause.setTextColor(Color.GRAY);
                    mButtonStart.setEnabled(true);
                    mButtonStart.setTextColor(Color.parseColor("#06AF0C"));

//                }else if(mTimerRunning=false){
////                    //TEST>
////                    mTimeLeftInMillis = START_TIME_IN_MILLIS;
////                    mBreakTimeLeftInMillis = START_BREAK_TIME;
////                    updateCountDownText();
////                    //TEST END
//                    startWorkTimer();
////                    startBreakTimer();
//                    mCountDownPauseTimer.cancel();
//                }
            }
        });

        mButtonEnde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endWorkTimer();

                mButtonStart.setEnabled(true);
                mButtonStart.setTextColor(Color.parseColor("#06AF0C"));
                mButtonPause.setEnabled(true);
                mButtonPause.setTextColor(Color.parseColor("#FF5722"));
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonPause.setTextColor(Color.parseColor("#FF5722"));
                resetAllTimes();
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
//                mCountDownPauseTimer.cancel();
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
                mButtonStart.setEnabled(true);
            }
        }.start();
        mTimerRunning= true;
    }
    private void startBreakTimer(){
        //        //BreakTimer:
        mCountDownPauseTimer = new CountDownTimer(mBreakTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mBreakTimeLeftInMillis = millisUntilFinished;
                updateCountDownTextPause();
                mCountDownTimer.cancel();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
            }
        }.start();
        mTimerRunning= true;//BreakTimer code END
    }

    private void pauseWorkTimer(){
        mCountDownTimer.cancel();
        mTimerRunning=false;
    }
    private void endWorkTimer(){

        mCountDownTimer.cancel();
        mCountDownPauseTimer.cancel();
    }
    private void resetAllTimes(){
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        mBreakTimeLeftInMillis = START_BREAK_TIME;
        updateCountDownTextPause();
        updateCountDownText();
        mCountDownTimer.cancel();
        mCountDownPauseTimer.cancel();
        mButtonStart.setTextColor(Color.parseColor("#06AF0C"));
        mButtonPause.setTextColor(Color.parseColor("#FF5722"));
        mButtonStart.setEnabled(true);
        mButtonPause.setEnabled(true);
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

