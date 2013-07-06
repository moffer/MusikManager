package de.rmuselmann.entity;

import java.util.Date;

import de.rmuselmann.entity.impl.EntityFactory;


public interface IFields {
	enum NullObjects {
		ALBUM, INTERPRET, TANZART, USER, SONG;

		public Object getObject() {

			if (this.equals(ALBUM)) {
				return album;
			} else if (this.equals(INTERPRET)) {
				return interpret;
			} else if (this.equals(TANZART)) {
				return tanzart;
			} else if (this.equals(USER)) {
				return user;
			} else if (this.equals(SONG)) {
				return song;
			}
			return null;
		}
	}

	IAlbum album = EntityFactory.getNewAlbum(0, "");
	IInterpret interpret = EntityFactory.getNewInterpret(0, "");
	ITanzart tanzart = EntityFactory.getNewTanzart(1, "unbekannt");
	IUser user = EntityFactory.getNewUser(0, "");

	ISong song = EntityFactory.getNewSong(0, "", interpret, album, tanzart,
			user, false, new Date(0));

	@Override
	public boolean equals(Object obj);

	/**
	 * Gibt die ID des Feldes zur�ck.
	 * 
	 * @return
	 */
	public long getID();

	@Override
	public int hashCode();

	/**
	 * Setzt die ID des Feldes.
	 * 
	 * @param id
	 */
	public void setID(long id);

	@Override
	public String toString();
}
