/*
 * 关于背包的实时更新
 *
 */

package com.icss.happyfarm.ui;


import com.icss.happyfarm.bean.Seed;
import com.icss.happyfarm.bean.Tools;
import com.icss.happyfarm.stype.User;
import com.icss.happyfarm.util.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
/**
 * 背包类
 * 
 */
public class BagPanel extends JPanel{
    private User user;
    private JLabel lblBackGround;
    private Page page;
    private Map<Seed, Integer> seeds = new HashMap<Seed, Integer>();
    private Map<Tools, Integer> tools = new HashMap<Tools, Integer>();
    private JLabel lblLeft = new JLabel();
    private JLabel lblRight = new JLabel();
    private int currentPage = 1;
    private int maxPage = 1;
    private MainFrame mf;
    private JLabel[] lblCropNum = new JLabel[5];
    DbUtil util=new DbUtil();
    private String url="/com/icss/happyfarm/imag/cursor/";
    private Object obj=null;

    public BagPanel(User user, MainFrame mf){
        this.user = user;
        this.mf = mf;

        setOpaque(false);            //设置背包条属性
        setLayout(null);
        setBounds(50, 620, 380, 70);
        setVisible(false);

        lblBackGround = new JLabel();
        page = new Page(5);


        //initProp();                   //连接数据库 将结果存入HashMap
        initCompenents();
    }

    public void initCompenents(){

        lblBackGround.setIcon(new ImageIcon(getClass().getResource("/com/icss/happyFarm/images/seedPicture/bagBar.png")));
        lblBackGround.setBounds(0, 0, 380, 70);

        lblLeft.setIcon(new ImageIcon(getClass().getResource("/com/icss/happyFarm/images/seedPicture/lblLeft.png")));
        lblLeft.setName("left");
        lblRight.setIcon(new ImageIcon(getClass().getResource("/com/icss/happyFarm/images/seedPicture/lblRight.png")));
        lblRight.setName("right");
        lblLeft.setBounds(0, 0, 20, 70);
        lblRight.setBounds(360, 0, 20, 70);

        LeftAndRight lar = new LeftAndRight();
        lblLeft.addMouseListener(lar);
        lblRight.addMouseListener(lar);

        add(lblLeft);
        add(lblRight);
    }

    public void initProp(){             //连接数据库 将结果存入HashMap

        if( seeds.size() > 0){
             seeds.clear();                  //存入数据之前清空hashmap
             this.removeAll();
        }
         if( tools.size() > 0){
             tools.clear();                  //存入数据之前清空hashmap
             this.removeAll();
        }

         for(int i=0; i<lblCropNum.length; i++){
            lblCropNum[i] = new JLabel();
            lblCropNum[i].setFont(new Font("宋体", 1, 12));
            lblCropNum[i].setBounds(73+i*67, 50, 20, 20);
            this.add(lblCropNum[i]);
        }
        Seed seed;
        Tools tool;

//        int goodsSum=0;                      //背包内的作物总数

        String sqlS = "select c.cropId,cropName,fruitPrice,expectQuantity,experience,seedPrice," +
                    "g.seedQuantity,seedPicture,explain from crop c inner join t_goods" +
                    " g on g.cropId=c.cropId where userId="+user.getUserId();

        String sqlT = "select t.toolsId,toolsName,toolsPrice,toolsPicture,toolsType,explain,toolsQuantity from " +
                "t_tool t inner join t_goods g on g.toolsId=t.toolsId where userId=" + user.getUserId()+" and toolsType=0";
        ResultSet rs = util.execute(sqlS);
        try {                                              //初始化hashmap
            while (rs.next()) {
                seed = new Seed(rs.getInt("cropId"),rs.getInt("seedPrice"),rs.getString("seedPicture"));

                seed.setSeedQuantity(rs.getInt("seedQuantity"));

                seeds.put(seed, seed.getSeedQuantity());
            }
        } catch (SQLException ex) {
            Logger.getLogger(BagPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResultSet rst = util.execute(sqlT);
        try {
            while (rst.next()) {
                tool = new Tools(rst.getString("toolsId"),rst.getString("toolsName"),rst.getInt("toolsPrice"),rst.getString("toolsPicture"));
                tool.setToolsQuantity(rst.getInt("toolsQuantity"));
                tools.put(tool, tool.getToolsQuantity());
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        util.close();              //关闭数据库连接

        page.init( seeds.size()+tools.size(), currentPage);

        maxPage = page.getHowManyPage();

        Iterator it =  seeds.entrySet().iterator();  //将HashMap中的作物放入背包
        Iterator tl =  tools.entrySet().iterator();  //将HashMap中的作物放入背包
        int i=1;
        int temp=0;
        //循环
        while(it.hasNext()){
            if(i < page.getStartRecord()){
                i++;
                Entry es = (Entry)it.next();
                continue;
            }
            if(i > page.getEndRecord()){
                break;
            }
            Entry es = (Entry)it.next();
            seed = ((Seed)es.getKey());
            if(i <= 5){
                seed.setBounds(27+(i-1)*66, 5, 61, 61);
            }else{
                seed.setBounds(27+(i%6)*66, 5, 61, 61);
            }
            seed.setIcon(new ImageIcon(getClass().getResource("/com/icss/happyfarm/images/seedPicture/" + seed.getSeedsPicture())));
            //seed.setName(""+seed.getSeedId());
            lblCropNum[temp].setText(((Integer)es.getValue()).toString());
            seed.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e){
                    mouseClick(e);
                }
            });
            this.add(seed);
            temp++;
            i++;
        }
        while(tl.hasNext()){
            if(i < page.getStartRecord()){
                i++;
                Entry es = (Entry)tl.next();
                continue;
            }
            if(i > page.getEndRecord()){
                break;
            }
            Entry es = (Entry)tl.next();
            tool = ((Tools)es.getKey());
            if(i <= 5){
                tool.setBounds(27+(i-1)*66, 5, 61, 61);
            }else{
                tool.setBounds(27+(i%6)*66, 5, 61, 61);
            }
            tool.setIcon(new ImageIcon(getClass().getResource("/com/icss/happyfarm/images/toolsPicture/" + tool.getToolsPicture())));
            //tool.setName(tool.getToolsId());
            lblCropNum[temp].setText(((Integer)es.getValue()).toString());
            tool.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e){
                    mouseClick(e);
                }
            });
            this.add(tool);
            temp++;
            i++;
        }
        add(lblBackGround);
        page.clear();
    }

    public void mouseClick(MouseEvent evt) {
        if(mf.getCursorTag()!=2){
            mf.setCursorTag(2);
        }
        obj = evt.getSource();
        if(mf.getObj()==null || !mf.getObj().equals(obj)){
            mf.setObj(obj);
            if (obj instanceof Seed) {
                mf.getLblMouse().setIcon(new ImageIcon(getClass().getResource(url + ((Seed) obj).getSeedsPicture())));
                
            } else {
                
                mf.getLblMouse().setIcon(new ImageIcon(getClass().getResource(url + ((Tools) obj).getToolsPicture())));
            }
            
        }else {
            mf.setObj(null);
            mf.getLblMouse().setIcon(new ImageIcon());
        } 
    }

    public boolean useSeed() {

        Seed sd = (Seed) obj;
        boolean can = true;
        int seedQuantity = seeds.get(sd);
        seedQuantity--;
        if (seedQuantity == 0) {
            String sql = "delete from t_goods where cropId=" + sd.getSeedId() + " and userId=" + user.getUserId();
            util.execute(sql);
            mf.getLblMouse().setIcon(new ImageIcon());   //设置移动标签
            mf.setCursorTag(0);

            this.remove(sd);
            can = false;
        } else {
            String sql = "update t_goods set seedQuantity=" + seedQuantity + " where" +
                    " cropId=" + sd.getSeedId() + " and userId=" + user.getUserId();
            util.execute(sql);
            can =  true;
        }

        BagPanel.this.initProp();  //从新加载背包
        initCompenents();
        BagPanel.this.repaint();
        util.close();
        return can;
    }


    //使用化肥时，化肥数量减少
    public Boolean useTool() {
        Tools tl = (Tools) obj;
        boolean can = true;
        int toolQuantity = tools.get(tl);
        toolQuantity--;
        if (toolQuantity == 0) {
            String sql = "delete from t_goods where toolsId='" + tl.getToolsId() + "' and userId=" + user.getUserId();
            util.execute(sql);
            
            mf.setCursorTag(0);

            this.remove(tl);
            can = false;
            TimerRun();
//            mf.getLblMouse().setIcon(new ImageIcon());   //设置移动标签
        } else {
            String sql = "update t_goods set toolsQuantity=" + toolQuantity + " where" +
                    " toolsId='" + tl.getToolsId() + "' and userId=" + user.getUserId();
            util.execute(sql);
            can =  true;
        }

        BagPanel.this.initProp();  //从新加载背包
        initCompenents();
        BagPanel.this.repaint();
        util.close();
        return can;
    }


    public void open(){
        if(!this.isVisible()){
            initProp();
            this.setVisible(true);
        }
    }

    public void close(){
        if(this.isVisible()){
            this.setVisible(false);
        }
    }

    public void putInBag(){

    }

    public Map<Seed, Integer> getSeed() {
        return  seeds;
    }

    private class LeftAndRight extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent me){
            JLabel lblTemp = (JLabel)me.getSource();
            if(lblTemp.getName().equals("left")){
                if(currentPage <= 1){
//                    System.out.println("不能往左边翻了");
//                    System.out.println("这是第 "+currentPage+"页");
                    return;
                }else{
                    currentPage--;
                    BagPanel.this.removeAll();
                    BagPanel.this.initProp();
                    initCompenents();
                    BagPanel.this.repaint();
                }
//                System.out.println(currentPage);
            }else if(lblTemp.getName().equals("right")){
                if(currentPage >= maxPage){
//                    System.out.println("不能往右边翻了");
//                    System.out.println("这是第 "+currentPage+"页");
                    return;
                }else{
                    currentPage++;
                    BagPanel.this.removeAll();
                    BagPanel.this.initProp();
                    initCompenents();
                    BagPanel.this.repaint();
                }
//                System.out.println(currentPage);
            }
        }
    }


    class MyTask extends TimerTask {

        @Override
        public void run() {
            mf.getLblMouse().setIcon(new ImageIcon());
        }
    }

    //启动定时器，延迟200MS
    public void TimerRun() {
        Timer t = new Timer();
        t.schedule(new MyTask(), 200);
    }
}




