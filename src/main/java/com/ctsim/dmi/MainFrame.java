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
	private Image atp_status_auto;
	private Image atp_status_mcs;
	private Image atp_status_atb;
	private Image atp_status_yard_sr;
	private Image atp_status_yard_eoa;
	private Image atp_status_line_sr;
	private Image atp_status_rv;
	private Image atenna_yellow;
	private Image atenna_green;
	private Image atenna_fail;
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
	private final int bttnWidth = 90;
	private final int bttnHeight = 70;
	private int click_x, click_y;
	private Calendar now;
	private final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	private final DecimalFormat df = new DecimalFormat("#,###");

	private boolean bttnATB_click = false;
	private boolean bttnAUTO_click = false;
	private boolean bttnMCS_click = false;
	private boolean bttnYARD_click = false;
	private boolean iconATPBreak_click = false;
	private boolean iconNonATPBreak_click = false;

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
			atp_status_auto = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/atp_status_auto.png")));
			atp_status_mcs = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/atp_status_mcs.png")));
			atp_status_atb = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/atp_status_atb.png")));
			atp_status_yard_sr = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/atp_status_yard_sr.png")));
			atp_status_yard_eoa = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/atp_status_yard_eoa.png")));
			atp_status_line_sr = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/atp_status_line_sr.png")));
			atp_status_rv = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/atp_status_rv.png")));
			atenna_yellow = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/atena_yellow.png")));
			atenna_green = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/atena_green.png")));
			atenna_fail = ImageIO.read(FileUtils.toFile(this.getClass().getClassLoader().getResource("img/atena_fail.png")));
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

	private void operationLoop() {

		if (bttnATB_click) {
			App.outQueue.add("TRAIN {\"atp_status\":5}");
			App.atpStatus = 5;
			bttnATB_click = false;

		} else if (bttnAUTO_click) {
			App.outQueue.add("TRAIN {\"atp_status\":4}");
			App.atpStatus = 4;
			bttnAUTO_click = false;

		} else if (bttnMCS_click) {
			App.outQueue.add("TRAIN {\"atp_status\":3}");
			App.atpStatus = 3;
			bttnMCS_click = false;

		} else if (bttnYARD_click) {
			App.outQueue.add("TRAIN {\"atp_status\":1}");
			App.atpStatus = 1;
			bttnYARD_click = false;
		}

		if (App.atpBrake & iconATPBreak_click) {
			App.outQueue.add("TRAIN {\"atp_brake\":false}");
			App.atpBrake = false;
		}
		iconATPBreak_click = false;

		if (App.nonAtpBrake & iconNonATPBreak_click) {
			App.outQueue.add("TRAIN {\"non_atp_brake\":false}");
			App.nonAtpBrake = false;
		}
		iconNonATPBreak_click = false;

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
		drawButtons();
		drawTime();

		drawColorBar();

		g2.setColor(Color.GRAY);
		g2.setFont(new Font("Loma", Font.PLAIN, 12));
		g2.drawString("(" + x + ", " + y + ")", 20, 20);

		operationLoop();
	}

	private void drawBrake() {
		if (App.atpBrake) {
			g2.drawImage(brake_indicator_red, 10, 525, this);
		}

		if (App.nonAtpBrake) {
			g2.drawImage(brake_indicator_yellow, 10, 565, this);
		}
	}

	private void drawTargetDestance() {
		String str = df.format(App.targetDistance) + " m.";
		int strWidth;
		double barHeight;

		g2.drawImage(targetDestination, 30, 90, this);

		g2.setColor(Color.GREEN);
		g2.setFont(new Font("Loma", Font.PLAIN, 18));
		FontMetrics metrics = g2.getFontMetrics();
		strWidth = metrics.stringWidth(str);
		g2.drawString(str, 40 - (strWidth / 2), 70);

		barHeight = (App.targetDistanceActual / App.targetDistance) * 360;
		g2.fillRect(52, (int) (450 - barHeight), 14, (int) barHeight);
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
		g2.setColor(Color.BLACK);
		FontMetrics metrics = g2.getFontMetrics();
		strWidth = metrics.stringWidth(speedShow);

		g2.drawString(String.valueOf(speedShow), 306 - strWidth / 2, 265);
	}

	private void drawCeillingSpeed() {
		int strWidth;
		String speedShow = df.format(App.ceilingSpeed);

		g2.setColor(Color.RED);
		g2.setFont(new Font("Loma", Font.BOLD, 50));
		FontMetrics metrics = g2.getFontMetrics();
		strWidth = metrics.stringWidth(speedShow);

		g2.drawString(String.valueOf(speedShow), 306 - strWidth / 2, 535);

		g2.setColor(Color.GREEN);
		g2.setStroke(new BasicStroke(15));

		int arc = (int) (App.ceilingSpeed * 2.76);
		g2.drawArc(90, 40, 430, 430, 228 - arc, arc);
	}

	private double getPinAngle(double speed) {
		if (speed <= 50) {
			return -140 + (2.8 * speed);
		} else {
			return 2.8 * (speed - 50);
		}
	}

	private void drawATOStatus() {
		Image img;

		if (App.atpStatus == 0) {
			img = ato_status_fail;
		} else {
			img = ato_status_ok;
		}
		g2.drawImage(img, 220, 430, this);
	}

	private void drawAtenna() {
		Image img;

		switch (App.atennaStatus) {

			case 1:
				img = atenna_yellow;
				break;

			case 2:
				img = atenna_green;
				break;

			default:
				img = atenna_fail;
				break;
		}

		g2.drawImage(img, 281, 430, this);
	}

	private void drawATPStatus() {
		Image img;

		switch (App.atpStatus) {
			case 1:
				img = atp_status_yard_sr;
				break;
			case 2:
				img = atp_status_yard_eoa;
				break;
			case 3:
				img = atp_status_mcs;
				break;
			case 4:
				img = atp_status_auto;
				break;
			case 5:
				img = atp_status_atb;
				break;
			case 6:
				img = atp_status_line_sr;
				break;
			default:
				img = atp_status_rv;
		}

		g2.drawImage(img, 450, 430, this);
	}

	private void drawDoorIndicator() {
		Image img;

		switch (App.doorIndicator) {
			case 1:
				img = door_indicator_right;
				break;

			case 2:
				img = door_indicator_left;
				break;

			default:
				img = door_indicator_both;
		}

		g2.drawImage(img, 520, 490, this);
	}

	private void drawDoorStatus() {
		Image img;

		if (App.doorStatus) {
			img = door_status_open;
		} else {
			img = door_status_close;
		}

		g2.drawImage(img, 565, 483, this);
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

	private void drawButtons() {
		int bttn_x = 900;
		int bttn_y = 20;
		g2.setFont(new Font("Loma", Font.BOLD, 25));
		g2.setStroke(new BasicStroke(2));

		drawButton("ATB", 5, bttn_x, bttn_y);
		drawButton("AUTO", 4, bttn_x, bttn_y += 75);
		drawButton("MCS", 3, bttn_x, bttn_y += 75);
		drawButtonYard(bttn_x, bttn_y += 75);

		bttn_y = 575;
		drawButton("Data", 8, bttn_x, bttn_y);
		drawButton("Spec", 9, bttn_x, bttn_y += 75);
	}

	private void drawButton(String name, int status, int x, int y) {

		FontMetrics metrics = g2.getFontMetrics();
		int strWidth = metrics.stringWidth(name);
		int width = 90;
		int height = 70;

		if (App.atpStatus == status) {
			g2.setColor(Color.GREEN);
			g2.fillRect(x, y, width, height);
			g2.setColor(Color.BLACK);

		} else {
			g2.setColor(Color.LIGHT_GRAY);
			g2.drawRect(x, y, width, height);
		}
		g2.drawString(name, x + (width / 2) - strWidth / 2, y + height - 5 - metrics.getHeight() / 2);
	}

	private void drawButtonYard(int x, int y) {
		FontMetrics metrics = g2.getFontMetrics();
		int strWidth = metrics.stringWidth("Yard");

		if (0 < App.atpStatus & App.atpStatus < 3) {
			g2.setColor(Color.GREEN);
			g2.fillRect(x, y, bttnWidth, bttnHeight);
			g2.setColor(Color.BLACK);

		} else {
			g2.setColor(Color.LIGHT_GRAY);
			g2.drawRect(x, y, bttnWidth, bttnHeight);
		}
		g2.drawString("Yard", x + (bttnWidth / 2) - strWidth / 2, y + bttnHeight - 5 - metrics.getHeight() / 2);
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
        viewPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewPanelMouseClicked(evt);
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

	private void checkClickObject() {
		int bttn_x = 900;
		int bttn_y = 20;

		bttnATB_click = click_x >= bttn_x & click_x <= bttn_x + bttnWidth & click_y >= bttn_y & click_y <= bttn_y + bttnHeight;
		bttn_y += 75;
		bttnAUTO_click = click_x >= bttn_x & click_x <= bttn_x + bttnWidth & click_y >= bttn_y & click_y <= bttn_y + bttnHeight;
		bttn_y += 75;
		bttnMCS_click = click_x >= bttn_x & click_x <= bttn_x + bttnWidth & click_y >= bttn_y & click_y <= bttn_y + bttnHeight;
		bttn_y += 75;
		bttnYARD_click = click_x >= bttn_x & click_x <= bttn_x + bttnWidth & click_y >= bttn_y & click_y <= bttn_y + bttnHeight;

		iconATPBreak_click = click_x >= 10 & click_x <= 80 & click_y >= 525 & click_y <= 560;
		iconNonATPBreak_click = click_x >= 10 & click_x <= 80 & click_y >= 565 & click_y <= 600;
	}

    private void viewPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewPanelMouseClicked
		click_x = evt.getX();
		click_y = evt.getY();

		checkClickObject();
    }//GEN-LAST:event_viewPanelMouseClicked

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
			java.util.logging.Logger.getLogger(MainFrame.class
				.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
