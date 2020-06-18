/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icss.happyfarm.bean;

import com.icss.happyfarm.stype.User;


/**
 *
 * @author Administrator
 */
public class House {

    //没有实际意义的类
    private int wareHouseId;     //仓库编号
    private int fruitQuantity;   //果实数量
    private Fruit fruit;     //仓库拥有果实表的引用
    private User user;   //

    public int getWareHouseId() {
        return wareHouseId;
    }

    public void setWareHouseId(int wareHouseId) {
        this.wareHouseId = wareHouseId;
    }

    public int getFruitQuantity() {
        return fruitQuantity;
    }

    public void setFruitQuantity(int fruitQuantity) {
        this.fruitQuantity = fruitQuantity;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
