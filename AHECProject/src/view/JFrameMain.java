package view;

import ahecproject.*;
import java.awt.Color;
import java.awt.Cursor;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.*;

import java.awt.Cursor.*;

/**
 *
 * @author Michał Pochopień @ the hungry beavers
 */
public class JFrameMain extends javax.swing.JFrame {

    JFreeChart ratioChart;
    XYSeries ratioSeries;
    int counter = 0;
    double lastRatio = 0.0, minimum = 0.0, max = 22.0;
    Thread runnable;

    /**
     * Creates new formonitor JFramonitoreMain
     */
    public JFrameMain() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    System.out.println("NIMBUS!!!!");
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        initComponents();
        this.showGraph();
        
        jPanelAddUser.setVisible(false);
        jPanelComputations.setVisible(false);
        jPanelSettings.setVisible(false);
        
        jLabelCurrentUser.setText(AHECProject.monitor.getCurrentUser());
        jLabelLogOut.setText("<HTML><U>Log Out</U></HTML>");
        jLabelLogOut.setCursor(new Cursor(Cursor.HAND_CURSOR));

        runnable = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);
                        JFrameMain.this.refreshGraph();
                        JFrameMain.this.refreshIndicators();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(JFrameMain.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };

        runnable.start();
    }

    /**
     * This monitorethod is called fromonitor within the constructor to initialize the formonitor.
     * WARNING: Do NOT monitorodify this code. The content of this monitorethod is always
    regenerated by the Formonitor Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelHeader = new javax.swing.JPanel();
        jLabelLogo = new javax.swing.JLabel();
        jButtonComputationsPanel = new javax.swing.JButton();
        jButtonSettingsPanel = new javax.swing.JButton();
        jButtonAddUserPanel = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabelCurrentUser = new javax.swing.JLabel();
        jLabelLogOut = new javax.swing.JLabel();
        jLayeredPaneMain = new javax.swing.JLayeredPane();
        jPanelAddUser = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPaneLogin = new javax.swing.JTextPane();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPasswordFieldPassword = new javax.swing.JPasswordField();
        jPasswordFieldPasswordRepeat = new javax.swing.JPasswordField();
        jLabel7 = new javax.swing.JLabel();
        jLabelAddUserInfo = new javax.swing.JLabel();
        jPanelComputations = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButtonLiftStop = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButtonDragStop = new javax.swing.JButton();
        jButtonOptimiserStop = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabelLiftIndicator = new javax.swing.JLabel();
        jLabelDragIndicator = new javax.swing.JLabel();
        jLabelOptimiserIndicator = new javax.swing.JLabel();
        jPanelSettings = new javax.swing.JPanel();
        jButtonAutomatic = new javax.swing.JButton();
        jButtonManual = new javax.swing.JButton();
        jButtonReactivate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelHeader.setBackground(new java.awt.Color(-12829897,true));

        jLabelLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/logo.png"))); // NOI18N

        jButtonComputationsPanel.setBackground(new java.awt.Color(-12829897,true));
        jButtonComputationsPanel.setForeground(new java.awt.Color(-1,true));
        jButtonComputationsPanel.setText("Computations");
        jButtonComputationsPanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonComputationsPanelActionPerformed(evt);
            }
        });

        jButtonSettingsPanel.setBackground(new java.awt.Color(-12829897,true));
        jButtonSettingsPanel.setForeground(new java.awt.Color(-1,true));
        jButtonSettingsPanel.setText("Settings");
        jButtonSettingsPanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSettingsPanelActionPerformed(evt);
            }
        });

        jButtonAddUserPanel.setBackground(new java.awt.Color(-12829897,true));
        jButtonAddUserPanel.setForeground(new java.awt.Color(-1,true));
        jButtonAddUserPanel.setText("Add User");
        jButtonAddUserPanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddUserPanelActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(-1,true));
        jLabel4.setText("Logged as:");

        jLabelCurrentUser.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabelCurrentUser.setForeground(new java.awt.Color(-14336,true));
        jLabelCurrentUser.setText(" ");

        jLabelLogOut.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabelLogOut.setForeground(new java.awt.Color(-14336,true));
        jLabelLogOut.setText("Log Out");
        jLabelLogOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelLogOutMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelHeaderLayout = new javax.swing.GroupLayout(jPanelHeader);
        jPanelHeader.setLayout(jPanelHeaderLayout);
        jPanelHeaderLayout.setHorizontalGroup(
            jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHeaderLayout.createSequentialGroup()
                .addGroup(jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelHeaderLayout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addComponent(jButtonComputationsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonSettingsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelHeaderLayout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jLabelLogo)))
                .addGroup(jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelHeaderLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jButtonAddUserPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(141, Short.MAX_VALUE))
                    .addGroup(jPanelHeaderLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelHeaderLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabelLogOut, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabelCurrentUser, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24))))
        );
        jPanelHeaderLayout.setVerticalGroup(
            jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHeaderLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelLogo)
                    .addGroup(jPanelHeaderLayout.createSequentialGroup()
                        .addGroup(jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabelCurrentUser))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelLogOut)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonComputationsPanel)
                    .addComponent(jButtonSettingsPanel)
                    .addComponent(jButtonAddUserPanel)))
        );

        jLayeredPaneMain.setBackground(new java.awt.Color(-1118482,true));
        jLayeredPaneMain.setForeground(new java.awt.Color(-1118482,true));

        jPanelAddUser.setBackground(new java.awt.Color(-1118482,true));
        jPanelAddUser.setBorder(null);
        jPanelAddUser.setForeground(new java.awt.Color(-1118482,true));

        jScrollPane1.setViewportView(jTextPaneLogin);

        jLabel5.setText("login");

        jLabel6.setText("password");

        jButton1.setText("Add user");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel7.setText("repeat");

        jLabelAddUserInfo.setText(" ");

        javax.swing.GroupLayout jPanelAddUserLayout = new javax.swing.GroupLayout(jPanelAddUser);
        jPanelAddUser.setLayout(jPanelAddUserLayout);
        jPanelAddUserLayout.setHorizontalGroup(
            jPanelAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAddUserLayout.createSequentialGroup()
                .addGroup(jPanelAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAddUserLayout.createSequentialGroup()
                        .addGap(196, 196, 196)
                        .addGroup(jPanelAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                            .addComponent(jPasswordFieldPassword, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPasswordFieldPasswordRepeat, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addGroup(jPanelAddUserLayout.createSequentialGroup()
                        .addGap(179, 179, 179)
                        .addComponent(jLabelAddUserInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(142, Short.MAX_VALUE))
        );
        jPanelAddUserLayout.setVerticalGroup(
            jPanelAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAddUserLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabelAddUserInfo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanelAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jPasswordFieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordFieldPasswordRepeat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(146, Short.MAX_VALUE))
        );

        jPanelAddUser.setBounds(0, 0, 720, 340);
        jLayeredPaneMain.add(jPanelAddUser, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanelComputations.setBackground(new java.awt.Color(-1118482,true));
        jPanelComputations.setForeground(new java.awt.Color(-1118482,true));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14));
        jLabel1.setText("Lift solver");

        jButtonLiftStop.setFont(new java.awt.Font("SansSerif", 1, 14));
        jButtonLiftStop.setForeground(new java.awt.Color(-65536,true));
        jButtonLiftStop.setText("STOP");
        jButtonLiftStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLiftStopActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 14));
        jLabel2.setText("Drag solver");

        jButtonDragStop.setFont(new java.awt.Font("SansSerif", 1, 14));
        jButtonDragStop.setForeground(new java.awt.Color(-65536,true));
        jButtonDragStop.setText("STOP");
        jButtonDragStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDragStopActionPerformed(evt);
            }
        });

        jButtonOptimiserStop.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jButtonOptimiserStop.setForeground(new java.awt.Color(-65536,true));
        jButtonOptimiserStop.setText("STOP");
        jButtonOptimiserStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOptimiserStopActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel3.setText("Optimiser");

        jLabelLiftIndicator.setFont(new java.awt.Font("SansSerif", 1, 14));
        jLabelLiftIndicator.setText(" ");

        jLabelDragIndicator.setFont(new java.awt.Font("SansSerif", 1, 14));
        jLabelDragIndicator.setText(" ");

        jLabelOptimiserIndicator.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabelOptimiserIndicator.setText(" ");

        javax.swing.GroupLayout jPanelComputationsLayout = new javax.swing.GroupLayout(jPanelComputations);
        jPanelComputations.setLayout(jPanelComputationsLayout);
        jPanelComputationsLayout.setHorizontalGroup(
            jPanelComputationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelComputationsLayout.createSequentialGroup()
                .addGap(293, 293, 293)
                .addGroup(jPanelComputationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonDragStop, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelComputationsLayout.createSequentialGroup()
                        .addGroup(jPanelComputationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonLiftStop, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelComputationsLayout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabelLiftIndicator, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(135, 135, 135))
                    .addGroup(jPanelComputationsLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelDragIndicator, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelComputationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelComputationsLayout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelOptimiserIndicator, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jButtonOptimiserStop, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(485, 485, 485))
        );
        jPanelComputationsLayout.setVerticalGroup(
            jPanelComputationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelComputationsLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanelComputationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelLiftIndicator))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonLiftStop, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelComputationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabelDragIndicator))
                .addGap(9, 9, 9)
                .addComponent(jButtonDragStop, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanelComputationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelComputationsLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel3))
                    .addGroup(jPanelComputationsLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabelOptimiserIndicator)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonOptimiserStop, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanelComputations.setBounds(0, 0, 720, 340);
        jLayeredPaneMain.add(jPanelComputations, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanelSettings.setBackground(new java.awt.Color(-1118482,true));
        jPanelSettings.setBorder(null);
        jPanelSettings.setForeground(new java.awt.Color(-1118482,true));

        jButtonAutomatic.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButtonAutomatic.setText("Automatic");
        jButtonAutomatic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAutomaticActionPerformed(evt);
            }
        });

        jButtonManual.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButtonManual.setText("Manual");
        jButtonManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonManualActionPerformed(evt);
            }
        });

        jButtonReactivate.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButtonReactivate.setText("Reactivate");
        jButtonReactivate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReactivateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelSettingsLayout = new javax.swing.GroupLayout(jPanelSettings);
        jPanelSettings.setLayout(jPanelSettingsLayout);
        jPanelSettingsLayout.setHorizontalGroup(
            jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSettingsLayout.createSequentialGroup()
                .addGap(157, 157, 157)
                .addGroup(jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonManual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonAutomatic, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
                .addGap(53, 53, 53)
                .addComponent(jButtonReactivate, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(230, Short.MAX_VALUE))
        );
        jPanelSettingsLayout.setVerticalGroup(
            jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSettingsLayout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addGroup(jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelSettingsLayout.createSequentialGroup()
                        .addComponent(jButtonAutomatic, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonManual, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButtonReactivate, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(147, Short.MAX_VALUE))
        );

        jPanelSettings.setBounds(0, 0, 720, 340);
        jLayeredPaneMain.add(jPanelSettings, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelHeader, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLayeredPaneMain, javax.swing.GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLayeredPaneMain, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jButtonLiftStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLiftStopActionPerformed
    AHECProject.liftSolver.interrupt();
}//GEN-LAST:event_jButtonLiftStopActionPerformed

private void jButtonDragStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDragStopActionPerformed
    AHECProject.dragSolver.interrupt();
}//GEN-LAST:event_jButtonDragStopActionPerformed

private void jButtonOptimiserStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOptimiserStopActionPerformed
    AHECProject.optimiser.interrupt();
}//GEN-LAST:event_jButtonOptimiserStopActionPerformed

private void jButtonAutomaticActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAutomaticActionPerformed
    AHECProject.monitor.setAutomaticMode();
}//GEN-LAST:event_jButtonAutomaticActionPerformed

private void jButtonManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonManualActionPerformed
    AHECProject.monitor.setManualMode();
}//GEN-LAST:event_jButtonManualActionPerformed

private void jButtonReactivateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReactivateActionPerformed
    AHECProject.monitor.reactivate();
}//GEN-LAST:event_jButtonReactivateActionPerformed

private void jButtonComputationsPanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonComputationsPanelActionPerformed
    jPanelComputations.setVisible(true);
    jPanelAddUser.setVisible(false);
    jPanelSettings.setVisible(false);
}//GEN-LAST:event_jButtonComputationsPanelActionPerformed

private void jButtonSettingsPanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSettingsPanelActionPerformed
    jPanelComputations.setVisible(false);
    jPanelAddUser.setVisible(false);
    jPanelSettings.setVisible(true);
}//GEN-LAST:event_jButtonSettingsPanelActionPerformed

private void jButtonAddUserPanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddUserPanelActionPerformed
    jPanelComputations.setVisible(false);
    jPanelAddUser.setVisible(true);
    jPanelSettings.setVisible(false);
}//GEN-LAST:event_jButtonAddUserPanelActionPerformed

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    //password validation
    String errorMessage = "";
    if (jTextPaneLogin.getText().length() < 4)
        errorMessage = "Login needs to be at least 4 characters long";
    
    if (!jPasswordFieldPassword.getText().equals(jPasswordFieldPasswordRepeat.getText()))
        errorMessage = "Password and password repeat are different";
    
    if (jPasswordFieldPassword.getPassword().length < 6)
         errorMessage = "Password needs to be at least 6 characters long";
        
    if (errorMessage.isEmpty()) {
        if (AHECProject.dbManager.insertUser(jTextPaneLogin.getText(), jPasswordFieldPassword.getText())) {
            jLabelAddUserInfo.setText("User added");
            jLabelAddUserInfo.setForeground(Color.green);
            jTextPaneLogin.setText("");
        } else {
            jLabelAddUserInfo.setText("User with such login already exists");
        }
        
    } else  {
        jLabelAddUserInfo.setText(errorMessage);
        jLabelAddUserInfo.setForeground(Color.red);
    }
     //cleaning the password boxes
     jPasswordFieldPassword.setText("");
     jPasswordFieldPasswordRepeat.setText("");
}//GEN-LAST:event_jButton1ActionPerformed

private void jLabelLogOutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelLogOutMouseClicked
    this.setVisible(false);
    chartFrame.setVisible(false);
    AHECProject.loginFrame.setVisible(true);
    AHECProject.monitor.logout();
    jLabelCurrentUser.setText("Logged out");
}//GEN-LAST:event_jLabelLogOutMouseClicked

    private void refreshIndicators() {
        if (AHECProject.monitor.isAutoMode()) {
            jButtonAutomatic.setBackground(Color.orange);
            jButtonManual.setBackground(Color.lightGray);
            jButtonReactivate.setEnabled(false);;
        } else {
            jButtonAutomatic.setBackground(Color.lightGray);
            jButtonManual.setBackground(Color.orange);
            jButtonReactivate.setEnabled(true);
        }

        if (AHECProject.liftSolver.isAlive()) {
            setON(jLabelLiftIndicator);
        } else {
            setOFF(jLabelLiftIndicator);
        }

        if (AHECProject.dragSolver.isAlive()) {
            setON(jLabelDragIndicator);
        } else {
            setOFF(jLabelDragIndicator);
        }

        if (AHECProject.optimiser.isAlive()) {
            setON(jLabelOptimiserIndicator);
        } else {
            setOFF(jLabelOptimiserIndicator);
        }
       
        jPanelComputations.repaint();
    }

    private void setOFF(javax.swing.JLabel label) {
        label.setText("OFF");
        label.setForeground(Color.red);
    }

    private void setON(javax.swing.JLabel label) {
        label.setText("ON");
        label.setForeground(Color.green);
    }

    private void showGraph() {
        ratioSeries = new XYSeries("Lift/Drag");
        ratioSeries.add(0.0, 0.0);
        XYDataset xyDataset = new XYSeriesCollection(ratioSeries);

        ratioChart = ChartFactory.createXYLineChart("Lift/Drag ratio", "Cycle", "Ratio",
                xyDataset, PlotOrientation.VERTICAL, true, true, false);

        chartFrame = new ChartFrame("Ratio", ratioChart);
        chartFrame.setVisible(true);
        chartFrame.setSize(400, 400);
    }

    /**
     * @param args the comonitormonitorand line argumonitorents
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        System.out.println("TRY!!!!");
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    System.out.println("NIMBUS!!!!");
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new JFrameMain().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonAddUserPanel;
    private javax.swing.JButton jButtonAutomatic;
    private javax.swing.JButton jButtonComputationsPanel;
    private javax.swing.JButton jButtonDragStop;
    private javax.swing.JButton jButtonLiftStop;
    private javax.swing.JButton jButtonManual;
    private javax.swing.JButton jButtonOptimiserStop;
    private javax.swing.JButton jButtonReactivate;
    private javax.swing.JButton jButtonSettingsPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelAddUserInfo;
    private javax.swing.JLabel jLabelCurrentUser;
    private javax.swing.JLabel jLabelDragIndicator;
    private javax.swing.JLabel jLabelLiftIndicator;
    private javax.swing.JLabel jLabelLogOut;
    private javax.swing.JLabel jLabelLogo;
    private javax.swing.JLabel jLabelOptimiserIndicator;
    private javax.swing.JLayeredPane jLayeredPaneMain;
    private javax.swing.JPanel jPanelAddUser;
    private javax.swing.JPanel jPanelComputations;
    private javax.swing.JPanel jPanelHeader;
    private javax.swing.JPanel jPanelSettings;
    private javax.swing.JPasswordField jPasswordFieldPassword;
    private javax.swing.JPasswordField jPasswordFieldPasswordRepeat;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPaneLogin;
    // End of variables declaration//GEN-END:variables

    
    ChartFrame chartFrame;
            
    private void refreshGraph() {
        //refresh graph
        if (ratioSeries != null) {
            double ratio = AHECProject.optimiser.getLift() / AHECProject.optimiser.getDrag();
            if (ratio != lastRatio && AHECProject.optimiser.getDrag() != 0.0) {
                counter++;
                if (ratio < minimum) {
                    ratioSeries.add(counter, ratio * 0.3 + minimum);
                } else {
                    ratioSeries.add(counter, ratio);
                }
                lastRatio = ratio;
                //20s -> counter=60
                if (minimum < max) {
                    double x = counter;
                    minimum = 547.0 / 6930000 * (x + 45) * (x + 45) * (x + 45) - 35279.0 / 1386000 * (x + 45) * (x + 45) + 129629.0 / 46200 * x + 11677.0 / 280;
                    System.out.println("counter=" + counter + ", min=" + minimum);
                }
            }
        }
    }
}
