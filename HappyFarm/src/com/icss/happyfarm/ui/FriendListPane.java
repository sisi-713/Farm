/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.icss.happyfarm.ui;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
/**
 *
 * @author Administrator
 */
public class FriendListPane extends JPanel implements MouseListener{
    private JLabel[] lblFriend = new JLabel[5];
    private JPopupMenu popMenu;
    private int y = 65;
    private String url="/com/icss/happyfarm/imag/bg/";

    public FriendListPane(int y) {
        this.y = y;
        this.setLayout(null);
        this.setLocation(30, y);
        this.setSize(155,40);
        for (int i = 0; i < lblFriend.length; i++) {
            lblFriend[i] = new JLabel();
            lblFriend[i].addMouseListener(this);
        }

       lblFriend[0].setBounds(0, 4, 140, 45);
       lblFriend[1].setBounds(8, 8, 30, 30);
       lblFriend[2].setBounds(35, 10, 30, 30);
       lblFriend[3].setBounds(75, 4, 76, 30);
       lblFriend[4].setBounds(-6, 4, 160, 45);
       lblFriend[4].setComponentPopupMenu(popMenu);
                lblFriend[4].addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popMenu.show(null, e.getPoint().x, e.getPoint().y);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popMenu.show(null, e.getPoint().x, e.getPoint().y);
                }
            }
        });
       for (int i = 0; i < lblFriend.length; i++) {
            this.add(lblFriend[i]);
        }

    }
     public void createPopupMenu(){
    popMenu=new JPopupMenu();
    popMenu.add("撤销");
    popMenu.add("删除好友");
    popMenu.add("刷新列表");
    }

    public JLabel[] getLblFriend() {
        return lblFriend;
    }

    public void setLblFriend(JLabel[] lblFriend) {
        this.lblFriend = lblFriend;
    }



    public void mousePressed(MouseEvent e) {
//        String str=lblFriend[3].getText();
//        System.out.println(str);


    }
    public void mouseClicked(MouseEvent e) {


    }
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {
     if(lblFriend[3].getText()!=null)   {
    lblFriend[4].setIcon(new ImageIcon(getClass().getResource(url+"backMap.png")));
    }}
    public void mouseExited(MouseEvent e) {
    lblFriend[4].setIcon(new ImageIcon(""));
    }
}
