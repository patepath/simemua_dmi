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
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author patipat
 */
public class App {

	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private String msg;

	JSONParser parser;
	JSONObject jsonObj;

	public static double speed;

	public App() {

	}

	private void initConnection() {
		Iterator<String> keys;
		String key;
		boolean isRegiester = false;

		while (true) {

			try {
				socket = new Socket("localhost", 2510);
				out = new PrintWriter(socket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				while (true) {
					if (0 < socket.getInputStream().available()) {
						parser = new JSONParser();
						jsonObj = (JSONObject) parser.parse(in.readLine());

						keys = jsonObj.keySet().iterator();

						while (keys.hasNext()) {
							key = keys.next();

							switch (key) {
								case "speed":
									speed = (double) jsonObj.get("speed");
									break;

								case "atp_brake":
									handleATPBrake();
									break;

								case "non_atp_brake":
									handleNonATPBrake();
									break;

								case "target_distance":
									handleTargetDistance();
									break;

								case "ato_status":
									handleATOStatus();
									break;

								case "atenna_status":
									handleAtennaStatus();
									break;

								case "door_indicator":
									handleDoorIndicator();
									break;

								case "ceiling_speed":
									handleCeilingSpeed();
									break;

								case "door_status":
									handleDoorStatus();
									break;

								case "skipstop_status":
									handleSkipStopStatus();
									break;

							}
						}

					} else if (!isRegiester) { // assign session id
						out.println("SESSIONID=DMI");
						out.flush();
						isRegiester = true;
					}
				}

			} catch (IOException | ParseException ex) {
				Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	private void handleATPBrake() {

	}

	private void handleNonATPBrake() {

	}

	private void handleTargetDistance() {

	}

	private void handleATOStatus() {

	}

	private void handleAtennaStatus() {

	}

	private void handleDoorIndicator() {

	}

	private void handleCeilingSpeed() {

	}

	private void handleDoorStatus() {

	}

	private void handleSkipStopStatus() {

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
