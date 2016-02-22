package com.example.administrator.LookAndLost.entity;

/**
 * Created by Administrator on 2016/2/18.
 */
public class LookAndLostEntity {
    int id;
    String title;
    String location;
    String note;
    String img;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "LookAndLostEntity{" +
                "title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", note='" + note + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
