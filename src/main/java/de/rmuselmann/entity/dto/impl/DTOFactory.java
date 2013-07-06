package de.rmuselmann.entity.dto.impl;

import java.util.Date;

import de.rmuselmann.entity.dto.IAlbumDTO;
import de.rmuselmann.entity.dto.IInterpretDTO;
import de.rmuselmann.entity.dto.ISongDTO;
import de.rmuselmann.entity.dto.ITanzartDTO;


public class DTOFactory {
	/**
	 * Erstellt ein neues {@link AlbumDTO}-Object und gibt dieses zur�ck.
	 * 
	 * @param id
	 * @param albumName
	 * @return Album
	 */
	static public IAlbumDTO getNewAlbum(long id, String albumName) {
		return new AlbumDTO(id, albumName);
	}

	/**
	 * Erstellt ein neues {@link InterpretDTO}-Object und gibt dieses zur�ck.
	 * 
	 * @param id
	 * @param interpretName
	 * @return Interpret
	 */
	static public IInterpretDTO getNewInterpret(long id, String interpretName) {
		return new InterpretDTO(id, interpretName);
	}

	/**
	 * Erstellt ein neues {@link SongDTO}-Object und gibt dieses zur�ck.
	 * 
	 * @param songID
	 * @param title
	 * @param interpret
	 * @param album
	 * @param tanzart
	 * @param isFileExisting
	 * @return Song
	 */
	static public ISongDTO getNewSong(long songID, String title,
			long interpretID, long albumID, long tanzartID, long userID,
			boolean isFileExisting, Date date) {

		return new SongDTO(songID, interpretID, title, albumID, tanzartID,
				userID, isFileExisting, date);
	}

	/**
	 * Erstellt ein neues {@link TanzartDTO}-Object und gibt dieses zur�ck.
	 * 
	 * @param id
	 * @param tanzartName
	 * @return Tanzart
	 */
	static public ITanzartDTO getNewTanzart(long id, String tanzartName) {
		return new TanzartDTO(id, tanzartName);
	}
}
