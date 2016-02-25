package com.example.administrator.LookAndLost.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2016/2/18.
 */
public class LookAndLostEntity implements Parcelable {
    int eventId=-1;
    String title="";
    String eventType="";
    List<ImgEntity> imgs=null;
    String address="";
    String contact="";
    long timeout=0;
    String notes="";
    String userName="";
    String reward="";
    String content="";

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public List<ImgEntity> getImgs() {
        return imgs;
    }

    public void setImgs(List<ImgEntity> imgs) {
        this.imgs = imgs;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "LookAndLostEntity{" +
                "eventId=" + eventId +
                ", title='" + title + '\'' +
                ", eventType='" + eventType + '\'' +
                ", imgs=" + imgs +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", timeout=" + timeout +
                ", notes='" + notes + '\'' +
                ", userName='" + userName + '\'' +
                ", reward='" + reward + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.eventId);
        dest.writeString(this.title);
        dest.writeString(this.eventType);
        dest.writeTypedList(imgs);
        dest.writeString(this.address);
        dest.writeString(this.contact);
        dest.writeLong(this.timeout);
        dest.writeString(this.notes);
        dest.writeString(this.userName);
        dest.writeString(this.reward);
        dest.writeString(this.content);
    }

    public LookAndLostEntity() {
    }

    protected LookAndLostEntity(Parcel in) {
        this.eventId = in.readInt();
        this.title = in.readString();
        this.eventType = in.readString();
        this.imgs = in.createTypedArrayList(ImgEntity.CREATOR);
        this.address = in.readString();
        this.contact = in.readString();
        this.timeout = in.readLong();
        this.notes = in.readString();
        this.userName = in.readString();
        this.reward = in.readString();
        this.content = in.readString();
    }

    public static final Parcelable.Creator<LookAndLostEntity> CREATOR = new Parcelable.Creator<LookAndLostEntity>() {
        public LookAndLostEntity createFromParcel(Parcel source) {
            return new LookAndLostEntity(source);
        }

        public LookAndLostEntity[] newArray(int size) {
            return new LookAndLostEntity[size];
        }
    };
}
