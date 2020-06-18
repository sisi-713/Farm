/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.icss.happyfarm.bean;

/**
 *肥料类
 * @author Administrator
 */
public class Muck {

    private int muckId;   //肥料编号
    private int muckType;     //肥料类型 0表示普通肥料，1表示高级肥料，2表示特效肥料
    private int muckPrice;    //肥料价格
    private int muckEffect;      //肥料的催熟效果
    private String muckPicture;   //肥料图片

    public int getMuckId() {
        return muckId;
    }

    public void setMuckId(int muckId) {
        this.muckId = muckId;
    }

    public int getMuckEffect() {
        return muckEffect;
    }

    public void setMuckEffect(int muckEffect) {
        this.muckEffect = muckEffect;
    }

    public String getMuckPicture() {
        return muckPicture;
    }

    public void setMuckPicture(String muckPicture) {
        this.muckPicture = muckPicture;
    }

    public int getMuckPrice() {
        return muckPrice;
    }

    public void setMuckPrice(int muckPrice) {
        this.muckPrice = muckPrice;
    }

    public int getMuckType() {
        return muckType;
    }

    public void setMuckType(int muckType) {
        this.muckType = muckType;
    }

   
}
