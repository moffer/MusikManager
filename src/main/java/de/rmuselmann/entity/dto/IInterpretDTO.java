package de.rmuselmann.entity.dto;

public interface IInterpretDTO extends IFieldsDTO {

	@Override
	public boolean equals(Object obj);

	public String getInterpretName();

	@Override
	public int hashCode();

	public void setInterpretName(String interpretName);
}
