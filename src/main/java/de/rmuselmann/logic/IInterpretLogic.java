package de.rmuselmann.logic;

import java.util.List;

import de.rmuselmann.entity.IInterpret;


public interface IInterpretLogic {
	/**
	 * F�gt ein Interpret ein. Beim zur�ckgegebenen Interpeten ist die ID
	 * gesetzt.
	 * 
	 * @param {@link IInterpret} interpret
	 * @return {@link IInterpret} interpret mit gesetzter ID.
	 */
	public IInterpret addInterpret(IInterpret interpret);

	/**
	 * Gibt eine Liste aller Interprten zur�ck.
	 * 
	 * @return {@link List<IInterpet>} interpreten
	 */
	public List<IInterpret> getAllInterpret();

	/**
	 * Gibt einen Interpreten anhand des Namens zur�ck. Sollte er nicht
	 * vorhanden sein, wird es in die Datenbank eingef�gt.
	 * 
	 * @param interpretName
	 * @return {@link IInterpret} interpret
	 */
	public IInterpret getInterpret(String interpretName);

	/**
	 * Gibt ein Interpreten anhand der ID zur�ck.
	 * 
	 * @param id
	 * @return {@link IInterpret} interpret
	 */
	public IInterpret getInterpretByID(long id);

}