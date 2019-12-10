package com.nt28.modulo3_zeiterfassung.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nt28.modulo3_zeiterfassung.R;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class GalleryFragment extends Fragment {


     private DatabaseReference refFDB;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//  super.onCreate(savedInstanceState);

//        galleryViewModel =
//                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View v = inflater.inflate(R.layout.fragment_gallery, container, false);
//        final TextView textView = root.findViewById(R.id.text_gallery);
//        galleryViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        refFDB = FirebaseDatabase.getInstance().getReference().child("m3-arbeitszeitenef");
        refFDB.addValueEventListener(new ValueEventListener() {
             @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                               final TextView textBFD1 = v.findViewById(R.id.bDatum1);


//             final TextView tvEndTime = getView().findViewById(R.id.bDatum1);
//           String nameBFD1= dataSnapshot.child("m3-arbeitszeitenef").child("Betriebsfeier1").child("Datum").getValue().toString();
//           tvEndTime.setText(nameBFD1);

                  TextView tvEndTime = getView().findViewById(R.id.bDatum1);
                 String value = dataSnapshot.child("0").getValue().toString();
                    Log.d(TAG, value);
                    tvEndTime.setText((value));
          }

          @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {
              // Failed to read value
                    Log.w(TAG, "Failed to read value.", databaseError.toException());
               }
                });


                //Vorschlag 2:
//        public void basicReadWrite() {
//            // [START write_message]
//            // Write a message to the database
//            FirebaseDatabase database = FirebaseDatabase.getInstance();
//            DatabaseReference myRef = database.getReference("message");
//
//            myRef.setValue("Hello, World!");
//            // [END write_message]
//
//            // [START read_message]
//            // Read from the database
//            myRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    // This method is called once with the initial value and again
//                    // whenever data at this location is updated.
//                    String value = dataSnapshot.getValue(String.class);
//                    Log.d(TAG, "Value is: " + value);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError error) {
//                    // Failed to read value
//                    Log.w(TAG, "Failed to read value.", error.toException());
//                }
//            });

        return v;
    }
}