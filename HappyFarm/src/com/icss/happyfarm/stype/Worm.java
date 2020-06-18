/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.icss.happyfarm.stype;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @GF4
 */
public class Worm extends Thread{
    private JLabel[] wormArray;
    private Land[] lands;
    private final static int DELAY=10; //虫刷新时间间隔，单位秒

    private String url="/com/icss/happyfarm/imag/bg/worm";

    public Worm(JLabel[] wormArray, Land[] lands) {
        this.wormArray = wormArray;
        this.lands = lands;
    }

    //除虫
    public void kill(int i){
        lands[i].setLandWorm(false);
        if(lands[i].hasWorm()){
            wormArray[i].setIcon(new ImageIcon(getClass().getResource(url+lands[i].getLandWorm()+".png")));
        }else{
             wormArray[i].setIcon(new ImageIcon());
        }
    }

    /**
     * 程序启动时加载作物上长的虫子
     */
    public void init(int num,int i){
        wormArray[i].setIcon(new ImageIcon(getClass().getResource(url+lands[i].getLandWorm()+".png")));
    }

    /**
     * 作物成熟时重置
     * @param i 土地编号
     */
    public void reset(int i){
        if(lands[i].hasWorm()){
            wormArray[i].setIcon(new ImageIcon());
        }
    }

    @Override
    public void run(){
        while (true) {

            Random r = new Random();

            for (int i = 0; i < 18; i++) {
                if (lands[i]!=null&&lands[i].isGrown()) {     //当前土地上种植了作物
                    //作物还没成熟，还没枯萎
                    if (!lands[i].cropIsMature() && lands[i].getCrop().getCurrentGrade()>2 && r.nextInt(10) > 8) {

                        if(lands[i].hasWorm()){
                            lands[i].getCrop().minusHealth(1);
                        }
                        lands[i].setLandWorm(true);
                        wormArray[i].setIcon(new ImageIcon(getClass().getResource(url+lands[i].getLandWorm()+".png")));
                        //System.out.println(lands[i].getLandGlass());
                    }
                }
            }

            try {
                Thread.sleep(1000*DELAY);
            } catch (InterruptedException ex) {
                Logger.getLogger(DroughtLand.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
