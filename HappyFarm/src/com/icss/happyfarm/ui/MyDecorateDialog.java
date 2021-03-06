/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MyDecorateDialog.java
 *
 * 
 */
package com.icss.happyfarm.ui;

import com.icss.happyfarm.bean.Decorate;
import com.icss.happyfarm.stype.User;
import com.icss.happyfarm.util.DbUtil;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;

/**
 *
 * @author Administrator
 */
public class MyDecorateDialog extends javax.swing.JDialog {

    private Map<String, Decorate> itemDecorates = new HashMap<String, Decorate>();
    private Map<String, Decorate> itemNotices = new HashMap<String, Decorate>();
    Decorate previousDecorate = null;    //保存上一次点击的标签
    static private MainFrame parent;
    Decorate decorate;
    static User user;
    int i = 1000;
    String sqlD = "";
    String sqlN = "";
    DbUtil util=new DbUtil();

    /** Creates new form MyDecorateDialog */
    public MyDecorateDialog(MainFrame parent, boolean modal, User user) {
        super(parent, modal);
        MyDecorateDialog.parent = parent;
        MyDecorateDialog.user = user;

        this.setLocation((parent.getWidth() - this.getWidth()) / 2, (parent.getHeight() - this.getHeight()) / 2);
        this.setTitle("装饰");
        initComponents();
        //初始化查询装饰
        sqlD = "select d.decorateId,decorateName,decoratePrice,decoratePicture," +
                "decorateType,g.decorateIsUsed from t_decorate d inner join t_goods g" +
                " on g.decorateId=d.decorateId where userId=" + user.getUserId() + " and decorateType<>4 " +
                "order by decoratePrice asc";
        SelectDB(sqlD, "decorate");

        sqlN = "select d.decorateId,decorateName,decoratePrice,decoratePicture," +
                "decorateType,g.decorateIsUsed from t_decorate d inner join t_goods g" +
                " on g.decorateId=d.decorateId where userId=" + user.getUserId() + " and decorateType=4 " +
                "order by decorateId asc";

        this.setResizable(false);
        this.setVisible(true);

        Toolkit tk=Toolkit.getDefaultToolkit();
        Image hand_img=tk.createImage(getClass().getResource("/com/icss/happyfarm/imag/bg/mouse.png"));
        Cursor hand=tk.createCustomCursor(hand_img,new Point(0,0),"MY_CURSOR");
        setCursor(hand);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDecorateLayeredPane = new javax.swing.JLayeredPane();
        lblNotice = new javax.swing.JLabel();
        lblDecorate = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        decorateList = new javax.swing.JList();
        lblDecorateBack = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblNotice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/告示牌2.png"))); // NOI18N
        lblNotice.setName("notice"); // NOI18N
        lblNotice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblButtonMouseClicked(evt);
            }
        });
        lblNotice.setBounds(80, 7, 60, 30);
        jDecorateLayeredPane.add(lblNotice, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblDecorate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/装饰1.jpg"))); // NOI18N
        lblDecorate.setName("decorate"); // NOI18N
        lblDecorate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblButtonMouseClicked(evt);
            }
        });
        lblDecorate.setBounds(20, 10, 60, 22);
        jDecorateLayeredPane.add(lblDecorate, javax.swing.JLayeredPane.DEFAULT_LAYER);

        decorateList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(decorateList);

        jScrollPane1.setBounds(0, 60, 400, 210);
        jDecorateLayeredPane.add(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblDecorateBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/购买的装饰.png"))); // NOI18N
        lblDecorateBack.setText("jLabel1");
        lblDecorateBack.setBounds(0, 0, 400, 270);
        jDecorateLayeredPane.add(lblDecorateBack, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDecorateLayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDecorateLayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    //点击相应按钮，相应变化
    private void lblButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblButtonMouseClicked

        String btnStr = ((JLabel) evt.getSource()).getName().trim();
        //当点击的是装饰时执行
        if (btnStr.equals("decorate")) {
            //设置装饰背景
            lblDecorateBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/购买的装饰.png")));
            lblDecorate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/装饰1.jpg")));
            lblNotice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/告示牌2.png")));
            //String sql = "select * from t_decorate where isToBuy=1 and decorateType<>4 order by decoratePrice asc";
            SelectDB(sqlD, btnStr);
        //当点击的是告示牌时执行
        } else {
            lblDecorateBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/告示牌背景.png")));
            lblDecorate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/装饰2.jpg")));
            lblNotice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/告示牌1.png")));
            SelectDB(sqlN, btnStr);
        }
    }//GEN-LAST:event_lblButtonMouseClicked

    //根据点击的不同，查询出不同的结果
    public void SelectDB(String sql, String btnStr) {

        ResultSet rs = util.execute(sql);
        DefaultListModel listModel = new DefaultListModel();
        try {
            while (rs.next()) {
                decorate = new Decorate(rs.getString("decorateId"), rs.getString("decoratePicture"), rs.getInt("decoratePrice"));
                decorate.setDecorateName(rs.getString("decorateName"));
                decorate.setDecorateType(rs.getInt("decorateType"));
                decorate.setIsUsed(rs.getInt("decorateIsUsed"));
                //decorate.setIsToBuy(rs.getInt("isToBuy"));
                if (btnStr.equals("decorate")) {
                     //清空装饰里面的内容
                    for (Iterator iter = itemDecorates.entrySet().iterator(); iter.hasNext();) {
                        Decorate item = (Decorate) ((Entry) iter.next()).getValue();
                        jDecorateLayeredPane.remove(item);
                    }

                      //清空告示牌里面的内容
                    for (Iterator iter = itemNotices.entrySet().iterator(); iter.hasNext();) {
                        Decorate item = (Decorate) ((Entry) iter.next()).getValue();
                        jDecorateLayeredPane.remove(item);
                    }
                    itemDecorates.put(rs.getString("decorateId"), decorate);    //将查询出的装饰添加到hashmap中去                   
                   
                    //itemNotices.clear();
                } else {
                    //清空告示牌里面的内容
                    for (Iterator iter = itemNotices.entrySet().iterator(); iter.hasNext();) {
                        Decorate item = (Decorate) ((Entry) iter.next()).getValue();
                        jDecorateLayeredPane.remove(item);
                    }

                     //清空装饰里面的内容
                    for (Iterator iter = itemDecorates.entrySet().iterator(); iter.hasNext();) {
                        Decorate item = (Decorate) ((Entry) iter.next()).getValue();
                        jDecorateLayeredPane.remove(item);
                    }
                    itemNotices.put(rs.getString("decorateId"), decorate);    //将查询出的告示牌添加到hashmap中去
                                    
                    //itemDecorates.clear();
                }
            }
                       
            //点击的是装饰，调用显示装饰的方法
            if (btnStr.equals("decorate")) {
                showDecorate();
            //点击的是告示牌，调用显示告示牌的方法
            } else {
                showNotices();
            }
            //将结果添加到滚动框中
            decorateList.setModel(listModel);
        } catch (Exception ex) {
            Logger.getLogger(MyDecorateDialog.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            util.close();
        }

    }

    public void showDecorate() {
        int j = 0;    //用来记录垂直位置
        int z = 0;    //用来记录垂直位置
        int a = 0;    //用来记录垂直位置
        int b = 0;    //用来记录垂直位置        

        for (Iterator iter = itemDecorates.entrySet().iterator(); iter.hasNext();) {
            Entry es = (Entry) iter.next();
            decorate = (Decorate) es.getValue();
            if (decorate.getDecorateType() == 0) {
                decorate.setBounds(20, 60 + (70 * a), 70, 70);
                a++;
            } else if (decorate.getDecorateType() == 1) {
                decorate.setBounds(90, 60 + (70 * b), 70, 70);
                b++;
            } else if (decorate.getDecorateType() == 2) {
                decorate.setBounds(230, 60 + (70 * z), 70, 70);
                z++;
            } else if (decorate.getDecorateType() == 3) {
                decorate.setBounds(160, 60 + (70 * j), 70, 70);
                j++;
            }
            if (decorate.getIsUsed() == 0) {  //显示没有装饰的图片
                decorate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + decorate.getDecoratePicture())));
            } else {
                decorate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + decorate.getDecorateName() + "已装饰.png")));
            }
            jDecorateLayeredPane.add(decorate, new Integer(++i));   //将装饰标签添加到分层面板中去
            decorate.setToolTipText(decorate.getDecorateName());

            decorate.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                //给装饰图标添加事件相应
                public void mouseClicked(MouseEvent evt) {
                    decoratedClicked(evt);
                }
            });
        }
    }

    public void showNotices() {
        int j = 0;  //用来记录告示牌的位置
        int z = 0;  //用来记录垂直位置
        //遍历HashMap中的种子
        for (Iterator iter = itemNotices.entrySet().iterator(); iter.hasNext();) {

            Entry es = (Entry) iter.next();
            decorate = (Decorate) es.getValue();
            decorate.setBounds(25 + (120 * j++), 80 + (120 * z), 120, 120);     //告示牌的显示位置

            if (decorate.getX() > this.getWidth() - 50) {       //显示位置超过框架是，换行显示
                j = 0;
                decorate.setBounds(25 + (120 * j++), 80 + (120 * ++z), 120, 120);
            }
            //设置seed Label的显示图标
            if (decorate.getIsUsed() == 0) {  //显示没有装饰的图片
                decorate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + decorate.getDecoratePicture())));
            } else {
                decorate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + decorate.getDecorateName() + "已装饰.png")));
            }

            decorate.setName("" + decorate.getDecorateName());    //将告示牌的名称设置成 告示牌标签的Name值
            jDecorateLayeredPane.add(decorate, new Integer(++i));     //将告示牌标签添加到分层面板中去
            decorate.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                //给告示牌图标添加事件相应
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    noticeClicked(evt);
                }
            });
        }
    }

    //获取指定类型的 已装饰 装饰
    public Decorate getUsedDecorate(int decorateType) {
        Decorate usedDecorates = null; //保存已经装饰的背景
        try {
            String used = "select d.decorateId,decorateName,decoratePrice," +
                    "decoratePicture from t_decorate d inner join t_goods g " +
                    "on g.decorateId=d.decorateId where decorateIsUsed=1 and " +
                    "decorateType=" + decorateType + " and userId=" + user.getUserId(); //获取指定类型的 已装饰 装饰
            ResultSet rs = util.execute(used);
            while (rs.next()) {
                usedDecorates = new Decorate(rs.getString("decorateId"), rs.getString("decoratePicture"), rs.getInt("decoratePrice"));
                usedDecorates.setDecorateName(rs.getString("decorateName"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(MyDecorateDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usedDecorates;
    }

    //改变装饰的 工具方法
    public void changeUtil(Decorate seleDecorate, Decorate usedBack, int type) {
        JLabel house = (JLabel) parent.getLblHouseDecorate(); //从主窗口中获取房子标签
        JLabel dogHouse = (JLabel) parent.getLblDogHouse(); //从主窗口中获取狗窝标签
        JLabel column = (JLabel) parent.getLblColumn(); //从主窗口中获取栅栏标签
        JLabel notic = (JLabel) parent.getLblNotice(); //从主窗口中获取告示牌标签

        if (usedBack != null) {
            if (usedBack.equals(seleDecorate)) {
                String sql = "update t_goods set decorateIsUsed=0 where decorateId=" +
                        "'" + seleDecorate.getDecorateId() + "'and userId=" + user.getUserId();
                util.execute(sql);
                seleDecorate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + seleDecorate.getDecoratePicture())));
                if (type == 1) {
                    house.setIcon(null);
                } else if (type == 2) {
                    dogHouse.setIcon(null);
                } else if (type == 3) {
                    column.setIcon(null);
                } else if (type == 4) {
                    notic.setIcon(null);
                } else {
                }

            } else {
  //System.out.println("执行到点击的为未装饰");
                String sql1 = "update t_goods set decorateIsUsed=1 where decorateId=" +
                        "'" + seleDecorate.getDecorateId() + "'and userId=" + user.getUserId();
                util.execute(sql1);
                seleDecorate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + seleDecorate.getDecorateName() + "已装饰.png")));
                String sql2 = "update t_goods set decorateIsUsed=0 where decorateId='" + usedBack.getDecorateId() + "' and userId=" + user.getUserId();
                util.execute(sql2);

                if (type == 4) {                    
                    usedBack.setBounds(itemNotices.get(usedBack.getDecorateId()).getBounds());
                    usedBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + usedBack.getDecorateName() + ".png")));
                    SelectDB(sqlN, "notice");   //每次刷新itemNotices中的数据
                } else {                   
                    usedBack.setBounds(itemDecorates.get(usedBack.getDecorateId()).getBounds());
                    usedBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + usedBack.getDecorateName() + "小图.png")));
                    SelectDB(sqlD, "decorate");   //每次刷新itemDecorates中的数据
                }
                jDecorateLayeredPane.add(usedBack, new Integer(++i));   //将装饰标签添加到分层面板中去

                if (type == 1) {
                    house.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + seleDecorate.getDecorateName() + "大图.png")));
                } else if (type == 2) {
                    dogHouse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + seleDecorate.getDecorateName() + "大图.png")));
                } else if (type == 3) {
                    column.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + seleDecorate.getDecorateName() + "大图.png")));
                } else if (type == 4) {
                    notic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + seleDecorate.getDecorateName() + "大图.png")));
                } else {
                }
            }
        } else {
            String sql1 = "update t_goods set decorateIsUsed=1 where decorateId=" +
                    "'" + seleDecorate.getDecorateId() + "'and userId=" + user.getUserId();
            util.execute(sql1);
            seleDecorate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + seleDecorate.getDecorateName() + "已装饰.png")));
            if (type == 1) {
                house.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + seleDecorate.getDecorateName() + "大图.png")));
            } else if (type == 2) {
                dogHouse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + seleDecorate.getDecorateName() + "大图.png")));
            } else if (type == 3) {
                column.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + seleDecorate.getDecorateName() + "大图.png")));
            } else if (type == 4) {
                notic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + seleDecorate.getDecorateName() + "大图.png")));
            } else {
            }
        }
        util.close();
    }

    public void decoratedClicked(MouseEvent evt) {

        Decorate seleDecorate = (Decorate) evt.getSource();
        Decorate usedBack = null; //保存已经装饰的背景
        if (seleDecorate.getDecorateType() == 0) {
            usedBack = getUsedDecorate(0);
            //如果点击的是已装饰的背景
            //点击已装饰的背景会与上一次点击的交换
            if (usedBack.equals(seleDecorate)) {

                if (null == previousDecorate) {    //假如第一次点击的是已装饰背景
                    return;
                } else {
                    //点击已经装饰的背景时，修改装饰属性
                    String sql = "update t_goods set decorateIsUsed=0 where decorateId='" + seleDecorate.getDecorateId() + "'and userId=" + user.getUserId();
                    util.execute(sql);
                    //将背景图像改为可装饰状态
                    seleDecorate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + seleDecorate.getDecorateName() + "小图.png")));
                    String preSql = "update t_goods set decorateIsUsed=1 where decorateId='" + previousDecorate.getDecorateId() + "'and userId=" + user.getUserId(); //修改装饰属性
                    util.execute(preSql);
                    //将背景图像改为不可装饰状态
                    previousDecorate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + seleDecorate.getDecorateName() + "已装饰.png")));

                    JLabel back = (JLabel) parent.getLblMain(); //从主窗口中获取装饰标签
                    back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + previousDecorate.getDecorateName() + "大图.png")));
                }

            } else {
                String sql = "update t_goods set decorateIsUsed=1 where decorateId='" + seleDecorate.getDecorateId() + "'and userId=" + user.getUserId();
                util.execute(sql);
                //将背景图像改为已装饰状态
                seleDecorate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + seleDecorate.getDecorateName() + "已装饰.png")));
                //将已装饰的背景改为可装饰状态
                String uesd = "update t_goods set decorateIsUsed=0 where decorateId='" + usedBack.getDecorateId() + "'and userId=" + user.getUserId();
                util.execute(uesd);

                SelectDB(sqlD, "decorate");   //每次刷新itemNotices中的数据
                usedBack.setBounds(itemDecorates.get(usedBack.getDecorateId()).getBounds());
                jDecorateLayeredPane.add(usedBack, new Integer(++i));   //将装饰标签添加到分层面板中去
                usedBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + usedBack.getDecorateName() + "小图.png")));

                JLabel back = (JLabel) parent.getLblMain(); //从主窗口中获取装饰标签
                back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + seleDecorate.getDecorateName() + "大图.png")));
                previousDecorate = usedBack;    //保存上一次点击的标签
            }
            util.close();
        } else if (seleDecorate.getDecorateType() == 1) {
            usedBack = getUsedDecorate(1);
            changeUtil(seleDecorate, usedBack, 1);
        } else if (seleDecorate.getDecorateType() == 2) {
            usedBack = getUsedDecorate(2);
            changeUtil(seleDecorate, usedBack, 2);
        } else if (seleDecorate.getDecorateType() == 3) {
            usedBack = getUsedDecorate(3);
            changeUtil(seleDecorate, usedBack, 3);
        } else {
        }
        repaint();
    }

    public void noticeClicked(MouseEvent evt) {
        //查询出已装饰告示牌
        Decorate seleDecorate = (Decorate) evt.getSource();
        Decorate usedDecorate = getUsedDecorate(4);    //获得已经装饰的告示牌
        //调用修改方法
        changeUtil(seleDecorate, usedDecorate, 4);
    //repaint();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                MyDecorateDialog dialog = new MyDecorateDialog(parent, true, user);
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
    private javax.swing.JList decorateList;
    private javax.swing.JLayeredPane jDecorateLayeredPane;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDecorate;
    private javax.swing.JLabel lblDecorateBack;
    private javax.swing.JLabel lblNotice;
    // End of variables declaration//GEN-END:variables
}
