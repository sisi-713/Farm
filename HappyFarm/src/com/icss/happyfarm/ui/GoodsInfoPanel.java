/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.icss.happyfarm.ui;

import java.awt.*;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 作物状态显示窗体
 * @author
 */
public class GoodsInfoPanel extends JPanel{
    private JLabel lblGoodsName;
    private JLabel lblGoodsPrice;
    private JLabel lblBackground;
    private Color color;

    public GoodsInfoPanel(){
        setOpaque(false);
        setLayout(null);
        setSize(130, 80);
        setVisible(false);
        initComponents();
    }

    public void initComponents(){
        lblGoodsName = new JLabel();
        lblGoodsPrice = new JLabel();
        lblBackground = new JLabel();
        color = new Color(0, 0, 81);

        lblGoodsName.setForeground(color);
        //lblToolName.setText("");
        this.add(lblGoodsName);
        lblGoodsName.setBounds(30, 15, 80, 20);
        lblGoodsName.setFont(new java.awt.Font("新宋体", 0, 12));
        lblGoodsName.setForeground(new java.awt.Color(155, 100, 155));

        lblGoodsPrice.setForeground(color);
        //lblToolPrice.setText("");
        this.add(lblGoodsPrice);
        lblGoodsPrice.setBounds(30, 30, 80, 20);
        lblGoodsPrice.setFont(new java.awt.Font("新宋体", 0, 12));
        lblGoodsPrice.setForeground(new java.awt.Color(155, 100, 155));

        lblBackground.setIcon(new ImageIcon(getClass().getResource("/com/icss/happyFarm/images/seedPicture/kuangkuang.png")));
        lblBackground.setBounds(20, 0, 110, 70);
        this.add(lblBackground);

        Toolkit tk=Toolkit.getDefaultToolkit();
        Image hand_img=tk.createImage(getClass().getResource("/com/icss/happyfarm/imag/bg/mouse.png"));
        Cursor hand=tk.createCustomCursor(hand_img,new Point(0,0),"MY_CURSOR");
        setCursor(hand);
    }

    public void setLblGoodsNameText(String str){
            lblGoodsName.setText(str);
        
    }
 
    public void setLblGoodsPrice(String str){
            lblGoodsPrice.setText(str);
    }
    public void setLblBack(Icon icon) {
           lblBackground.setIcon(icon);
    }
}








