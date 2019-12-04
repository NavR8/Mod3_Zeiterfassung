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
import java.util.concurrent.TimeUnit;

public class HomeFragment extends Fragment {

//    private HomeViewModel homeViewModel;



    //---ORIGINAL
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);

//                mTextViewCountDown = textView.findViewById(R.id.arbeitsZeitVerbleibend);
//            }
//        });
//        return root;
        //---ORIGINAL

//        return textView;

        //----------- youtube Android tutorial (2018) - 13
//    @Override
//    public View onCreateView(LayoutInflater inflater,viewGroup container, )
//    = inflater.inflate(R.layout.fragment_home,container,false);
//            mTextViewCountDown = view.findViewById(R.id.arbeitsZeitVerbleibend);
//
//        return view;

        //-----------

        //Coding with flow

        //coding with flow:
        // CHECK this: updateCountDownText by search to see connection to arbeitsZeitVerbleibend
//        mTextViewCountDown= View
////        mTextViewCountDown=findViewById(R.id.arbeitsZeitVerbleibend);
//        mButtonStart = findViewById(R.id.startButton);
//        mButtonPause = findViewById(R.id.pauseButton);
//        mButtonEnde = findViewById(R.id.endeButton);
//
//        mButtonStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        mButtonStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startTimer();
//            }
//        });
//        mButtonPause.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pauseTimer();
//            }
//        });
//
//        mButtonEnde.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                endTimer();
//            }
//        });
//        updateCountDownText();
//    }
//
////Coding with flow:
//    private void startTimer(){
//        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                mTimeLeftInMillis = millisUntilFinished;
//                updateCountDownText();
//            }
//
//            @Override
//            public void onFinish() {
//                mTimerRunning = false;
////                mButtonStart.setText("Start");
////                mButtonStart.setVisibility(View.INVISIBLE);
////                mButtonEnde.setVisibility(View.VISIBLE);
//            }
//        }.start();
//
//        mTimerRunning= true;
////        mButtonStart.setText("pause");
////        mButtonEnde.setVisibility(View.INVISIBLE);
//    }
//    private void pauseTimer(){
//        mCountDownTimer.cancel();
//        mTimerRunning =false;
////        mButtonStart.setText("Start");
////        mButtonEnde.setVisibility(View.VISIBLE);
//    }
//    private void endTimer(){
//        mTimeLeftInMillis = START_TIME_IN_MILLIS;
//        updateCountDownText();
////        mButtonEnde.setVisibility(View.INVISIBLE);
////        mButtonStart.setVisibility(View.VISIBLE);
//    }
//
//    private void updateCountDownText(){
//        int hours=(int) (mTimeLeftInMillis /1000) /60;
//        int minutes=(int) (mTimeLeftInMillis / 1000) /60;
//        int seconds=(int) (mTimeLeftInMillis / 1000) %60;
//
//        String timeLeftFormatted =String.format(Locale.getDefault(),"%02d:%02d:%02d",hours,minutes, seconds);
//
//        mTextViewCountDown.setText(timeLeftFormatted);
//End of Coding with flow

//    }

    //from coding with flow:
    private static final long START_TIME_IN_MILLIS = 28800000; // 480000; 28800000 millisec != 8 hrs (60/60/60) >> 1min = 60.000 Millisec, 6 sec = 6.000 millisec!!
    private static final long BREAK_TIME = 216000;
    private TextView mTextViewCountDown;
    private Button mButtonStart;
    private Button mButtonPause;
    private Button mButtonEnde;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private long mBreakTimeLeft = BREAK_TIME;



//    private FragmentAListener listener;
//public interface  FragmentAListener{
//    void onInputASent(CharSequence  input);
//}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        mTextViewCountDown = v.findViewById(R.id.arbeitsZeitVerbleibend);
        mButtonStart = v.findViewById(R.id.startButton);
        mButtonPause = v.findViewById(R.id.pauseButton);
        mButtonEnde = v.findViewById(R.id.endeButton);

        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWorkTimer();
            }
        });
        mButtonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning){
                    pauseWorkTimer();
                }else{
                    startWorkTimer();
                }
            }
        });
        mButtonEnde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endWorkTimer();
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
//                mButtonStart.setText("Start");
//                mButtonStart.setVisibility(View.INVISIBLE);
//                mButtonEnde.setVisibility(View.VISIBLE);
            }
        }.start();

//        //BreakTimer:
//        mCountDownTimer = new CountDownTimer(mBreakTimeLeft, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                mBreakTimeLeft = millisUntilFinished;
//                updateCountDownText();
//            }
//
//            @Override
//            public void onFinish() {
//                mTimerRunning = false;
//            }
//        }.start(); //BreakTimer code END

        mTimerRunning= true;
//        mButtonStart.setText("pause");
//        mButtonEnde.setVisibility(View.INVISIBLE);
    }
    private void pauseWorkTimer(){
        mCountDownTimer.cancel();
        mTimerRunning=false;
//        mButtonStart.setText("Start");
//        mButtonEnde.setVisibility(View.VISIBLE);
    }
    private void endWorkTimer(){
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        mCountDownTimer.cancel();
        updateCountDownText();
//        mButtonEnde.setVisibility(View.INVISIBLE);
//        mButtonStart.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {
        //from coding with flow (hour is not working!):
//            int hours = (int) (mTimeLeftInMillis / 1000) / 36000; // 480000; 28800000
//            int minutes = (int) (mTimeLeftInMillis /1000) /60;
//            int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
//
//            String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
//
//            mTextViewCountDown.setText(timeLeftFormatted);
// End of Coding in flow code.

            //stackoverflow:
        int hours = (int) TimeUnit.MILLISECONDS.toHours(mTimeLeftInMillis);
        int minutes = (int) ((int) TimeUnit.MILLISECONDS.toMinutes(mTimeLeftInMillis) % TimeUnit.HOURS.toMinutes(1));
        int seconds = (int) ((int)TimeUnit.MILLISECONDS.toSeconds(mTimeLeftInMillis) % TimeUnit.MINUTES.toSeconds(1));

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
        //stackoverflow end
        }
  //End Coding flow
//    }
}

