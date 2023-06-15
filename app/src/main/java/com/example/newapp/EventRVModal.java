package com.example.newapp;

import android.os.Parcel;
import android.os.Parcelable;

public class EventRVModal implements Parcelable {
//    creating variable
    private String eventName;
    private String eventLocation;
    private String eventDate;
    private String eventImgLink;
    private String eventDesc;

    private String eventId;

    public EventRVModal() {
        // Required empty constructor for Firebase
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventImgLink() {
        return eventImgLink;
    }

    public void setEventImgLink(String eventImgLink) {
        this.eventImgLink = eventImgLink;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public EventRVModal(String eventName, String eventLocation, String eventDate, String eventImgLink, String eventDesc, String eventId) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.eventImgLink = eventImgLink;
        this.eventDesc = eventDesc;
        this.eventId = eventId;
    }

    protected EventRVModal(Parcel in) {
        eventName = in.readString();
        eventLocation = in.readString();
        eventDate = in.readString();
        eventImgLink = in.readString();
        eventDesc = in.readString();
        eventId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(eventName);
        dest.writeString(eventLocation);
        dest.writeString(eventDate);
        dest.writeString(eventImgLink);
        dest.writeString(eventDesc);
        dest.writeString(eventId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EventRVModal> CREATOR = new Creator<EventRVModal>() {
        @Override
        public EventRVModal createFromParcel(Parcel in) {
            return new EventRVModal(in);
        }

        @Override
        public EventRVModal[] newArray(int size) {
            return new EventRVModal[size];
        }
    };
}
