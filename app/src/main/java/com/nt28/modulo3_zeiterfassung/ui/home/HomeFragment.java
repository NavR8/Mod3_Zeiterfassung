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

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    private Button mArbeitStart;
    private CountDownTimer mCountDownTimer;
    private CountDownTimer mCountDownPauseTimer;
    private boolean mTimerRunning;
    private boolean mPauseTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private long mBreakTimeLeftInMillis = START_BREAK_TIME;

    private TextView mTVstartetTime;
    private TextView mTVendedTime;
    private TextView mTVshowStartetTime;
    private TextView mTVshowEndedTime;


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
        mArbeitStart = v.findViewById(R.id.startWorkBtn);

        mTVstartetTime = v.findViewById(R.id.startedTime);
        mTVendedTime = v.findViewById(R.id.endedTime);
        mTVshowStartetTime =v.findViewById(R.id.showStartedTime);
        mTVshowEndedTime = v.findViewById(R.id.showEndedTime);

        mButtonStart.setVisibility(View.INVISIBLE);
        mButtonPause.setVisibility(View.INVISIBLE);
        mButtonEnde.setVisibility(View.INVISIBLE);
        mButtonReset.setVisibility(View.INVISIBLE);

        final TextView tvStartTime = v.findViewById(R.id.showStartedTime);
        final TextView tvEndTime = v.findViewById(R.id.showEndedTime);

        mArbeitStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWorkTimer();
                mArbeitStart.setVisibility(View.INVISIBLE);

                //saveWorkTime();
                SimpleDateFormat format  = new SimpleDateFormat("EEEE dd.MMM.yyyy, hh:mm:ss a");
                Calendar calendar = Calendar.getInstance(); // OK, bei anderem video gleich// This from Coding in Flow.
                String dateTime = format.format(calendar.getTime());
                tvStartTime.setText(dateTime);

                mButtonStart.setVisibility(View.VISIBLE);
                mButtonPause.setVisibility(View.VISIBLE);
                mButtonEnde.setVisibility(View.VISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);

                mButtonStart.setEnabled(false);
                mButtonStart.setTextColor(Color.GRAY);
                mButtonEnde.setEnabled(false);
                mButtonEnde.setTextColor(Color.GRAY);
                mButtonReset.setEnabled(false);
                mButtonReset.setTextColor(Color.GRAY);

                mTVstartetTime.setVisibility(View.VISIBLE);
                mTVshowStartetTime.setVisibility(View.VISIBLE);
                mTVshowEndedTime.setVisibility(View.INVISIBLE);
                mTVendedTime.setVisibility(View.INVISIBLE);


            }
        });

        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startWorkTimer();
                    mButtonStart.setEnabled(false);
                    mButtonStart.setTextColor(Color.GRAY);
                mButtonPause.setTextColor(Color.parseColor("#FF5722"));
                mButtonPause.setEnabled(true);

                mTimerRunning=true;



                if (mPauseTimerRunning)
                mCountDownPauseTimer.cancel();
                }
        });
        mButtonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPauseTimerRunning=true;
                    mTimerRunning=false; //2
                    pauseWorkTimer(); //2
                    startBreakTimer(); //2
                    mButtonPause.setEnabled(false);
                    mButtonPause.setTextColor(Color.GRAY);
                    mButtonStart.setEnabled(true);
                    mButtonStart.setTextColor(Color.parseColor("#06AF0C"));
                mButtonEnde.setTextColor(Color.parseColor("#F8D60000"));
                mButtonEnde.setEnabled(true);

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
                mButtonReset.setEnabled(true);
                mButtonReset.setTextColor(Color.parseColor("#FFFFFF"));

                SimpleDateFormat format  = new SimpleDateFormat("EEEE dd.MMM.yyyy, hh:mm:ss a");
                Calendar calendar = Calendar.getInstance(); // OK, bei anderem video gleich// This from Coding in Flow.
                String dateTime = format.format(calendar.getTime());
                tvEndTime.setText(dateTime);

                mTVendedTime.setVisibility(View.VISIBLE);
                mTVshowEndedTime.setVisibility(View.VISIBLE);
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAllTimes();
                mArbeitStart.setVisibility(View.VISIBLE);

                mButtonStart.setVisibility(View.INVISIBLE);
                mButtonPause.setVisibility(View.INVISIBLE);
                mButtonEnde.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.INVISIBLE);

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

        //Zuruck zum AnfangsSituation der 3 Buttons:
//        mButtonPause.setEnabled(false);
//        mButtonPause.setTextColor(Color.GRAY);
//        mButtonEnde.setEnabled(false);
//        mButtonEnde.setTextColor(Color.GRAY);
//        mButtonReset.setEnabled(false);
//        mButtonReset.setTextColor(Color.GRAY);

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

