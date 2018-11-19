package com.example.user.gamebacklog;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

@Entity(tableName = "backlogGame")
public class Backlog implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private String platform;
    private String notes;
    private String status;
    private Date saveDate;

    public Date getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public Backlog() {}

    public Backlog(String title, String platform, String notes, String status, Date saveDate) {
        this.title = title;
        this.platform = platform;
        this.notes = notes;
        this.status = status;
        this.saveDate = saveDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.platform);
        dest.writeString(this.notes);
        dest.writeString(this.status);
        dest.writeSerializable(this.saveDate);
    }

    protected Backlog(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.platform = in.readString();
        this.notes = in.readString();
        this.status = in.readString();
        this.saveDate = (Date) in.readSerializable();
    }

    public static final Parcelable.Creator<Backlog> CREATOR = new Parcelable.Creator<Backlog>() {
        @Override
        public Backlog createFromParcel(Parcel source) {
            return new Backlog(source);
        }

        @Override
        public Backlog[] newArray(int size) {
            return new Backlog[size];
        }
    };
}
