package de.rmuselmann.logic.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.rmuselmann.beanFacade.BeanFactory;
import de.rmuselmann.database.dao.ISongDAO;
import de.rmuselmann.database.dao.impl.ConnectionException;
import de.rmuselmann.database.dao.impl.ConnectionManager;
import de.rmuselmann.database.dao.impl.DAOFactory;
import de.rmuselmann.entity.IAlbum;
import de.rmuselmann.entity.IFields.NullObjects;
import de.rmuselmann.entity.IInterpret;
import de.rmuselmann.entity.ISong;
import de.rmuselmann.entity.ITanzart;
import de.rmuselmann.entity.IUser;
import de.rmuselmann.entity.impl.EntityFactory;
import de.rmuselmann.entity.impl.Song;
import de.rmuselmann.gui.events.IProgressListener;
import de.rmuselmann.gui.events.IUpdateEventListener;
import de.rmuselmann.gui.events.ProgressEvent;
import de.rmuselmann.gui.events.UpdateEvent;
import de.rmuselmann.gui.extraClasses.InsertFiles;
import de.rmuselmann.logic.IAlbumLogic;
import de.rmuselmann.logic.IInterpretLogic;
import de.rmuselmann.logic.ISongLogic;
import de.rmuselmann.logic.ITanzartLogic;
import de.rmuselmann.logic.exceptions.UniqueConstraintException;
import entagged.audioformats.AudioFile;
import entagged.audioformats.exceptions.CannotReadException;
import entagged.audioformats.exceptions.CannotWriteException;
import entagged.audioformats.mp3.Mp3FileReader;
import entagged.audioformats.mp3.Mp3FileWriter;

public class SongLogic implements ISongLogic {
	private ISongDAO songDAO;
	private boolean isTaskCanceled;
	private IAlbumLogic albumLogic;
	private ITanzartLogic tanzartLogic;
	private IInterpretLogic interpretLogic;

	private final static Logger LOGGER = Logger.getLogger(InsertFiles.class
			.getName());

	protected SongLogic(IAlbumLogic albumLogic, ITanzartLogic tanzartLogic,
			IInterpretLogic interpretLogic) throws ConnectionException {
		songDAO = DAOFactory.getNewSongDAO(ConnectionManager
				.getDatabaseConnection());
		this.albumLogic = albumLogic;
		this.tanzartLogic = tanzartLogic;
		this.interpretLogic = interpretLogic;
	}

	@Override
	public ISong changeSong(ISong change) throws IOException {
		if (!change.equals(NullObjects.SONG.getObject())) {
			if (change.isFileExisting()) {
				File tempFile = new File("../temp.mp3");
				downloadSong(change, tempFile);
				try {
					AudioFile file = new Mp3FileReader().read(tempFile);
					file.getTag().setArtist(
							change.getInterpret().getInterpretName());
					file.getTag().setTitle(change.getTitle());
					file.getTag().setAlbum(change.getAlbum().getAlbumName());
					new Mp3FileWriter().write(file);
					uploadSong(change, file);
					tempFile.deleteOnExit();
				} catch (CannotReadException e) {
					e.printStackTrace();
				} catch (CannotWriteException e) {
					e.printStackTrace();
				}
			}
			change = songDAO.changeSong(change);
			return change;
		}
		return null;
	}

	@Override
	public boolean checkUploadingFile(File file, ISong song) {
		if (file != null) {
			EntaggedTagReader tagReader;
			try {
				tagReader = new EntaggedTagReader(file);
				if (song.getInterpret().getInterpretName() != null
						&& !song.getInterpret().getInterpretName()
								.equals(tagReader.getArtist())) {
					return false;
				} else if (!song.getTitle().equals(tagReader.getTitle())) {
					return false;
				} else if (song.getAlbum().getAlbumName() != null
						&& !song.getAlbum().getAlbumName()
								.equals(tagReader.getAlbum())) {
					return false;
				}
			} catch (CannotReadException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

	@Override
	public void deleteSong(ISong song) throws IOException {
		if (song.isFileExisting()) {
			DAOFactory.getNewFileDAO().deleteFile(song.getID());
		}
		songDAO.deleteSong(song.getID());
	}

	@Override
	public void deleteSongList(ArrayList<ISong> songList,
			IProgressListener progressListener) throws IOException {

		double counter = 1;
		for (ISong song : songList) {
			deleteSong(song);
			double percentOfDone = counter / songList.size();
			progressListener
					.handleEvent(new ProgressEvent(this, percentOfDone));
			counter++;
		}
	}

	@Override
	public boolean downloadSong(ISong song, File file) throws IOException {
		if (song.isFileExisting()) {
			DAOFactory.getNewFileDAO().downloadSong(song, file);
			return true;
		} else {
			return false;
		}
	}

	public ArrayList<ISong> downloadSongList(ArrayList<ISong> songList,
			File directory, IProgressListener listener) throws IOException {
		ArrayList<ISong> failedSongs = new ArrayList<>();
		double counter = 0;
		for (ISong song : songList) {
			// falls downloadSong false zur�ckgibt, ist keine Datei im Server
			// von diesem Song vorhanden
			if (!downloadSong(song,
					new File(directory.getPath() + "/" + song.getInterpret()
							+ " - " + song.getTitle()))) {
				failedSongs.add(song);
			}
			counter++;
			listener.handleEvent(new ProgressEvent(this, counter
					/ songList.size()));
		}

		return failedSongs;
	}

	@Override
	public int getDatabaseSize() {
		return songDAO.getDatabaseSize();
	}

	@Override
	public ISong insertSong(ISong song) throws UniqueConstraintException {
		if (!song.equals(NullObjects.SONG.getObject())) {
			return songDAO.addSong(song);
		} else {
			return null;
		}
	}

	@Override
	public void isCanceled(boolean isTaskCanceled) {
		this.isTaskCanceled = isTaskCanceled;
	}

	@Override
	public List<ISong> readSongDatabase(final IProgressListener b) {
		List<ISong> list = new ArrayList<>();
		IUpdateEventListener updateListener = null;
		if (b != null) {
			updateListener = new IUpdateEventListener() {
				double counter = 1;
				int size = getDatabaseSize();

				@Override
				public void handleEvent(UpdateEvent updateEvent) {
					double percentOfDone = counter / size;
					b.handleEvent(new ProgressEvent(this, percentOfDone));
					counter++;
				}
			};
		}
		list = songDAO.readSongDatabase(updateListener);
		Collections.sort(list, new Song.SongComparator());
		return list;
	}

	@Override
	public ISong uploadSong(ISong song, File selectedFile) throws IOException {

		if (selectedFile != null) {

			String fileString = selectedFile.getPath();
			File localFile = new File(fileString);

			if (DAOFactory.getNewFileDAO().uploadFile(song, localFile)) {
				song = songDAO.setFileToSong(song);
			}
		}
		return song;
	}

	@Override
	public ISong uploadSongFromFile(File file, ISong song) throws IOException,
			UniqueConstraintException, CannotReadException {

		EntaggedTagReader tagReader = new EntaggedTagReader(file);
		String title = tagReader.getTitle();

		IInterpret interpret = this.interpretLogic.getInterpret(tagReader
				.getArtist());
		IAlbum album = this.albumLogic.getAlbum(tagReader.getAlbum());
		ITanzart tanzart;
		if (song != null && song.getTanzart() != null) {
			tanzart = song.getTanzart();
		} else {
			tanzart = tanzartLogic.getTanzart("unbekannt");
		}
		IUser user = BeanFactory.getSettingsBeanFacade().getUser();

		ISong newSong = EntityFactory.getNewSong(-1, title, interpret, album,
				tanzart, user, false, new Date());
		if (song == null) {
			newSong = insertSong(newSong);
		} else {
			newSong.setID(song.getID());
			newSong = changeSong(newSong);
		}
		try {
			return uploadSong(newSong, file);
		} catch (IOException e) {
			deleteSong(newSong);
			throw e;
		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	public ArrayList<ArrayList> uploadSongListFromFile(List<File> fileList,
			IProgressListener b) throws IOException {
		double counter = 1;
		if (fileList != null) {
			ArrayList<ArrayList> returnList = new ArrayList<>();
			ArrayList<ISong> sucessList = new ArrayList<>();
			ArrayList<File> failedListUnique = new ArrayList<>();
			ArrayList<File> failedListReader = new ArrayList<>();
			for (File file : fileList) {
				double percentOfDone = counter / fileList.size();
				b.handleEvent(new ProgressEvent(this, percentOfDone, file
						.getAbsolutePath()));
				if (isTaskCanceled) {
					LOGGER.log(Level.INFO, "Task abbrechen");
					break;
				}
				try {
					sucessList.add(uploadSongFromFile(file, null));
				} catch (UniqueConstraintException e) {
					failedListUnique.add(file);
				} catch (CannotReadException e) {
					failedListReader.add(file);
				}
				counter++;
			}
			returnList.add(sucessList);
			returnList.add(failedListUnique);
			returnList.add(failedListReader);
			isTaskCanceled = false;
			return returnList;
		}
		return null;
	}
}
