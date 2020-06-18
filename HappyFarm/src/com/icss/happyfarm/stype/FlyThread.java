/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.icss.happyfarm.stype;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *  作物收获时控制果实图片飞天的线程
 * 
 */
public class FlyThread extends Thread{
    private JLabel lblHarve;
    private int length=0;

    public FlyThread(JLabel lblHarve){
        this.lblHarve=lblHarve;
    }


    public void setlength(int i){
        length=i;
    }

    @Override
    public void run(){
        int x=lblHarve.getX();
        int y=lblHarve.getY();

        for(int i=y;i>0;i-=5){
            lblHarve.setLocation(x,i);
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(FlyThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        lblHarve.setIcon(new ImageIcon());
        lblHarve.setLocation(x,y);
    }
}
