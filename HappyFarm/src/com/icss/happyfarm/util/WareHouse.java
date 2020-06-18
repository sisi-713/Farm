/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icss.happyfarm.util;

import com.icss.happyfarm.bean.Fruit;
import com.icss.happyfarm.stype.Crop;
import com.icss.happyfarm.stype.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class WareHouse {

    protected Map<Integer, Fruit> items = null;
    private User user;
    private Fruit fruit;
    DbUtil util = new DbUtil();

    public Map<Integer, Fruit> getItems() {
        return items;
    }

    public WareHouse(User user) {
               
        this.user = user;
        try {
            if (items == null) {
                items = new HashMap<Integer, Fruit>();
            }
            if(items.size()>0) {
               items.clear();
            }
            int userId = user.getUserId();
            String sql2 = "select c.cropId,cropName,experience,fruitPrice,seedLevel,seedPicture,quantity,lock from " +
                    "crop c inner join t_warehouse w on w.cropId=c.cropId where userId=" + userId;
            ResultSet rs = util.execute(sql2);
            while (rs.next()) {
                fruit = new Fruit();
                fruit.setFruitId(rs.getInt("cropId"));
                fruit.setFruitName(rs.getString("cropName"));
                fruit.setFruitPicture(rs.getString("seedPicture"));
                fruit.setLevel(rs.getInt("seedLevel"));
                fruit.setFruitPrice(rs.getInt("fruitPrice"));
                fruit.setQuantity(rs.getInt("quantity"));

                fruit.setLock(rs.getString("lock"));
                items.put(new Integer(rs.getInt("cropId")), fruit); //将查询出的果实添加到hashmap中去
            }
        } catch (SQLException ex) {
            Logger.getLogger(WareHouse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //向仓库中添加果实

    public void addToWareHouse(Crop crop) {
        //存在，增加数量
        //不存在，则添加到集合中
        String sql = null;
        if (items.containsKey(crop.getCropId())) {
            fruit = items.get(crop.getCropId());

            fruit.setQuantity(fruit.getQuantity() + crop.getFactOutput());

            items.put(crop.getCropId(), fruit);
            sql = "update t_warehouse set quantity="+fruit.getQuantity()+" " +
                    "where cropId="+fruit.getFruitId()+" and userId="+user.getUserId();
        } else {
            Fruit fruit1 = new Fruit();
            fruit1.setFruitId(crop.getCropId());
            fruit1.setQuantity(crop.getFactOutput());
            fruit1.setFruitName(crop.getCropName());

            items.put(crop.getCropId(), fruit1);
            sql = "insert into t_warehouse values ("+crop.getCropId()+","+crop.getFactOutput()+",'未上锁',"+user.getUserId()+")";
        }
        util.execute(sql);

        util.close();
    }

    //卖出仓库中的果实
    public void deleteProduct(Integer pid) {
        items.remove(pid);
    }

    //计算总价格
    public int getTotalPrice() {
        int totalPrice = 0;
        for (Iterator iter = items.values().iterator(); iter.hasNext();) {
            fruit = (Fruit) iter.next();
            if (fruit.getLock().equals("未上锁")) {
                int quantity = fruit.getQuantity();
                int price = fruit.getFruitPrice();
                totalPrice += price * quantity;
            }
        }
        return totalPrice;
    }
}
