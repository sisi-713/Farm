/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * BuyDecorateDialog.java
 *
 * 
 */
package com.icss.happyfarm.ui;

import com.icss.happyfarm.bean.Decorate;
import com.icss.happyfarm.stype.User;
import com.icss.happyfarm.util.DbUtil;
import com.icss.happyfarm.util.ShoppingBag;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JLabel;

/**
 *
 * @author Administrator
 */
public class BuyDecorateDialog extends javax.swing.JDialog {

    static Decorate decorate;     //商店中点击的装饰
    static MainFrame pparent;    //向购买装饰对话框中传主窗口对象
    static ShoppingDialog parent;   //购买装饰对话框的父窗口
    static User user;
    private ShoppingBag shopBag;     //获取工具包ShoppingBag，进行购物袋操作
    AbolishExpectDialog abolish;   //取消预览对话框
    DbUtil util = new DbUtil();
    int userMoney;
    boolean canBuy = false;

    /** Creates new form BuyDecorateDialog */
    public BuyDecorateDialog(ShoppingDialog parent, boolean modal, Decorate decorate, MainFrame pparent, User user) {
        super(parent, modal);
        BuyDecorateDialog.parent = parent;
        BuyDecorateDialog.pparent = pparent;
        BuyDecorateDialog.decorate = decorate;
        BuyDecorateDialog.user = user;

        this.setLocation((parent.getWidth() - this.getWidth()) / 2 + 198, (parent.getHeight() - this.getHeight()) / 2 + 238);
        this.setTitle("购买装饰");
        initComponents();
        setContent();
        check();
        this.setResizable(false);
        this.setVisible(true);
    }

    public void setContent() {
        lblDecoratePicture.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + decorate.getDecorateName() + ".png")));
        lblDecorateName.setText("   " + decorate.getDecorateName());
        lblDecorateName.setFont(new java.awt.Font("宋体", 0, 24)); // NOI18N
        lblDecorateName.setForeground(new java.awt.Color(0, 204, 153));
        lblDecoratePrice.setText("" + decorate.getDecoratePrice());
        lblExperience.setText("0");
        lblEffect.setText("186");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        lblDecoratePicture = new javax.swing.JLabel();
        lblBtnExpect = new javax.swing.JLabel();
        lblDecorateName = new javax.swing.JLabel();
        lblBuy = new javax.swing.JLabel();
        lblNotice = new javax.swing.JLabel();
        lblExit = new javax.swing.JLabel();
        lblDecoratePrice = new javax.swing.JLabel();
        lblEffect = new javax.swing.JLabel();
        lblExperience = new javax.swing.JLabel();
        lblBlack = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblDecoratePicture.setBounds(20, 20, 120, 130);
        jLayeredPane1.add(lblDecoratePicture, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblBtnExpect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/expect.png"))); // NOI18N
        lblBtnExpect.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBtnExpectMouseClicked(evt);
            }
        });
        lblBtnExpect.setBounds(50, 160, 70, 30);
        jLayeredPane1.add(lblBtnExpect, javax.swing.JLayeredPane.DEFAULT_LAYER);
        lblDecorateName.setBounds(180, 10, 170, 40);
        jLayeredPane1.add(lblDecorateName, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblBuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/sureBuyDecorate.png"))); // NOI18N
        lblBuy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBuyMouseClicked(evt);
            }
        });
        lblBuy.setBounds(120, 190, 110, 30);
        jLayeredPane1.add(lblBuy, javax.swing.JLayeredPane.DEFAULT_LAYER);
        lblNotice.setBounds(140, 160, 140, 30);
        jLayeredPane1.add(lblNotice, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/fruit/cancel.png"))); // NOI18N
        lblExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblExitMouseClicked(evt);
            }
        });
        lblExit.setBounds(240, 190, 70, 30);
        jLayeredPane1.add(lblExit, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblDecoratePrice.setFont(new java.awt.Font("新宋体", 0, 12));
        lblDecoratePrice.setBounds(200, 50, 30, 30);
        jLayeredPane1.add(lblDecoratePrice, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblEffect.setFont(new java.awt.Font("新宋体", 0, 12));
        lblEffect.setBounds(210, 80, 30, 20);
        jLayeredPane1.add(lblEffect, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblExperience.setFont(new java.awt.Font("新宋体", 0, 12));
        lblExperience.setBounds(340, 80, 20, 20);
        jLayeredPane1.add(lblExperience, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblBlack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/buyDecorate.png"))); // NOI18N
        lblBlack.setText("jLabel1");
        lblBlack.setBounds(0, 0, 400, 230);
        jLayeredPane1.add(lblBlack, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMouseClicked
        this.dispose();
    }//GEN-LAST:event_lblExitMouseClicked

    private void lblBtnExpectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBtnExpectMouseClicked

        Icon housePath = null;
        if (decorate.getDecorateType() == 0) {
            JLabel back = (JLabel) pparent.getLblMain();    //从主窗口中获取装饰标签
            housePath = back.getIcon();     //保存主界面原有装饰
            back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + decorate.getDecorateName() + "大图.png")));
        } else if (decorate.getDecorateType() == 1) {
            JLabel house = (JLabel) pparent.getLblHouseDecorate();    //从主窗口中获取装饰标签
            housePath = house.getIcon();     //保存主界面原有装饰
            house.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + decorate.getDecorateName() + "大图.png")));
        } else if (decorate.getDecorateType() == 2) {
            JLabel dogHouse = (JLabel) pparent.getLblDogHouse();    //从主窗口中获取装饰标签
            housePath = dogHouse.getIcon();     //保存主界面原有装饰
            dogHouse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + decorate.getDecorateName() + "大图.png")));
        } else {
            JLabel column = (JLabel) pparent.getLblColumn();    //从主窗口中获取栅栏标签
            housePath = column.getIcon();     //保存主界面原有栅栏
            column.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + decorate.getDecorateName() + "大图.png")));
        }

        BuyDecorateDialog.this.setVisible(false);   //点击预览时，本窗口不可见
        parent.setVisible(false);   //点击预览时，父窗口不可见
        abolish = new AbolishExpectDialog(BuyDecorateDialog.this, true, parent, pparent, housePath, decorate);   //创建预览窗口
    }//GEN-LAST:event_lblBtnExpectMouseClicked

    public int getUserMoneyNow() {
        //购买花费
        int pirce = Integer.parseInt(lblDecoratePrice.getText().trim().toString());
        //购买结余
        int uMoney = Integer.parseInt(pparent.getLblMoney().getText());
        return (uMoney - pirce);
    }

    public void check() {
        //获得购买结余
        userMoney = getUserMoneyNow();
        if (userMoney < 0) {
            lblNotice.setText("对不起，你的金币不足！");
            canBuy = false;   //不能购买
            lblBuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/sureBuyDecorate1.png")));
        } else {
            lblNotice.setText("");
            canBuy = true;   //能购买
            lblBuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/sureBuyDecorate.png")));
        }
    }
    //购买装饰方法
    private void lblBuyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBuyMouseClicked
        try {
            check();
            if (canBuy) {
                boolean buyResult = true;
                shopBag = new ShoppingBag(user);    //实例购物袋工具
                boolean res = shopBag.addDecorateToShoppingBag(decorate.getDecorateId(), decorate);   //调用向购物袋中添加装饰的方法
                if (!res) {
                    //如果购物袋中还没有购买该装饰,添加到商品表中
                    String sql = "insert into t_goods (decorateId,decorateIsUsed,userId) " +
                            "values ('" + decorate.getDecorateId() + "',0," + user.getUserId() + ")";
                    buyResult = true;
                    util.execute(sql);
                    //页面显示修改
                    userMoney = getUserMoneyNow();
                    pparent.getLblMoney().setText("" + userMoney);
                    //数据库中修改用户的金币数
                    String sql1 = "update userl set userMoney=" + userMoney + " where userId=" + user.getUserId() + "";
                    util.execute(sql1);
                    util.close();

                } else {
                    buyResult = false;
                }
                //根据是否购买成功生成对话框
                BuyResultDialog bdr = new BuyResultDialog(BuyDecorateDialog.this, true, buyResult);
            } else {
                return;
            }
        } catch (Exception ex) {
            Logger.getLogger(BuyDecorateDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_lblBuyMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                BuyDecorateDialog dialog = new BuyDecorateDialog(parent, true, decorate, pparent, user);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLabel lblBlack;
    private javax.swing.JLabel lblBtnExpect;
    private javax.swing.JLabel lblBuy;
    private javax.swing.JLabel lblDecorateName;
    private javax.swing.JLabel lblDecoratePicture;
    private javax.swing.JLabel lblDecoratePrice;
    private javax.swing.JLabel lblEffect;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblExperience;
    private javax.swing.JLabel lblNotice;
    // End of variables declaration//GEN-END:variables
}