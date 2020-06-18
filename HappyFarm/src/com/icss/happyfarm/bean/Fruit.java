/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icss.happyfarm.bean;

import javax.swing.JLabel;

/**
 *果实类
 * @author Administrator
 */
public class Fruit extends JLabel {

    private int fruitId;     //果实编号
    private String fruitName;     //果实名称
    private int experience;    //可增长的经验值
    private int fruitPrice;    //果实单价
    private int quantity;    //数量 !!   11
    private int level;     //等级
    private String lock;   //果实是否锁定  ！！
    private String fruitPicture;    //果实图片

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Fruit other = (Fruit) obj;
        if (this.fruitId != other.fruitId) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + this.fruitId;
        return hash;
    }

    public int getFruitId() {
        return fruitId;
    }

    public void setFruitId(int fruitId) {
        this.fruitId = fruitId;
    }

    public int getFruitPrice() {
        return fruitPrice;
    }

    public void setFruitPrice(int fruitPrice) {
        this.fruitPrice = fruitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getFruitName() {
        return fruitName;
    }

    public void setFruitName(String fruitName) {
        this.fruitName = fruitName;
    }

    public String getLock() {
        return lock;
    }

    public void setLock(String lock) {
        this.lock = lock;
    }

    public String getFruitPicture() {
        return fruitPicture;
    }

    public void setFruitPicture(String fruitPicture) {
        this.fruitPicture = fruitPicture;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
