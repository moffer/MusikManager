package de.rmuselmann.logic.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import de.rmuselmann.entity.IUser;
import de.rmuselmann.logic.IUserLogic;

public class SettingsLogic {
	private File file = new File("./settings.ini");
	private IUserLogic userLogic;

	public SettingsLogic(IUserLogic userLogic) {
		this.userLogic = userLogic;
	}

	public IUser loadSettings() throws IOException {
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		String zeile = br.readLine();
		zeile = zeile.substring("User: ".length());

		long id = Integer.parseInt(zeile);
		return userLogic.getUserByID(id);
	}

	public IUser setStandartSettings(IUser user) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file));) {
			file.createNewFile();
			writer.append("User: " + user.getID() + "\n");
			return user;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public IUser setUser(IUser user) {
		writeToFile("User: ", user.getID());
		return user;
	}

	/**
	 * @param containsString
	 * @param changeString
	 */
	private void writeToFile(String containsString, long changeString) {
		try (BufferedReader br = new BufferedReader(new FileReader(file));
				BufferedWriter writer = new BufferedWriter(new FileWriter(file));) {

			StringBuffer buffer = new StringBuffer();

			String zeile = "";
			while ((zeile = br.readLine()) != null) {
				if (zeile.contains(containsString)) {
					zeile = containsString + changeString + "\n";
				}
				buffer.append(zeile);
			}

			writer.append(buffer.toString());
			writer.flush();
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
