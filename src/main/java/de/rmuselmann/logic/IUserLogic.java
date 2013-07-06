package de.rmuselmann.logic;

import java.util.List;

import de.rmuselmann.entity.IUser;


public interface IUserLogic {
	/**
	 * F�gt eine User ein. Bei der zur�ckgegebenen User ist die ID gesetzt.
	 * 
	 * @param {@link IUser} user
	 * @return {@link IUser} user mit gesetzter ID.
	 */
	public IUser addUser(IUser user);

	/**
	 * Gibt eine Liste aller Useren zur�ck.
	 * 
	 * @return {@link List<IUser>} user
	 */
	public List<IUser> getAllUser();

	/**
	 * Gibt eine User anhand des Namens zur�ck. Sollte sie nicht vorhanden sein,
	 * wird sie in die Datenbank eingef�gt.
	 * 
	 * @param userName
	 * @return {@link IUser} user
	 */
	public IUser getUser(String userName);

	/**
	 * Gibt ein User anhand der ID zur�ck.
	 * 
	 * @param id
	 * @return {@link IUser} user
	 */
	public IUser getUserByID(long id);

}
