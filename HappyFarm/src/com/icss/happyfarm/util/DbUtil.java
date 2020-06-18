/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icss.happyfarm.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @GF4
 */
public class DbUtil {

    private String url = "jdbc:sqlserver://127.0.0.1;DatabaseName=happy_farm";
    private String userName = "sa";
    private String passWord = "sa";
    private String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    public Connection getConn() {
        return conn;
    }

    public Statement getStmt() {
        return stmt;
    }
    
    
    public ResultSet execute(String sql) {
        //加载数据驱动
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DbUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        //连接数据库
        try {
            conn = DriverManager.getConnection(url, userName, passWord);
        } catch (SQLException ex) {
            Logger.getLogger(DbUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        //创建Statement
        try {
            stmt = conn.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,java.sql.ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException ex) {
            Logger.getLogger(DbUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        //返回结果集
        try {
            if (sql.substring(0, 6).equalsIgnoreCase("SELECT")) {
                rs = stmt.executeQuery(sql);
            } else {
                stmt.executeUpdate(sql);
            }
        } catch (SQLException ex) {
            System.out.println(sql);
            Logger.getLogger(DbUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;


    }

    public void close() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        DbUtil util=new DbUtil();
        String sql="select * from crop";
        ResultSet rs=util.execute(sql);
        try {
            while (rs.next()) {
                System.out.println("OK");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
