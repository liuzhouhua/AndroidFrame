package com.lzh.administrator.androidframe.Model;

/**
 * Created by lzh27651 on 2016/9/20.
 * 站点
 */

public class TrainStationBean {

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

}
