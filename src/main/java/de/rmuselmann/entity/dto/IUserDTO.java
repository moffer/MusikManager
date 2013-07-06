package de.rmuselmann.entity.dto;

public interface IUserDTO extends IFieldsDTO {
	@Override
	public boolean equals(Object obj);

	public String getUserName();

	@Override
	public int hashCode();

	public void setUserName(String userName);

	@Override
	public String toString();
}
