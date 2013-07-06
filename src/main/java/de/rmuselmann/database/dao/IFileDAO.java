package de.rmuselmann.database.dao;

import java.io.File;
import java.io.IOException;

import de.rmuselmann.entity.ISong;


public interface IFileDAO {

	/**
	 * L�scht die Datei anhand der mitgegebenen SongID
	 * 
	 * @param songID
	 * @throws IOException
	 */
	public void deleteFile(long songID) throws IOException;

	/**
	 * L�dt den Song herunter. Kopiert dabei die Serverdatei in die mitgebebene
	 * Datei.
	 * 
	 * @param song
	 *            - Der zu herunterladene Song.
	 * @param file
	 *            - Datei, wo der Song hingespeichert werden soll.
	 * @throws IOException
	 */
	public void downloadSong(ISong song, File file) throws IOException;

	/**
	 * L�dt den Song auf den Server hoch. L�dt dabei den mitgegebene Datei hoch
	 * und verkn�pft sie mit dem mitgegeben Song.
	 * 
	 * @param song
	 *            - Der hochzuladene Song.
	 * @param localFile
	 *            - Die locale Datei zu dem Song.
	 * @return boolean - hat geklappt/nicht geklappt
	 * @throws IOException
	 */
	boolean uploadFile(ISong song, File localFile) throws IOException;

}