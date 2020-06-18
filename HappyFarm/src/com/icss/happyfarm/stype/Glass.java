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


public class Glass extends Thread{
    private JLabel[] glassArray;
    private Land[] lands;
    private final static int DELAY=10; //草刷新时间间隔，单位秒
    
    private String url="/com/icss/happyfarm/imag/bg/glass";

    public Glass(JLabel[] glassArray,Land[] lands) {
        this.glassArray = glassArray;
        this.lands=lands;
    }

    //除草
    public void weed(int i){
        lands[i].setLandGlass(false);
        if(lands[i].hasGlass()){
            glassArray[i].setIcon(new ImageIcon(getClass().getResource(url+lands[i].getLandGlass()+".png")));
        }else{
             glassArray[i].setIcon(new ImageIcon());
        }
    }


     /**
     * 程序启动时加载土地上长的草
     */
    public void init(int num,int i){
        glassArray[i].setIcon(new ImageIcon(getClass().getResource(url+lands[i].getLandGlass()+".png")));
    }

    /**
     * 作物成熟时重置
     * @param i 土地编号
     */
    public void reset(int i){
        if(lands[i].hasGlass()){
            glassArray[i].setIcon(new ImageIcon());
        }
    }

    @Override
    public void run() {
        while (true) {

            Random r = new Random();

            for (int i = 0; i < 18; i++) {
                if (lands[i]!=null&&lands[i].isGrown()) {     //当前土地上种植了作物
                    //作物还没成熟，还没枯萎
                    if (!lands[i].cropIsMature() && !lands[i].cropIsDead() && r.nextInt(10) > 7) {
                        if(lands[i].hasGlass())lands[i].getCrop().minusHealth(1);
                        lands[i].setLandGlass(true);    //长草
                        glassArray[i].setIcon(new ImageIcon(getClass().getResource(url+lands[i].getLandGlass()+".png")));
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
