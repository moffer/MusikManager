package de.rmuselmann.database.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionManager {

	private static Connection con;
	private static String host = "subel.no-ip.org" /* "192.168.178.41" */;
	private static String dbPassword = "musikDB";
	private static String dbPort = "3306";
	private static String database = "musikDB";
	private static String dbUser = "musikDBUser";
	// TODO umstellen bei deploy
	private static boolean isLocal = true;

	private final static Logger LOGGER = Logger
			.getLogger(ConnectionManager.class.getName());

	/**
	 * Beendet die Verbindung zur Datenbank.
	 */
	public static void close() {
		try {
			LOGGER.log(Level.INFO, "Beende Connection.");
			if (con != null) {
				con.close();
			}
			LOGGER.log(Level.INFO, "Verbindung beendet.");
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "Fehler: Verbindung nicht beendet", e);
			e.printStackTrace();
		}
	}

	/**
	 * Gibt die Datenbank-Verbindung zur�ck.
	 * 
	 * @return Datenkbank-Verbindung
	 * @throws SQLException
	 * @throws ConnectionException
	 */
	public static Connection getDatabaseConnection() throws ConnectionException {
		try {
			if (con == null || con.isClosed()) {
				if (isLocal) {
					host = "localhost";
				}

				LOGGER.log(Level.INFO, "Versuche Verbindung aufzubauen.");

				Class.forName("com.mysql.jdbc.Driver").newInstance();

				con = DriverManager.getConnection("jdbc:mysql://" + host + ":"
						+ dbPort + "/" + database + "?" + "user=" + dbUser
						+ "&" + "password=" + dbPassword);
				LOGGER.log(Level.INFO, "Verbindung aufgebaut.");
			}
			return con;
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			LOGGER.log(Level.WARNING,
					"Fehler: Versuch Datenverbindung aufzubauen fehlgeschlagen: "
							+ e);
			throw new ConnectionException(e.getMessage());
		}

	}

	/**
	 * @return the host
	 */
	public static String getHost() {
		return host;
	}
}