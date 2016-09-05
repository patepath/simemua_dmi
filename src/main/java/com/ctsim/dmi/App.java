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

	public static boolean isTurnOn;
	public static double speed;
	public static int atpBrake;
	public static boolean nonAtpBrake;
	public static double targetDistance;
	public static double targetDistanceActual;
	public static double ceilingSpeed;
	public static int atpStatus;
	public static boolean isATOon = false;
	public static int atennaStatus;
	public static int doorIndicator;
	public static boolean doorStatus;
	public static int DWELL;
	public static int skipstopStatus;
	public static int MODE;

	public static boolean isReqBttnATB = false;
	public static boolean isReqBttnMCS = false;
	public static boolean isReqBttnAUTO = false;
	public static boolean isReqBttnYARD = false;

	public App() {

	}

	public void run() {
		initConnection();
	}

	private void initConnection() {

		Iterator<String> keys;
		String key;
		boolean isRegiester = false;

		parser = new JSONParser();

		try {
			socket = new Socket("192.168.1.10", 2510);
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
								case "IS_TURNON":
									isTurnOn = (boolean) jsonObj.get("IS_TURNON");

									if (!isTurnOn) {
										atpStatus = 0;
									}

									break;

								case "DISABLE_BUTTON":
									System.out.println("disable button");

									isReqBttnYARD = false;
									isReqBttnMCS = false;
									isReqBttnAUTO = false;
									isReqBttnATB = false;
									break;

								case "REQ_MODE":
									int value = (int) (long) jsonObj.get("REQ_MODE");

									switch (value) {
										case 1:
											isReqBttnYARD = true;
											break;

										case 3:
											isReqBttnMCS = true;
											break;

										case 4:
											isReqBttnAUTO = true;
											break;

										case 5:
											isReqBttnATB = true;
											break;
									}

									break;

								case "SPEED":
									try {
										speed = (double) jsonObj.get(key);
									} catch (Exception ex) {

									}
									break;

								case "ATP_BRAKE":
									handleATPBrake((int) (long) jsonObj.get("ATP_BRAKE"));
									break;

								case "NON_ATP_BRAKE":
									handleNonATPBrake((boolean) jsonObj.get("NON_ATP_BRAKE"));
									break;

								case "TARGET_DISTANCE":
									handleTargetDistance((double) jsonObj.get("TARGET_DISTANCE"));
									break;

								case "TARGET_DISTANCE_ACTUAL":
									handleTargetDistanceActual((double) jsonObj.get("TARGET_DISTANCE_ACTUAL"));
									break;

								case "CEILING_SPEED":
									handleCeilingSpeed((double) jsonObj.get("CEILING_SPEED"));
									break;

								case "ATP_STATUS":
									handleATOStatus((int) (long) jsonObj.get("ATP_STATUS"));
									break;

								case "ATENNA_STATUS":
									handleAtennaStatus((int) (long) jsonObj.get("ATENNA_STATUS"));
									break;

								case "DOOR_INDICATOR":
									handleDoorIndicator((int) (long) jsonObj.get("DOOR_INDICATOR"));
									break;

								case "DOOR_STATUS":
									handleDoorStatus((boolean) jsonObj.get("DOOR_STATUS"));
									break;

								case "SKIPSTOP_STATUS":
									handleSkipStopStatus((int) (long) jsonObj.get("SKIPSTOP_STATUS"));
									break;

								case "DWELL":
									handleDwell((int) (long) jsonObj.get("DWELL"));
									break;

								case "MODE":
									handleMode((int) (long) jsonObj.get("MODE"));
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

	private void handleATPBrake(int atpBrake) {
		App.atpBrake = atpBrake;
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

		switch (status) {
			case 3:
				isReqBttnMCS = false;
				break;

			case 4:
				isReqBttnAUTO = false;
				break;

		}
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

	private void handleDwell(int DWELL) {
		App.DWELL = DWELL;
	}

	private void handleSkipStopStatus(int status) {
		App.skipstopStatus = status;
	}

	private void handleMode(int MODE) {
		App.MODE = MODE;
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
