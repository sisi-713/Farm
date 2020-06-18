/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NewJDialog.java
 *
 * 
 */

package com.icss.happyfarm.ui;

/**
 *
 * @author Administrator
 */
public class SaleFruitResultDialog extends javax.swing.JDialog {

    static private WareHouseDialog parent;
    private static int quantity;
    private static int money;
    private static String name;
    /** Creates new form NewJDialog */
    public SaleFruitResultDialog(WareHouseDialog parent, boolean modal,int quantity,int money,String name) {
        super(parent, modal);
        SaleFruitResultDialog.quantity = quantity;
        SaleFruitResultDialog.money = money;
        SaleFruitResultDialog.name = name;
        this.setLocation((parent.getWidth() - this.getWidth()) / 2 + 228, (parent.getHeight() - this.getHeight()) / 2 + 290);
        initComponents();
        lblNotice.setText("成功卖出"+quantity+"个"+name+",  获得"+money+"个金币！");
        this.setResizable(false);
        this.setVisible(true);
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
        lblNotice = new javax.swing.JLabel();
        lblTrue = new javax.swing.JLabel();
        lblBack = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblNotice.setForeground(new java.awt.Color(255, 0, 153));
        lblNotice.setBounds(50, 110, 220, 30);
        jLayeredPane1.add(lblNotice, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblTrue.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/fruit/确定按钮.png"))); // NOI18N
        lblTrue.setText("jLabel2");
        lblTrue.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTrueMouseClicked(evt);
            }
        });
        lblTrue.setBounds(130, 150, 70, 30);
        jLayeredPane1.add(lblTrue, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/fruit/提示.png"))); // NOI18N
        lblBack.setText("jLabel1");
        lblBack.setBounds(0, 0, 320, 190);
        jLayeredPane1.add(lblBack, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblTrueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTrueMouseClicked
        this.dispose();
    }//GEN-LAST:event_lblTrueMouseClicked

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SaleFruitResultDialog dialog = new SaleFruitResultDialog(parent, true,quantity,money,name);
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
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLabel lblBack;
    private javax.swing.JLabel lblNotice;
    private javax.swing.JLabel lblTrue;
    // End of variables declaration//GEN-END:variables

}
