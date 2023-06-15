package com.example.newapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class EventRVAdapter extends RecyclerView.Adapter<EventRVAdapter.ViewHolder> {
    // creating variables for our list, context, interface and position.
    private final ArrayList<EventRVModal> eventRVModalArrayList;
    private final Context context;
    private final EventClickInterface eventClickInterface;
    int lastPos = -1;

    // creating a constructor.
    public EventRVAdapter(ArrayList<EventRVModal> eventRVModalArrayList, Context context, EventClickInterface eventClickInterface) {
        this.eventRVModalArrayList = eventRVModalArrayList;
        this.context = context;
        this.eventClickInterface = eventClickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout file on below line.
        View view = LayoutInflater.from(context).inflate(R.layout.event_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // setting data to our recycler view item on below line.
        EventRVModal eventRVModal = eventRVModalArrayList.get(position);
        holder.eventTV.setText(eventRVModal.getEventName());
        holder.eventDateTV.setText(eventRVModal.getEventDate());
        Picasso.get().load(eventRVModal.getEventImgLink()).into(holder.eventIV);
        // adding animation to recycler view item on below line.
        setAnimation(holder.itemView, position);
        holder.eventIV.setOnClickListener(v -> eventClickInterface.onEventClick(position));
     }

    private void setAnimation(View itemView, int position) {
        if (position > lastPos) {
            // on below line we are setting animation.
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }

    @Override
    public int getItemCount() {
        return eventRVModalArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // creating variable for our image view and text view on below line.
        private ImageView eventIV;
        private TextView eventTV, eventDateTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing all our variables on below line.
            eventIV = itemView.findViewById(R.id.idIVEvent);
            eventTV = itemView.findViewById(R.id.idTVEventName);
            eventDateTV = itemView.findViewById(R.id.idTVDate);
        }
    }

    // creating a interface for on click
    public interface EventClickInterface {
        void onEventClick(int position);
    }
}



















