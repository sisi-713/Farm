/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icss.happyfarm.bean;

import javax.swing.JLabel;

/**
 *种子类
 * @author Administrator
 */
public class Seed extends JLabel implements Products {

    private int seedId;          //种子编号  !!
    private String seedName;     //种子名
    private int seedPrice;     //种子单价
    private int fruitPrice;    //果实售价！！
    private int growthCycle;     //种子生长周期
    private int expectQuantity;     //预计产量
    private int expectHarvest;     //预计收入
    private int harvestTime;     //第一次收获时间
    private int nextHarvestTime;    //下一次成熟的时间
    private int level;          //所属等级
    private int experience;    //可增的经验值
    private String seedsPicture;     //种子图片
    private String explain;      //作物说明 ！！
    private int cropSeason;    //作物季数
    private int seedQuantity = 0;    //种子被购买的数量

    public Seed() {
        
    }

    public Seed(int seedId, int seedPrice, String seedsPicture) {
        this.seedId = seedId;
        this.seedPrice = seedPrice;
        this.seedsPicture = seedsPicture;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Seed other = (Seed) obj;
        if (this.seedId != other.seedId) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + this.seedId;
        return hash;
    }

    public int getSeedId() {
        return seedId;
    }

    public void setSeedId(int seedId) {
        this.seedId = seedId;
    }
   

    public int getExpectQuantity() {
        return expectQuantity;
    }

    public void setExpectQuantity(int expectQuantity) {
        this.expectQuantity = expectQuantity;
    }

    public int getGrowthCycle() {
        return growthCycle;
    }

    public void setGrowthCycle(int growthCycle) {
        this.growthCycle = growthCycle;
    }

    public int getHarvestTime() {
        return harvestTime;
    }

    public void setHarvestTime(int harvestTime) {
        this.harvestTime = harvestTime;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getSeedName() {
        return seedName;
    }

    public void setSeedName(String seedName) {
        this.seedName = seedName;
    }

    public int getSeedPrice() {
        return seedPrice;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setSeedPrice(int seedPrice) {
        this.seedPrice = seedPrice;
    }

    public String getSeedsPicture() {
        return seedsPicture;
    }

    public void setSeedsPicture(String seedsPicture) {
        this.seedsPicture = seedsPicture;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public int getFruitPrice() {
        return fruitPrice;
    }

    public void setFruitPrice(int fruitPrice) {
        this.fruitPrice = fruitPrice;
    }

    public int getNextHarvestTime() {
        return nextHarvestTime;
    }

    public void setNextHarvestTime(int nextHarvestTime) {
        this.nextHarvestTime = nextHarvestTime;
    }

    public int getExpectHarvest() {
        return expectHarvest;
    }

    public void setExpectHarvest(int expectHarvest) {
        this.expectHarvest = expectHarvest;
    }

    public int getSeedQuantity() {
        return seedQuantity;
    }

    public void setSeedQuantity(int seedQuantity) {
        this.seedQuantity = seedQuantity;
    }

    public int getCropSeason() {
        return cropSeason;
    }

    public void setCropSeason(int cropSeason) {
        this.cropSeason = cropSeason;
    }
}
