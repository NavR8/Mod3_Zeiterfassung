package com.nt28.modulo3_zeiterfassung;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


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
                Snackbar.make(view, "Für Updates Internetverbindung nötig", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_events, R.id.nav_send)
//                  Gehört oben in Klammer: ,R.id.nav_slideshow,R.id.nav_tools, R.id.nav_share

                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

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

        Button mShowDialog = findViewById(R.id.showDialogBtn);
        mShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_login, null);
                final EditText mPersNr = mView.findViewById(R.id.etPersonalNr);
                final EditText mPassword = mView.findViewById(R.id.password);
                Button mLogin = mView.findViewById(R.id.loginBtn);

                mLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!mPersNr.getText().toString().isEmpty() && !mPassword.getText().toString().isEmpty()){
//                           //Original from video on y.tube> Android custom dialog = Create Android Alertdialog with a custom layout.mp4
                            Toast.makeText(MainActivity.this,
                                    getString(R.string.login_ok), Toast.LENGTH_SHORT).show();

                            //change background color for Toast
//                            Toast toast = Toast.makeText(MainActivity.this,  getString(R.string.login_ok), Toast.LENGTH_LONG);
//                            View view = toast.getView();
//                            view.setBackgroundResource(R.drawable.custom_background);
                            //background color change end
                            //view.getBackground().setColorFilter(getColor., PorterDuff.Mode.SRC_IN);
//                            view.setBackgroundColor(Color.YELLOW); //ZEIGT keine Aenderung!
                            //view.getSolidColor(Color.WHITE); //TextColor(Color.WHITE);

                        }else{
                            Toast.makeText(MainActivity.this,
                                    getString(R.string.error_login), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });


    }

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
