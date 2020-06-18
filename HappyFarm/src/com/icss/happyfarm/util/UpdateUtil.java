/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.icss.happyfarm.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author GF4
 */
public class UpdateUtil{
    private final static String url="jdbc:sqlserver://127.0.0.1;DatabaseName=happy_farm";
    private final static String username="sa";
    private final static String password="sa";
    private final static String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";

    private static Connection conn=null;


/**
 * 执行更新，删除，插入的sql语句
 * @param sql update,delete,insert语句
 * @return 执行后影响的结果数
 */
    public static int execute(String sql) {

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UpdateUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            Logger.getLogger(UpdateUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            return conn.createStatement().executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(UpdateUtil.class.getName()).log(Level.SEVERE, null, ex);

        }
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UpdateUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }
}
