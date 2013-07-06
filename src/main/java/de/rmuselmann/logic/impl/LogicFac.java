package de.rmuselmann.logic.impl;

import java.sql.SQLException;

import de.rmuselmann.database.dao.impl.ConnectionException;
import de.rmuselmann.logic.IAlbumLogic;
import de.rmuselmann.logic.IInterpretLogic;
import de.rmuselmann.logic.ISongLogic;
import de.rmuselmann.logic.ITanzartLogic;
import de.rmuselmann.logic.IUserLogic;

/**
 * Klasse zum Bilden einer Logik
 * 
 * @author Rafael Muselmann
 * 
 */
public abstract class LogicFac {

	/**
	 * Gibt eine neue {@link AlbumLogic} zur�ck.
	 * 
	 * @return neue {@link AlbumLogic}
	 * @throws SQLException
	 */
	public static IAlbumLogic getNewAlbumLogic() throws ConnectionException {
		return new AlbumLogic();
	}

	/**
	 * Gibt eine neue {@link InterpretLogic} zur�ck.
	 * 
	 * @return neue {@link AlbumLogic}
	 * @throws SQLException
	 */
	public static IInterpretLogic getNewInterpretLogic()
			throws ConnectionException {
		return new InterpretLogic();
	}

	/**
	 * Gibt eine neue {@link SongLogic} zur�ck.
	 * 
	 * @param albumLogic
	 * @param tanzartLogic
	 * @param interpretLogic
	 * 
	 * @return neue {@link SongLogic}
	 * @throws SQLException
	 */
	public static ISongLogic getNewSongLogic(IAlbumLogic albumLogic,
			ITanzartLogic tanzartLogic, IInterpretLogic interpretLogic)
			throws ConnectionException {
		return new SongLogic(albumLogic, tanzartLogic, interpretLogic);
	}

	/**
	 * Gibt eine neue {@link SettingsLogic} zur�ck.
	 * 
	 * @param userLogic
	 * @return neue {@link SettingsLogic}
	 */
	public static SettingsLogic getNewSettingsLogic(IUserLogic userLogic) {
		return new SettingsLogic(userLogic);
	}

	/**
	 * Gibt eine neue {@link TanzartLogic} zur�ck.
	 * 
	 * @return neue {@link TanzartLogic}
	 * @throws SQLException
	 */
	public static ITanzartLogic getNewTanzartLogic() throws ConnectionException {
		return new TanzartLogic();
	}

	/**
	 * Gibt eine neue {@link UserLogic} zur�ck.
	 * 
	 * @return neue {@link UserLogic}
	 * @throws SQLException
	 */
	public static IUserLogic getNewUserLogic() throws ConnectionException {
		return new UserLogic();
	}

	public static TableConfigurationLogic getNewTableConfigurationLogic()
			throws ConnectionException {
		return new TableConfigurationLogic();
	}
}
