package de.rmuselmann.entity.dto.impl;

import de.rmuselmann.entity.dto.IInterpretDTO;

public class InterpretDTO implements IInterpretDTO {
	private long id;
	private String interpretName;

	public InterpretDTO(long id, String interpretName) {
		this.id = id;
		this.interpretName = interpretName;
	}

	@Override
	public long getID() {
		return id;
	}

	@Override
	public String getInterpretName() {
		return interpretName;
	}

	@Override
	public void setID(long id) {
		this.id = id;
	}

	@Override
	public void setInterpretName(String interpretName) {
		this.interpretName = interpretName;
	}

}
