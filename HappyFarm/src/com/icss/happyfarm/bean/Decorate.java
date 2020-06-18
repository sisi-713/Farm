/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.icss.happyfarm.bean;

import javax.swing.JLabel;

/**
 *装饰类
 * @GF4
 */
public class Decorate extends JLabel implements Products{

    private String decorateId;    //装饰编号    ！！
    private String decorateName;   //装饰名称
    private int decoratePrice;     //装饰价格
    private String decoratePicture;   //装饰图片
    private int isUsed;   //用0表示未用，1表示已用  ！！
    private int decorateType;   //用0背景，1房子，2表示狗窝，3表示栅栏，4表示告示牌  ！！

    public Decorate(String decorateId, String decoratePicture,int decoratePrice) {
        this.decorateId = decorateId;
        this.decoratePicture = decoratePicture;
        this.decoratePrice = decoratePrice;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Decorate other = (Decorate) obj;
        if ((this.decorateId == null) ? (other.decorateId != null) : !this.decorateId.equals(other.decorateId)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (this.decorateId != null ? this.decorateId.hashCode() : 0);
        return hash;
    }
   
    public String getDecorateId() {
        return decorateId;
    }

    public void setDecorateId(String decorateId) {
        this.decorateId = decorateId;
    }

    public String getDecorateName() {
        return decorateName;
    }

    public void setDecorateName(String decorateName) {
        this.decorateName = decorateName;
    }

    public String getDecoratePicture() {
        return decoratePicture;
    }

    public void setDecoratePicture(String decoratePicture) {
        this.decoratePicture = decoratePicture;
    }

    public int getDecoratePrice() {
        return decoratePrice;
    }

    public void setDecoratePrice(int decoratePrice) {
        this.decoratePrice = decoratePrice;
    }

    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }

    public int getDecorateType() {
        return decorateType;
    }

    public void setDecorateType(int decorateType) {
        this.decorateType = decorateType;
    }


}
