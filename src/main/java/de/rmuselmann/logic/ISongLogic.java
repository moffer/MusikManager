package de.rmuselmann.logic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.rmuselmann.entity.ISong;
import de.rmuselmann.gui.events.IProgressListener;
import de.rmuselmann.gui.events.ProgressEvent;
import de.rmuselmann.logic.exceptions.UniqueConstraintException;
import entagged.audioformats.exceptions.CannotReadException;

public interface ISongLogic {

	/**
	 * Ver�ndert den migegebenen Song anhand der ID. Dabei wird auch die Datei
	 * auf dem Server ver�ndert.
	 * 
	 * @param change
	 *            - der zu ver�nderte Song
	 * @return der ver�nderte Song
	 * @throws IOException
	 *             - falls keine Verbindung aufgebaut werden konnte (zum Server)
	 */
	public ISong changeSong(ISong change) throws IOException;

	/**
	 * Pr�ft die Datei auf Unterschiede zum Song.
	 * 
	 * @param file
	 *            - Datei, die verglichen werden soll.
	 * @param song
	 *            - Song, der verglichen werden soll.
	 * @return <b>true</b>, wenn kein Unterschied ist<br>
	 *         <b>false</b>, wenn ein Unterschied ist oder eine leere Datei
	 *         �bergeben wurde.
	 */
	public boolean checkUploadingFile(File file, ISong song);

	/**
	 * L�scht den mitgegebenen Song (anhand der ID) aus der Datenbank und vom
	 * Server.
	 * 
	 * @param song
	 *            - der zu l�schende Song.
	 * @throws IOException
	 */
	public void deleteSong(ISong song) throws IOException;

	/**
	 * L�scht die Liste an Songs in einem neuen Thread. Sendet au�erdem ein
	 * Event an den mitgegenben Listener, wenn ein Song gel�scht wurde, eine
	 * {@link IOException} aufgetreten ist, oder der Thread fertig ist.
	 * 
	 * @param songList
	 *            - Liste, die gel�scht wird.
	 * @param progressListener
	 *            - an den Listener wird ein ProgressEvent gefeuert, wenn ein
	 *            Song gel�scht wurde.
	 * @throws IOException
	 */
	public void deleteSongList(ArrayList<ISong> songList,
			IProgressListener progressListener) throws IOException;

	/**
	 * L�dt den Song <b>song</b> herunter und speichert diesen in die Datei
	 * <b>file</b>
	 * 
	 * @param song
	 *            - der zu herunterladene Song
	 * @param file
	 *            - Datei, in die der Song gespeichert wird.
	 * @return false, wenn File nicht vorhanden
	 * @throws IOException
	 */
	public boolean downloadSong(ISong song, File file) throws IOException;

	/**
	 * Gibt die Anzahl der Eintr�ge der Song-Tabelle zur�ck.
	 * 
	 * @return Anzahl der Eintr�ge
	 */
	public int getDatabaseSize();

	/**
	 * F�gt den {@link ISong} der Datenbank zu und gibt ihn aktualisiert zur�ck.
	 * 
	 * @param song
	 *            - Song der hinzugef�gt werden soll.
	 * @return aktualisierter (mit ID gesetzer) {@link ISong}
	 * @throws UniqueConstraintException
	 *             - falls der Song schon vorhanden ist
	 */
	public ISong insertSong(ISong song) throws UniqueConstraintException;

	/**
	 * Setzt, ob der Task abgebrochen werden soll.
	 * 
	 * @param isTaskCanceled
	 */
	public void isCanceled(boolean isTaskCanceled);

	/**
	 * Liest die SongDatenbank aus. Dabei wird nach jedem Song ein Event an den
	 * �bergebenen Progresslistener gesendet.
	 * 
	 * @param b
	 *            - {@link IProgressListener} an den bei jedem geladenen Song
	 *            der Status/Progess mitgegeben wird.
	 * @return Liste aller {@link ISong} in der Datenbank
	 */
	public List<ISong> readSongDatabase(final IProgressListener b);

	/**
	 * Uploaded den Song. In der Datenbank wird der entsprechende Boolean-Wert
	 * auf true gesetzt. Es sollte vorher der Name ueberprueft werden:
	 * checkFileName()
	 * 
	 * @param song
	 *            - Der {@link ISong}, der hochgeladen wird.
	 * @param selectedFile
	 *            - Die zu dem Song geh�rende Datei.
	 * @return aktualisierter {@link ISong}
	 * @throws IOException
	 */
	public ISong uploadSong(ISong song, File selectedFile) throws IOException;

	/**
	 * L�dt die Datei hoch. Der Song wird dabei an die Datei angepasst.
	 * 
	 * @param file
	 *            - Die Datei die hochgeladen werden soll.
	 * @param song
	 *            - Der Song, der an die Datei angepasst wird (�bernimmt ID3Tags
	 *            der Datei).
	 * @return aktualisierter, an die Datei angepasster Song
	 * @throws IOException
	 * @throws UniqueConstraintException
	 * @throws CannotReadException
	 */
	public ISong uploadSongFromFile(File file, ISong song) throws IOException,
			UniqueConstraintException, CannotReadException;

	/**
	 * L�dt alle Songs der Liste hoch. Gibt eine Liste zur�ck. Dort ist einmal
	 * eine Liste aller hinzugef�gten, eine Liste aller schon vorhandenen
	 * Dateien {@link UniqueConstraintException} und eine Liste aller
	 * fehlgeschlagenen Dateien zur�ck {@link CannotReadException}
	 * 
	 * @param fileList
	 *            - Liste aller Dateien, die hinzugef�gt werden sollen.
	 * @param progressListener
	 *            - {@link IProgressListener}, an den nach jeder hinzugef�gter
	 *            Datei ein {@link ProgressEvent} gefeuert wird.
	 * @return Liste mit Listen (siehe oben)
	 * @throws IOException
	 * @throws CannotReadException
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList<ArrayList> uploadSongListFromFile(List<File> fileList,
			IProgressListener progressListener) throws IOException;
}