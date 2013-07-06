package de.rmuselmann.entity;

public interface IUser extends IFields {
	@Override
	public boolean equals(Object obj);

	/**
	 * Gibt den Namen des User zur�ck.
	 * 
	 * @return Namen des Users.
	 */
	public String getUserName();

	@Override
	public int hashCode();

	/**
	 * Setzt den Namen des Users.
	 * 
	 * @param userName
	 */
	public void setUserName(String userName);

	@Override
	public String toString();
}
