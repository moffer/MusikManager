package de.rmuselmann.entity.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import de.rmuselmann.entity.IAlbum;
import de.rmuselmann.entity.IInterpret;
import de.rmuselmann.entity.ISong;
import de.rmuselmann.entity.ITanzart;
import de.rmuselmann.entity.IUser;


public class Song implements ISong {

	public static class SongComparator implements Comparator<ISong> {

		@Override
		public int compare(ISong o1, ISong o2) {
			if (o2.getInterpret().getInterpretName() == null
					&& o1.getInterpret().getInterpretName() == null) {
				return 0;
			}
			if (o1.getInterpret().getInterpretName() == null) {
				return 1;
			}
			if (o2.getInterpret().getInterpretName() == null) {
				return -1;
			}
			return o1.getInterpret().getInterpretName()
					.compareTo(o2.getInterpret().getInterpretName());
		}

	}

	private long songID;
	private IInterpret interpret;
	private String title;
	private IAlbum album;
	private ITanzart tanzart;
	private IUser user;
	private boolean isFileExisting;

	private Date date;

	protected Song(long songID, IInterpret interpret, String title,
			IAlbum album, ITanzart tanzart, IUser user, boolean isFileExisting,
			Date date) {
		super();
		this.songID = songID;
		this.interpret = interpret;
		this.title = title;
		this.album = album;
		this.tanzart = tanzart;
		this.user = user;
		this.isFileExisting = isFileExisting;
		this.date = date;
	}

	@Override
	public ArrayList<String> convertToList() {
		ArrayList<String> arr = new ArrayList<>();
		if (interpret != null) {
			arr.add(interpret.getInterpretName());
		} else {
			arr.add(null);
		}
		arr.add(title);
		if (album != null) {
			arr.add(album.getAlbumName());
		} else {
			arr.add(null);
		}

		if (tanzart != null) {
			arr.add(tanzart.getTanzartName());
		} else {
			arr.add(null);
		}
		return arr;
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

		Song other = (Song) obj;
		if (album == null) {
			if (other.album != null) {
				return false;
			}
		} else if (!album.equals(other.album)) {
			return false;
		}
		if (interpret == null) {
			if (other.interpret != null) {
				return false;
			}
		} else if (!interpret.equals(other.interpret)) {
			return false;
		}
		if (obj != NullObjects.SONG.getObject()) {
			if (songID != other.songID) {
				return false;
			}
		}
		if (tanzart == null) {
			if (other.tanzart != null) {
				return false;
			}
		} else if (!tanzart.equals(other.tanzart)) {
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
	public IAlbum getAlbum() {
		return album;
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Override
	public long getID() {
		return songID;
	}

	@Override
	public IInterpret getInterpret() {
		return interpret;
	}

	@Override
	public ITanzart getTanzart() {
		return tanzart;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public IUser getUser() {
		return user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((album == null) ? 0 : album.hashCode());
		result = prime * result
				+ ((interpret == null) ? 0 : interpret.hashCode());
		result = prime * result + (int) (songID ^ (songID >>> 32));
		result = prime * result + ((tanzart == null) ? 0 : tanzart.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean isFileExisting() {
		return isFileExisting;
	}

	@Override
	public void setAlbum(IAlbum album) {
		this.album = album;
	}

	@Override
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public void setFileExisting(boolean isFileExisting) {
		this.isFileExisting = isFileExisting;
	}

	@Override
	public void setID(long songID) {
		this.songID = songID;
	}

	@Override
	public void setInterpret(IInterpret interpret) {
		this.interpret = interpret;
	}

	@Override
	public void setTanzart(ITanzart tanzart) {
		this.tanzart = tanzart;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void setUser(IUser user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return this.getInterpret() + " - " + this.getTitle();
	}
}
