package com.example.natan.sqllitetodo.Adapter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by natan on 2/1/2018.
 */

public class Todo implements Parcelable {

    String title, date_time;


    public Todo(String title, String date_time) {
        this.title = title;
        this.date_time = date_time;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.date_time);
    }

    protected Todo(Parcel in) {
        this.title = in.readString();
        this.date_time = in.readString();
    }

    public static final Parcelable.Creator<Todo> CREATOR = new Parcelable.Creator<Todo>() {
        @Override
        public Todo createFromParcel(Parcel source) {
            return new Todo(source);
        }

        @Override
        public Todo[] newArray(int size) {
            return new Todo[size];
        }
    };
}
