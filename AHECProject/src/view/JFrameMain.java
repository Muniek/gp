package view;

import ahecproject.*;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.*;

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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButtonLiftStop = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButtonDragStop = new javax.swing.JButton();
        jButtonOptimiserStop = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButtonAutomatic = new javax.swing.JButton();
        jButtonManual = new javax.swing.JButton();
        jButtonReactivate = new javax.swing.JButton();
        jLabelLiftIndicator = new javax.swing.JLabel();
        jLabelDragIndicator = new javax.swing.JLabel();
        jLabelOptimiserIndicator = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setText("Lift solver");

        jButtonLiftStop.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jButtonLiftStop.setForeground(new java.awt.Color(-65536,true));
        jButtonLiftStop.setText("STOP");
        jButtonLiftStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLiftStopActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel2.setText("Drag solver");

        jButtonDragStop.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
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

        jLabelLiftIndicator.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabelLiftIndicator.setText(" ");

        jLabelDragIndicator.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabelDragIndicator.setText(" ");

        jLabelOptimiserIndicator.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabelOptimiserIndicator.setText(" ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(28, 28, 28)
                                .addComponent(jLabelLiftIndicator, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButtonLiftStop, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabelDragIndicator, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButtonDragStop, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabelOptimiserIndicator, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButtonOptimiserStop, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(176, 176, 176))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButtonManual, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonAutomatic, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
                        .addGap(61, 61, 61)
                        .addComponent(jButtonReactivate, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonAutomatic, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonManual, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButtonReactivate, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDragIndicator)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabelLiftIndicator)
                    .addComponent(jLabelOptimiserIndicator))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonLiftStop, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonDragStop, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonOptimiserStop, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(-12829897,true));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/logo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jLabel4)
                .addContainerGap(363, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel4)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
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

    private void refreshIndicators() {
        if (AHECProject.monitor.isAutoMode()) {
            jButtonAutomatic.setBackground(Color.orange);
            jButtonManual.setBackground(Color.lightGray);
        }  else  {
            jButtonAutomatic.setBackground(Color.lightGray);
            jButtonManual.setBackground(Color.orange);
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

        jPanel1.repaint();
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

        ChartFrame frame1 = new ChartFrame("Ratio", ratioChart);
        frame1.setVisible(true);
        frame1.setSize(400, 400);
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
    private javax.swing.JButton jButtonAutomatic;
    private javax.swing.JButton jButtonDragStop;
    private javax.swing.JButton jButtonLiftStop;
    private javax.swing.JButton jButtonManual;
    private javax.swing.JButton jButtonOptimiserStop;
    private javax.swing.JButton jButtonReactivate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelDragIndicator;
    private javax.swing.JLabel jLabelLiftIndicator;
    private javax.swing.JLabel jLabelOptimiserIndicator;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables

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
