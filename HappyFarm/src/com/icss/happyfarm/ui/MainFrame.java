/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainFrame.java
 * 
 * 
 */
package com.icss.happyfarm.ui;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.util.Timer;
import com.icss.happyfarm.stype.*;
import com.icss.happyfarm.util.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.Date;
import com.icss.happyfarm.bean.*;
import com.icss.happyfarm.util.WareHouse;

/**
 *
 * @author Zqun
 */
public class MainFrame extends javax.swing.JFrame {

    private static User  user;

    private JLabel[] landsArray = new JLabel[18]; //土地标签
    private Land[] lands=new Land[18];              //土地对象

    private JLabel[] glassArray=new JLabel[18];     //长草标签
    private JLabel[] wormArray=new JLabel[18];      //虫子标签数组

    private int land_x = 251;  //第一块土地标签顶点横坐标
    private int land_y = 285;  //第一块土地标签顶点纵坐标
    private int landNext = 0;  //下一块待开垦土地编号
    private int landMouseAt= -1; //当前鼠标指针所在的已开垦和待开垦土地编号

    private JLabel[] cropsArray = new JLabel[18]; //作物标签
    private JLabel[] cropHarve=new JLabel[18];  //作物收获时显示的图片标签

    private int crop_x=260; //第一块作物标签顶点横坐标
    private int crop_y=230; //第一块作物标签顶点纵坐标


    private int xPos = 0; //当前鼠标所在土地标签x坐标
    private int yPos = 0; //当前鼠标所在土地标签y坐标
        
    private int cursorTag = 0;    //标志位，用来表示当前鼠标指针设置为哪个图标，0为空
    private String imgUrl;  //跟随鼠标标签显示的图片地址

    private Weather weather;
    private  Glass glass;
    private Worm worm;

    private JLabel lblBg=new JLabel();  //作物信息面板的背景
    private JLabel lblTime=new JLabel();    //显示成长时间的标签
    private JLabel lblHealth=new JLabel();  //显示健康度标签
    private JLayeredPane pnlBg=new JLayeredPane();  //作物信息面板

    private DbUtil util=new DbUtil();

    private Cursor hand;

    Object obj=null;

    private BagPanel myBag;
    private WareHouse wh=null;

    public static User getUser() {
        return user;
    }

    public void setImgUrl(String s) {
        imgUrl = s;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setCursorTag(int cursorTag) {
        this.cursorTag = cursorTag;
    }

    public void dispMoney(int money){
        lblMoney.setText("" + money);
    }

    public void dispExp(int exp){
        lblExp.setText(exp + "/" + user.getNextExp());
         lblLevel.setText("" + user.getUserLevel());

         pbLevelBar.setMaximum(user.getNextExp());
         pbLevelBar.setValue(exp);
    }

    //有图标跟随之后单击鼠标的响应事件
    public void ClickPerform() throws InterruptedException {
//        System.out.println(cursorTag);
        switch (cursorTag) {
            case 1:
                spadeAction();      //铲子点击
                break;
            case 2:
                
                if(obj!=null){
                    if(obj instanceof Seed){
                        growAction(((Seed)obj).getSeedId());
                        
                    }else{
                        fertAction(obj);
                    }
                }
                      
                break;
            case 3:
                waterAction();      //浇水
                break;
            case 4:
                cleanGlassAction();   //除草
                break;
            case 5:             //除虫
                killWormAction();
                break;
            case 6:
                getHarvAction();      //收获
                break;
            case 7:
                getAllHarvAction();   //一键全收
                break;
            default:
                if(landMouseAt!=-1 &&landMouseAt==landNext){
                    digLandAction();  //开垦新土地
                }                        
                break;
        }
    }

    //在土地上点击铲子的动作
    public void spadeAction() {
        lblMouse.setIcon(new ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/cursor/spade2.png")));
        this.setImgUrl("/com/icss/happyfarm/imag/cursor/spade.png");
        TimerRun();

        if (landMouseAt != -1 && landMouseAt != landNext && lands[landMouseAt].isGrown()) {   //鼠标在已开垦土地上

            if (lands[landMouseAt].cropIsDead()) {     //鼠标所在土地有待铲除枯萎作物
                cropsArray[landMouseAt].setIcon(new ImageIcon(""));
                lands[landMouseAt].clear();  //把土地设为空土地状态

                //铲除枯萎作物经验+2，金钱+10
                dispExp(user.addExp(2));
                dispMoney(user.addMoney(10));
 
            } else {
                //弹出提示，是否把当前土地上作物铲掉
                int i = JOptionPane.showConfirmDialog(null, "当前土地还有作物，是否铲除？", "提示", JOptionPane.YES_NO_OPTION);

                if (i == 0) {   //选择是
                    worm.reset(landMouseAt);
                    glass.reset(landMouseAt);
                    cropsArray[landMouseAt].setIcon(new ImageIcon(""));
                    lands[landMouseAt].clear();  //把土地设为空土地状态
                    
                }
            }
        }
    }

    //使用化肥的鼠标点击事件
    public void fertAction(Object obj){

        Tools tool=(Tools)obj;
//        System.out.println(tool.getToolsPicture().substring(0, 6)+"2.png");
        lblMouse.setIcon(new ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/cursor/"+tool.getToolsPicture().substring(0, 6)+"2.png")));
        this.setImgUrl("/com/icss/happyfarm/imag/cursor/"+tool.getToolsPicture());
        TimerRun();


        String id=tool.getToolsId();
        int subTime=0;

        if(id.equals("T1")){
            subTime=10;
        }else if(id.equals("T2")){
            subTime=25;
        }else{
            subTime=55;
        }

        if(landMouseAt!=-1 && landMouseAt<landNext && lands[landMouseAt].isGrown()){
            if(!lands[landMouseAt].cropIsMature()&& !lands[landMouseAt].cropIsDead()){
                if(!lands[landMouseAt].isFert()){
                    lands[landMouseAt].fertLand(subTime);
                    myBag.useTool();
                }
            }
        }



    }


    //在土地上点击，鼠标图标动作，种下相应作物
    public void growAction(int cropId) {
        //在已开垦土地上，并且这块土地未种作物
        if (landMouseAt != -1 && landMouseAt < landNext && !lands[landMouseAt].isGrown()) {
            try {

                String sqlStr = "select * from crop where cropId="+cropId;
                ResultSet rs = util.execute(sqlStr);

                if (rs.next()) {
                    //在鼠标所指的土地上种植作物
                    lands[landMouseAt].growCrop(new Crop(user,rs, cropsArray[landMouseAt], lblTime, landMouseAt, lblHealth, lands, glass, worm));

                    lands[landMouseAt].getCrop().start();
 
                    //种个作物经验+3，金钱+10
                    dispExp(user.addExp(3));
                    dispMoney(user.addMoney(10));

                }
                util.close();

                myBag.useSeed();
            } catch (SQLException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //在土地上点击除草剂的动作
    public void cleanGlassAction() {
        lblMouse.setIcon(new ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/cursor/chucaoji2.png")));
        this.setImgUrl("/com/icss/happyfarm/imag/cursor/chucaoji1.png");
        TimerRun();

        if (landMouseAt != -1 && landMouseAt<landNext &&lands[landMouseAt].hasGlass()) {
            glass.weed(landMouseAt);

            dispExp(user.addExp(2));
            dispMoney(user.addMoney(10));
        }

    }

    //在土地上点击杀虫剂的动作
    public void killWormAction() {
        lblMouse.setIcon(new ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/cursor/shachongji2.png")));
        this.setImgUrl("/com/icss/happyfarm/imag/cursor/shachongji1.png");
        TimerRun();

        if (landMouseAt != -1 && landMouseAt<landNext && lands[landMouseAt].hasWorm()) {
            worm.kill(landMouseAt);

            dispExp(user.addExp(2));
            dispMoney(user.addMoney(10));
        }
    }

    //在土地上点击浇水的动作
    public void waterAction() {
        lblMouse.setIcon(new ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/cursor/shuihu2.png")));
        this.setImgUrl("/com/icss/happyfarm/imag/cursor/shuihu.png");
        TimerRun();

        if(landMouseAt!=-1 && landMouseAt<landNext &&lands[landMouseAt].isDry()){

            lands[landMouseAt].nomalLand();

            dispExp(user.addExp(2));
            dispMoney(user.addMoney(10));
        }
    }
    
    
    //收获作物
    public void getHarvAction() {
        lblMouse.setIcon(new ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/cursor/hand02.png")));
        this.setImgUrl("/com/icss/happyfarm/imag/cursor/hand1.png");
        TimerRun();

        if (landMouseAt != -1 && landMouseAt<landNext && lands[landMouseAt].isGrown()) {
            //判断当前作物是否已成熟
            if (lands[landMouseAt].cropIsMature()&&!lands[landMouseAt].cropIsDead()) {
                //作物收获飞天图片地址
                String flyUrl=lands[landMouseAt].getCrop().getCropUrl();

                //把作物收获
                wh.addToWareHouse(lands[landMouseAt].getCrop());
                lands[landMouseAt].harvest();

                //收获作物获得经验
                dispExp(user.addExp(lands[landMouseAt].getCrop().getCropExp()));
                //dispMoney(user.addMoney(10));

                cropHarve[landMouseAt].setIcon(new ImageIcon(getClass().getResource(flyUrl)));
                
                FlyThread ft = new FlyThread(cropHarve[landMouseAt]);
                ft.start();
            }
        }
    }
    
    //一键全收
    public void getAllHarvAction() {
        lblMouse.setIcon(new ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/cursor/handa2.png")));
        this.setImgUrl("/com/icss/happyfarm/imag/cursor/handa1.png");
        TimerRun();

        //鼠标在土地标签上并且这块土地种植了作物
        if (landMouseAt != -1 && landMouseAt<landNext && lands[landMouseAt].isGrown()) {
            
            if (lands[landMouseAt].cropIsMature()&&!lands[landMouseAt].cropIsDead()) {    //判断当前作物是否已成熟

                for (int i = 0; i < landNext; i++) {
                    if (!lands[i].isGrown()) {
                        continue;
                    
                    } else if (lands[i].cropIsMature()&&!lands[i].cropIsDead()) {
                        String flyUrl = lands[i].getCrop().getCropUrl();

                        //收获作物
                        wh.addToWareHouse(lands[i].getCrop());
                        lands[i].harvest();
                        
                        //收获作物获得10经验
                        dispExp(user.addExp(10));

                        cropHarve[i].setIcon(new ImageIcon(getClass().getResource(flyUrl)));

                        FlyThread ft = new FlyThread(cropHarve[i]);
                        ft.start();
                    }
                }
            }
        }
    }
    
    //开垦新土地
    public void digLandAction() {
        //将待开垦土地翻开，设置下块土地为待开垦
        if (user.getUserLevel() >= 5 + 2 * (landNext - 6)) {   //5级可以开垦第一块地，每2级可以多开垦一块

            if (landNext < 18 && user.getUserMoney() >= 10000 * (landNext - 5)) {    //还有土地可以开垦,并且金钱足够
                lands[landMouseAt]=Land.creatLand(landsArray[landMouseAt],user.getUserId(),landMouseAt);

                //开垦成功，扣除金钱
                dispMoney( user.minMoney(10000 * (landNext - 5)));

                JOptionPane.showMessageDialog(rootPane,
                        "恭喜您花费金币:" + 10000 * (landNext - 5) + "，开垦了一块土地!",
                        "提示信息", JOptionPane.ERROR_MESSAGE);

                landNext++;

                //土地还没有被开垦完
                if (landNext != 18) {
                    landsArray[landNext].setIcon(new ImageIcon(getClass().
                            getResource("/com/icss/happyfarm/imag/bg/kuojian.png")));
                }
            } else {  //金钱不够
                JOptionPane.showMessageDialog(rootPane,
                        "您当前金币:" + user.getUserMoney() + "不足，花费" +
                        10000 * (landNext - 5) + "金币，可以开垦下一块土地",
                        "提示信息", JOptionPane.ERROR_MESSAGE);
            }

        } else if (landMouseAt != -1 && landMouseAt == landNext &&
                user.getUserLevel() < 5 + 2 * (landNext - 6)) {  //等级不够

            JOptionPane.showMessageDialog(rootPane, "当您达到" + (5 + 2 * (landNext - 6)) + "等级时，" +
                    "花费" + 10000 * (landNext - 5) + "金币，可以开垦下一块土地",
                    "提示信息", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * 通过两点式转换的公式判断点与直线的位置关系
     * @param x 当前点横坐标
     * @param y 当前点纵坐标
     * @param x1 直线上一点横坐标
     * @param y1 直线上一点纵坐标
     * @param x2 直线上另一点横坐标
     * @param y2 直线上另一点纵坐标
     * @return 点在直线下方返回1，在直线上方返回-1，在直线上返回0
     */
    public int positionJudge(int x, int y, int x1, int y1, int x2, int y2) {
        if ((y - y1 - (x - x1) * (y2 - y1) / (x2 - x1)) > 0) {
            return 1;
        } else if ((y - y1 - (x - x1) * (y2 - y1) / (x2 - x1)) < 0) {
            return -1;
        } else {
            return 0;
        }
    }
    
    /**
     * 判断当前鼠标指针位于哪块土地上，返回土地号，否则返回-1
     * @param x 当前鼠标位置横坐标
     * @param y 当前鼠标位置纵坐标
    */
    public int landJudge(int x, int y) {
        int m = 251;    //第一块土地标签顶点横坐标
        int n = 285;    //第一块土地标签顶点纵坐标

        for (int j = 0; j < 18; j++) {
            //菱形的四个顶点坐标
            int x1 = m + 100, x2 = m, x3 = m + 100, x4 = m + 200;
            int y1 = n, y2 = n + 49, y3 = n + 98, y4 = n + 49;

            //判断当前鼠标坐标是否在土地菱形四条边之内
            if (positionJudge(x, y, x1, y1, x2, y2) > 0 &&
                    positionJudge(x, y, x2, y2, x3, y3) < 0 &&
                    positionJudge(x, y, x3, y3, x4, y4) < 0 &&
                    positionJudge(x, y, x4, y4, x1, y1) > 0) {

                xPos=m;
                yPos=n;
                return j;
            } else {
                //下块土地坐标
                if ((j + 1) % 3 == 0) {
                    m += 300;
                    n -= 49;

                } else {
                    m -= 100;
                    n += 49;

                }
            }
        }

        return -1;
    }

    //初始化标签数组，在窗口上按序排列
    public void initLabel(JLabel[] array,int x,int y,int width,int height,int index){
        //初始化土地数组
        for (int i = 0; i < 18; i++) {

            array[i] = new JLabel();
            
            array[i].setBounds(x, y, width, height);

            //从右上往左下排列土地标签，每排3个换行继续从右上开始
            if ((i + 1) % 3 == 0) {
                x = x + 300;
                y = y - 49;
            } else {
                x = x - 100;
                y = y + 49;
            }

            //在分层窗格上添加土地标签并设置相应优先级
            jLayeredPane1.add(array[i], Integer.valueOf(index+i));
        }
    }

    //初始化用户信息
    public void initUser() {
        lblName.setText(user.getUserName());
        lblLevel.setText("" + user.getUserLevel());
        lblExp.setText(user.getUserExp() + "/" + user.getNextExp());
        lblMoney.setText("" + user.getUserMoney());
        lblPhoto.setIcon(new ImageIcon(getClass().getResource(user.getUserPicture())));
        pbLevelBar.setMaximum(user.getNextExp());
        pbLevelBar.setValue(user.getUserExp());
    }

    //初始化用户开垦的土地，种植的作物
    public void initLand() {
        String sql = "select * from land where userId=" + user.getUserId();

        ResultSet landInfo = util.execute(sql);   //获得用户所有开垦土地信息

        try {
            while (landInfo.next()) {
                //初始化一块土地
                lands[landNext] = new Land(landsArray[landNext], user.getUserId(), landNext);

                int cropId = landInfo.getInt("cropId");

                //如果该块土地上种植了作物
                if (cropId != 0) {
                    //作物种植在土地上的时刻
                    Date growTime = landInfo.getTimestamp("growDate");
                    int currentSeason=landInfo.getInt("currentSeason");

                    //作物种植后经过的时间
                    int passTime = (int) ((new Date().getTime() - growTime.getTime()) / 1000);

                    ResultSet cropInfo = util.execute("select * from crop where cropId=" + cropId);

                    if (cropInfo.next()) {
                        //新建作物对象
                        Crop crop = new Crop(user, cropInfo, cropsArray[landNext],
                                lblTime, landNext, lblHealth, lands, glass, worm);
                        crop.setCurrentSeason(currentSeason);

                        if(currentSeason!=1){
                            passTime+=crop.getPerTime() * (crop.getCropGrade() - 2);
                        }

                        //作物总生长阶段
                        int grade = crop.getCropGrade();

                        int totalTime = crop.getCropGrowTime(); //作物成熟需要的时间

                        if (passTime >= totalTime) {  //经过时间超过了成熟需要时间
                            int heath = landInfo.getInt("cropHealth");
                            crop.setHealth(heath);      //设置作物健康度

                            if (crop.isDead()) {    //查询出健康度为0，证明作物已枯萎
                                crop.die();         //把作物设置为枯萎状态
                                lands[landNext].createCrop(crop);

                            } else {    //作物处于成熟状态
                                if(crop.isLastSeason()){
                                    crop.setCurrentGrade(grade+1);
                                }else{
                                    crop.setCurrentGrade(grade);
                                }
                                lands[landNext].createCrop(crop);
                                crop.start();
                            }
                        } else {    //作物处于成长阶段
                            //通过已种时间计算作物当前阶段
                            crop.setCurrentGrade(passTime / crop.getPerTime() + 1);

                            //计算当前阶段到下一阶段的剩余时间
                            crop.setLeftTime((totalTime - passTime) % crop.getPerTime());
//          System.out.println("作物当前阶段:"+crop.getCurrentGrade());
                            crop.changePicture();   //设置为当前成长阶段图片
                            lands[landNext].createCrop(crop);   //土地上初始化作物
                            crop.start();

                            int landStatue = landInfo.getInt("landStatue");
                            int landWorm = landInfo.getInt("landWorm");
                            int landGlass = landInfo.getInt("landGlass");
                            lands[landNext].setLandGlass(landGlass);
                            lands[landNext].setLandWorm(landWorm);

                            if (landWorm > 0) { //设置土地上虫子数
                                worm.init(landWorm, landNext);
                            }
                            if (landGlass > 0) {    //设置土地上草数量
                                glass.init(landGlass, landNext);
                            }

                            if (landStatue == 1) {  //土地干旱
                                lands[landNext].dryLand();
                            } else if (landStatue == 2) {   //土地已施肥
                                //lands[landNext].fertLand();
                            }
                        }

                    }
                }

                landNext++; //下块土地
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        util.close();
    }

    /** Creates new form MainFrame */
    public MainFrame(ResultSet userInfo) {
        initComponents();

        //初始化土地数组
        initLabel(landsArray,land_x,land_y,200,98,25);
        for(int i=0;i<18;i++){
            landsArray[i].setIcon(new ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/fieldg.png")));
        }


        initLabel(cropsArray,crop_x,crop_y,200,120,27);

        land_x=251;land_y=285;
        initLabel(cropHarve,land_x+33,land_y-112,119,119,50);

        land_x=251;land_y=285;
        initLabel(glassArray,land_x,land_y,200,98,26);

        initLabel(wormArray,260,230,200,120,30);
        

        lblBg.setSize(155, 67);
        lblBg.setIcon(new ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/cropinfo.png")));
        pnlBg.setBounds(200,100,155, 67);

        lblHealth.setBounds(50, 25, 100, 27);
        lblHealth.setForeground(new Color(250,0,0));

        lblTime.setBounds(5,0,155, 40);
        lblTime.setFont(new java.awt.Font("华文彩云", 1, 17));
        lblTime.setForeground(new java.awt.Color(0, 51, 102));
        lblTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        pnlBg.add(lblTime,2);
        pnlBg.add(lblBg,1);
        pnlBg.add(lblHealth,JLayeredPane.POPUP_LAYER);

        jLayeredPane1.add(pnlBg, JLayeredPane.DRAG_LAYER);
        pnlBg.setVisible(false);

        
        user = new User(userInfo);
        initUser();

        wh=new WareHouse(user);

        //初始化天气线程
        weather=new Weather(lblWeather,lands);
        weather.start();

        //初始化土地干旱线程
        DroughtLand dryLand=new DroughtLand(landsArray,lands);
        dryLand.start();

        //初始化长草线程
        glass=new Glass(glassArray,lands);
        glass.start();
        
        //初始化长虫线程
        worm=new Worm(wormArray,lands);
        worm.start();

        initLand(); //初始化用户开垦的土地，作物

        //设置待扩建土地标签
        if(landNext<18)
        landsArray[landNext].setIcon(new ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/kuojian.png")));

        //设置窗口标题栏图标
        Toolkit tk=Toolkit.getDefaultToolkit();
        Image image=tk.createImage(getClass().getResource("/com/icss/happyfarm/imag/bg/biaoti.jpg"));
        this.setIconImage(image);

        //自定义鼠标指针
        Image hand_img=tk.createImage(getClass().getResource("/com/icss/happyfarm/imag/bg/mouse.png"));
        hand=tk.createCustomCursor(hand_img,new Point(0,0),"MY_CURSOR");
        setCursor(hand);

        initFrame();

        myBag = new BagPanel(user, MainFrame.this);
        jLayeredPane1.add(myBag, JLayeredPane.POPUP_LAYER);

        FriendMainPane fmp=new FriendMainPane(user);
        jLayeredPane1.add(fmp,JLayeredPane.DRAG_LAYER);
    }

    public void initFrame() {
            //如果点击的是已装饰的告示牌
        try {
            Decorate usedDecorate = null; //保存已经装饰的告示牌

            String used = "select d.decorateId,decorateName,decoratePrice," +
                    "decoratePicture,decorateType from t_decorate d inner join t_goods g " +
                    "on g.decorateId=d.decorateId where decorateIsUsed=1 and userId="+user.getUserId();    //查询出已经装饰的装饰
            ResultSet rs = util.execute(used);
            while (rs.next()) {
                usedDecorate = new Decorate(rs.getString("decorateId"), rs.getString("decoratePicture"), rs.getInt("decoratePrice"));
                usedDecorate.setDecorateName(rs.getString("decorateName"));
                usedDecorate.setDecorateType(rs.getInt("decorateType"));

                if(usedDecorate.getDecorateType()==0) {
                    lblMain.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + usedDecorate.getDecorateName() + "大图.png")));
                } else if(usedDecorate.getDecorateType()==1) {
                    lblHouseDecorate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + usedDecorate.getDecorateName() + "大图.png")));
                } else if(usedDecorate.getDecorateType()==2) {
                    lblDogHouse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + usedDecorate.getDecorateName() + "大图.png")));
                } else if(usedDecorate.getDecorateType()==3) {
                    lblColumn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + usedDecorate.getDecorateName() + "大图.png")));
                } else if(usedDecorate.getDecorateType()==4) {
                    lblNotice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + usedDecorate.getDecorateName() + "大图.png")));
                }  else if(usedDecorate.getDecorateType()==5) {
                    lblDog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/" + usedDecorate.getDecorateName() + "大图.png")));
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    class MyTask extends TimerTask {

        @Override
        public void run() {
            lblMouse.setIcon(new ImageIcon(getClass().getResource(imgUrl)));
        }
    }

    //启动定时器，延迟200MS
    public void TimerRun() {
        Timer t = new Timer();
        t.schedule(new MyTask(), 200);
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
        lblMouse = new javax.swing.JLabel();
        lblNotice = new javax.swing.JLabel();
        lblArrow = new javax.swing.JLabel();
        lblSpade = new javax.swing.JLabel();
        lblBag = new javax.swing.JLabel();
        lblWater = new javax.swing.JLabel();
        lblGrass = new javax.swing.JLabel();
        lblHand = new javax.swing.JLabel();
        lblWorm = new javax.swing.JLabel();
        lblHandAll = new javax.swing.JLabel();
        lblMyFarm = new javax.swing.JLabel();
        lblShop = new javax.swing.JLabel();
        lblWareHouse = new javax.swing.JLabel();
        lblDecorate = new javax.swing.JLabel();
        lblBottomBG = new javax.swing.JLabel();
        lblWeather = new javax.swing.JLabel();
        lblPhoto = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        lblJinBi = new javax.swing.JLabel();
        lblMoney = new javax.swing.JLabel();
        lblExp = new javax.swing.JLabel();
        lblLevel = new javax.swing.JLabel();
        lblLevelBg = new javax.swing.JLabel();
        lblTopBG = new javax.swing.JLabel();
        pbLevelBar = new javax.swing.JProgressBar();
        lblDog = new javax.swing.JLabel();
        lblDogHouse = new javax.swing.JLabel();
        lblColumn = new javax.swing.JLabel();
        lblHouseDecorate = new javax.swing.JLabel();
        lblMain = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("开心农场 RTM2.00058");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        jLayeredPane1.setAlignmentX(0.0F);
        jLayeredPane1.setAlignmentY(0.0F);
        jLayeredPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLayeredPane1MouseClicked(evt);
            }
        });
        jLayeredPane1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLayeredPane1MouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLayeredPane1MouseMoved(evt);
            }
        });
        lblMouse.setBounds(50, 430, 110, 110);
        jLayeredPane1.add(lblMouse, javax.swing.JLayeredPane.POPUP_LAYER);

        lblNotice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/告示牌4大图.png"))); // NOI18N
        lblNotice.setBounds(70, 190, 140, 130);
        jLayeredPane1.add(lblNotice, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblArrow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/Arrow1.png"))); // NOI18N
        lblArrow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblArrowMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bigArrow(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                nomalArrow(evt);
            }
        });
        lblArrow.setBounds(30, 690, 45, 42);
        jLayeredPane1.add(lblArrow, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblSpade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/spade.png"))); // NOI18N
        lblSpade.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSpadeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSpadeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSpadeMouseExited(evt);
            }
        });
        lblSpade.setBounds(80, 690, 47, 45);
        jLayeredPane1.add(lblSpade, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblBag.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/bag1.png"))); // NOI18N
        lblBag.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBagMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblBagMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblBagMouseExited(evt);
            }
        });
        lblBag.setBounds(130, 690, 45, 43);
        jLayeredPane1.add(lblBag, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblWater.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/water1.png"))); // NOI18N
        lblWater.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblWaterMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblWaterMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblWaterMouseExited(evt);
            }
        });
        lblWater.setBounds(170, 690, 60, 43);
        jLayeredPane1.add(lblWater, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblGrass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/grass1.png"))); // NOI18N
        lblGrass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblGrassMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblGrassMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblGrassMouseExited(evt);
            }
        });
        lblGrass.setBounds(230, 690, 48, 44);
        jLayeredPane1.add(lblGrass, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblHand.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/hand1.png"))); // NOI18N
        lblHand.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHandMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblHandMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblHandMouseExited(evt);
            }
        });
        lblHand.setBounds(330, 690, 55, 44);
        jLayeredPane1.add(lblHand, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblWorm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/worm.png"))); // NOI18N
        lblWorm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblWormMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblWormMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblWormMouseExited(evt);
            }
        });
        lblWorm.setBounds(280, 690, 47, 45);
        jLayeredPane1.add(lblWorm, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblHandAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/handAll1.png"))); // NOI18N
        lblHandAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHandAllMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblHandAllMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblHandAllMouseExited(evt);
            }
        });
        lblHandAll.setBounds(390, 690, 62, 44);
        jLayeredPane1.add(lblHandAll, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblMyFarm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/myfarm.png"))); // NOI18N
        lblMyFarm.setToolTipText("我的农场");
        lblMyFarm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblMyFarmMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblMyFarmMouseExited(evt);
            }
        });
        lblMyFarm.setBounds(710, 20, 62, 50);
        jLayeredPane1.add(lblMyFarm, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblShop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/shop.png"))); // NOI18N
        lblShop.setToolTipText("农场商店(来这里买种子)");
        lblShop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblShopMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblShopMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblShopMouseExited(evt);
            }
        });
        lblShop.setBounds(850, 20, 56, 50);
        jLayeredPane1.add(lblShop, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblWareHouse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/storehouse.png"))); // NOI18N
        lblWareHouse.setToolTipText("仓库(收获的果子会放在这里)");
        lblWareHouse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblWareHouseMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblWareHouseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblWareHouseMouseExited(evt);
            }
        });
        lblWareHouse.setBounds(780, 20, 50, 50);
        jLayeredPane1.add(lblWareHouse, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblDecorate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/zhuangshi.png"))); // NOI18N
        lblDecorate.setToolTipText("装饰农场");
        lblDecorate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDecorateMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblDecorateMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblDecorateMouseExited(evt);
            }
        });
        lblDecorate.setBounds(920, 20, 47, 50);
        jLayeredPane1.add(lblDecorate, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblBottomBG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/menubg2.png"))); // NOI18N
        lblBottomBG.setText("jLabel1");
        lblBottomBG.setBounds(30, 680, 420, 70);
        jLayeredPane1.add(lblBottomBG, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblWeather.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/sun.png"))); // NOI18N
        lblWeather.setToolTipText("晴天,有可能长虫、长草或干旱。");
        lblWeather.setBounds(460, 20, 65, 60);
        jLayeredPane1.add(lblWeather, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblPhoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/photo/0.png"))); // NOI18N
        lblPhoto.setBounds(20, 10, 80, 80);
        jLayeredPane1.add(lblPhoto, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblName.setFont(new java.awt.Font("宋体", 1, 14));
        lblName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblName.setText("wangzhiqun");
        lblName.setBounds(120, 10, 100, 20);
        jLayeredPane1.add(lblName, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblJinBi.setFont(new java.awt.Font("宋体", 0, 14));
        lblJinBi.setForeground(new java.awt.Color(204, 0, 0));
        lblJinBi.setText("金币");
        lblJinBi.setBounds(120, 70, 30, 20);
        jLayeredPane1.add(lblJinBi, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblMoney.setFont(new java.awt.Font("宋体", 1, 14));
        lblMoney.setForeground(new java.awt.Color(255, 51, 51));
        lblMoney.setText("1000");
        lblMoney.setBounds(160, 70, 70, 20);
        jLayeredPane1.add(lblMoney, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblExp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblExp.setText("688/1000");
        lblExp.setBounds(130, 40, 90, 15);
        jLayeredPane1.add(lblExp, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblLevel.setFont(new java.awt.Font("微软雅黑", 1, 12));
        lblLevel.setForeground(new java.awt.Color(0, 130, 209));
        lblLevel.setText("12");
        lblLevel.setBounds(250, 40, 20, 10);
        jLayeredPane1.add(lblLevel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblLevelBg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/levelbg.png"))); // NOI18N
        lblLevelBg.setBounds(240, 30, 32, 30);
        jLayeredPane1.add(lblLevelBg, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblTopBG.setFont(new java.awt.Font("Arial", 0, 12));
        lblTopBG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/topbg.png"))); // NOI18N
        lblTopBG.setBounds(0, 0, 1000, 100);
        jLayeredPane1.add(lblTopBG, javax.swing.JLayeredPane.DEFAULT_LAYER);

        pbLevelBar.setBackground(new java.awt.Color(255, 255, 255));
        pbLevelBar.setForeground(new java.awt.Color(0, 80, 255));
        pbLevelBar.setBounds(110, 38, 120, 20);
        jLayeredPane1.add(pbLevelBar, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblDog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/狗叫.gif"))); // NOI18N
        lblDog.setToolTipText("您的宠物，农场的守卫者");
        lblDog.setBounds(50, 210, 490, 80);
        jLayeredPane1.add(lblDog, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblDogHouse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/幽静狗舍大图.png"))); // NOI18N
        lblDogHouse.setBounds(450, 180, 80, 70);
        jLayeredPane1.add(lblDogHouse, javax.swing.JLayeredPane.MODAL_LAYER);
        lblColumn.setBounds(370, 140, 600, 270);
        jLayeredPane1.add(lblColumn, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblHouseDecorate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/房子1大图.png"))); // NOI18N
        lblHouseDecorate.setBounds(540, 120, 260, 200);
        jLayeredPane1.add(lblHouseDecorate, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblMain.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/background.jpg"))); // NOI18N
        lblMain.setAlignmentY(0.0F);
        lblMain.setBounds(0, 0, 1008, 781);
        jLayeredPane1.add(lblMain, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1008, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 781, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-1008)/2, (screenSize.height-781)/2, 1008, 781);
    }// </editor-fold>//GEN-END:initComponents

    private void bigArrow(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bigArrow
        // TODO add your handling code here:
        lblArrow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/arrow2B.png")));
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_bigArrow

    private void nomalArrow(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nomalArrow
        // TODO add your handling code here:
        lblArrow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/Arrow1.png")));
        setCursor(hand);
    }//GEN-LAST:event_nomalArrow

    private void lblSpadeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSpadeMouseEntered
        // TODO add your handling code here:
        lblSpade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/spade2B.png")));
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_lblSpadeMouseEntered

    private void lblSpadeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSpadeMouseExited
        // TODO add your handling code here:
        lblSpade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/spade.png")));
        setCursor(hand);
    }//GEN-LAST:event_lblSpadeMouseExited

    private void lblBagMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBagMouseEntered
        // TODO add your handling code here:
        lblBag.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/bagB.png")));
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_lblBagMouseEntered

    private void lblBagMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBagMouseExited
        // TODO add your handling code here:
        lblBag.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/bag1.png")));
        setCursor(hand);
    }//GEN-LAST:event_lblBagMouseExited

    private void lblWaterMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblWaterMouseEntered
        // TODO add your handling code here:
        lblWater.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/waterB.png")));
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_lblWaterMouseEntered

    private void lblWaterMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblWaterMouseExited
        // TODO add your handling code here:
        lblWater.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/water1.png")));
        setCursor(hand);
    }//GEN-LAST:event_lblWaterMouseExited

    private void lblGrassMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGrassMouseEntered
        // TODO add your handling code here:
        lblGrass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/grassB.png")));
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_lblGrassMouseEntered

    private void lblGrassMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGrassMouseExited
        // TODO add your handling code here:
        lblGrass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/grass1.png")));
        setCursor(hand);
    }//GEN-LAST:event_lblGrassMouseExited

    private void lblWormMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblWormMouseEntered
        // TODO add your handling code here:
        lblWorm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/wormB.png")));
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_lblWormMouseEntered

    private void lblWormMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblWormMouseExited
        // TODO add your handling code here:
        lblWorm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/worm.png")));
        setCursor(hand);
    }//GEN-LAST:event_lblWormMouseExited

    private void lblHandMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHandMouseEntered
        // TODO add your handling code here:
        lblHand.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/handB.png")));
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_lblHandMouseEntered

    private void lblHandMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHandMouseExited
        // TODO add your handling code here:
        lblHand.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/hand1.png")));
        setCursor(hand);
    }//GEN-LAST:event_lblHandMouseExited

    private void lblHandAllMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHandAllMouseEntered
        // TODO add your handling code here:
        lblHandAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/handAllB.png")));
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_lblHandAllMouseEntered

    private void lblHandAllMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHandAllMouseExited
        // TODO add your handling code here:
        lblHandAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/bg/handAll1.png")));
        setCursor(hand);
    }//GEN-LAST:event_lblHandAllMouseExited

    private void lblMyFarmMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMyFarmMouseEntered
        // TODO add your handling code here:
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_lblMyFarmMouseEntered

    private void lblMyFarmMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMyFarmMouseExited
        // TODO add your handling code here:
        setCursor(hand);
    }//GEN-LAST:event_lblMyFarmMouseExited

    private void lblWareHouseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblWareHouseMouseExited
        // TODO add your handling code here:
        setCursor(hand);
    }//GEN-LAST:event_lblWareHouseMouseExited

    private void lblWareHouseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblWareHouseMouseEntered
        // TODO add your handling code here:
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_lblWareHouseMouseEntered

    private void lblShopMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblShopMouseEntered
        // TODO add your handling code here:
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_lblShopMouseEntered

    private void lblShopMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblShopMouseExited
        // TODO add your handling code here:
        setCursor(hand);
    }//GEN-LAST:event_lblShopMouseExited

    private void lblDecorateMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDecorateMouseEntered
        // TODO add your handling code here:
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_lblDecorateMouseEntered

    private void lblDecorateMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDecorateMouseExited
        // TODO add your handling code here:
        setCursor(hand);
    }//GEN-LAST:event_lblDecorateMouseExited

    private void jLayeredPane1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLayeredPane1MouseMoved
        // TODO add your handling code here:
        lblMouse.setLocation(evt.getX() + 10, evt.getY() - 80);

        int i = landJudge(evt.getX(), evt.getY());

        if (i < landNext && i != -1) {   //鼠标在已开垦土地上

            landMouseAt = i;
            pnlBg.setLocation(xPos + 29, yPos - 122);
            pnlBg.setVisible(false);
            //当前土地上有作物，则把鼠标在的土地号传给作物
            if (lands[i].isGrown()) {
                //System.out.println(i);
                Crop.setLandNum(i);
                pnlBg.setVisible(true);

                if (lands[i].cropIsDead()){
                    lblTime.setText(" 可以铲除！");
                    lblHealth.setText("");
                }else if (lands[i].cropIsMature()) {
                    lblTime.setText("可采摘：" + lands[i].getCrop().getFactOutput());
                    lblHealth.setText("");
                }

            } else {
                Crop.setLandNum(100);
            }
            setCursor(hand);
        } else if (i == landNext) { //在待开垦土地上
            landMouseAt = i;
            pnlBg.setVisible(false);
            Crop.setLandNum(-1);
            setCursor(new Cursor(java.awt.Cursor.HAND_CURSOR));

        } else {
            landMouseAt = i;
            pnlBg.setVisible(false);
            Crop.setLandNum(-1);
            setCursor(hand);
        }

    }//GEN-LAST:event_jLayeredPane1MouseMoved

    private void lblSpadeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSpadeMouseClicked
        // TODO add your handling code here:
        if(cursorTag==1){
            lblArrowMouseClicked(evt);
        }else{
            lblMouse.setIcon(new ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/cursor/spade.png")));
            this.setCursorTag(1);
        }
    }//GEN-LAST:event_lblSpadeMouseClicked

    private void jLayeredPane1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLayeredPane1MouseDragged
        // TODO add your handling code here:
        lblMouse.setLocation(evt.getX() + 10, evt.getY() - 80);
    }//GEN-LAST:event_jLayeredPane1MouseDragged

    private void lblWaterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblWaterMouseClicked
        // TODO add your handling code here:
        if(cursorTag==3){
            lblArrowMouseClicked(evt);
        }else{
            lblMouse.setIcon(new ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/cursor/shuihu.png")));
            this.setCursorTag(3);
        }
    }//GEN-LAST:event_lblWaterMouseClicked

    private void lblGrassMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGrassMouseClicked
        // TODO add your handling code here:
        if(cursorTag==4){
            lblArrowMouseClicked(evt);
        }else{
            lblMouse.setIcon(new ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/cursor/chucaoji1.png")));
            this.setCursorTag(4);
        }
    }//GEN-LAST:event_lblGrassMouseClicked

    private void lblHandMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHandMouseClicked
        // TODO add your handling code here:
        if(cursorTag==6){
            lblArrowMouseClicked(evt);
        }else{
            lblMouse.setIcon(new ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/cursor/hand1.png")));
            this.setCursorTag(6);
        }
    }//GEN-LAST:event_lblHandMouseClicked

    private void lblArrowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblArrowMouseClicked
        // TODO add your handling code here:
        lblMouse.setIcon(new ImageIcon(getClass().getResource("")));
        this.setCursorTag(0);
    }//GEN-LAST:event_lblArrowMouseClicked

    private void lblWormMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblWormMouseClicked
        // TODO add your handling code here:
        if(cursorTag==5){
            lblArrowMouseClicked(evt);
        }else{
            lblMouse.setIcon(new ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/cursor/shachongji1.png")));
            this.setCursorTag(5);
        }
    }//GEN-LAST:event_lblWormMouseClicked

    private void jLayeredPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLayeredPane1MouseClicked
        try {

            // TODO add your handling code here:
            ClickPerform();
        } catch (InterruptedException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLayeredPane1MouseClicked

    private void lblBagMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBagMouseClicked
        // TODO add your handling code here:        
        if (!myBag.isVisible()) {
            myBag.open();
             this.setCursorTag(2);
        } else {
            myBag.close();
            this.setCursorTag(0);
        }
       
    }//GEN-LAST:event_lblBagMouseClicked

    private void lblHandAllMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHandAllMouseClicked
        // TODO add your handling code here:
        if(cursorTag==7){
            cursorTag=0;
            lblMouse.setIcon(new ImageIcon());
        }else{
            lblMouse.setIcon(new ImageIcon(getClass().getResource("/com/icss/happyfarm/imag/cursor/handa1.png")));
            this.setCursorTag(7);
        }
    }//GEN-LAST:event_lblHandAllMouseClicked

    private void lblWareHouseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblWareHouseMouseClicked
        // TODO add your handling code here:
        new WareHouseDialog(MainFrame.this,true,user);
    }//GEN-LAST:event_lblWareHouseMouseClicked

    private void lblShopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblShopMouseClicked
        // TODO add your handling code here:
        new ShoppingDialog(MainFrame.this,true,user);
    }//GEN-LAST:event_lblShopMouseClicked

    private void lblDecorateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDecorateMouseClicked
        // TODO add your handling code here:
        new MyDecorateDialog(MainFrame.this,true,user);
    }//GEN-LAST:event_lblDecorateMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLabel lblArrow;
    private javax.swing.JLabel lblBag;
    private javax.swing.JLabel lblBottomBG;
    private javax.swing.JLabel lblColumn;
    private javax.swing.JLabel lblDecorate;
    private javax.swing.JLabel lblDog;
    private javax.swing.JLabel lblDogHouse;
    private javax.swing.JLabel lblExp;
    private javax.swing.JLabel lblGrass;
    private javax.swing.JLabel lblHand;
    private javax.swing.JLabel lblHandAll;
    private javax.swing.JLabel lblHouseDecorate;
    private javax.swing.JLabel lblJinBi;
    private javax.swing.JLabel lblLevel;
    private javax.swing.JLabel lblLevelBg;
    private javax.swing.JLabel lblMain;
    private javax.swing.JLabel lblMoney;
    private javax.swing.JLabel lblMouse;
    private javax.swing.JLabel lblMyFarm;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblNotice;
    private javax.swing.JLabel lblPhoto;
    private javax.swing.JLabel lblShop;
    private javax.swing.JLabel lblSpade;
    private javax.swing.JLabel lblTopBG;
    private javax.swing.JLabel lblWareHouse;
    private javax.swing.JLabel lblWater;
    private javax.swing.JLabel lblWeather;
    private javax.swing.JLabel lblWorm;
    private javax.swing.JProgressBar pbLevelBar;
    // End of variables declaration//GEN-END:variables
public JLabel getLblDogHouse() {
        return lblDogHouse;
    }

    public JLabel getLblHouseDecorate() {
        return lblHouseDecorate;
    }

    public JLabel getLblMain() {
        return lblMain;
    }

    public JLabel getLblNotice() {
        return lblNotice;
    }

    public JLabel getLblMoney() {
        return lblMoney;
    }

    public JLabel getLblColumn() {
        return lblColumn;
    }

    public JLabel getLblDog() {
        return lblDog;
    }

    public BagPanel getMyBag() {
        return myBag;
    }

    public JLabel getLblMouse(){
        return lblMouse;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Object getObj() {
        return obj;
    }

    public WareHouse getWh() {
        return wh;
    }

    public void setWh(WareHouse wh) {
        this.wh = wh;
    }

    public int getCursorTag() {
        return cursorTag;
    }


}
