/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ctsim.dmi;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Timer;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author patipat
 */
public class MainFrame extends javax.swing.JFrame implements ActionListener {

	private final Timer timer;

	private Image brake_indicator_red;
	private Image brake_indicator_yellow;
	private Image speedoDial;
	private Image speedoPinWhite;
	private Image speedoPinYellow;
	private Image speedoPinRed;
	private Image targetDestination;
	private Image apt_status_auto;
	private Image apt_status_mcs;
	private Image apt_status_atb;
	private Image apt_status_yard_sr;
	private Image apt_status_yard_eoa;
	private Image apt_status_line_sr;
	private Image apt_status_rv;
	private Image atena_yellow;
	private Image atena_green;
	private Image atena_fail;
	private Image ato_status_ok;
	private Image ato_status_fail;
	private Image door_indicator_both;
	private Image door_indicator_left;
	private Image door_indicator_right;
	private Image door_status_close;
	private Image door_status_open;
	private Image scroll_arrow;
	private Graphics2D g2;

	private int x, y;
	private Calendar now;
	private final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	private final DecimalFormat df = new DecimalFormat("#,###");

	/**
	 * Creates new form MainFrame
	 */
	public MainFrame() {
		initComponents();

		timer = new Timer(30, this);
		timer.start();

		try {
			brake_indicator_yellow = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/brake_indicator_yellow.png")));
			brake_indicator_red = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/brake_indicator_red.png")));
			speedoDial = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/speedo_dial.png")));
			speedoPinWhite = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/speedo_pin_white.png")));
			speedoPinYellow = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/speedo_pin_yellow.png")));
			speedoPinRed = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/speedo_pin_red.png")));
			targetDestination = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/target_destination.png")));
			apt_status_auto = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/atp_status_auto.png")));
			apt_status_mcs = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/atp_status_mcs.png")));
			apt_status_atb = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/atp_status_atb.png")));
			apt_status_yard_sr = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/atp_status_yard_sr.png")));
			apt_status_yard_eoa = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/atp_status_yard_eoa.png")));
			apt_status_line_sr = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/atp_status_line_sr.png")));
			apt_status_rv = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/atp_status_rv.png")));
			atena_yellow = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/atena_yellow.png")));
			atena_green = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/atena_green.png")));
			atena_fail = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/atena_fail.png")));
			ato_status_ok = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/ato_status_ok.png")));
			ato_status_fail = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/ato_status_fail.png")));
			door_indicator_both = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/door_indicator_both.png")));
			door_indicator_left = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/door_indicator_left.png")));
			door_indicator_right = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/door_indicator_right.png")));
			door_status_close = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/door_status_close.png")));
			door_status_open = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/door_status_open.png")));
			scroll_arrow = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/scroll_arrow.png")));
		} catch (IOException ex) {
			Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void initGraphics() {
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawImage(speedoDial, 100, 50, this);

		drawBrake();
		drawTargetDestance();
		drawPin();
		drawATOStatus();
		drawAtenna();
		drawATPStatus();
		drawDoorIndicator();
		drawCeillingSpeed();
		drawDoorStatus();
		drawScroll();
		drawSkipStop();
		drawID();
		drawButton();
		drawTime();

		drawColorBar();

		g2.setColor(Color.GRAY);
		g2.setFont(new Font("Loma", Font.PLAIN, 12));
		g2.drawString("(" + x + ", " + y + ")", 20, 20);
	}

	private void drawBrake() {
		g2.drawImage(brake_indicator_red, 10, 525, this);
		g2.drawImage(brake_indicator_yellow, 10, 565, this);
	}

	private void drawTargetDestance() {
		g2.drawImage(targetDestination, 30, 90, this);
	}

	private void drawPin() {

		//double speed = 100 * (double) x / 1013;
		String speedShow = df.format(App.speed);
		int strWidth;

		AffineTransform restore = g2.getTransform();
		AffineTransform trans = new AffineTransform();
		trans.translate(266, 90);
		trans.rotate(Math.toRadians(getPinAngle(App.speed)), 40, 165);
		g2.setTransform(trans);
		g2.drawImage(speedoPinWhite, 0, 0, this);
		g2.setTransform(restore);

		// Draw actual speed
		g2.setFont(new Font("Loma", Font.BOLD, 30));
		FontMetrics metrics = g2.getFontMetrics();
		strWidth = metrics.stringWidth(speedShow);

		g2.drawString(String.valueOf(speedShow), 306 - strWidth / 2, 265);
	}

	private void drawCeillingSpeed() {
		int strWidth;
		String speedShow = "0";

		g2.setColor(Color.RED);
		g2.setFont(new Font("Loma", Font.BOLD, 50));
		FontMetrics metrics = g2.getFontMetrics();
		strWidth = metrics.stringWidth(speedShow);

		g2.drawString(String.valueOf(speedShow), 306 - strWidth / 2, 535);
	}

	private double getPinAngle(double speed) {
		if (speed <= 50) {
			return -140 + (2.8 * speed);
		} else {
			return 2.8 * (speed - 50);
		}
	}

	private void drawATOStatus() {
		g2.drawImage(ato_status_fail, 220, 430, this);
	}

	private void drawAtenna() {
		g2.drawImage(atena_yellow, 281, 430, this);
	}

	private void drawATPStatus() {
		g2.setColor(Color.LIGHT_GRAY);
		g2.setStroke(new BasicStroke(2));

		g2.drawImage(apt_status_mcs, 450, 430, this);
		//g2.drawRect(450, 430, 50, 50);
	}

	private void drawDoorIndicator() {
		g2.drawImage(door_indicator_right, 520, 490, this);
	}

	private void drawDoorStatus() {
		g2.drawImage(door_status_open, 565, 483, this);
	}

	private void drawScroll() {
		g2.drawImage(scroll_arrow, 470, 560, this);
	}

	private void drawSkipStop() {
		g2.setColor(Color.LIGHT_GRAY);
		g2.setFont(new Font("Loma", Font.BOLD, 18));

		g2.drawString("Skip", 520, 560);
		g2.drawString("Stop", 520, 580);
	}

	private void drawButton() {
		g2.setColor(Color.LIGHT_GRAY);
		g2.setStroke(new BasicStroke(2));

		g2.setFont(new Font("Loma", Font.BOLD, 25));
		FontMetrics metrics = g2.getFontMetrics();
		String bttnName = "ATB";
		int strWidth = metrics.stringWidth(bttnName);

		g2.drawRect(900, 20, 90, 70);
		g2.drawString(bttnName, 945 - strWidth / 2, 65);

		bttnName = "AUTO";
		strWidth = metrics.stringWidth(bttnName);
		g2.drawRect(900, 95, 90, 70);
		g2.drawString(bttnName, 945 - strWidth / 2, 140);

		bttnName = "MCS";
		strWidth = metrics.stringWidth(bttnName);
		g2.drawRect(900, 170, 90, 70);
		g2.drawString(bttnName, 945 - strWidth / 2, 215);

		bttnName = "Yard";
		strWidth = metrics.stringWidth(bttnName);
		g2.drawRect(900, 245, 90, 70);
		g2.drawString(bttnName, 945 - strWidth / 2, 290);

		bttnName = "Data";
		strWidth = metrics.stringWidth(bttnName);
		g2.drawRect(900, 575, 90, 70);
		g2.drawString(bttnName, 945 - strWidth / 2, 620);

		bttnName = "Spec";
		strWidth = metrics.stringWidth(bttnName);
		g2.drawRect(900, 650, 90, 70);
		g2.drawString(bttnName, 945 - strWidth / 2, 695);
	}

	private void drawID() {
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Loma", Font.BOLD, 25));

		g2.drawString("Driver no.", 535, 90);
		g2.drawString("000000", 670, 90);

		g2.drawString("Train no.", 535, 130);
		g2.drawString("00068R", 670, 130);
	}

	private void drawTime() {
		now = Calendar.getInstance();

		g2.setColor(Color.LIGHT_GRAY);
		g2.setFont(new Font("Loma", Font.BOLD, 20));
		g2.drawString(sdf.format(now.getTime()), 800, 720);
	}

	private void drawColorBar() {
		g2.setColor(Color.RED);
		g2.fillRect(10, 710, 20, 4);

		g2.setColor(Color.GREEN);
		g2.fillRect(35, 710, 20, 4);

		g2.setColor(Color.BLUE);
		g2.fillRect(60, 710, 20, 4);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        viewPanel = new javax.swing.JPanel(){
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g2 = (Graphics2D) g;
                initGraphics();
            }
        };

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DMI");
        setPreferredSize(new java.awt.Dimension(1024, 768));

        viewPanel.setBackground(new java.awt.Color(30, 30, 30));
        viewPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                viewPanelMouseMoved(evt);
            }
        });

        javax.swing.GroupLayout viewPanelLayout = new javax.swing.GroupLayout(viewPanel);
        viewPanel.setLayout(viewPanelLayout);
        viewPanelLayout.setHorizontalGroup(
            viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        viewPanelLayout.setVerticalGroup(
            viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void viewPanelMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewPanelMouseMoved
		this.x = evt.getX();
		this.y = evt.getY();
    }//GEN-LAST:event_viewPanelMouseMoved

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
		/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(() -> {
			new MainFrame().setVisible(true);
		});
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel viewPanel;
    // End of variables declaration//GEN-END:variables

	@Override
	public void actionPerformed(ActionEvent e) {
		viewPanel.repaint();
	}
}
