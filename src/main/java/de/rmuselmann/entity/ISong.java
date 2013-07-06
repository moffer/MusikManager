package de.rmuselmann.entity;

import java.util.ArrayList;
import java.util.Date;

public interface ISong extends IFields {
	/**
	 * F�gt einer ArrayList alle Felder des Songs hinzu.
	 * 
	 * @return ArrayList mit allen Feldern des Songs.
	 */
	public ArrayList<String> convertToList();

	@Override
	public boolean equals(Object obj);

	/**
	 * Gibt das Album zur�ck.
	 * 
	 * @return Album des Songs
	 */
	public IAlbum getAlbum();

	/**
	 * Gibt das Hochladedatum des Songs zur�ck.
	 * 
	 * @return HochladeDatum
	 */
	public Date getDate();

	public IInterpret getInterpret();

	public ITanzart getTanzart();

	public String getTitle();

	public IUser getUser();

	@Override
	public int hashCode();

	public boolean isFileExisting();

	public void setAlbum(IAlbum album);

	public void setDate(Date date);

	public void setFileExisting(boolean isFileExisting);

	public void setInterpret(IInterpret interpret);

	public void setTanzart(ITanzart tanzart);

	public void setTitle(String title);

	public void setUser(IUser user);

	@Override
	public String toString();
}