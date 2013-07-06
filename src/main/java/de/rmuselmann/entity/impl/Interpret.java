package de.rmuselmann.entity.impl;

import de.rmuselmann.entity.IInterpret;

public class Interpret implements IInterpret {

	private long interpretID;
	private String interpretName;

	protected Interpret(long id, String interpretName) {
		interpretID = id;
		this.interpretName = interpretName;
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
		Interpret other = (Interpret) obj;
		if (interpretID != other.interpretID) {
			return false;
		}
		if (interpretName == null) {
			if (other.interpretName != null) {
				return false;
			}
		} else if (!interpretName.equals(other.interpretName)) {
			return false;
		}
		return true;
	}

	@Override
	public long getID() {
		return interpretID;
	}

	@Override
	public String getInterpretName() {
		return interpretName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (interpretID ^ (interpretID >>> 32));
		result = prime * result
				+ ((interpretName == null) ? 0 : interpretName.hashCode());
		return result;
	}

	@Override
	public void setID(long interpretID) {
		this.interpretID = interpretID;
	}

	@Override
	public void setInterpretName(String interpretName) {
		this.interpretName = interpretName;
	}

	@Override
	public String toString() {
		return interpretName;
	}

}
