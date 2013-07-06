package de.rmuselmann.database.dao;

import java.util.List;

import de.rmuselmann.entity.IInterpret;


public interface IInterpretDAO {

	/**
	 * F�gt einen neuen Interpreten ein. Die ID wird dabei berechnet. Gibt den
	 * hinzugef�gten Interpret bei Erfolg zur�ck. Sonst wird null zur�ckgegeben
	 * 
	 * @param interpret
	 * @return bei Erfolg hinzugef�gter Interpret sonst: null
	 */
	public IInterpret addInterpret(IInterpret interpret);

	/**
	 * Sucht den Interpret mit dem Namen heraus und gibt ihn zur�ck. Falls kein
	 * Interpret gefunden wird, wird ein neuer hinzugef�gt und dieser
	 * zur�ckgegeben.
	 * 
	 * @param interpretName
	 * @return interpret
	 */
	public IInterpret getInterpret(String interpretName);

	/**
	 * Durchsucht die Datenbank nach der ID und gibt den entsprechenden Wert
	 * (Interpret) zur�ck.
	 * 
	 * @param interpretID
	 */
	public IInterpret getInterpretByID(long interpretID);

	/**
	 * Liest die Datenbank aus. Gibt eine ArrayList<Interpret>(Interpret)
	 * zur�ck.
	 * 
	 * @return List<Interpret>
	 */
	public List<IInterpret> readDatabase();

}