package de.rmuselmann.entity;

public interface ITanzart extends IFields {

	@Override
	public boolean equals(Object obj);

	/**
	 * Gibt den Namen der Tanzart zur�ck.
	 * 
	 * @return
	 */
	public String getTanzartName();

	@Override
	public int hashCode();

	/**
	 * Setzt den Namen der Tanzart.
	 * 
	 * @param tanzartName
	 */
	public void setTanzartName(String tanzartName);

	@Override
	public String toString();
}
