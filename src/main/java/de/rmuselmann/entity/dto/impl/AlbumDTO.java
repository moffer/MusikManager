package de.rmuselmann.entity.dto.impl;

import de.rmuselmann.entity.dto.IAlbumDTO;

public class AlbumDTO implements IAlbumDTO {
	private long id;
	private String albumName;

	public AlbumDTO(long id, String albumName) {
		this.id = id;
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
		AlbumDTO other = (AlbumDTO) obj;
		if (albumName == null) {
			if (other.albumName != null) {
				return false;
			}
		} else if (!albumName.equals(other.albumName)) {
			return false;
		}
		if (id != other.id) {
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
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((albumName == null) ? 0 : albumName.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	@Override
	public void setID(long id) {
		this.id = id;
	}
}
