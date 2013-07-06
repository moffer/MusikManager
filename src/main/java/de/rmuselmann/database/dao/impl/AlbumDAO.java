package de.rmuselmann.database.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.rmuselmann.database.dao.IAlbumDAO;
import de.rmuselmann.entity.IAlbum;
import de.rmuselmann.entity.IFields.NullObjects;
import de.rmuselmann.entity.impl.EntityFactory;

public class AlbumDAO implements IAlbumDAO {

	private Connection con;

	protected AlbumDAO(Connection con) {
		this.con = con;
	}

	@Override
	public IAlbum addAlbum(IAlbum album) {
		long id = getNextID();
		try (PreparedStatement ps = con
				.prepareStatement("INSERT INTO Album VALUES(" + id + ",?)");) {
			// Setzt den Namen des Albums in das SQL-Statement
			ps.setString(1, album.getAlbumName());
			ps.executeUpdate();
			// Setzt die ID, da diese erst jetzt festgelegt ist.
			album.setID(id);
			return album;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public IAlbum getAlbum(String albumName) {
		// Damit keine leeren Alben angelegt werden.
		if (albumName != null && !albumName.equals("")) {

			try (PreparedStatement ps = con
					.prepareStatement("SELECT idAlbum FROM Album WHERE name = ?");) {
				ps.setString(1, albumName);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					// Erstellt ein neues Album und gibt dieses zur�ck.
					return EntityFactory.getNewAlbum(rs.getLong(1), albumName);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public IAlbum getAlbumByID(long albumID) {
		// Die IDs fangen mit 1 an. Wenn die ID kleiner ist, gibt es kein Album
		// davon
		if (albumID > 0) {
			String albumName = null;

			try (PreparedStatement ps = con
					.prepareStatement("SELECT name FROM Album WHERE idAlbum = ?");) {

				ps.setLong(1, albumID);
				ResultSet rs = ps.executeQuery();
				rs.next();
				albumName = rs.getString(1);

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return EntityFactory.getNewAlbum(albumID, albumName);
		} else {
			return (IAlbum) NullObjects.ALBUM.getObject();
		}
	}

	/**
	 * Gibt die n�chst-verwendbare ID zur�ck.
	 * 
	 * @return n�chst-verwenbare ID oder -1 falls ein Fehler auftritt.
	 */
	private long getNextID() {
		try (PreparedStatement ps = con
				.prepareStatement("SELECT Max(idAlbum) FROM Album");
				ResultSet rs = ps.executeQuery();) {

			rs.next();
			// Gibt die gr��te ID + 1 zur�ck.
			return rs.getLong(1) + 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Falls ein Fehler auftritt/es keine Tabellengr��e gibt, wird -1
		// zur�ckgegeben.
		return -1;
	}

	@Override
	public List<IAlbum> readDatabase() {
		ArrayList<IAlbum> albums = new ArrayList<IAlbum>();
		try (PreparedStatement ps = con
				.prepareStatement("SELECT idAlbum, name FROM Album");
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long albumID = rs.getLong(1);
				String name = rs.getString(2);

				IAlbum album = EntityFactory.getNewAlbum(albumID, name);
				albums.add(album);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return albums;
	}
}
