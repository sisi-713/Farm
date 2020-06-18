/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.icss.happyfarm.bean;

/**
 *
 * @author Administrator
 */
public class WareHouse {

    private int WareHouseId;     //仓库编号
    private int fruitQuantity;   //果实数量
    private Fruit fruit;     //仓库拥有果实表的引用

    public int getWareHouseId() {
        return WareHouseId;
    }

    public void setWareHouseId(int WareHouseId) {
        this.WareHouseId = WareHouseId;
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

}
