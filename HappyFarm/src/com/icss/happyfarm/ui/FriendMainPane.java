/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icss.happyfarm.ui;

import com.icss.happyfarm.stype.User;
import com.icss.happyfarm.util.DbUtil;
import com.icss.happyfarm.util.ImageUtil;
import com.icss.happyfarm.util.Page;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
//import java.util.Map.Entry;
import java.awt.event.*;
//import java.util.ArrayList;
import java.sql.*;

/**
 *
 * @author Administrator
 */
public class FriendMainPane extends JPanel {

    private User user = null;
    private JLabel lblFriendList;
    private JLabel lblBackground;
    // private ArrayList<Integer> user = new ArrayList<Integer>();
    //  private Map<String, String> t_user = new HashMap<String, String>();
    private int currentPage = 1;   //当前页面
    private int totalRecord;      //总共有多少条记录
    private int howManyPage;           //总共页数
    private int startRecord;       //当前页的开始记录
    private int lastRecord;       //当前页的结束记录
    private JTextField selectField;
    private JLabel selectLabel;
    private JLabel refreshLabel;
    private JLabel sortLabel;
    private JLabel sortLabel2;
    private FriendListPane[] fp = new FriendListPane[6];
    private JLabel left;
    private JLabel right;
    private Page page = null;
    private JLabel pageLabel;
    private JLabel addFriend;
    private ResultSet rs;
    private int sum;
    private int sum2;
    private boolean sch = false;
    private boolean flag = true;
    private DbUtil util = new DbUtil();
    private String url = "/com/icss/happyfarm/imag/bg/";
    private ImageUtil imageUtil = null;

    public FriendMainPane(User user) {
        imageUtil = new ImageUtil();
        this.user = user;
        page = new Page(6);
        this.setBounds(971, 140, 191, 399);
        this.setLayout(null);
        this.setOpaque(false);
        initDataBase();
        launchPanel();
        initPane(currentPage);
        pageLabel.setText(currentPage + "/" + howManyPage);
    }

    public void initDataBase3(String text) {    //查询功能的数据库操作
        try {

            String sql = "select * from userl where userName like ('%'+'" + text + "'+'%') and userID<>" + user.getUserId();

            rs = util.execute(sql);
            rs.last();
            sum2 = rs.getRow();
            if (sum2 == 0) {
                sum2 = 1;
                for (int j = 0; j < 6; j++) {
                    fp[j].getLblFriend()[1].setIcon(new ImageIcon(""));
                    fp[j].getLblFriend()[2].setIcon(new ImageIcon(""));
                    fp[j].getLblFriend()[3].setText(null);
                }
            }
            rs.beforeFirst();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void initDataBase2() {    //金钱排行查询数据库
        try {
            String sql = "select * from userl order by userMoney desc";

            rs = util.execute(sql);
            rs.last();
            sum = rs.getRow();
            rs.beforeFirst();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void initDataBase() {           //经验排行查询数据库
        try {
            String sql = "select *from userl order by userExperience desc";
//            ConDataBase.conDB();
            rs = util.execute(sql);
            rs.last();
            sum = rs.getRow();
            rs.beforeFirst();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void initPane(int currentPage) {        //数据分页显示，配合right哈left标签
        if (sch == true) {
            totalRecord = sum2;
        } else {
            totalRecord = sum;
        }
        page.init(totalRecord, currentPage);
        startRecord = page.getStartRecord();
        lastRecord = page.getEndRecord();
        howManyPage = page.getHowManyPage();
        pageLabel.setText(currentPage + "/" + howManyPage);

        if (currentPage == 1) {
            left.setIcon(new ImageIcon(getClass().getResource(url + "leftDark.png")));
        } else {
            left.setIcon(new ImageIcon(getClass().getResource(url + "向左.png")));
        }
        if (currentPage == howManyPage) {
            right.setIcon(new ImageIcon(getClass().getResource(url + "rightDark.png")));
        } else {
            right.setIcon(new ImageIcon(getClass().getResource(url + "向右.png")));
        }


        int m = 0, i = 1;
        try {
            while (rs.next()) {
                if (i < startRecord) {
                    i++;
                    continue;
                }
                if (i > lastRecord) {
                    break;
                }

                String str = rs.getString("userPicture");
                String str2 = rs.getString("userName");
                //  System.out.println(str+"\t"+str2);
                fp[m].getLblFriend()[1].setIcon(new ImageIcon(getClass().getResource(url + "number/nub" + i + ".png")));
                try {
                    fp[m].getLblFriend()[2].setIcon(imageUtil.createZoomSizeImage(str));
                } catch (IOException ex) {
                    Logger.getLogger(FriendMainPane.class.getName()).log(Level.SEVERE, null, ex);
                }
                fp[m].getLblFriend()[3].setText(str2);
                i++;
                m++;
                if (m == 6) {
                    m = 0;
                }
                for (int j = totalRecord - startRecord + 1; j < currentPage * 6 - startRecord + 1; j++) {
                    fp[j].getLblFriend()[1].setIcon(new ImageIcon(""));
                    fp[j].getLblFriend()[2].setIcon(new ImageIcon(""));
                    fp[j].getLblFriend()[3].setText(null);
                }
            }
            rs.first();         //每次调用此函数要把游标移到第一条记录
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        util.close();
    }

    public void launchPanel() {
        lblFriendList = new JLabel();
        lblBackground = new JLabel();
        pageLabel = new JLabel();
        selectField = new JTextField("按昵称查找");
        selectField.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                selectField.setText("");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (selectField.getText().isEmpty()) {
                    selectField.setText("按昵称查找");
                }
            }
        });

        selectField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    search();
                }
            }
        });

        selectLabel = new JLabel();
        refreshLabel = new JLabel();
        sortLabel = new JLabel();
        sortLabel2 = new JLabel();
        left = new JLabel();
        right = new JLabel();
        addFriend = new JLabel();

        lblFriendList.setIcon(new ImageIcon(getClass().getResource(url + "好友列表1.png")));
        lblFriendList.setBounds(0, 130, 30, 123);
        lblFriendList.setName("list");
        lblBackground.setIcon(new ImageIcon(getClass().getResource(url + "好友列表背景.png")));
        lblBackground.setBounds(30, 0, 160, 400);
        selectLabel.setIcon(new ImageIcon(getClass().getResource(url + "search.png")));
        selectLabel.setBounds(130, 0, 30, 40);
        selectLabel.setName("search");
        refreshLabel.setBounds(160, 0, 30, 40);
        refreshLabel.setIcon(new ImageIcon(getClass().getResource(url + "fl_1.png")));
        refreshLabel.setName("refresh");
        selectField.setBounds(40, 10, 90, 20);
        // selectField.setForeground(new java.awt.Color(204, 204, 204));
        sortLabel.setIcon(new ImageIcon(getClass().getResource(url + "经验排行1.png")));
        sortLabel.setBounds(40, 30, 80, 30);
        sortLabel.setName("sortExp");
        sortLabel2.setIcon(new ImageIcon(getClass().getResource(url + "金钱排行2.png")));
        sortLabel2.setBounds(115, 30, 80, 30);
        sortLabel2.setName("sortMoney");
        left.setIcon(new ImageIcon(getClass().getResource(url + "向左.png")));
        left.setBounds(55, 360, 30, 30);
        left.setName("left");
        right.setIcon(new ImageIcon(getClass().getResource(url + "向右.png")));
        right.setBounds(130, 360, 30, 30);
        right.setName("right");
        //addFriend.setIcon(new ImageIcon(getClass().getResource(url+"add.png")));
        // addFriend.setBounds(80, 370, 80, 30);
        // addFriend.setName("addFriend");
        pageLabel.setBounds(98, 360, 30, 30);

        add(selectLabel);
        add(selectField);
        add(refreshLabel);
        add(sortLabel);
        add(sortLabel2);
        add(left);
        add(right);
        // add(addFriend);
        add(pageLabel);

        int temp = 70;
        for (int i = 0; i < fp.length; i++) {
            fp[i] = new FriendListPane(temp);
            fp[i].setOpaque(false);
            temp += 46;
            this.add(fp[i]);

        }

        add(lblFriendList);
        add(lblBackground);
        ChangePage cp = new ChangePage();
        left.addMouseListener(cp);
        right.addMouseListener(cp);
        selectLabel.addMouseListener(cp);
        sortLabel.addMouseListener(cp);
        sortLabel2.addMouseListener(cp);
        refreshLabel.addMouseListener(cp);
        lblFriendList.addMouseListener(cp);
        addFriend.addMouseListener(cp);
//        selectField.transferFocus();
    }

    public void search() {
        String text = selectField.getText();
        if (text.equals("") || text.equals("按昵称查找")) {

            selectField.setText("按昵称查找");
        } else {
            sch = true;
            currentPage = 1;
            FriendMainPane.this.initDataBase3(text);
            FriendMainPane.this.initPane(currentPage);
        }
    }

    private class ChangePage extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            JLabel lblTemp = (JLabel) e.getSource();

            //点击左翻页
            if (lblTemp.getName().equals("left")) {
            
                if (currentPage <= 1) {
                    return;
                } else {
                    currentPage--;
                    if (sch) {
                        String text = selectField.getText();
                        FriendMainPane.this.initDataBase3(text);
                    } else {
                        if (flag) {
                            FriendMainPane.this.initDataBase();   //必须执行此函数，否则将丢失数据，悲剧 的游标遍历。
                        } else {
                            FriendMainPane.this.initDataBase2();
                        }
                    }
                    FriendMainPane.this.initPane(currentPage);
                    pageLabel.setText(currentPage + "/" + howManyPage);
                }

            //点击右翻页
            } else if (lblTemp.getName().equals("right")) {
               
                if (currentPage >= howManyPage) {
//                    System.out.println("不能往右边翻了");
                    return;
                } else {
                    currentPage++;
                    if(sch){
                    String text = selectField.getText();
                    FriendMainPane.this.initDataBase3(text);
                    }else{
                    if (flag) {
                        FriendMainPane.this.initDataBase();
                    } else {
                        FriendMainPane.this.initDataBase2();
                    }
                    }
                    FriendMainPane.this.initPane(currentPage);
                    pageLabel.setText(currentPage + "/" + howManyPage);
                }
            } //查询功能
            else if (lblTemp.getName().equals("search")) {
                search();

            } //经验排行
            else if (lblTemp.getName().equals("sortExp")) {
                flag = true;
                sch = false;
                //selectField.setText("按昵称查找");
                if (sortLabel.getIcon().equals(new ImageIcon(getClass().getResource(url + "经验排行1.png")))) {

                    sortLabel2.setIcon(new ImageIcon(getClass().getResource(url + "金钱排行1.png")));
                    sortLabel.setIcon(new ImageIcon(getClass().getResource(url + "经验排行2.png")));
                } else {

                    sortLabel.setIcon(new ImageIcon(getClass().getResource(url + "经验排行1.png")));
                    sortLabel2.setIcon(new ImageIcon(getClass().getResource(url + "金钱排行2.png")));
                }
                FriendMainPane.this.initDataBase();
                FriendMainPane.this.initPane(currentPage);
            } //金钱排行
            else if (lblTemp.getName().equals("sortMoney")) {

                flag = false;
                sch = false;
                //selectField.setText("按昵称查找");
                if (sortLabel2.getIcon().equals(new ImageIcon(getClass().getResource(url + "金钱排行2.png")))) {
                    sortLabel2.setIcon(new ImageIcon(getClass().getResource(url + "金钱排行1.png")));
                    sortLabel.setIcon(new ImageIcon(getClass().getResource(url + "经验排行2.png")));
                } else {
                    sortLabel.setIcon(new ImageIcon(getClass().getResource(url + "经验排行2.png")));
                    sortLabel2.setIcon(new ImageIcon(getClass().getResource(url + "金钱排行1.png")));
                }
                FriendMainPane.this.initDataBase2();
                FriendMainPane.this.initPane(currentPage);
            } //刷新记录
            else if (lblTemp.getName().equals("refresh")) {
                flag = true;
                sch = false;
                //selectField.setText("按昵称查找");
                FriendMainPane.this.initDataBase();
                FriendMainPane.this.initPane(currentPage);
                pageLabel.setText(currentPage + "/" + howManyPage);
            } //添加好友按钮
            else if (lblTemp.getName().equals("addFriend")) {
//                System.out.println("1");
//                new addFrame();
            } //好友界面弹出与弹进
            else if (lblTemp.getName().equals("list")) {
                int x = FriendMainPane.this.getX();
                int y = FriendMainPane.this.getY();
                myThread mt = new myThread(x, y);
                mt.start();
            }
        }
    }
    //线程控制好友界面弹出与弹进

    class myThread extends Thread {

        private int x;
        private int y;

        public myThread(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void run() {
            //System.out.println(x);
            try {
                if (x == 971 || x == 970) {
                    for (x = 972; x > 820; x -= 2) {
                        Thread.sleep(1);
                        FriendMainPane.this.setLocation(x, y);
                        lblFriendList.setIcon(new javax.swing.ImageIcon(getClass().getResource(url + "好友列表2.png")));
                    }
                } else if (x == 822) {
                    for (x = 820; x < 972; x += 2) {
                        Thread.sleep(1);
                        FriendMainPane.this.setLocation(x, y);
                        lblFriendList.setIcon(new javax.swing.ImageIcon(getClass().getResource(url + "好友列表1.png")));
                    }
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}












