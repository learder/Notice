package com.example.administrator.LookAndLost.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 颜厥共 on 2016/2/23.
 * email:644613693@qq.com
 */
public class ImgEntity extends BaseEntity implements Parcelable {
    String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.img);
    }

    public ImgEntity() {
    }

    protected ImgEntity(Parcel in) {
        this.img = in.readString();
    }

    public static final Parcelable.Creator<ImgEntity> CREATOR = new Parcelable.Creator<ImgEntity>() {
        public ImgEntity createFromParcel(Parcel source) {
            return new ImgEntity(source);
        }

        public ImgEntity[] newArray(int size) {
            return new ImgEntity[size];
        }
    };
}
