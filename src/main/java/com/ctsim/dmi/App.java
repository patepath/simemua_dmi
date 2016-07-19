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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
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

	public static BlockingQueue<String> outQueue = new LinkedBlockingQueue<>();

	JSONParser parser;
	JSONObject jsonObj;

	public static double speed;
	public static boolean atpBrake;
	public static boolean nonAtpBrake;
	public static double targetDistance;
	public static double targetDistanceActual;
	public static double ceilingSpeed;
	public static int atpStatus;
	public static int atennaStatus;
	public static int doorIndicator;
	public static boolean doorStatus;
	public static int dwell;
	public static int skipstopStatus;
	public static int mode;

	public App() {

	}

	private void initConnection() {
		Iterator<String> keys;
		String key;
		boolean isRegiester = false;

		parser = new JSONParser();

		try {
			socket = new Socket("localhost", 2510);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			while (true) {
				if (0 < socket.getInputStream().available()) {

					try {
						jsonObj = (JSONObject) parser.parse(in.readLine());
						keys = jsonObj.keySet().iterator();

						while (keys.hasNext()) {
							key = keys.next();

							switch (key) {
								case "speed":
									speed = (double) jsonObj.get("speed");
									break;

								case "atp_brake":
									handleATPBrake((boolean) jsonObj.get("atp_brake"));
									break;

								case "non_atp_brake":
									handleNonATPBrake((boolean) jsonObj.get("non_atp_brake"));
									break;

								case "target_distance":
									handleTargetDistance((double) jsonObj.get("target_distance"));
									break;

								case "target_distance_actual":
									handleTargetDistanceActual((double) jsonObj.get("target_distance_actual"));
									break;

								case "ceiling_speed":
									handleCeilingSpeed((double) jsonObj.get("ceiling_speed"));
									break;

								case "atp_status":
									handleATOStatus((int) (long) jsonObj.get("atp_status"));
									break;

								case "atenna_status":
									handleAtennaStatus((int) (long) jsonObj.get("atenna_status"));
									break;

								case "door_indicator":
									handleDoorIndicator((int) (long) jsonObj.get("door_indicator"));
									break;

								case "door_status":
									handleDoorStatus((boolean) jsonObj.get("door_status"));
									break;

								case "skipstop_status":
									handleSkipStopStatus((int) (long) jsonObj.get("skipstop_status"));
									break;

								case "dwell":
									handleDwell((int) (long) jsonObj.get("dwell"));
									break;

								case "mode":
									handleMode((int) (long) jsonObj.get("mode"));
									break;
							}
						}

					} catch (ParseException ex) {
						Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
					}

				} else if (!isRegiester) { // assign session id
					out.println("SESSIONID=DMI");
					out.flush();
					isRegiester = true;

				} else {
					while (!outQueue.isEmpty()) {
						msg = outQueue.poll();
						out.println(msg);
						out.flush();
					}
				}
			}

		} catch (IOException ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void handleATPBrake(boolean isBrake) {
		App.atpBrake = isBrake;
	}

	private void handleNonATPBrake(boolean isBrake) {
		App.nonAtpBrake = isBrake;
	}

	private void handleTargetDistance(double distance) {
		App.targetDistance = distance;
	}

	private void handleTargetDistanceActual(double distance) {
		App.targetDistanceActual = distance;
	}

	private void handleATOStatus(int status) {
		App.atpStatus = status;
	}

	private void handleAtennaStatus(int status) {
		App.atennaStatus = status;
	}

	private void handleDoorIndicator(int status) {
		App.doorIndicator = status;
	}

	private void handleCeilingSpeed(double speed) {
		App.ceilingSpeed = speed;
	}

	private void handleDoorStatus(boolean status) {
		App.doorStatus = status;
	}

	private void handleDwell(int dwell) {
		App.dwell = dwell;
	}

	private void handleSkipStopStatus(int status) {
		App.skipstopStatus = status;
	}

	private void handleMode(int mode) {
		App.mode = mode;
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
