/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ShoppingDialog.java
 *
 * 
 */

package com.icss.happyfarm.ui;

import com.icss.happyfarm.bean.Decorate;

import com.icss.happyfarm.bean.Seed;
import com.icss.happyfarm.bean.Tools;
import com.icss.happyfarm.stype.User;
import com.icss.happyfarm.util.DbUtil;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import java.awt.*;

/**
 *
 * @author Administrator
 */
public class ShoppingDialog extends javax.swing.JDialog {
    private JLabel lblShopping1;
    static private MainFrame parent;
    static private User user;

    private Map<String,Seed> itemseeds = new HashMap<String,Seed>();
    private Map<String,Decorate> itemdecorates = new HashMap<String,Decorate>();
    private Map<String,Tools> itemtools = new HashMap<String,Tools>();
    private List<JLabel> text = new ArrayList<JLabel>();  //用来金币信息标签
    private List<JLabel> lbltype = new ArrayList<JLabel>();   //用来存储装饰类型

    private Seed seed;
    private Decorate decorate;
    private Tools tool;
    BuySeedDialog buySeed;  //购买种子对话框
    BuyToolDialog buyTool;  //购买道具对话框
    BuyDecorateDialog buyDecorate;     //购买装饰对话框
    private GoodsInfoPanel goodsPanel;     //道具信息提示框

    private JLabel lblClickImage; //用于显示相关的道具信息标签

    DbUtil util=new DbUtil();
    //显示位置常量
    int TOP = 0;   //离顶部的距离
    int LEFT = 0;  //离左边的距离
    int GW = 0;   //商品标签的宽
    int GH = 0;   //商品标签的高
    int TW = 0;   //文本标签的宽
    int TH = 0;   //文本标签的高
    int i = 10;
    int j = 0;  //用来记录道具的位置
    int z = 0;  //用来记录垂直位置
    int a = 0;  //用来记录金币的位置
    int c = 0;  //用来显示种子等级位置
    /** Creates new form ShoppingDialog */
    public ShoppingDialog(MainFrame parent, boolean modal,User user) {
        super(parent, modal);
        ShoppingDialog.parent = parent;
        ShoppingDialog.user = user;

        lblClickImage=new JLabel();
        //jShopLayeredPane.add(lblClickImage, JLayeredPane.POPUP_LAYER);
        //toolPanel.setLocation(200,300);

        initComponents();
        lblSeed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/seedPicture/种子1.jpg")));
        this.setLocation((parent.getWidth()-this.getWidth())/2+150, (parent.getHeight()-this.getHeight())/2+150);
        this.setTitle("商店");
        
        String sql = "select * from crop order by seedLevel desc";    //初始页面查询种子信息
        SelectDB(sql,"seeds");

        Toolkit tk=Toolkit.getDefaultToolkit();
        Image hand_img=tk.createImage(getClass().getResource("/com/icss/happyfarm/imag/bg/mouse.png"));
        Cursor hand=tk.createCustomCursor(hand_img,new Point(0,0),"MY_CURSOR");
        setCursor(hand);

        goodsPanel = new GoodsInfoPanel();
        this.setResizable(false);
        this.setVisible(true);
        new Thread(new PaintThread()).start();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jShopLayeredPane = new javax.swing.JLayeredPane();
        lblSeed = new javax.swing.JLabel();
        lblDecorate = new javax.swing.JLabel();
        lblTools = new javax.swing.JLabel();
        lblShopping = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        shoppingList = new javax.swing.JList();
        lblBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblSeed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/seedPicture/种子2.jpg"))); // NOI18N
        lblSeed.setName("seeds"); // NOI18N
        lblSeed.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblButtonMouseClicked(evt);
            }
        });
        lblSeed.setBounds(30, 10, 60, 22);
        jShopLayeredPane.add(lblSeed, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblDecorate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/装饰2.jpg"))); // NOI18N
        lblDecorate.setName("decorate"); // NOI18N
        lblDecorate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblButtonMouseClicked(evt);
            }
        });
        lblDecorate.setBounds(150, 10, 60, 23);
        jShopLayeredPane.add(lblDecorate, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblTools.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/toolsPicture/道具2.jpg"))); // NOI18N
        lblTools.setName("tools"); // NOI18N
        lblTools.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblButtonMouseClicked(evt);
            }
        });
        lblTools.setBounds(90, 10, 60, 23);
        jShopLayeredPane.add(lblTools, javax.swing.JLayeredPane.DEFAULT_LAYER);
        lblShopping.setBounds(20, 50, 120, 120);
        jShopLayeredPane.add(lblShopping, javax.swing.JLayeredPane.DEFAULT_LAYER);

        shoppingList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        shoppingList.setAutoscrolls(false);
        jScrollPane1.setViewportView(shoppingList);

        jScrollPane1.setBounds(0, 50, 500, 260);
        jShopLayeredPane.add(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/seedPicture/商店背景.gif"))); // NOI18N
        lblBackground.setBounds(0, 0, 500, 310);
        jShopLayeredPane.add(lblBackground, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jShopLayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jShopLayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
     class PaintThread implements Runnable {  //实现重画的线程

		public void run() {
			while(true){
				repaint();
				try {
					Thread.sleep(60);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
    //商店功能中，点击相应按钮执行
    private void lblButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblButtonMouseClicked
         String btnStr = ((JLabel)evt.getSource()).getName().trim();
        //当点击的是种子时执行
        if(btnStr.equals("seeds")) {
            lblBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/seedPicture/商店背景.gif")));
            lblSeed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/seedPicture/种子1.jpg")));
            lblTools.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/toolsPicture/道具2.jpg")));
            lblDecorate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/装饰2.jpg")));
            String sql = "select * from crop order by seedLevel desc";
            SelectDB(sql,btnStr);
        //当点击的是道具时执行
        } else if(btnStr.equals("tools")) {
            lblBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/toolsPicture/道具背景.png")));
            lblTools.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/toolsPicture/道具1.jpg")));
            lblSeed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/seedPicture/种子2.jpg")));
            lblDecorate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/装饰2.jpg")));
            String sql = "select * from t_tool order by toolsPrice desc";
            SelectDB(sql,btnStr);
        //当点击的是装饰时执行
        } else {
            //首先设置装饰页面背景
            lblBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/装饰背景.png")));
            
            lblTools.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/toolsPicture/道具2.jpg")));
            lblSeed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/seedPicture/种子2.jpg")));
            lblDecorate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/装饰1.jpg")));
            String sql = "select * from t_decorate order by decorateId desc";
            SelectDB(sql,btnStr);
        }
    }//GEN-LAST:event_lblButtonMouseClicked
    
    //根据点击的不同，查询出不同的结果
    public void SelectDB(String sql,String btnStr) {
         ResultSet rs = util.execute(sql);
         DefaultListModel listModel = new DefaultListModel();
         try {
            while (rs.next()) {
                if(btnStr.equals("seeds")) {
                    //构造种子对象
                    int cropId = rs.getInt("cropId");
                    seed = new Seed(cropId,rs.getInt("seedPrice"),rs.getString("seedPicture"));
                    seed.setSeedName(rs.getString("cropName"));
                    seed.setExperience(rs.getInt("experience"));
                    seed.setHarvestTime(rs.getInt("cropGrowTime"));
                    seed.setExpectQuantity(rs.getInt("expectQuantity"));
                    seed.setExpectHarvest(rs.getInt("fruitPrice")*rs.getInt("expectQuantity"));
                    seed.setFruitPrice(rs.getInt("fruitPrice"));
                    seed.setLevel(rs.getInt("seedLevel"));
                    seed.setCropSeason(rs.getInt("cropSeason"));
                    seed.setExplain(rs.getString("explain"));

                    itemseeds.put(cropId+"", seed);   //将查询出的种子添加到hashmap中去

                    //清空装饰里面的内容
                    for(Iterator iter=itemdecorates.entrySet().iterator();iter.hasNext();) {
                        Decorate item = (Decorate)((Entry)iter.next()).getValue();
                        jShopLayeredPane.remove(item);
                    }

                     //清空道具里面的内容
                    for(Iterator iter=itemtools.entrySet().iterator();iter.hasNext();) {
                        Tools item = (Tools)((Entry)iter.next()).getValue();
                        jShopLayeredPane.remove(item);
                    }
                     //清空文本内容
                    for(Iterator iter=text.iterator();iter.hasNext();) {
                        JLabel lbl = (JLabel)iter.next();
                        jShopLayeredPane.remove(lbl);
                    }
                    //清空装饰类型信息
                   for(Iterator iter=lbltype.iterator();iter.hasNext();) {
                        JLabel lbl = (JLabel)iter.next();
                        jShopLayeredPane.remove(lbl);
                    }
                }  else if(btnStr.equals("tools")) {
                    String toolsId = rs.getString("toolsId");
                    tool = new Tools(toolsId,rs.getString("toolsName"),rs.getInt("toolsPrice"),rs.getString("toolsPicture"));
                    tool.setToolsType(rs.getInt("toolsType"));
                    tool.setExplain(rs.getString("explain"));

                    itemtools.put(toolsId, tool);   //将查询出的种子添加到hashmap中去

                    //清空装饰里面的内容
                    for(Iterator iter=itemdecorates.entrySet().iterator();iter.hasNext();) {
                        Decorate item = (Decorate)((Entry)iter.next()).getValue();
                        jShopLayeredPane.remove(item);
                    }
                   //清空文本内容
                    for(Iterator iter=text.iterator();iter.hasNext();) {
                        JLabel lbl = (JLabel)iter.next();
                        jShopLayeredPane.remove(lbl);
                    }

                     //清空种子里面的内容
                    for(Iterator iter=itemseeds.entrySet().iterator();iter.hasNext();) {
                        Seed item = (Seed)((Entry)iter.next()).getValue();
                        jShopLayeredPane.remove(item);
                    }
                      //清空装饰类型信息
                   for(Iterator iter=lbltype.iterator();iter.hasNext();) {
                        JLabel lbl = (JLabel)iter.next();
                        jShopLayeredPane.remove(lbl);
                    }
                     
                }else {
                    decorate =  new Decorate(rs.getString("decorateId"),rs.getString("decoratePicture"),rs.getInt("decoratePrice"));
                    decorate.setDecorateName(rs.getString("decorateName"));
                    decorate.setDecorateType(rs.getInt("decorateType"));
                    itemdecorates.put(rs.getString("decorateId"), decorate);    //将查询出的装饰添加到hashmap中去

                    //清空种子里面的内容
                    for(Iterator iter=itemseeds.entrySet().iterator();iter.hasNext();) {
                        Seed item = (Seed)((Entry)iter.next()).getValue();
                        jShopLayeredPane.remove(item);
                    }
                    //清空道具里面的内容
                    for(Iterator iter=itemtools.entrySet().iterator();iter.hasNext();) {
                        Tools item = (Tools)((Entry)iter.next()).getValue();
                        jShopLayeredPane.remove(item);
                    }
                    //清空文本内容
                    for(Iterator iter=text.iterator();iter.hasNext();) {
                        JLabel lbl = (JLabel)iter.next();
                        jShopLayeredPane.remove(lbl);
                    }
                }
            }
            
            //点击的是种子，调用显示种子的方法
            if(btnStr.equals("seeds")) {
                showSeeds();   
            //点击的是装饰，调用显示装饰的方法
            } else if(btnStr.equals("decorate")) {
                showDecorate();
            } else {
                showTools();
            }

            //将结果添加到滚动框中
            shoppingList.setModel(listModel);

        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            util.close();
        }
    }

    //道具的显示位置
    public void toolLocation(Tools tool,int TOP,int LEFT,int GW,int GH,int TW,int TH) {
       
        tool.setBounds(LEFT+(GW*j++), TOP+(GH+TH)*z, GW, GH);     //种子的显示位置

        JLabel lblText = new JLabel();    //种子所需金币信息
        lblText.setBounds(LEFT+(TW*a++), (TOP+GH)+(GH+TH)*z, TW, TH);  //所需金币的显示位置
        lblText.setForeground(new java.awt.Color(255, 102, 0));
        lblText.setText(" 金币"+tool.getToolsPrice());
        text.add(lblText);     //将lblText添加到List中

        if(tool.getX()>this.getWidth()-50) {       //显示位置超过框架是，换行显示
            j = 0;
            a = 0;
            tool.setBounds(LEFT+(GW*j++), TOP+(GH+TH)*++z, GW, GH);
            lblText.setBounds(LEFT+(TW*a++), (TOP+GH)+(GH+TH)*z, TW, TH);
            lblText.setText(" 金币"+tool.getToolsPrice());
            text.add(lblText);
        }
        //设置seed Label的显示图标
        tool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/toolsPicture/"+tool.getToolsPicture())));
        //System.out.println(seed.getSeedsPicture());
        tool.setName(""+tool.getToolsName());    //将种子的名称设置成 种子标签的Name值
        jShopLayeredPane.add(tool, new Integer(++i));     //将种子标签添加到分层面板中去
        jShopLayeredPane.add(lblText, new Integer(++i));   //将金币显示标签添加到分层面板中去

    }

    public void seedLocation(Seed seed,int TOP,int LEFT,int GW,int GH,int TW,int TH) {
        
        seed.setBounds(LEFT+(GW*j++), TOP+(GH+TH)*z, GW, GH);     //种子的显示位置
        JLabel lblText = new JLabel();    //种子所需金币信息
        lblText.setBounds(LEFT+(TW*a++), (TOP+GH)+(GH+TH)*z, TW, TH);  //所需金币的显示位置
        lblText.setText(" 金币"+seed.getSeedPrice());
        lblText.setForeground(new java.awt.Color(255, 102, 0));
        text.add(lblText);     //将lblText添加到List中

        JLabel lblLevel =  new JLabel();   //种子等级
        lblLevel.setBounds(55+(80*c++), 100+(100*z), 25, 20);  //种子等级的显示位置
        lblLevel.setText(seed.getLevel()+"级");
        lblLevel.setFont(new java.awt.Font("新宋体", 0, 12));
        lblLevel.setForeground(new java.awt.Color(0, 204, 153));
        text.add(lblLevel);

        if(seed.getX()>this.getWidth()-50) {       //显示位置超过框架是，换行显示
            j = 0;
            a = 0;
            c = 0;
            seed.setBounds(LEFT+(GW*j++), TOP+(GH+TH)*++z, GW, GH);
            lblText.setBounds(LEFT+(TW*a++), (TOP+GH)+(GH+TH)*z, TW, TH);
            lblText.setText(" 金币"+seed.getSeedPrice());
            lblLevel.setBounds(55+80*c++, 100+(100*z), 25, 20);
            text.add(lblText);
            text.add(lblLevel);

        }
        //设置seed Label的显示图标
        seed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/seedPicture/"+seed.getSeedsPicture())));
        jShopLayeredPane.add(seed, new Integer(++i));     //将种子标签添加到分层面板中去
        jShopLayeredPane.add(lblText, new Integer(++i));   //将金币显示标签添加到分层面板中去
        jShopLayeredPane.add(lblLevel, new Integer(++i));   //将种子等级显示标签添加到分层面板中去
    }
    public void showSeeds() {
        j = 0;  //用来记录种子的位置
        z = 0;  //用来记录垂直位置
        a = 0;  //用来记录金币的位置
        c = 0;  //用来显示种子等级位置
        //遍历HashMap中的种子
        for(Iterator iter=itemseeds.entrySet().iterator();iter.hasNext();) {
            
                Entry es = (Entry)iter.next();
                seed = (Seed)es.getValue();
                 //调用显示位置方法
                seedLocation(seed,50,18,80,80,80,20);
                
                seed.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                //给种子图标添加事件相应
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        buySeedClicked(evt);
                }
             });
             seed.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
     //System.out.println("进入的是："+((Tools)e.getSource()).getToolsName());
                    Seed seed = (Seed)e.getSource();
                    goodsPanel.setVisible(true);
                    goodsPanel.setLblGoodsNameText(seed.getSeedName());
                    goodsPanel.setOpaque(false);
                    goodsPanel.setLblGoodsPrice("收获："+seed.getExpectHarvest());
                    //Icon icon = tools.getIcon();
                    //toolPanel.setLblBack(lblBackground.getIcon());
                    int width = seed.getX()-15;
                    int height = seed.getY()+90;
                    goodsPanel.setLocation(width,height);
                    jShopLayeredPane.add(goodsPanel, JLayeredPane.POPUP_LAYER);

                }
                @Override
                public void mouseExited(MouseEvent e) {
                    goodsPanel.setVisible(false);
                    jShopLayeredPane.add(goodsPanel, JLayeredPane.POPUP_LAYER);
                }
             });
        }
    }

      public void buySeedClicked(MouseEvent evt) {
        buySeed = new BuySeedDialog(ShoppingDialog.this,true,(Seed)evt.getSource(),parent);
    }

    public void showTools() {
        j = 0;  //用来记录道具的位置
        z = 0;  //用来记录垂直位置
        a = 0;  //用来记录金币的位置
        //遍历HashMap中的道具
        for(Iterator iter=itemtools.entrySet().iterator();iter.hasNext();) {
                //int i = 10;
                Entry es = (Entry)iter.next();
                tool = (Tools)es.getValue();
                //调用显示位置方法
                toolLocation(tool,50,18,100,100,100,20);

                tool.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                //给种子图标添加事件相应
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        buyToolsClicked(evt);
                }

             });
             tool.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    Tools tools = (Tools)e.getSource();
                    goodsPanel.setVisible(true);
                    goodsPanel.setLblGoodsNameText(tools.getToolsName());
                    goodsPanel.setOpaque(false);
                    goodsPanel.setLblGoodsPrice("价格："+tools.getToolsPrice());
 
                    int width = tools.getX()-15;
                    int height = tools.getY()+90;
                    goodsPanel.setLocation(width,height);
                    jShopLayeredPane.add(goodsPanel, JLayeredPane.POPUP_LAYER);
    
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    goodsPanel.setVisible(false);
                    jShopLayeredPane.add(goodsPanel, JLayeredPane.POPUP_LAYER);
                }
             });
        }
    }

     public void buyToolsClicked(MouseEvent evt) {
         buyTool = new BuyToolDialog(ShoppingDialog.this,true,(Tools)evt.getSource(),parent);
    }
  
    public void showDecorate() {
        j = 0;    //用来记录垂直位置
        z = 0;    //用来记录垂直位置
        a = 0;    //用来记录垂直位置
        int b = 0;    //用来记录垂直位置
        int d = 0;
        String[] type = {"背景","房子 ","狗窝","栅栏"};
        for(int e=0;e<type.length;e++) {
             d++;
             JLabel lblDecorateType = new JLabel();    //装饰类型信息
             lblDecorateType.setBounds(35+(120*(d-1)), 60, 120, 20);  //装饰类型信息的显示位置
             lblDecorateType.setText(type[e]);
             lbltype.add(lblDecorateType);
             jShopLayeredPane.add(lblDecorateType, new Integer(++i));   //将装饰类型标签添加到分层面板中去
        }
       
       //
        for(Iterator iter=itemdecorates.entrySet().iterator();iter.hasNext();) {
                Entry es = (Entry)iter.next();
                decorate = (Decorate)es.getValue();
                JLabel lblDecorateText = lblDecorateText = new JLabel();  //装饰所需金币信息
                lblDecorateText.setForeground(new java.awt.Color(255, 102, 0));
                if(decorate.getDecorateType()==0) {
                    decorate.setBounds(20, 70+(110*a), 120, 90);
                    //lblDecorateText = new JLabel();    //装饰所需金币信息
                    lblDecorateText.setBounds(26, 160+(110*a), 120, 20);  //所需金币的显示位置
                    lblDecorateText.setText(" 金币"+decorate.getDecoratePrice());
                    text.add(lblDecorateText);     //将lblDecorateText添加到List中
                    a++;
                }
                if(decorate.getDecorateType()==1) {
                    decorate.setBounds(140, 70+(110*b), 120, 90);
                    //lblDecorateText = new JLabel();    //装饰所需金币信息
                    lblDecorateText.setBounds(145, 160+(110*b), 120, 20);  //所需金币的显示位置
                    lblDecorateText.setText(" 金币"+decorate.getDecoratePrice());
                    text.add(lblDecorateText);     //将lblDecorateText添加到List中
                    b++;
                }
                if(decorate.getDecorateType()==2) {
                    decorate.setBounds(260, 70+(110*j), 120, 90);
                    //lblDecorateText = new JLabel();    //装饰所需金币信息
                    lblDecorateText.setBounds(270, 160+(110*j), 120, 20);  //所需金币的显示位置
                    lblDecorateText.setText(" 金币"+decorate.getDecoratePrice());
                    text.add(lblDecorateText);     //将lblDecorateText添加到List中
                    j++;
                }
                if(decorate.getDecorateType()==3) {
                    decorate.setBounds(380, 70+(110*z), 120, 90);
                    //lblDecorateText = new JLabel();    //装饰所需金币信息
                    lblDecorateText.setBounds(390, 160+(110*z), 120, 20);  //所需金币的显示位置
                    lblDecorateText.setText(" 金币"+decorate.getDecoratePrice());
                    text.add(lblDecorateText);     //将lblDecorateText添加到List中
                    z++;
                }
                
                decorate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/decoratePicture/"+decorate.getDecoratePicture())));
                jShopLayeredPane.add(decorate, new Integer(++i));   //将装饰标签添加到分层面板中去
                jShopLayeredPane.add(lblDecorateText, new Integer(++i));   //将金币显示标签添加到分层面板中去
                

                decorate.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                //给种子图标添加事件相应
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                       buyDecorateClicked(evt);
                }
             });
             decorate.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    Decorate decorate = (Decorate)e.getSource();
                    goodsPanel.setVisible(true);
                    goodsPanel.setLblGoodsNameText(decorate.getDecorateName());
                    goodsPanel.setOpaque(false);
                    goodsPanel.setLblGoodsPrice("价格："+decorate.getDecoratePrice());
                    int width = decorate.getX()-15;
                    int height = decorate.getY()+90;
                    goodsPanel.setLocation(width,height);
                    jShopLayeredPane.add(goodsPanel, JLayeredPane.POPUP_LAYER);

                }
                @Override
                public void mouseExited(MouseEvent e) {
                    goodsPanel.setVisible(false);
                    jShopLayeredPane.add(goodsPanel, JLayeredPane.POPUP_LAYER);
                }
             });
        }
    }
    public void buyDecorateClicked(java.awt.event.MouseEvent evt) {
         buyDecorate = new BuyDecorateDialog(ShoppingDialog.this,true,(Decorate)evt.getSource(),parent,user);
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ShoppingDialog dialog = new ShoppingDialog(parent, true,user);
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLayeredPane jShopLayeredPane;
    private javax.swing.JLabel lblBackground;
    private javax.swing.JLabel lblDecorate;
    private javax.swing.JLabel lblSeed;
    private javax.swing.JLabel lblShopping;
    private javax.swing.JLabel lblTools;
    private javax.swing.JList shoppingList;
    // End of variables declaration//GEN-END:variables

}
