package com.nt28.modulo3_zeiterfassung.ui.home;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.nt28.modulo3_zeiterfassung.R;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

//from coding with flow:

    private static final long START_TIME_IN_MILLIS = 28800000; // = 8 hrs
    private TextView mTextViewCountDown;
    private Button mButtonStart;
    private Button mButtonPause;
    private Button mButtonEnde;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    //End Coding flow

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;


        //Coding with flow

        //coding with flow:
        // CHECK this: updateCountDownText by search to see connection to arbeitsZeitVerbleibend
        mTextViewCountDown= View
//        mTextViewCountDown=findViewById(R.id.arbeitsZeitVerbleibend);
        mButtonStart = findViewById(R.id.startButton);
        mButtonPause = findViewById(R.id.pauseButton);
        mButtonEnde = findViewById(R.id.endeButton);

        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });
        mButtonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
            }
        });

        mButtonEnde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTimer();
            }
        });
        updateCountDownText();
    }

//Coding with flow:
    private void startTimer(){
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
//                mButtonStart.setText("Start");
//                mButtonStart.setVisibility(View.INVISIBLE);
//                mButtonEnde.setVisibility(View.VISIBLE);
            }
        }.start();

        mTimerRunning= true;
//        mButtonStart.setText("pause");
//        mButtonEnde.setVisibility(View.INVISIBLE);
    }
    private void pauseTimer(){
        mCountDownTimer.cancel();
        mTimerRunning =false;
//        mButtonStart.setText("Start");
//        mButtonEnde.setVisibility(View.VISIBLE);
    }
    private void endTimer(){
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
//        mButtonEnde.setVisibility(View.INVISIBLE);
//        mButtonStart.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText(){
        int hours=(int) (mTimeLeftInMillis /1000) /60;
        int minutes=(int) (mTimeLeftInMillis / 1000) /60;
        int seconds=(int) (mTimeLeftInMillis / 1000) %60;

        String timeLeftFormatted =String.format(Locale.getDefault(),"%02d:%02d:%02d",hours,minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
//End of Coding with flow
    }
}