package de.rmuselmann.logic.impl;

import java.io.File;
import java.util.List;

import entagged.audioformats.AudioFile;
import entagged.audioformats.AudioFileIO;
import entagged.audioformats.Tag;
import entagged.audioformats.exceptions.CannotReadException;
import entagged.audioformats.generic.TagTextField;

/**
 * 
 * 
 * $Id: EntaggedTagReader.java,v 1.1 2005/08/01 15:52:35 eythian Exp $
 * 
 * @author robin
 */
public class EntaggedTagReader extends AbstractTagReader {

	private AudioFile audioFile;
	private Tag tag;

	public EntaggedTagReader(File file) throws CannotReadException {
		this.audioFile = AudioFileIO.read(file);
		this.tag = audioFile.getTag();
	}

	@Override
	public String getAlbum() {
		List<?> list = tag.getAlbum();
		if (list.size() == 0) {
			return "";
		}
		String album = ((TagTextField) list.get(0)).getContent();
		if ("".equals(album)) {
			return "";
		}
		return album;
	}

	@Override
	public String getArtist() {
		List<?> list = tag.getArtist();
		if (list.size() == 0) {
			return "";
		}
		String artist = ((TagTextField) list.get(0)).getContent();
		if ("".equals(artist)) {
			return "";
		}
		return artist;
	}

	@Override
	public int getBitrate() {
		int bitrate = audioFile.getBitrate();
		if (bitrate <= 0) {
			return -1;
		}
		return bitrate;
	}

	@Override
	public String getGenre() {
		List<?> list = tag.getGenre();
		if (list.size() == 0) {
			return "";
		}
		String genre = ((TagTextField) list.get(0)).getContent();
		if ("".equals(genre)) {
			return "";
		}
		return genre;
	}

	@Override
	public long getLength() {
		long length = audioFile.getLength();
		if (length < 0) {
			return -1;
		}
		return length;
	}

	@Override
	public String getTitle() {
		List<?> list = tag.getTitle();
		if (list.size() == 0) {
			return "";
		}
		String title = ((TagTextField) list.get(0)).getContent();
		if ("".equals(title)) {
			return "";
		}
		return title;
	}

	@Override
	public String getTracknumber() {
		List<?> list = tag.getTrack();
		if (list.size() == 0) {
			return "";
		}
		String trackNum = ((TagTextField) list.get(0)).getContent();
		if ("".equals(trackNum)) {
			return "";
		}
		return trackNum;
	}

	@Override
	public boolean isVBR() throws UnsupportedOperationException {
		boolean vbr = audioFile.isVbr();
		return vbr;
	}

}