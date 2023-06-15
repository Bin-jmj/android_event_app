package com.example.newapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class EditEventActivity extends AppCompatActivity {

//    create variable
    private TextInputEditText eventNameEdit, eventDateEdit, eventDescEdit,eventLocationEdit,eventImgEdit;
    private ProgressBar loadingPB;
    private String eventID;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    EventRVModal eventRVModal;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
//        initialize variable
        Button addEventBtn = findViewById(R.id.idBtnAddEvent);
        Button deleteEventBtn = findViewById(R.id.idBtnDeleteEvent);
        eventNameEdit = findViewById(R.id.idEditEventName);
        eventLocationEdit = findViewById(R.id.idEditEventLocation);
        eventDateEdit = findViewById(R.id.idEditEventDate);
        eventImgEdit = findViewById(R.id.idEditEventImageLink);
        eventDescEdit = findViewById(R.id.idEditEventDescription);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase =FirebaseDatabase.getInstance();
//        calling modal class
        eventRVModal = getIntent().getParcelableExtra("event");

        if(eventRVModal != null) {
//            edit event details in modal class
            eventNameEdit.setText(eventRVModal.getEventName());
            eventLocationEdit.setText(eventRVModal.getEventLocation());
            eventDateEdit.setText(eventRVModal.getEventDate());
            eventImgEdit.setText(eventRVModal.getEventImgLink());
            eventDescEdit.setText(eventRVModal.getEventDesc());
            eventID = eventRVModal.getEventId();
        }
//             initialize database reference
        databaseReference = firebaseDatabase.getReference("events").child(eventID);
//        adding event listener to add event button
        addEventBtn.setOnClickListener(view -> {
//                show progress bar
            loadingPB.setVisibility(View.VISIBLE);
            String eventName = (eventNameEdit.getText()).toString();
            String eventLocation = (eventLocationEdit.getText()).toString();
            String eventDate = (eventDateEdit.getText()).toString();
            String eventDesc = (eventDescEdit.getText()).toString();
            String eventImgLink = (eventImgEdit.getText()).toString();

//                creating map for passing data using key value
            Map<String, Object> map = new HashMap<>();
            map.put("eventName", eventName);
            map.put("eventLocation" , eventLocation);
            map.put("eventDate", eventDate);
            map.put("eventDesc", eventDesc);
            map.put("eventImgLink", eventImgLink);

//                adding database reference
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        hiding progress bar
                    loadingPB.setVisibility(View.GONE);
//                        adding a map to database
                    databaseReference.updateChildren(map);
                    Toast.makeText(EditEventActivity.this, "Event Updated...", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditEventActivity.this, MainActivity.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(EditEventActivity.this, "Fail to Update Event", Toast.LENGTH_SHORT).show();
                }
            });
        });

//          add event listener to the delete button
        deleteEventBtn.setOnClickListener(view -> deleteEvent());
    }
        private void deleteEvent() {
            // on below line calling a method to delete the course.
            databaseReference.removeValue();
            // displaying a toast message on below line.
            Toast.makeText(this, "Course Deleted..", Toast.LENGTH_SHORT).show();
            // opening a main activity on below line.
            startActivity(new Intent(EditEventActivity.this, MainActivity.class));
        }
}