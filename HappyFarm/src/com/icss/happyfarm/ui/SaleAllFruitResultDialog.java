/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SaleAllFruitResultDialog.java
 *
 *
 */

package com.icss.happyfarm.ui;

/**
 *
 * @author Administrator
 */
public class SaleAllFruitResultDialog extends javax.swing.JDialog {

    static SureSaleFruitDialog parent;
    static int totalMoney;
    /** Creates new form SaleAllFruitResultDialog */
    public SaleAllFruitResultDialog(SureSaleFruitDialog parent, boolean modal,int totalMoney) {
        super(parent, modal);
        this.setLocation((parent.getWidth() - this.getWidth()) / 2 + 320, (parent.getHeight() - this.getHeight()) / 2 + 320);
        initComponents();
        lblMoney.setText(""+totalMoney);
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
        lblMoney = new javax.swing.JLabel();
        lblTure = new javax.swing.JLabel();
        lblBack = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblMoney.setFont(new java.awt.Font("宋体", 1, 14));
        lblMoney.setForeground(new java.awt.Color(255, 0, 0));
        lblMoney.setBounds(130, 130, 50, 20);
        jLayeredPane1.add(lblMoney, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblTure.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/fruit/确定按钮.png"))); // NOI18N
        lblTure.setText("jLabel1");
        lblTure.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTureMouseClicked(evt);
            }
        });
        lblTure.setBounds(120, 170, 70, 30);
        jLayeredPane1.add(lblTure, javax.swing.JLayeredPane.DEFAULT_LAYER);

        lblBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icss/happyfarm/images/fruit/卖出仓库中所有果实.gif"))); // NOI18N
        lblBack.setBounds(0, 0, 320, 220);
        jLayeredPane1.add(lblBack, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblTureMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTureMouseClicked
        this.dispose();
    }//GEN-LAST:event_lblTureMouseClicked

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SaleAllFruitResultDialog dialog = new SaleAllFruitResultDialog(parent, true,totalMoney);
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
    private javax.swing.JLabel lblMoney;
    private javax.swing.JLabel lblTure;
    // End of variables declaration//GEN-END:variables

}
