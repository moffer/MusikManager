package de.rmuselmann.logic;

import java.util.List;

import de.rmuselmann.entity.IAlbum;


public interface IAlbumLogic {

	/**
	 * F�gt ein Album ein. Beim zur�ckgegebenen Album ist die ID gesetzt.
	 * 
	 * @param {@link IAlbum} album
	 * @return {@link IAlbum} album mit gesetzter ID.
	 */
	public IAlbum addAlbum(IAlbum album);

	/**
	 * Gibt ein Album anhand des Namens zur�ck. Sollte es nicht vorhanden sein,
	 * wird es in die Datenbank eingef�gt. Sollte ein leerer String �bergeben
	 * werden, wird ein Album mit einem leeren String local zur�ckgegeben
	 * 
	 * @param albumName
	 * @return {@link IAlbum} album <br>
	 *         - bei albumName = "" --> album.getAlbumName = "";
	 */
	public IAlbum getAlbum(String albumName);

	/**
	 * Gibt ein Album anhand der ID zur�ck.
	 * 
	 * @param id
	 * @return {@link IAlbum} album
	 */
	public IAlbum getAlbumByID(long id);

	/**
	 * Gibt eine Liste aller Alben zur�ck.
	 * 
	 * @return {@link List<IAlbum>} alben
	 */
	public List<IAlbum> getAllAlbums();
}