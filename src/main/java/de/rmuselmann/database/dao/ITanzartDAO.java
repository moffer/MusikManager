package de.rmuselmann.database.dao;

import java.util.ArrayList;
import java.util.List;

import de.rmuselmann.entity.ITanzart;


public interface ITanzartDAO {

	/**
	 * F�gt eine Tanzart in die Datenbank ein. Bei der zur�ckgegebenen Tanzart
	 * ist die ID gesetzt.
	 * 
	 * @param {@link ITanzart} tanzart
	 * @return eingef�gte Tanzart mit ID
	 */
	public ITanzart addTanzart(ITanzart tanzart);

	/**
	 * Gibt ein Tanzart anhand seines Namens zur�ck.
	 * 
	 * @param {@link String} tanzartName
	 * @return {@link ITanzart} tanzart
	 */
	public ITanzart getTanzart(String tanzartName);

	/**
	 * Gibt ein Tanzart anhand seiner ID zur�ck.
	 * 
	 * @param {@link String} tanzartID
	 * @return {@link ITanzart} tanzart
	 */
	public ITanzart getTanzartByID(long tanzartID);

	/**
	 * Gibt eine Liste aller Tanzarten zur�ck.
	 * 
	 * @return {@link List} aller Tanzarten
	 */
	public ArrayList<ITanzart> readDatabase();
}