package de.rmuselmann.database.dao;

import java.util.List;

import de.rmuselmann.entity.IAlbum;


public interface IAlbumDAO {

	/**
	 * F�gt ein Album in die Datenbank ein. Beim zur�ckgegebenen Album ist die
	 * ID gesetzt.
	 * 
	 * @param {@link IAlbum} album
	 * @return eingef�gtes Album mit ID (oder null, bei Fehler)
	 */
	public IAlbum addAlbum(IAlbum album);

	/**
	 * Gibt ein Album anhand seines Namens zur�ck.
	 * 
	 * @param {@link String} albumName
	 * @return {@link IAlbum} album
	 */
	public IAlbum getAlbum(String albumName);

	/**
	 * Gibt ein Album anhand seiner ID zur�ck.
	 * 
	 * @param {@link String} albumID
	 * @return {@link IAlbum} album
	 */
	public IAlbum getAlbumByID(long albumID);

	/**
	 * Gibt eine Liste aller Alben zur�ck.
	 * 
	 * @return {@link List} aller Alben
	 */
	public List<IAlbum> readDatabase();
}