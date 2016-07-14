/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ctsim.dmi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author patipat
 */
public class App {

	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private String msg;
	
	public static double speed;

	public App() {

	}

	private void initConnection() {
		boolean isRegiester = false;

		while (true) {

			try {
				socket = new Socket("192.168.1.10", 2510);
				out = new PrintWriter(socket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				while (true) {

					if (0 < socket.getInputStream().available()) {
						msg = in.readLine().toUpperCase().trim().replaceAll(" ", "");
						
						if(msg.split("=")[0].equals("SPEED")) {
							speed = Double.parseDouble(msg.split("=")[1]);
						}
						
						
					} else if (!isRegiester) {
						out.println("DMI");
						out.flush();
						isRegiester = true;
					}

					try {
						Thread.sleep(100);
					} catch (InterruptedException ex) {
						Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
					}
				}

			} catch (IOException ex) {
				Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public void run() {
		initConnection();
	}

	public static void main(String[] args) {
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

		App app = new App();
		app.run();
	}

}
