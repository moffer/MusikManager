package de.rmuselmann.entity;

public interface IAlbum extends IFields {
	@Override
	public boolean equals(Object obj);

	/**
	 * Gibt den Albumname zur�ck.
	 * 
	 * @return albumname
	 */
	public String getAlbumName();

	@Override
	public int hashCode();

	/**
	 * Setzt den Albumname.
	 * 
	 * @param albumName
	 */
	public void setAlbumName(String albumName);
}
