package de.rmuselmann.entity.dto;

public interface ITanzartDTO extends IFieldsDTO {

	@Override
	public boolean equals(Object obj);

	public String getTanzartName();

	@Override
	public int hashCode();

	public void setTanzartName(String tanzartName);

	@Override
	public String toString();
}
