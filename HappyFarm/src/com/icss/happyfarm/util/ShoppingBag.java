/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icss.happyfarm.util;

import com.icss.happyfarm.bean.Decorate;
import com.icss.happyfarm.bean.Seed;
import com.icss.happyfarm.bean.Tools;
import com.icss.happyfarm.stype.*;
import com.icss.happyfarm.bean.Products;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
//import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class ShoppingBag {

    private int userId;
    private User user;
    protected Map<String, Products> items;   //Goods只是一个接口，用于实现多态
    DbUtil util = new DbUtil();

    public Map<String, Products> getItems() {
        return items;
    }

    public ShoppingBag(User user) {
        this.user = user;
        try {
            if (items == null) {
                items = new HashMap<String, Products>();
            }
            String sqlS = "select s.cropId,cropName,seedPrice,fruitPrice," +
                    "expectQuantity,cropGrowTime,experience,seedLevel," +
                    "g.seedQuantity,seedPicture,explain from crop s inner join t_goods" +
                    " g on g.cropId=s.cropId where userId=" + user.getUserId();

            String sqlD = "select d.decorateId,decorateName,decoratePrice,decoratePicture," +
                    "decorateType,g.decorateIsUsed from t_decorate d inner join t_goods g on " +
                    "g.decorateId=d.decorateId where userId=" + user.getUserId();

            String sqlT = "select t.toolsId,toolsName,toolsPrice,totalQuantity,toolsPicture,toolsType,explain,toolsQuantity " +
                    "from t_tool t inner join t_goods g on g.toolsId=t.toolsId where userId=" + user.getUserId();

            ResultSet drs = util.execute(sqlD);
            String decorateId = "";
            while (drs.next()) {
                decorateId = drs.getString("decorateId");
                Decorate decorate1 = new Decorate(decorateId, drs.getString("decoratePicture"), drs.getInt("decoratePrice"));
                decorate1.setDecorateName(drs.getString("decorateName"));
                decorate1.setIsUsed(drs.getInt("decorateIsUsed"));

                items.put(decorateId, decorate1); //将查询出被买的装饰添加到购物袋hashmap中去
            }

            ResultSet srs = util.execute(sqlS);
            int cropId = 0;
            while (srs.next()) {
                cropId = srs.getInt("cropId");
                Seed seed = new Seed(cropId, srs.getInt("seedPrice"), srs.getString("seedPicture"));
                seed.setSeedName(srs.getString("cropName"));
                seed.setExperience(srs.getInt("experience"));
                seed.setHarvestTime(srs.getInt("cropGrowTime"));
                seed.setExpectQuantity(srs.getInt("expectQuantity"));
                seed.setFruitPrice(srs.getInt("fruitPrice"));
                seed.setLevel(srs.getInt("seedLevel"));

                System.out.println(srs.getInt("seedQuantity"));
                seed.setSeedQuantity(srs.getInt("seedQuantity"));
                seed.setExplain(srs.getString("explain"));
                items.put(cropId + "", seed);   //将查询出被买的种子添加到购物袋hashmap中去
            }

            ResultSet trs = util.execute(sqlT);
            String toolsId;
            while (trs.next()) {
                toolsId = trs.getString("toolsId");
                Tools tool = new Tools(toolsId, trs.getString("toolsName"), trs.getInt("toolsPrice"), trs.getString("toolsPicture"));
                tool.setBuyQuantity(trs.getInt("toolsQuantity"));
                tool.setToolsType(trs.getInt("toolsType"));
                tool.setTotalQuantity(trs.getInt("totalQuantity"));

                tool.setExplain(trs.getString("explain"));

                items.put(toolsId, tool);   //将查询出的道具添加到hashmap中去
            }

        } catch (SQLException ex) {
            Logger.getLogger(ShoppingBag.class.getName()).log(Level.SEVERE, null, ex);
        }
        util.close();
    }

    //向购物袋添加种子
    public boolean addSeedsToShoppingBag(String seedId, Seed seeds) {
        //存在，增加数量
        //不存在，则添加到集合中
        if (items.containsKey(seedId)) {

            Seed seeds1 = (Seed) items.get(seedId);
            seeds.setSeedQuantity(seeds1.getSeedQuantity() + seeds.getSeedQuantity());


            items.put(seedId, seeds);
            return true;

        } else {
            items.put(seedId, seeds);
            return false;
        }
    }

    //向购物袋添加肥料
    public boolean addToolToShoppingBag(String toolsId, Tools tool) {
        //存在，增加数量
        //不存在，则添加到集合中
        if (items.containsKey(toolsId)) {
            if (tool.getToolsType() == 0) {

                Tools tool1 = (Tools) items.get(toolsId);
                tool.setBuyQuantity(tool.getBuyQuantity() + tool1.getBuyQuantity());

                items.put(toolsId, tool);
            }
            return true;
        } else {
            items.put(toolsId, tool);
            return false;
        }
    }
    //向购物袋添加装饰

    public boolean addDecorateToShoppingBag(String decorateId, Decorate decorate) {
        //存在，增加数量
        //不存在，则添加到集合中
        if (items.containsKey(decorateId)) {

            Decorate decorate1 = (Decorate) items.get(decorateId);

            return true;
        } else {
            items.put(decorateId, decorate);
            return false;
        }
    }


    //使用购物袋中的种子
    public void deleteSeeds(String seedId) {
        items.remove(seedId);
        //
    }
    //使用购物袋中的肥料

    public void deleteMuck(String muckId) {
        items.remove(muckId);
    }
    //使用购物袋中的装饰

    public void deleteDecorate(String decorateId) {
        items.remove(decorateId);
    }
}
