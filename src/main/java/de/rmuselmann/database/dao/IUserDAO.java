package de.rmuselmann.database.dao;

import java.util.ArrayList;
import java.util.List;

import de.rmuselmann.entity.IUser;


public interface IUserDAO {
	/**
	 * F�gt einen User in die Datenbank ein. Beim zur�ckgegebenen User ist die
	 * ID gesetzt.
	 * 
	 * @param {@link IUser} User
	 * @return eingef�gter User mit ID
	 */
	public IUser addUser(IUser user);

	/**
	 * Gibt ein User anhand seines Namens zur�ck.
	 * 
	 * @param {@link String} userName
	 * @return {@link IUser} user
	 */
	public IUser getUser(String userName);

	/**
	 * Gibt ein User anhand seiner ID zur�ck.
	 * 
	 * @param {@link String} userID
	 * @return {@link IUser} user
	 */
	public IUser getUserByID(long userID);

	/**
	 * Gibt eine Liste aller Useren zur�ck.
	 * 
	 * @return {@link List} aller Useren
	 */
	public ArrayList<IUser> readDatabase();
}
