/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.icss.happyfarm.ui;

import java.awt.Color;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 作物状态显示窗体
 * 
 */
public class ToolInfoPanel extends JPanel{
    private JLabel lblToolName;
    private JLabel lblToolPrice;
    private JLabel lblBackground;
    private Color color;

    public ToolInfoPanel(){
        setOpaque(false);
        setLayout(null);
        setSize(120, 60);
        setVisible(false);
        initComponents();
    }

    public void initComponents(){
        lblToolName = new JLabel();
        lblToolPrice = new JLabel();
        lblBackground = new JLabel();
        color = new Color(0, 0, 81);

        lblToolName.setForeground(color);
        //lblToolName.setText("");
        this.add(lblToolName);
        lblToolName.setBounds(20, 0, 80, 20);

        lblToolPrice.setForeground(color);
        //lblToolPrice.setText("");
        this.add(lblToolPrice);
        lblToolPrice.setBounds(20, 30, 80, 25);

        lblBackground.setIcon(new ImageIcon(getClass().getResource("/com/icss/happyFarm/images/toolsPicture/toolPanelBack.png")));
        lblBackground.setBounds(20, 0, 80, 60);
        this.add(lblBackground);
    }

    public void setLblToolNameText(String str){
            lblToolName.setText(str);
        
    }
 
    public void setLblToolPrice(String str){
            lblToolPrice.setText(str);     
    }
    public void setLblBack(Icon icon) {
           lblBackground.setIcon(icon);
    }
}








