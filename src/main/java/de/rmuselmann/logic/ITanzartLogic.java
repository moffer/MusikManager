package de.rmuselmann.logic;

import java.util.List;

import de.rmuselmann.entity.ITanzart;


public interface ITanzartLogic {
	/**
	 * F�gt eine Tanzart ein. Bei der zur�ckgegebenen Tanzart ist die ID
	 * gesetzt.
	 * 
	 * @param {@link ITanzart} tanzart
	 * @return {@link ITanzart} tanzart mit gesetzter ID.
	 */
	public ITanzart addTanzart(ITanzart tanzart);

	/**
	 * Gibt eine Liste aller Tanzarten zur�ck.
	 * 
	 * @return {@link List<ITanzart>} alben
	 */
	public List<ITanzart> getAllTanzart();

	/**
	 * Gibt eine Tanzart anhand des Namens zur�ck. Sollte sie nicht vorhanden
	 * sein, wird sie in die Datenbank eingef�gt.
	 * 
	 * @param tanzartName
	 * @return {@link ITanzart} tanzart
	 */
	public ITanzart getTanzart(String tanzartName);

	/**
	 * Gibt ein Tanzart anhand der ID zur�ck.
	 * 
	 * @param id
	 * @return {@link ITanzart} tanzart
	 */
	public ITanzart getTanzartByID(long id);

}