package de.rmuselmann.entity.dto.impl;

import de.rmuselmann.entity.dto.IUserDTO;

public class UserDTO implements IUserDTO {

	private long id;
	private String userName;

	public UserDTO(long id, String userName) {
		this.id = id;
		this.userName = userName;
	}

	@Override
	public long getID() {
		return id;
	}

	@Override
	public String getUserName() {
		return userName;
	}

	@Override
	public void setID(long id) {
		this.id = id;
	}

	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
