package de.rmuselmann.entity.dto;

import java.util.Date;

public interface ISongDTO extends IFieldsDTO {

	@Override
	public boolean equals(Object obj);

	public long getAlbumID();

	public Date getDate();

	public long getInterpretID();

	public long getTanzartID();

	public String getTitle();

	public long getUserID();

	@Override
	public int hashCode();

	public boolean isFileExisting();

	public void setAlbumID(long albumID);

	public void setDate(Date date);

	public void setFileExisting(boolean isFileExisting);

	public void setInterpretID(long interpretID);

	public void setTanzartID(long tanzartID);

	public void setTitle(String title);

	public void setUserID(long userID);
}