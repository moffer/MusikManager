package de.rmuselmann.logic.impl;

/**
 * This class provides a generic interface to the tag libraries, allowing a
 * range of simple reading functions to be performed.
 * 
 * $Id: AbstractTagReader.java,v 1.1 2005/08/01 15:52:35 eythian Exp $
 * 
 * @author robin
 */
public abstract class AbstractTagReader {

	/**
	 * Gets the album of a track
	 * 
	 * @return the album name, or null if it doesn't exist or couldn't be read
	 */
	public abstract String getAlbum();

	/**
	 * Gets the artist of a track
	 * 
	 * @return the artist, or null if it doesn't exist or couldn't be read
	 */
	public abstract String getArtist();

	/**
	 * Gets the bit rate for a track
	 * 
	 * @return the bit rate, or -1 if it can't be read
	 */
	public abstract int getBitrate();

	/**
	 * Gets the genre of a track
	 * 
	 * @return the genre, or null if it doesn't exist or couldn't be read
	 */
	public abstract String getGenre();

	/**
	 * The length of a track in seconds
	 * 
	 * @return the length of the track, or -1 if it couldn't be read
	 */
	public abstract long getLength();

	/**
	 * Gets the title of a track
	 * 
	 * @return the title, or null if it doesn't exist or couldn't be read
	 */
	public abstract String getTitle();

	/**
	 * Gets the track number of a track. This is a string because it is often in
	 * the form of a single number, or as 'tracknum/total tracks'.
	 * 
	 * @return the track number, or null if it doesn't exist or couldn't be read
	 */
	public abstract String getTracknumber();

	/**
	 * Whether this track is a VBR track or not.
	 * 
	 * @return true if it is VBR, false otherwise.
	 * @throws UnsupportedOperationException
	 *             if this can't be determined by the tag reading library
	 */
	public abstract boolean isVBR() throws UnsupportedOperationException;

	/**
	 * Provides a formatted breakdown of this class, suitable for printing
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Title: ");
		String value = getTitle();
		if (value == null) {
			sb.append("Unknown");
		} else {
			sb.append(value);
		}
		sb.append("\n");

		sb.append("Artist: ");
		value = getArtist();
		if (value == null) {
			sb.append("Unknown");
		} else {
			sb.append(value);
		}
		sb.append("\n");

		sb.append("Album: ");
		value = getAlbum();
		if (value == null) {
			sb.append("Unknown");
		} else {
			sb.append(value);
		}
		sb.append("\n");

		sb.append("Genre: ");
		value = getGenre();
		if (value == null) {
			sb.append("Unknown");
		} else {
			sb.append(value);
		}
		sb.append("\n");

		sb.append("Track num: ");
		value = getTracknumber();
		if (value == null) {
			sb.append("Unknown");
		} else {
			sb.append(value);
		}
		sb.append("\n");

		sb.append("Bitrate: ");
		int iValue = getBitrate();
		if (iValue == -1) {
			sb.append("Unknown");
		} else {
			sb.append(iValue);
		}
		sb.append("\n");

		sb.append("Length: ");
		long lValue = getLength();
		if (lValue == -1) {
			sb.append("Unknown");
		} else {
			sb.append(lValue);
		}
		sb.append("\n");

		sb.append("VBR? ");
		try {
			if (isVBR()) {
				sb.append("Yes");
			} else {
				sb.append("No");
			}
		} catch (UnsupportedOperationException e) {
			sb.append("Unknown");
		}
		sb.append("\n");

		return sb.toString();
	}
}