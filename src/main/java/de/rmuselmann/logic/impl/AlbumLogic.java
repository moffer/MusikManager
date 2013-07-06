package de.rmuselmann.logic.impl;

import java.util.List;

import de.rmuselmann.database.dao.IAlbumDAO;
import de.rmuselmann.database.dao.impl.ConnectionException;
import de.rmuselmann.database.dao.impl.ConnectionManager;
import de.rmuselmann.database.dao.impl.DAOFactory;
import de.rmuselmann.entity.IAlbum;
import de.rmuselmann.entity.IFields;
import de.rmuselmann.entity.impl.EntityFactory;
import de.rmuselmann.logic.IAlbumLogic;

public class AlbumLogic implements IAlbumLogic {
	private IAlbumDAO dao;

	protected AlbumLogic() throws ConnectionException {
		dao = DAOFactory.getNewAlbumDAO(ConnectionManager
				.getDatabaseConnection());
	}

	@Override
	public IAlbum addAlbum(IAlbum album) {
		if (album.getAlbumName().trim().equals("")) {
			return (IAlbum) IFields.NullObjects.ALBUM.getObject();
		}
		return dao.addAlbum(album);
	}

	@Override
	public IAlbum getAlbum(String albumName) {
		if (!albumName.trim().equals("")) {
			IAlbum album = dao.getAlbum(albumName);
			if (album != null) {
				return album;
			} else {
				return dao.addAlbum(EntityFactory.getNewAlbum(-1, albumName));
			}
		} else {
			return (IAlbum) IFields.NullObjects.ALBUM.getObject();
		}
	}

	@Override
	public IAlbum getAlbumByID(long id) {
		return dao.getAlbumByID(id);
	}

	@Override
	public List<IAlbum> getAllAlbums() {
		return dao.readDatabase();
	}
}
