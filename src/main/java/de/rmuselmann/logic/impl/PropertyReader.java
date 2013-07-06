package de.rmuselmann.logic.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

	public String getProperty(String name) {

		Properties prob = new Properties();
		try {
			InputStream inStream = getClass().getResourceAsStream(
					"/main.properties");

			prob.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prob.getProperty("version");
	}
}
