package de.rmuselmann.database.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;

import rmuselmann.main.FileServer.FileOption;
import de.rmuselmann.database.dao.IFileDAO;
import de.rmuselmann.entity.ISong;

public class FileDAO implements IFileDAO {

	protected FileDAO() {
	}

	@Override
	public void deleteFile(long songID) throws IOException {
		try (Socket sock = new Socket(ConnectionManager.getHost(), 2000);
				OutputStream out = sock.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(out);) {

			oos.writeObject(FileOption.DELETE);
			oos.writeObject(new File(songID + ".mp3"));

		} catch (IOException e) {
			if (e.getClass().getName().equals("java.net.ConnectException")) {
				throw e;
			} else {
				e.printStackTrace();
			}
		}
	}

	private void downloadSong(File serverFile, File localFile)
			throws IOException {
		try (Socket sock = new Socket(ConnectionManager.getHost(), 2000);
				InputStream in = sock.getInputStream();
				OutputStream out = sock.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(out);
				FileOutputStream fileOut = new FileOutputStream(localFile);) {

			// Schreibt die Datei rein, damit der Server wei�, welche Datei er
			// ausw�hlen muss.

			oos.writeObject(FileOption.DOWNLOAD);
			oos.writeObject(serverFile);

			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = in.read(buffer)) > 0) {
				fileOut.write(buffer, 0, bytesRead);
			}
		} catch (IOException e) {
			if (e.getClass().getName().equals("java.net.ConnectException")) {
				throw e;
			} else {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void downloadSong(ISong song, File file) throws IOException {
		downloadSong(new File(song.getID() + ".mp3"), file);
	}

	private boolean uploadFile(File serverFile, File localFile)
			throws IOException {
		try (InputStream fileIn = new FileInputStream(localFile);
				Socket sock = new Socket(ConnectionManager.getHost(), 2000);
				OutputStream out = sock.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(out)) {

			oos.writeObject(FileOption.UPLOAD);
			oos.writeObject(serverFile);

			byte[] buffer = new byte[1024];
			while (fileIn.available() > 0) {
				out.write(buffer, 0, fileIn.read(buffer));
			}

		} catch (IOException e) {
			if (e.getClass().equals(ConnectException.class)) {
				throw e;
			} else {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean uploadFile(ISong song, File localFile) throws IOException {
		return uploadFile(new File(song.getID() + ".mp3"), localFile);
	}
}
