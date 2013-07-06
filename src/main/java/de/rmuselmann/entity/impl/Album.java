package de.rmuselmann.entity.impl;

import de.rmuselmann.entity.IAlbum;

public class Album implements IAlbum {

	private long albumID;
	private String albumName;

	public Album(long id, String albumName) {
		this.albumID = id;
		this.albumName = albumName;
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
		Album other = (Album) obj;
		if (albumID != other.albumID) {
			return false;
		}
		if (albumName == null) {
			if (other.albumName != null) {
				return false;
			}
		} else if (!albumName.equals(other.albumName)) {
			return false;
		}
		return true;
	}

	@Override
	public String getAlbumName() {
		return albumName;
	}

	@Override
	public long getID() {
		return albumID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (albumID ^ (albumID >>> 32));
		result = prime * result
				+ ((albumName == null) ? 0 : albumName.hashCode());
		return result;
	}

	@Override
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	@Override
	public void setID(long albumID) {
		this.albumID = albumID;
	}

	@Override
	public String toString() {
		return albumName;
	}
}
