package com.example.newapp;

import static com.example.newapp.R.*;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements EventRVAdapter.EventClickInterface {
    private ProgressBar loadingPB;
    private ArrayList<EventRVModal> eventRVModalArrayList;
    private EventRVAdapter eventRVAdapter;
    private RelativeLayout homeRL, RLBSheet;
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
//initialize all variable here
        RecyclerView eventRV = findViewById(R.id.idRVEvent);
        homeRL = findViewById(R.id.idRLHome);
        loadingPB = findViewById(R.id.idPBLoading);
        //    creating variable for database and listView
        FloatingActionButton addEventFAB = findViewById(R.id.idFABAddEvent);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        eventRVModalArrayList = new ArrayList<>();
//        get database reference
        databaseReference = firebaseDatabase.getReference("events");
//        adding click event to add event to float button
        addEventFAB.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddEventActivity.class);
            startActivity(intent);
        });
//initialize adapter class
        eventRVAdapter = new EventRVAdapter(eventRVModalArrayList,this, this);
//  set layout manager to recycler view
        eventRV.setLayoutManager(new LinearLayoutManager(this));
//  set adapter to recycle view
        eventRV.setAdapter(eventRVAdapter);
        getEvents();
    }
    public void getEvents() {
//        first clear list
        eventRVModalArrayList.clear();
//        calling add child event listener
        databaseReference.addChildEventListener(new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                hiding progress bar
                loadingPB.setVisibility(View.GONE);
//                add snapshot to array list
                eventRVModalArrayList.add(snapshot.getValue(EventRVModal.class));
//                notify adapter that data has changed
                eventRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

//                when new event add this function is called
                loadingPB.setVisibility(View.GONE);
                eventRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // notifying our adapter when child is removed.
                eventRVAdapter.notifyDataSetChanged();
                loadingPB.setVisibility(View.GONE);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // notifying our adapter when child is removed.
                eventRVAdapter.notifyDataSetChanged();
                loadingPB.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onEventClick(int position) {
//        calling a method to a bottom sheet
        displayBottomSheet(eventRVModalArrayList.get(position));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        add click listener for option selected
        int id = item.getItemId();

        if (id == R.id.idLogOut) {
            Toast.makeText(getApplicationContext(), "User Logged Out", Toast.LENGTH_SHORT).show();
            // sign out a user
            mAuth.signOut();
            // opening login activity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            this.finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }
    private void displayBottomSheet(EventRVModal modal) {

        // creating bottom sheet dialog
        RLBSheet = findViewById(id.idRLBSheet);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);

        // inflating layout file for bottom sheet
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_layout,RLBSheet);

        // removing view from its parent
//        ((ViewGroup) layout.getParent()).removeView(layout);

        // setting content view for bottom sheet
        bottomSheetDialog.setContentView(layout);

        // setting a cancelable
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);

        // calling a method to display bottom sheet
        bottomSheetDialog.show();

        // setting data to different views
        TextView eventNameTV = layout.findViewById(R.id.idTVEventName);
        TextView eventLocationTV = layout.findViewById(R.id.idTVEventLocation);
        TextView eventDateTV = layout.findViewById(R.id.idTVDate);
        ImageView eventIV = layout.findViewById(R.id.idIVEvent);

        eventNameTV.setText(modal.getEventName());
        eventLocationTV.setText(modal.getEventLocation());
        eventDateTV.setText(modal.getEventDate());
        Picasso.get().load(modal.getEventImgLink()).into(eventIV);

        Button viewBtn = layout.findViewById(R.id.idBtnVIewDetails);
        Button editBtn = layout.findViewById(R.id.idBtnEditEvent);

        // adding on click listener for our edit button
        editBtn.setOnClickListener(view -> {
            // open editEventAction
            Intent intent = new Intent(MainActivity.this, EditEventActivity.class);
            // passing modal
            intent.putExtra("event", modal);
            startActivity(intent);
        });

        // adding on click listener for view button
        viewBtn.setOnClickListener(view -> {
            // display event details
        });
    }

}









