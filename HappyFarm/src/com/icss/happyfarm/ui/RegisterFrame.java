/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * RegisterFrame.java
 *
 * 
 */
package com.icss.happyfarm.ui;

import com.icss.happyfarm.util.*;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Zqun
 */
public class RegisterFrame extends javax.swing.JFrame {    
    private int imagNum;//头像图片号码 
    private final int imagCount = 9;//图片数量

    private String userName;
    private String passWord;
    private String imagPicture;
    private String passWordTemp;//判断输入时临时存储密码值

    /** Creates new form RegisterFrame */
    public RegisterFrame() {
        initComponents();
        this.setResizable(false);
        Toolkit tk=Toolkit.getDefaultToolkit();
        Image image=tk.createImage(getClass().getResource("/com/icss/happyfarm/imag/bg/biaoti.jpg"));
        this.setIconImage(image);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tfUserName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPasswordField1 = new javax.swing.JPasswordField();
        jPasswordField2 = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("注册");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("微软雅黑", 1, 14));
        jLabel1.setForeground(new java.awt.Color(0, 102, 51));
        jLabel1.setText("开心农场用户注册");

        jLabel2.setFont(new java.awt.Font("微软雅黑", 0, 12));
        jLabel2.setText("用户名：");

        tfUserName.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        tfUserName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfUserNameActionPerformed(evt);
            }
        });
        tfUserName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfUserNameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfUserNameFocusLost(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("微软雅黑", 0, 12));
        jLabel3.setText("密码：");

        jLabel4.setFont(new java.awt.Font("微软雅黑", 0, 12));
        jLabel4.setText("重复密码：");

        jLabel5.setFont(new java.awt.Font("微软雅黑", 0, 12));
        jLabel5.setText("用户头像：");

        jButton1.setText("注册");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("取消");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(0, 153, 0));

        jPasswordField1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPasswordField1.setEchoChar('\u25cf');
        jPasswordField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordField1ActionPerformed(evt);
            }
        });
        jPasswordField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPasswordField1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPasswordField1FocusLost(evt);
            }
        });

        jPasswordField2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPasswordField2.setEchoChar('\u25cf');
        jPasswordField2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPasswordField2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPasswordField2FocusLost(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/photo/0.png"))); // NOI18N

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/down.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/up.jpg"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel7.setForeground(new java.awt.Color(0, 102, 51));
        jLabel7.setText("用户名为最长20位字符，可以为汉字。");

        jLabel8.setForeground(new java.awt.Color(0, 102, 51));
        jLabel8.setText("密码可以是任意英文、数字，最长20位。");

        jButton3.setFont(new java.awt.Font("微软雅黑", 0, 12));
        jButton3.setText("返回");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(152, 152, 152)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel4))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(54, 54, 54)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jPasswordField1, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                                    .addComponent(jPasswordField2)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jButton4, 0, 0, Short.MAX_VALUE)
                                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, Short.MAX_VALUE))))
                                .addGap(63, 63, 63)))))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jButton1)
                .addGap(67, 67, 67)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(73, 73, 73))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel5))
                .addGap(67, 67, 67)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-422)/2, (screenSize.height-582)/2, 422, 582);
    }// </editor-fold>//GEN-END:initComponents

    private void tfUserNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfUserNameActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_tfUserNameActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        if (imagNum == imagCount) {
            imagNum = 0;
        
        } else {
            imagNum++;
        }

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/photo/" + imagNum + ".png")));
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if (imagNum == 0) {
            imagNum = imagCount;
        
        } else {
            imagNum--;
        }

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/photo/" + imagNum + ".png")));
    }//GEN-LAST:event_jButton4ActionPerformed

    private void tfUserNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfUserNameFocusGained
        // TODO add your handling code here:
        tfUserName.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 153, 255), 2, true));
    }//GEN-LAST:event_tfUserNameFocusGained

    private void tfUserNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfUserNameFocusLost
        // TODO add your handling code here:
        tfUserName.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        String userNameTemp = tfUserName.getText().trim();
        if (userNameTemp.length() > 20) {//用户名
            jLabel7.setText("您输入的用户名长度超过20位！");
            jLabel7.setForeground(new java.awt.Color(255, 0, 0));
            jButton1.setEnabled(false);//注册按钮设置为不可用
        
        } else if (userNameTemp.length() == 0) {
            jLabel7.setText("请输入用户名！");
            jLabel7.setForeground(new java.awt.Color(255, 0, 0));
            jButton1.setEnabled(false);//注册按钮设置为不可用
        
        } else {
            jLabel7.setForeground(new java.awt.Color(0, 102, 51));
            jLabel7.setText("用户名为最长20位字符，可以为汉字。");
            jButton1.setEnabled(true);

        }
    }//GEN-LAST:event_tfUserNameFocusLost

    private void jPasswordField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordField1ActionPerformed

    private void jPasswordField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPasswordField1FocusGained
        // TODO add your handling code here:
        jPasswordField1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 153, 255), 2, true));
    }//GEN-LAST:event_jPasswordField1FocusGained

    private void jPasswordField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPasswordField1FocusLost
        // TODO add your handling code here:
        jPasswordField1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        passWordTemp = String.valueOf(jPasswordField1.getPassword()).trim();
        if (passWordTemp.length() > 20) {
            jLabel8.setText("您输入的密码长度超过20位！");
            jLabel8.setForeground(new java.awt.Color(255, 0, 0));
            jButton1.setEnabled(false);//注册按钮设置为不可用
        
        } else if (passWordTemp.length() == 0) {
            jLabel8.setText("请输入密码！");
            jLabel8.setForeground(new java.awt.Color(255, 0, 0));
            jButton1.setEnabled(false);//注册按钮设置为不可用
        
        } else {
            jLabel8.setForeground(new java.awt.Color(0, 102, 51));
            jLabel8.setText("密码可以是任意英文、数字，最长20位。");
            jButton1.setEnabled(true);

        }

    }//GEN-LAST:event_jPasswordField1FocusLost

    private void jPasswordField2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPasswordField2FocusGained
        // TODO add your handling code here:
        jPasswordField2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 153, 255), 2, true));
    }//GEN-LAST:event_jPasswordField2FocusGained

    private void jPasswordField2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPasswordField2FocusLost
        // TODO add your handling code here:
        jPasswordField2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        String passWordTemp2 = String.valueOf(jPasswordField2.getPassword()).trim();
        if (passWordTemp2.length() > 20) {
            jLabel8.setText("您输入的密码长度超过20位！");
            jLabel8.setForeground(new java.awt.Color(255, 0, 0));
            jButton1.setEnabled(false);//注册按钮设置为不可用
            
        } else if (passWordTemp2.length() == 0) {
            jLabel8.setText("请输入密码！");
            jLabel8.setForeground(new java.awt.Color(255, 0, 0));
            jButton1.setEnabled(false);//注册按钮设置为不可用
       
        } else if (!passWordTemp.equals(passWordTemp2)) {
            jLabel8.setText("您2次输入的密码不一致，请重新输入！");
            jLabel8.setForeground(new java.awt.Color(255, 0, 0));
            jButton1.setEnabled(false);//注册按钮设置为不可用
       
        } else {
            jLabel8.setForeground(new java.awt.Color(0, 102, 51));
            jLabel8.setText("密码可以是任意英文、数字，最长20位。");
            jButton1.setEnabled(true);
        }
    }//GEN-LAST:event_jPasswordField2FocusLost

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        userName = tfUserName.getText().trim();
        passWord = String.valueOf(jPasswordField2.getPassword()).trim();
        imagPicture = "/com/icss/happyfarm/imag/photo/" + imagNum + ".png";//图片相对路径

        DbUtil util = new DbUtil();
        ResultSet rs = util.execute("select * from dbo.userl where userName='" + userName + "'");

        try {
            //用户名不存在，添加进数据库
            if (!rs.next()) {
                
                util.getStmt().execute("insert into dbo.userl values('" + userName
                        + "','" + passWord + "'," + "default,default,default,'" + imagPicture + "')");

                        ResultSet userInfo=util.execute("select * from dbo.userl where userName='" + userName + "'");
                        int userId=0;
                        while(userInfo.next()){
                            userId=Integer.parseInt(userInfo.getString("userId"));
                        }
                        //默认初始化6块空土地
                        for(int i=0;i<6;i++){
                            util.getStmt().execute("insert into land(userId,landNum) values("+userId+","+i+")");
                        }

                        ResultSet notice=util.execute("select count(*) from t_decorate where decorateType=4");
                        notice.next();
                        int num = notice.getInt(1);
                         System.out.println(num);
                        //初始化告示牌
                        for(int j=3;j<=num+2;j++) {
                            util.getStmt().execute("insert into t_goods(userId,decorateId) values("+userId+",'D"+j+"')");
                        }
                        //初始化装饰
                        util.getStmt().execute("insert into t_goods(userId,decorateId,decorateIsUsed) values("+userId+",'B2',1)");
                        util.getStmt().execute("insert into t_goods(userId,decorateId,decorateIsUsed) values("+userId+",'F1',1)");
                        util.getStmt().execute("insert into t_goods(userId,decorateId,decorateIsUsed) values("+userId+",'G1',1)");
                        util.getStmt().execute("insert into t_goods(userId,decorateId,decorateIsUsed) values("+userId+",'Z1',1)");

            //用户名已存在，提示错误信息
            } else {
                JOptionPane.showMessageDialog(rootPane, "您的用户名已存在，请重新输入！",
                        "错误信息", JOptionPane.ERROR_MESSAGE);
                tfUserName.setText("");
                jButton1.setEnabled(false);//注册按钮设置为不可用
                return;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegisterFrame.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("添加SQL语句错误！");
        }

        try {
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(RegisterFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        util.close();

        JOptionPane.showMessageDialog(rootPane, "注册成功，点击确定返回登录界面。",
                "消息", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
        new LoginFrame().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new LoginFrame().setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new RegisterFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField tfUserName;
    // End of variables declaration//GEN-END:variables
}
