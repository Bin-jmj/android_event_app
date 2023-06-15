package com.example.newapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddEventActivity extends AppCompatActivity {

    private TextInputEditText eventNameEdit, eventLocationEdit,
            eventDescEdit, eventDateEdit, eventImgLinkEdit;
    private ProgressBar loadingPB;
    private String eventID;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

//        initialize variable
        //    creating variables
        Button addEventBtn = findViewById(R.id.idBtnAddEvent);
        eventNameEdit = findViewById(R.id.idEdtEventName);
        eventLocationEdit = findViewById(R.id.idEditEventLocation);
        eventDateEdit = findViewById(R.id.idEditEventDate);
        eventImgLinkEdit = findViewById(R.id.idEdtEventImageLink);
        eventDescEdit = findViewById(R.id.idEdtEventDescription);
        loadingPB = findViewById(R.id.idPBLoading);

//        firebase instance
        firebaseDatabase = FirebaseDatabase.getInstance();
//        creating database reference
        databaseReference = firebaseDatabase.getReference("events");

//        adding click listener for our add event button
        addEventBtn.setOnClickListener(view -> {
//                loading progress bar
            loadingPB.setVisibility(View.VISIBLE);
//                getting data from edit text
            String eventName = eventNameEdit.getText().toString();
            String eventLocation = eventLocationEdit.getText().toString();
            String eventDate = eventDateEdit.getText().toString();
            String eventImg = eventImgLinkEdit.getText().toString();
            String eventDesc = eventDescEdit.getText().toString();
            eventID = eventName;

//                pass all data to model eventModal class
            EventRVModal eventRVModal = new EventRVModal(eventName,eventLocation,eventDate,eventImg,eventDesc,eventID);
//                pass data to the firebase
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        here we set data to firebase database
                    databaseReference.child(eventID).setValue(eventRVModal);
                    Toast.makeText(AddEventActivity.this, "Event is Added...", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddEventActivity.this,MainActivity.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(AddEventActivity.this, "Fail to add Event Try again..", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
}
