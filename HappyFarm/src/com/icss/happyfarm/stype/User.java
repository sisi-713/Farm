/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icss.happyfarm.stype;

import com.icss.happyfarm.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class User {

    private int userId;     //用户ID，主键
    private String userName;    //用户名
    private String userPicture; //用户头像图片地址
    private int userExp;    //总经验值
    private int userLevel;   //等级
    private int userMoney;  //金钱

    /**
     *返回当前等级拥有的经验,
     *等级与经验对应的公式是：200*n,n为等级
     */
    public int getUserExp() {
        if (userLevel == 0) {
            return userExp;
        } else {
            return userExp % (200 * userLevel);
        }
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public int getUserId() {
        return userId;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public int getUserMoney() {
        return userMoney;
    }

    public User(ResultSet rs) {
        try {
            userId = rs.getInt("userId");
            userName = rs.getString("userName");
            userLevel = rs.getInt("userLevel");
            userExp = rs.getInt("userExperience");
            userMoney = rs.getInt("userMoney");
            userPicture = rs.getString("userPicture");

        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int addMoney(int money) {  //添加金钱
        userMoney += money;

        //执行更新用户信息的sql语句
        String sql = "update userl set userMoney=" + userMoney + " where userId=" + userId;

        UpdateUtil.execute(sql);

        return userMoney;
    }

    public int minMoney(int money) {  //扣除金钱
        userMoney -= money;

        //执行更新用户信息的sql语句
        String sql = "update userl set userMoney=" + userMoney + " where userId=" + userId;

        UpdateUtil.execute(sql);

        return userMoney;
    }

    //提升当前等级，并更新数据库
    public void levelUp() {
        userLevel++;

        //执行更新用户信息的sql语句
        String sql = "update userl set userLevel=" + userLevel + " where userId=" + userId;

        UpdateUtil.execute(sql);
    }


    //升到下一级所需的总经验
    public int getNextExp() {
        return 200 * (userLevel + 1);
    }

    public int addExp(int exp) {
        userExp += exp;   //增加经验值

        String sql = "update userl set userExperience=" + userExp + " where userId=" + userId;

        UpdateUtil.execute(sql);

        int nextExp = getNextExp();

        if (userExp >= nextExp) {   //增加经验后达到升级经验要求
            levelUp();
        }

        //返回当前等级拥有的经验
        return getUserExp();
    }
}
