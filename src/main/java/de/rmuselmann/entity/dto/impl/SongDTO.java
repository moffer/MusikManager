package de.rmuselmann.entity.dto.impl;

import java.util.Date;

import de.rmuselmann.entity.dto.ISongDTO;


public class SongDTO implements ISongDTO {

	private long songID;
	private long interpretID;
	private String title;
	private long albumID;
	private long tanzartID;
	private boolean isFileExisting;
	private long userID;
	private Date date;

	protected SongDTO(long songID, long interpretID, String title,
			long albumID, long tanzartID, long userID, boolean isFileExisting,
			Date date) {
		super();
		this.songID = songID;
		this.interpretID = interpretID;
		this.title = title;
		this.albumID = albumID;
		this.tanzartID = tanzartID;
		this.userID = userID;
		this.isFileExisting = isFileExisting;
		this.date = date;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SongDTO other = (SongDTO) obj;
		if (albumID != other.albumID) {
			return false;
		}
		if (interpretID != other.interpretID) {
			return false;
		}
		if (isFileExisting != other.isFileExisting) {
			return false;
		}
		if (songID != other.songID) {
			return false;
		}
		if (tanzartID != other.tanzartID) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		return true;
	}

	@Override
	public long getAlbumID() {
		return albumID;
	}

	@Override
	public Date getDate() {
		return date;
	}

	/**
	 * @return the songID
	 */
	@Override
	public long getID() {
		return songID;
	}

	@Override
	public long getInterpretID() {
		return interpretID;
	}

	@Override
	public long getTanzartID() {
		return tanzartID;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public long getUserID() {
		return userID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (albumID ^ (albumID >>> 32));
		result = prime * result + (int) (interpretID ^ (interpretID >>> 32));
		result = prime * result + (isFileExisting ? 1231 : 1237);
		result = prime * result + (int) (songID ^ (songID >>> 32));
		result = prime * result + (int) (tanzartID ^ (tanzartID >>> 32));
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean isFileExisting() {
		return isFileExisting;
	}

	@Override
	public void setAlbumID(long albumID) {
		this.albumID = albumID;
	}

	@Override
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @param isFileExisting
	 *            the isFileExisting to set
	 */
	@Override
	public void setFileExisting(boolean isFileExisting) {
		this.isFileExisting = isFileExisting;
	}

	/**
	 * @param songID
	 *            the songID to set
	 */
	@Override
	public void setID(long songID) {
		this.songID = songID;
	}

	@Override
	public void setInterpretID(long interpretID) {
		this.interpretID = interpretID;
	}

	@Override
	public void setTanzartID(long tanzartID) {
		this.tanzartID = tanzartID;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void setUserID(long userID) {
		this.userID = userID;
	}
}
