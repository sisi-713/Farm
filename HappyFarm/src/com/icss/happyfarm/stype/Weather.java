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
 * 
 */
public class Weather extends Thread{

    private JLabel lblWeather;
    private static int weather;
    private JLabel lblBg;
    private Land[] lands;

    public Weather(JLabel label,Land[] lands){
        lblWeather=label;
        this.lands=lands;
    }

    public static int getWeather(){
        return weather;
    }


    @Override
    public void run(){
        while(true){
            Random r=new Random();
            weather=r.nextInt(100);

            if(weather > 30){   //大于30，晴天
                lblWeather.setIcon(new ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/sun.png")));
                lblWeather.setToolTipText("晴天,有可能长虫、长草或干旱。");


            } else {      // 小于30，雨天
                lblWeather.setIcon(new ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/rain.png")));
                lblWeather.setToolTipText("雨天,不用浇水、不会干旱，只会长草长虫。");

                for (int i = 0; i < 18; i++) {
                    if (lands[i] != null && lands[i].isDry()) {     //当前土地上种植了作物
                        lands[i].nomalLand();
                    }
                }
            }

            try {
                //一小时随机一次
                Thread.sleep(1000*10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Weather.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
