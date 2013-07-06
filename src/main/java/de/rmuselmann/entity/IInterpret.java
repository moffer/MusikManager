package de.rmuselmann.entity;

public interface IInterpret extends IFields {

	@Override
	public boolean equals(Object obj);

	/**
	 * Gibt den Namen des Interpreten zur�ck.
	 * 
	 * @return Namen des Interpreten.
	 */
	public String getInterpretName();

	@Override
	public int hashCode();

	/**
	 * Setzt den Namen des Interpreten.
	 * 
	 * @param interpretName
	 */
	public void setInterpretName(String interpretName);
}
