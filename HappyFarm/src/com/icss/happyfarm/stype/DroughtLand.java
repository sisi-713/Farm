/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.icss.happyfarm.stype;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 * 土地干旱类
 *
 */
public class DroughtLand extends Thread{
    private JLabel[] landsArray;    //
    private Land[] lands;
    public static final int DELAY=10;

    public DroughtLand(JLabel[] landsArray, Land[] lands) {
        this.landsArray = landsArray;
        this.lands = lands;
        //this.landsStatue=landsStatue;
    }


    @Override
    public void run() {
        while (true) {
            if (Weather.getWeather() >30) {    //当前天气状况，大于50表示晴天
                Random r = new Random();

                for (int i = 0; i < 18; i++) {
                    if (lands[i]!=null && lands[i].isGrown()) {     //当前土地上种植了作物
                        //作物还没成熟，还没枯萎
                        if (!lands[i].cropIsMature() && !lands[i].cropIsDead()
                                && r.nextInt(10) > 7) {
                            if(lands[i].isDry()){
                                lands[i].getCrop().minusHealth(1);
                            }else if(lands[i].isFert()){
                                //
                            }else{
                                lands[i].dryLand();
                            }
                        }
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
