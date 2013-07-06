package de.rmuselmann.entity.dto;

public interface IFieldsDTO {

	@Override
	public boolean equals(Object obj);

	/**
	 * Gibt die ID des Feldes zur�ck.
	 * 
	 * @return
	 */
	public long getID();

	@Override
	public int hashCode();

	/**
	 * Setzt die ID des Feldes.
	 * 
	 * @param id
	 */
	public void setID(long id);

	@Override
	public String toString();
}
