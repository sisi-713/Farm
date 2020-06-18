/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icss.happyfarm.stype;

import com.icss.happyfarm.util.UpdateUtil;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * 土地类
 * 
 */
public class Land {

    private int userId;
    //private int landId; //土地ID
    private int landNum;    //土地编号，0-17
    private int landStatus = 0;    //土地状态，0正常，1干旱，2施肥
    private int landGlass = 0;    //长了几棵草，最多3颗
    private int landWorm = 0;     //长了几只虫，最多3只
    private Crop crop = null;         //种植在这块土地上的作物
    private JLabel lblLand = null;
    private static final String nomalImageUrl = "/com/icss/happyfarm/imag/bg/field.png";
    private static final String dryImageUrl = "/com/icss/happyfarm/imag/bg/fieldl.png";
    private static final String fertImageUrl = "/com/icss/happyfarm/imag/bg/fieldB.png";

    public Land(JLabel lblLand, int userId, int landNum) {
        this.lblLand = lblLand;
        lblLand.setIcon(new ImageIcon(getClass().getResource(nomalImageUrl)));
        this.userId = userId;
        this.landNum = landNum;

    }

    /**
     * 登录时候初始化土地
     * @param lblLand   土地标签
     * @param userId    用户ID
     * @param landNum   土地编号
     * @return  返回初始化的土地对象
     */
    public static Land creatLand(JLabel lblLand, int userId, int landNum) {
        Land land = new Land(lblLand, userId, landNum);
        UpdateUtil.execute("insert into land(userId,landNum) values(" + userId + "," + landNum + ")");
        return land;
    }

    //土地干旱
    public void dryLand() {
        lblLand.setIcon(new ImageIcon(getClass().getResource(dryImageUrl)));
        landStatus = 1;
        UpdateUtil.execute("update land set landStatue=1 where userId=" +
                userId + " and landNum=" + landNum);
    }

    public boolean isDry() {
        return landStatus == 1;
    }

    //土地正常
    public void nomalLand() {
        lblLand.setIcon(new ImageIcon(getClass().getResource(nomalImageUrl)));
        landStatus = 0;
        UpdateUtil.execute("update land set landStatue=0 where userId=" +
                userId + " and landNum=" + landNum);
    }

    //对土地施肥
    public void fertLand(int time) {
        if (!isFert()) {
            lblLand.setIcon(new ImageIcon(getClass().getResource(fertImageUrl)));
            crop.ferted(time);
            landStatus = 2;
            UpdateUtil.execute("update land set landStatue=2 where userId=" +
                    userId + " and landNum=" + landNum);
        }

    }

    /**
     * 判断土地是否被施肥
     * @return 施过肥返回true,否则返回false
     */
    public boolean isFert() {
        return landStatus == 2;
    }

    //在土地上种植作物
    public void growCrop(Crop crop) {
        this.crop = crop;
        String growSql = "update land set cropId=" + crop.getCropId() +
                ",cropHealth=100,growDate=getDate(),currentSeason=1 " +
                "where userId=" + userId + " and landNum=" + landNum;
        UpdateUtil.execute(growSql);
    }

    //初始化程序时查询数据库后加载土地上已种植的作物
    public void createCrop(Crop crop) {
        this.crop = crop;
    }

    public Crop getCrop() {
        return crop;
    }

    /**
     * 收获作物
     */
    public void harvest() {
        if (crop.isLastSeason()) {
            crop.die();

        } else {
            crop.ctnGrow();
        }
    }

    /**
     * 铲除土地上种植的作物
     */
    public void clear() {
        reset();
        UpdateUtil.execute("update land set cropId=0,growDate=0 where userId=" +
                userId + " and landNum=" + landNum);
        crop.clear();
        crop = null;
    }

    /**
     * 作物成熟时重置
     */
    public void reset() {
        if (hasWorm()) {
            landWorm = 0;
            UpdateUtil.execute("update land set landWorm=0 where userId=" +
                     userId + " and landNum=" + landNum);
        }
        if (hasGlass()) {
            landGlass = 0;
            UpdateUtil.execute("update land set landGlass=0 where userId=" +
                    userId + " and landNum=" + landNum);
        }
        
        nomalLand();
        
    }

    /**
     *  判断土地上是否长草了
     * @return 长了草返回true,否则返回false
     */
    public boolean hasGlass() {
        return landGlass != 0;

    }

    /**
     * 对长草数实行加减,最多长3颗草
     * @param done true则草的数量+1，flase 则-1
     */
    public void setLandGlass(boolean done) {
        if (done && landGlass < 3) {
            landGlass += 1;
        }

        if (!done && landGlass > 0) {
            landGlass -= 1;
        }

        UpdateUtil.execute("update land set landGlass=" + landGlass
                + " where userId=" + userId + " and landNum=" + landNum);
    }

    public void setLandGlass(int i) {
        landGlass = i;
    }

    public boolean hasWorm() {
        return landWorm != 0;
    }

    /**
     * 对长虫数实行加减,最多长3只虫
     * @param done true则虫的数量+1，flase 则-1
     */
    public void setLandWorm(boolean done) {
        if (done && landWorm < 3) {
            landWorm += 1;
        }

        if (!done && landWorm > 0) {
            landWorm -= 1;
        }
        UpdateUtil.execute("update land set landWorm=" + landWorm +
                " where userId=" + userId + " and landNum=" + landNum);
    }

    public void setLandWorm(int i) {
        landWorm = i;
    }

    public int getLandGlass() {
        return landGlass;
    }

    public int getLandWorm() {
        return landWorm;
    }

    public boolean cropIsDead() {
        return crop.isDead();
    }

    public boolean cropIsMature() {
        return crop.isMature();
    }

    /**
     * 是否种植了作物
     * @return  种植了作物返回true
     */
    public boolean isGrown() {
        return crop != null;
    }
}
