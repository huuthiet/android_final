package com.example.myfinal.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class RoomItem implements Parcelable {
    int roomId;
    private int resourceImgRoomId;
    private String roomName;

    public RoomItem(int roomId, int resourceImgRoomId, String roomName) {
        this.roomId = roomId;
        this.resourceImgRoomId = resourceImgRoomId;
        this.roomName = roomName;
    }

    protected RoomItem(Parcel in) {
        roomId = in.readInt();
        resourceImgRoomId = in.readInt();
        roomName = in.readString();
    }

    public static final Creator<RoomItem> CREATOR = new Creator<RoomItem>() {
        @Override
        public RoomItem createFromParcel(Parcel in) {
            return new RoomItem(in);
        }

        @Override
        public RoomItem[] newArray(int size) {
            return new RoomItem[size];
        }
    };

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getResourceId() {
        return resourceImgRoomId;
    }

    public void setResourceId(int resourceImgRoomId) {
        this.resourceImgRoomId = resourceImgRoomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String room) {
        this.roomName = room;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(roomId);
        parcel.writeInt(resourceImgRoomId);
        parcel.writeString(roomName);
    }
}
