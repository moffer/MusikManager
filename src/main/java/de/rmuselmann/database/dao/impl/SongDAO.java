package de.rmuselmann.database.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import de.rmuselmann.database.dao.ISongDAO;
import de.rmuselmann.entity.IAlbum;
import de.rmuselmann.entity.IFields;
import de.rmuselmann.entity.IFields.NullObjects;
import de.rmuselmann.entity.IInterpret;
import de.rmuselmann.entity.ISong;
import de.rmuselmann.entity.ITanzart;
import de.rmuselmann.entity.IUser;
import de.rmuselmann.entity.dto.ISongDTO;
import de.rmuselmann.entity.dto.impl.DTOFactory;
import de.rmuselmann.entity.impl.EntityFactory;
import de.rmuselmann.gui.events.IUpdateEventListener;
import de.rmuselmann.gui.events.UpdateEvent;
import de.rmuselmann.logic.exceptions.UniqueConstraintException;

public class SongDAO implements ISongDAO {

	private Connection con;

	protected SongDAO(Connection con) {
		this.con = con;
	}

	public ISong addSong(ISong song) throws UniqueConstraintException {
		long songID = getNextID();
		String titel = song.getTitle();

		long interpretID = -1;
		long albumID = -1;
		long tanzartID = -1;
		long userID = -1;

		if (song.getInterpret() != null) {
			interpretID = song.getInterpret().getID();
		}
		if (song.getAlbum() != null) {
			albumID = song.getAlbum().getID();
		}
		if (song.getTanzart() != null) {
			tanzartID = song.getTanzart().getID();
		}
		if (song.getUser() != null) {
			userID = song.getUser().getID();
		}

		try (PreparedStatement addSong = con
				.prepareStatement("INSERT INTO Song VALUES(" + songID
						+ ",?,?,?,?,?,?, ?)")) {

			addSong.setString(1, titel);
			addSong.setBoolean(2, false);

			if (interpretID != -1) {
				addSong.setLong(3, interpretID);
			} else {
				addSong.setObject(3, 1);
			}

			if (albumID != -1) {
				addSong.setLong(4, albumID);
			} else {
				addSong.setObject(4, null);
			}

			if (tanzartID != -1) {
				addSong.setLong(5, tanzartID);
			} else {
				addSong.setObject(5, null);
			}

			if (userID != -1) {
				addSong.setLong(6, userID);
			} else {
				addSong.setObject(6, null);
			}

			addSong.setDate(7, new java.sql.Date(song.getDate().getTime()));

			addSong.executeUpdate();
		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e1) {
			throw new UniqueConstraintException();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		song.setID(songID);
		return song;
	}

	public ISong changeSong(ISong song) {

		long songID = song.getID();
		String titel = song.getTitle();

		PreparedStatement changeSong = null;
		try {
			changeSong = con
					.prepareStatement("UPDATE Song SET title = ?, Interpret_idInterpret = ?, Album_idAlbum = ?, Tanzart_idTanzart = ? WHERE idSong = "
							+ songID + "");

			changeSong.setString(1, titel);
			if (!song.getInterpret().equals(
					IFields.NullObjects.INTERPRET.getObject())) {
				changeSong.setLong(2, song.getInterpret().getID());
			} else {
				changeSong.setObject(2, null);
			}

			if (!song.getAlbum().equals(IFields.NullObjects.ALBUM.getObject())) {
				changeSong.setLong(3, song.getAlbum().getID());
			} else {
				changeSong.setObject(3, null);
			}

			if (!song.getTanzart().equals(
					IFields.NullObjects.TANZART.getObject())) {
				changeSong.setLong(4, song.getTanzart().getID());
			} else {
				changeSong.setObject(4, null);
			}

			changeSong.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				changeSong.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return song;
	}

	public void deleteSong(long id) {
		PreparedStatement deleteSong = null;
		try {
			deleteSong = con
					.prepareStatement("Delete From Song WHERE idSong = ?");
			deleteSong.setLong(1, id);
			deleteSong.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				deleteSong.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public int getDatabaseSize() {
		int size = -1;
		try (PreparedStatement ps = con
				.prepareStatement("SELECT COUNT(*) FROM Song");
				ResultSet rs = ps.executeQuery()) {

			rs.next();
			size = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return size;
	}

	private long getNextID() {
		ResultSet rs = null;
		long id = -1;
		try {
			PreparedStatement ps = con
					.prepareStatement("SELECT Max(idSong) FROM Song");
			rs = ps.executeQuery();
			rs.next();
			id = rs.getLong(1) + 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return id;
	}

	public ArrayList<ISongDTO> readDatabase() {
		ArrayList<ISongDTO> songs = new ArrayList<>();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("SELECT idSong, title, isUploaded, Interpret_idInterpret, Album_idAlbum, Tanzart_idTanzart, User_idUser, date FROM Song");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				long songID = rs.getLong(1);
				String title = rs.getString(2);
				boolean isUploaded = rs.getBoolean(3);
				long interpretID = rs.getLong(4);
				long albumID = rs.getLong(5);
				long tanzartID = rs.getLong(6);
				long userID = rs.getLong(7);
				Date date = rs.getDate(8);

				ISongDTO song = DTOFactory.getNewSong(songID, title,
						interpretID, albumID, tanzartID, userID, isUploaded,
						date);
				songs.add(song);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return songs;
	}

	public ArrayList<ISong> readSongDatabase(IUpdateEventListener updateListener) {
		ArrayList<ISong> songs = new ArrayList<>();

		try (PreparedStatement ps = con
		// 5-Schritte
				.prepareStatement("SELECT  idSong, title, date, isUploaded, idAlbum,"
						+ " albumName, idInterpret, interpretName, idTanzart, tanzartName, "
						+ "idUser,  userName FROM WholeSong");
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				long songID = rs.getLong(1);
				String title = rs.getString(2);
				Date date = rs.getDate(3);
				boolean isUploaded = rs.getBoolean(4);
				IAlbum album;
				if (rs.getLong(5) > 0) {
					album = EntityFactory.getNewAlbum(rs.getLong(5),
							rs.getString(6));
				} else {
					album = (IAlbum) NullObjects.ALBUM.getObject();
				}

				IInterpret interpret;
				if (rs.getLong(7) > 0) {
					interpret = EntityFactory.getNewInterpret(rs.getLong(7),
							rs.getString(8));
				} else {
					interpret = (IInterpret) NullObjects.INTERPRET.getObject();
				}

				ITanzart tanzart;
				if (rs.getLong(9) > 0) {
					tanzart = EntityFactory.getNewTanzart(rs.getLong(9),
							rs.getString(10));
				} else {
					tanzart = (ITanzart) NullObjects.TANZART.getObject();
				}

				IUser user;
				if (rs.getLong(11) > 0) {
					user = EntityFactory.getNewUser(rs.getLong(11),
							rs.getString(12));
				} else {
					user = (IUser) NullObjects.USER.getObject();
				}

				ISong song = EntityFactory.getNewSong(songID, title, interpret,
						album, tanzart, user, isUploaded, date);

				songs.add(song);
				if (updateListener != null) {
					updateListener.handleEvent(new UpdateEvent(this));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return songs;

	}

	public ISong setFileToSong(ISong song) {
		long songID = song.getID();

		PreparedStatement fileStatement = null;
		try {
			fileStatement = con
					.prepareStatement("UPDATE Song SET  isUploaded = ? WHERE idSong = "
							+ songID + "");

			fileStatement.setBoolean(1, true);
			fileStatement.executeUpdate();
			song.setFileExisting(true);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fileStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return song;
	}
}
