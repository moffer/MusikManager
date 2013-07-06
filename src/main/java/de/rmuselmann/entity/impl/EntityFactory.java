package de.rmuselmann.entity.impl;

import java.util.Date;

import de.rmuselmann.entity.IAlbum;
import de.rmuselmann.entity.IInterpret;
import de.rmuselmann.entity.ISong;
import de.rmuselmann.entity.ITanzart;
import de.rmuselmann.entity.IUser;


public class EntityFactory {
	/**
	 * Erstellt ein neues {@link Album}-Object und gibt dieses zur�ck.
	 * 
	 * @param id
	 * @param albumName
	 * @return Album
	 */
	static public IAlbum getNewAlbum(long id, String albumName) {
		return new Album(id, albumName);
	}

	/**
	 * Erstellt ein neues {@link Interpret}-Object und gibt dieses zur�ck.
	 * 
	 * @param id
	 * @param interpretName
	 * @return Interpret
	 */
	static public IInterpret getNewInterpret(long id, String interpretName) {
		return new Interpret(id, interpretName);
	}

	/**
	 * Erstellt ein neues {@link Song}-Object und gibt dieses zur�ck.
	 * 
	 * @param songID
	 * @param title
	 * @param interpret
	 * @param album
	 * @param tanzart
	 * @param user
	 * @param isFileExisting
	 * @return Song
	 */
	static public ISong getNewSong(long songID, String title,
			IInterpret interpret, IAlbum album, ITanzart tanzart, IUser user,
			boolean isFileExisting, Date date) {

		return new Song(songID, interpret, title, album, tanzart, user,
				isFileExisting, date);
	}

	/**
	 * Erstellt ein neues {@link Tanzart}-Object und gibt dieses zur�ck.
	 * 
	 * @param id
	 * @param tanzartName
	 * @return Tanzart
	 */
	static public ITanzart getNewTanzart(long id, String tanzartName) {
		return new Tanzart(id, tanzartName);
	}

	/**
	 * Erstellt ein neues {@link User}-Object und gibt dieses zur�ck.
	 * 
	 * @param id
	 * @param userName
	 * @return
	 */
	public static IUser getNewUser(long id, String userName) {
		return new User(id, userName);
	}
}
