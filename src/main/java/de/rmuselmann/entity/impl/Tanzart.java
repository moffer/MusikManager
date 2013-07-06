package de.rmuselmann.entity.impl;

import de.rmuselmann.entity.ITanzart;

public class Tanzart implements ITanzart {

	private long tanzartID;
	private String tanzartName;

	public Tanzart(long id, String tanzartName) {
		this.tanzartID = id;
		this.tanzartName = tanzartName;
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
		Tanzart other = (Tanzart) obj;
		if (tanzartID != other.tanzartID) {
			return false;
		}
		if (tanzartName == null) {
			if (other.tanzartName != null) {
				return false;
			}
		} else if (!tanzartName.equals(other.tanzartName)) {
			return false;
		}
		return true;
	}

	@Override
	public long getID() {
		return tanzartID;
	}

	@Override
	public String getTanzartName() {
		return tanzartName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (tanzartID ^ (tanzartID >>> 32));
		result = prime * result
				+ ((tanzartName == null) ? 0 : tanzartName.hashCode());
		return result;
	}

	@Override
	public void setID(long tanzartID) {
		this.tanzartID = tanzartID;
	}

	@Override
	public void setTanzartName(String tanzartName) {
		this.tanzartName = tanzartName;
	}

	@Override
	public String toString() {
		return tanzartName;
	}
}
