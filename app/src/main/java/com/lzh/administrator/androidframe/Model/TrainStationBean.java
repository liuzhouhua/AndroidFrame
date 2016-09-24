package com.lzh.administrator.androidframe.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lzh27651 on 2016/9/20.
 * 站点
 */

public class TrainStationBean implements Parcelable{

    /**
     * 站点名称 北京
     */
    private String mName;
    /**
     * 站点拼音 beijing
     */
    private String mPinYin;
    /**
     * 站点拼音首字母 bj
     */
    private String mPinYinS;

    /**
     * 首字母 B
     */
    private String mFirstPYS;

    /**
     * 三字码 BJP
     * @return
     */
    private String mStationCode;

    public TrainStationBean(Parcel source) {
        mName = source.readString();
        mPinYin = source.readString();
        mPinYinS = source.readString();
        mStationCode = source.readString();
        mFirstPYS = source.readString();
    }

    public TrainStationBean() {

    }


    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPinYin() {
        return mPinYin;
    }

    public void setmPinYin(String mPinYin) {
        this.mPinYin = mPinYin;
    }

    public String getmPinYinS() {
        return mPinYinS;
    }

    public void setmPinYinS(String mPinYinS) {
        this.mPinYinS = mPinYinS;
    }

    public String getmFirstPYS() {
        return mFirstPYS;
    }

    public void setmFirstPYS(String mFirstPYS) {
        this.mFirstPYS = mFirstPYS;
    }

    public String getmStationCode() {
        return mStationCode;
    }

    public void setmStationCode(String mStationCode) {
        this.mStationCode = mStationCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mPinYin);
        dest.writeString(mPinYinS);
        dest.writeString(mStationCode);
        dest.writeString(mFirstPYS);
    }

    public static final  Parcelable.Creator<TrainStationBean> CREATOR = new Creator<TrainStationBean>() {
        @Override
        public TrainStationBean createFromParcel(Parcel source) {
            return new TrainStationBean(source);
        }

        @Override
        public TrainStationBean[] newArray(int size) {
            return new TrainStationBean[size];
        }
    };
}
