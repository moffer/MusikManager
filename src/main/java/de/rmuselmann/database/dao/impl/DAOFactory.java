package de.rmuselmann.database.dao.impl;

import java.sql.Connection;

import de.rmuselmann.database.dao.IAlbumDAO;
import de.rmuselmann.database.dao.IFileDAO;
import de.rmuselmann.database.dao.IInterpretDAO;
import de.rmuselmann.database.dao.ISongDAO;
import de.rmuselmann.database.dao.ITanzartDAO;
import de.rmuselmann.database.dao.IUserDAO;

public class DAOFactory {
	public static IAlbumDAO getNewAlbumDAO(Connection con) {
		return new AlbumDAO(con);
	}

	/**
	 * Erzeugt ein neues {@link FileDAO}-Object und gibt dieses zur�ck.
	 * 
	 * @return neues {@link FileDAO}-Object
	 */
	public static IFileDAO getNewFileDAO() {
		return new FileDAO();
	}

	/**
	 * Erzeugt ein neues {@link InterpretDAO}-Object und gibt dieses zur�ck.
	 * 
	 * @param con
	 * @return neues {@link InterpretDAO}-Object
	 */
	public static IInterpretDAO getNewInterpretDAO(Connection con) {
		return new InterpretDAO(con);
	}

	/**
	 * Erzeugt ein neues {@link SongDAO}-Object und gibt dieses zur�ck.
	 * 
	 * @param con
	 * @return neues {@link SongDAO}-Object
	 */
	public static ISongDAO getNewSongDAO(Connection con) {
		return new SongDAO(con);
	}

	/**
	 * Erzeugt ein neues {@link TanzartDAO}-Object und gibt dieses zur�ck.
	 * 
	 * @param con
	 * @return neues {@link TanzartDAO}
	 */
	public static ITanzartDAO getNewTanzartDAO(Connection con) {
		return new TanzartDAO(con);
	}

	/**
	 * Erzeugt ein neues {@link UserDAO}-Object und gibt dieses zur�ck.
	 * 
	 * @param con
	 * @return neues {@link UserDAO}
	 */
	public static IUserDAO getNewUserDAO(Connection con) {
		return new UserDAO(con);
	}

	public static TableConfigurationDAO getNewTableConfigurationDAO(
			Connection con) {
		return new TableConfigurationDAO(con);
	}
}
