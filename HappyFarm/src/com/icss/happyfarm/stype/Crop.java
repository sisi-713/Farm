/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.icss.happyfarm.stype;

import com.icss.happyfarm.util.UpdateUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Crop extends Thread{
    private User user;
    private String cropName=""; //作物名称
    private int cropId=0;       //作物ID
    private int season=1;    //作物有几季
    private int currentSeason=1;    //作物当前是第几季
    private int cropGrowTime=2; //作物成熟需要的时间,单位秒
    private int cropExp=5;  //果实收获时候获得的经验

    private int cropGrade=4;    //作物成熟阶段   
    private int expectedOutput=25;    //额定产量
    private int factOutput=0;       //实际产量
    private int health=100;         //健康度，影响最终产量
    
    private String cropPicture; //图片地址
    private String cropUrl; //每个阶段显示的图片地址

    private int growLand=0;    //被种在哪块地上
    private static int landNum=-1;    //当前鼠标指针所在土地编号

    private int currentGrade=1; //作物当前阶段

    private int perTime=0; //每一阶段时间
    private int leftTime = 0; //到下一阶段剩余时间

    private Land[] lands;

    private Glass glass;
    private Worm worm;

    private static String deadImageUrl="/com/icss/happyfarm/imag/crop/sere.png";
    private static String
            seedImageUrl="/com/icss/happyfarm/imag/crop/plant_seeds.png";

    private JLabel lblCrop;
    private JLabel lblInfo;
    private JLabel lblHealth;
    
    private int uproot=0;   //提前铲除作物的标志位，1为中断当前线程，铲除作物

    public static void setLandNum(int landNum) {
        Crop.landNum = landNum;
    }

    public int getCropId() {
        return cropId;
    }

    public void setCurrentSeason(int currentSeason) {
        this.currentSeason = currentSeason;
    }


    public void setLeftTime(int leftTime) {
        this.leftTime = leftTime;
    }

    public int getCropGrowTime() {
        return cropGrowTime;
    }

    public int getPerTime() {  
        return perTime;
    }



    public int getCurrentGrade() {
        return currentGrade;
    }

    public int getCropGrade() {
        return cropGrade;
    }

    public int getHealths() {
        return health;
    }

    public int getExpectedOutput() {
        return expectedOutput;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCurrentGrade(int currentGrade) {
        this.currentGrade = currentGrade;
    }

    public String getCropPicture() {
        return cropPicture;
    }

    public int getCropExp(){
        return cropExp;
    }

    /**
     * 改变作物标签的图片
     */
    public void changePicture() {
        if(currentGrade==1){
            lblCrop.setIcon(new ImageIcon(getClass().getResource(seedImageUrl)));
        }else{
            cropUrl = cropPicture + cropName + (currentGrade-1) + ".png";
            lblCrop.setIcon(new ImageIcon(getClass().getResource(cropUrl)));

        }
    }

    public void changePicture(String url){
        lblCrop.setIcon(new ImageIcon(getClass().getResource(url)));
    }

    public String getCropUrl() {
        cropUrl=cropPicture + cropName+currentGrade + ".png";
        return cropUrl;
    }

    /**
     * 实际产量等于预计产量*健康度/100
     * @return  实际产量
     */
    public int getFactOutput() {
        factOutput=this.expectedOutput*this.health/100;
        return factOutput;
    }

    /**
     * 为作物健康度赋值，用于程序初始化时
     * @param health    作物当前健康度
     */
    public void setHealth(int health){
        this.health=health;
    }

    /**
     * 由于干旱，长草，长虫等原因减少健康度
     * @param health    应减少的健康度大小
     */
    public void minusHealth(int health){
        if(this.health>60){
            this.health-=health;
            UpdateUtil.execute("update land set cropHealth="+this.health+" where userId="
                    +user.getUserId()+" and landNum="+growLand);
        }
    }

    /**
     * 对作物施肥
     * @param time 肥料效果，减去的时间
     */
    public void ferted(int time){
        if(leftTime>time){
            leftTime-=time;
        }else{
//            int temp=time-leftTime;
            leftTime=0;
            //currentGrade++;
            //changePicture();
//            if(!this.isMature()){
//                leftTime=perTime-temp;
//            }else{
//                leftTime=0;
//            }
        }
    }


    /**
     * 判断当前作物是否成熟
     * @return 成熟返回true
     */
    public boolean isMature(){
        return currentGrade>cropGrade;
    }

    /**
     * 作物枯萎，生长阶段变为-1
     */
    public void die(){
        worm.reset(growLand);
        glass.reset(growLand);
        lands[growLand].reset();

        health=0;
        UpdateUtil.execute("update land set cropHealth=0  where userId="+
                user.getUserId()+" and landNum="+growLand);
        changePicture(deadImageUrl);
    }

    /**
     * 当作物在生长状态时，被提前铲除。
     */
    public void clear(){
        uproot=1;
    }

    /**
     * 判断作物是否枯萎
     * @return 枯萎返回true
     */
    public boolean isDead(){
        return health==0;
    }

    public Crop(User user,ResultSet cropInfo,JLabel lblCrop,JLabel lblInfo,
            int landNum,JLabel lblHealth,Land[] lands,Glass glass,Worm worm) {
        try {
            this.user=user;
            cropName = cropInfo.getString("cropName");
            this.cropId=Integer.valueOf(cropInfo.getString("cropId"));
            this.cropGrowTime = Integer.valueOf(cropInfo.getString("cropGrowTime"));
            this.cropExp = cropInfo.getInt("experience");
            this.cropGrade = Integer.valueOf(cropInfo.getString("cropGrade"));
            cropPicture=cropInfo.getString("cropPicture").trim();
            this.season=cropInfo.getInt("cropSeason");
            perTime=cropGrowTime/cropGrade;
            leftTime=perTime;
            this.expectedOutput=cropInfo.getInt("expectQuantity");

            this.lands=lands;
            this.glass=glass;
            this.worm=worm;

            this.lblInfo = lblInfo;
            this.lblHealth = lblHealth;
            this.growLand = landNum;
            this.lblCrop = lblCrop;
            this.lblCrop.setIcon(new ImageIcon(getClass().getResource(seedImageUrl)));
        } catch (SQLException ex) {
            System.out.println("crop sql error");
            Logger.getLogger(Crop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 /**
     * 返回hh:mm:ss的时间格式字段
     * @param sec 时间长度，单位秒
     * @return
     */
    public String timeFormat(int sec) {
        int hour = sec / 3600;
        int min = sec % 3600 / 60;
        int second = sec % 3600 % 60;

        String strHour = "" + hour;
        String strMin = "" + min;
        String strSec = "" + second;

        if (hour < 10) {
            strHour = "0" + hour;
        }

        if (min < 10) {
            strMin = "0" + min;
        }

        if (second < 10) {
            strSec = "0" + second;
        }

        return new String(strHour + ":" + strMin + ":" + strSec);
    }

    public boolean isLastSeason(){
        return currentSeason==season;
    }

    public void stopGrow(){
        synchronized (this){
            //最后成长阶段并且不是最后一季
            if(!isLastSeason() && currentGrade==cropGrade){
                try {
                    currentGrade++; //成长阶段+1，表示成熟

                    changePicture();

                    mature();

                    this.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Crop.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }


    //作物成熟，清空杂草，虫子，还原土地状态
    public void mature(){
        worm.reset(growLand);
        glass.reset(growLand);
        lands[growLand].reset();
    }

//    多季作物采摘后恢复生长
    public synchronized void ctnGrow() {
        //重置健康度为100
        this.health=100;
        
        //重置生长阶段为大叶子
        currentGrade = cropGrade - 2;
        changePicture(this.getCropUrl());

        currentSeason++;
        
        UpdateUtil.execute("update land set cropHealth=100,growDate=getDate()," +
                "currentSeason="+currentSeason+" where userId="
                +user.getUserId()+" and landNum="+growLand);

        this.notify();  //唤醒线程
        
    }

    @Override
    public void run() {
        for (; currentGrade <= cropGrade; currentGrade++) {
            stopGrow();

            for (; leftTime > -1; leftTime--) {
                if(uproot==1){  //已经被铲除，线程死亡
                    return;
                }

                //如果鼠标在当前作物所种的土地上
                if (landNum == growLand) {
                    lblInfo.setText(cropName + " " + timeFormat(leftTime));
                    lblHealth.setText("健康度：" + health);
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Crop.class.getName()).log(Level.SEVERE, null, ex);
                }
               
            }
            
            lblCrop.setIcon(new ImageIcon(getClass().getResource(getCropUrl())));
            leftTime=perTime;

            //换阶段时，如果出于施肥状态，则重置
            if(lands[growLand].isFert()){
                lands[growLand].nomalLand();
            }
            
        }
        //没有被铲除
        if (uproot != 1) {
            changePicture();
            mature();   //作物成熟
        }
    }
}
