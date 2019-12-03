package com.nt28.modulo3_zeiterfassung;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    private static final long START_TIME_IN_MILLIS = 28800000; // = 8 hrs

    private TextView mTextViewCountDown;
    private Button mButtonStart;
    private Button mButtonPause;
    private Button mButtonEnde;

    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
//
//    private static final String TAG = "MainActivity"; // https://firebase.google.com/docs/database/android/start >> LINK: MainActivity.java >> TO gitHub full code

    private AppBarConfiguration mAppBarConfiguration;

//public int counter; // belongs to below code
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_events)
//                  Gehört oben in Klammer: ,R.id.nav_slideshow,R.id.nav_tools, R.id.nav_share, R.id.nav_send

                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

//coding with flow:
//        mTextViewCountDown=findViewById(R.id.arbeitsZeitVerbleibend);
//        mButtonStart = findViewById(R.id.startButton);
//        mButtonPause = findViewById(R.id.pauseButton);
//        mButtonEnde = findViewById(R.id.endeButton);
//
//        mButtonStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mTimerRunning){
//                    pauseTimer();
//                }else{
//                    startTimer();
//                }
//            }
//        });
//        mButtonPause.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pauseTimer();
//            }
//        });
//        updateCountDownText();
//
//        mButtonEnde.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                endTimer();
//            }
//        });




////  Write to firebase database // FROM: https://firebase.google.com/docs/database/android/start
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//
//        myRef.setValue ("ello");
//// Read from database // FROM: https://firebase.google.com/docs/database/android/start
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: "+ value);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.w(TAG, "Failedto read value.", databaseError.toException());
//            }
//
//
//        });

//        final TextView counttime=findViewById(R.id.arbeitsZeitVerbleibend);
//        new CountDownTimer(50000,1000){
//            @Override
//            public void onTick(long millisUtilFinished){
//                counttime.setText(String.valueOf(counter));
//                counter++;
//            }
//            @Override
//            public void onFinish(){
//                counttime.setText("Finished");
//            }
//        };


    }

//Coding with flow:
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
//                mButtonStart.setText("Start");
//                mButtonStart.setVisibility(View.INVISIBLE);
//                mButtonEnde.setVisibility(View.VISIBLE);
//            }
//        }.start();
//
//        mTimerRunning= true;
//        mButtonStart.setText("pause");
//        mButtonEnde.setVisibility(View.INVISIBLE);
//    }
//    private void pauseTimer(){
//        mCountDownTimer.cancel();
//        mTimerRunning =false;
//        mButtonStart.setText("Start");
//        mButtonEnde.setVisibility(View.VISIBLE);
//    }
//    private void endTimer(){
//        mTimeLeftInMillis = START_TIME_IN_MILLIS;
//        updateCountDownText();
//        mButtonEnde.setVisibility(View.INVISIBLE);
//        mButtonStart.setVisibility(View.VISIBLE);
//    }
//
//    private void updateCountDownText(){
//        int minutes=(int) (mTimeLeftInMillis / 1000) /60;
//        int seconds=(int) (mTimeLeftInMillis / 1000) %60;
//
//        String timeLeftFormatted =String.format(Locale.getDefault(),"%02d:%02d",minutes, seconds);
//
//        mTextViewCountDown.setText(timeLeftFormatted);
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
