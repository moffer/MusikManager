package de.rmuselmann.entity.dto.impl;

import de.rmuselmann.entity.dto.ITanzartDTO;

public class TanzartDTO implements ITanzartDTO {
	private long id;
	private String tanzartName;

	public TanzartDTO(long id, String tanzartName) {
		this.id = id;
		this.tanzartName = tanzartName;
	}

	@Override
	public long getID() {
		return id;
	}

	@Override
	public String getTanzartName() {
		return tanzartName;
	}

	@Override
	public void setID(long id) {
		this.id = id;
	}

	@Override
	public void setTanzartName(String tanzartName) {
		this.tanzartName = tanzartName;
	}

}
