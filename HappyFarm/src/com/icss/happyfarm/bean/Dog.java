/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.icss.happyfarm.bean;

/**
 *宠物类
 * 
 */
public class Dog {

     private int dogId;    //宠物编号
     private String dogName;   //宠物名
     private int dogPrice;    //宠物价格
     private String dogPicture;  //宠物图片

    public int getDogId() {
        return dogId;
    }

    public void setDogId(int dogId) {
        this.dogId = dogId;
    }

    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public int getDogPrice() {
        return dogPrice;
    }

    public void setDogPrice(int dogPrice) {
        this.dogPrice = dogPrice;
    }

    public String getDogPicture() {
        return dogPicture;
    }

    public void setDogPicture(String dogPicture) {
        this.dogPicture = dogPicture;
    }

}
