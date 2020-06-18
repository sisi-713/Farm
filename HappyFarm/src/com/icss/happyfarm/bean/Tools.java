/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.icss.happyfarm.bean;

import javax.swing.JLabel;

/**
 *道具类
 * @author Administrator
 */
public class Tools extends JLabel implements Products {

    private String toolsId;     //道具编号
    private String toolsName;     //道具名称
    private int toolsPrice;     //道具价格
    private int toolsType;     //道具类型 0表示化肥，1表示狗，2表示狗粮
    private int totalQuantity;   //道具数量
    private int buyQuantity;    //购买的道具数量
    private String toolsPicture;     //道具图片
    private int toolsQuantity;   //道具购买数量
    private String explain;      //道具说明

    public Tools(String toolsId, String toolsName, int toolsPrice, String toolsPicture) {
        this.toolsId = toolsId;
        this.toolsName = toolsName;
        this.toolsPrice = toolsPrice;
        this.toolsPicture = toolsPicture;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tools other = (Tools) obj;
        if ((this.toolsId == null) ? (other.toolsId != null) : !this.toolsId.equals(other.toolsId)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.toolsId != null ? this.toolsId.hashCode() : 0);
        return hash;
    }
    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getToolsId() {
        return toolsId;
    }

    public void setToolsId(String toolsId) {
        this.toolsId = toolsId;
    }

    public String getToolsPicture() {
        return toolsPicture;
    }

    public void setToolsPicture(String toolsPicture) {
        this.toolsPicture = toolsPicture;
    }

    public int getToolsType() {
        return toolsType;
    }

    public void setToolsType(int toolsType) {
        this.toolsType = toolsType;
    }

    public String getToolsName() {
        return toolsName;
    }

    public void setToolsName(String toolsName) {
        this.toolsName = toolsName;
    }

    public int getToolsPrice() {
        return toolsPrice;
    }

    public void setToolsPrice(int toolsPrice) {
        this.toolsPrice = toolsPrice;
    }

    public int getBuyQuantity() {
        return buyQuantity;
    }

    public void setBuyQuantity(int buyQuantity) {
        this.buyQuantity = buyQuantity;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getToolsQuantity() {
        return toolsQuantity;
    }

    public void setToolsQuantity(int toolsQuantity) {
        this.toolsQuantity = toolsQuantity;
    }


}
