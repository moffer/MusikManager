package de.rmuselmann.database.dao;

import java.util.ArrayList;

import de.rmuselmann.entity.ISong;
import de.rmuselmann.entity.dto.ISongDTO;
import de.rmuselmann.gui.events.IUpdateEventListener;
import de.rmuselmann.logic.exceptions.UniqueConstraintException;


public interface ISongDAO {

	/**
	 * F�gt den Song der Datenbank hinzu und gibt ihn aktualisiert (mit ID)
	 * zur�ck.
	 * 
	 * @param song
	 *            - Song, der hinzugef�gt werden soll.
	 * @return hinzugef�gter, aktualisierter (mit ID) Song
	 * @throws UniqueConstraintException
	 *             - Wenn der Song bereits vorhanden ist.
	 */
	public ISong addSong(ISong song) throws UniqueConstraintException;

	/**
	 * �ndert den mitgegebenen Song anhand der ID und gibt ihn aktualisiert
	 * zur�ck.
	 * 
	 * @param song
	 *            - Song, der geupdatet werden soll.
	 * @return ge�nderter Song.
	 */
	public ISong changeSong(ISong song);

	/**
	 * L�scht den Song mit der mitgegebenen ID.
	 * 
	 * @param id
	 *            - ID des Songs der gel�scht werden soll.
	 */
	public void deleteSong(long id);

	/**
	 * Gibt die Anzahl der Eintr�ge der Song-Tabelle zur�ck.
	 * 
	 * @return Anzahl der Eintr�ge
	 */
	public int getDatabaseSize();

	// public long getNextID();
	/**
	 * Gibt alle Eintr�ge der Songtabelle in einer ArrayList zur�ck. Es werden
	 * nur {@link ISongDTO} erzeugt (nur IDs von Album/Interpret...).
	 * 
	 * @return alle Eintr�ge in einer Arraylist mit {@link ISongDTO}s
	 */
	public ArrayList<ISongDTO> readDatabase();

	/**
	 * Gibt alle Eintr�ge der Songtabelle in einer ArrayList zur�ck. Es wird
	 * eine View abgefragt, sodass schnell alle Informationen des Songs
	 * abgefragt werden k�nnen.
	 * 
	 * Schickt nach jedem geladenen Song ein Event an den mitgegebenen Listener
	 * 
	 * @param updateListener
	 *            - Listener, an den bei jedem geladenen Song ein Event gefeuert
	 *            wird (bei null wird kein Event gefeuert).
	 * @return ArrayList mit allen ISong-Eintr�gen.
	 */
	public ArrayList<ISong> readSongDatabase(IUpdateEventListener updateListener);

	/**
	 * Setzt bei dem mitgegebenen Song den Boolean-Wert f�r die Datei auf true;
	 * 
	 * @param song
	 *            - Song, dessen Boolean isUploaded auf true gesetzt werden
	 *            soll.
	 * @return aktualisierter Song.
	 */
	public ISong setFileToSong(ISong song);

}