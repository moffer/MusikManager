package de.rmuselmann.beanFacade;

import java.util.List;

import de.rmuselmann.database.dao.impl.ConnectionException;
import de.rmuselmann.entity.ISong;
import de.rmuselmann.entity.impl.TableConfiguration;
import de.rmuselmann.gui.events.IProgressListener;
import de.rmuselmann.logic.IAlbumLogic;
import de.rmuselmann.logic.IInterpretLogic;
import de.rmuselmann.logic.ISongLogic;
import de.rmuselmann.logic.ITanzartLogic;
import de.rmuselmann.logic.impl.LogicFac;
import de.rmuselmann.logic.impl.TableConfigurationLogic;

public class SongBeanFacade {
	private TableConfigurationLogic tableConfig;
	private ISongLogic songLogic;

	private List<TableConfiguration> tableConfigurations;
	private List<ISong> songs;

	protected SongBeanFacade() throws ConnectionException {
		tableConfig = LogicFac.getNewTableConfigurationLogic();
		ITanzartLogic tanzartLogic = LogicFac.getNewTanzartLogic();
		IAlbumLogic albumLogic = LogicFac.getNewAlbumLogic();
		IInterpretLogic interpretLogic = LogicFac.getNewInterpretLogic();
		songLogic = LogicFac.getNewSongLogic(albumLogic, tanzartLogic,
				interpretLogic);
	}

	public List<TableConfiguration> updateTableConfigurations() {
		try {
			return tableConfigurations = tableConfig
					.getTableConfigurationOfTable("song");
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<TableConfiguration> getTableConfigurations() {
		if (tableConfigurations == null || tableConfigurations.size() <= 0) {
			return updateTableConfigurations();
		}
		return tableConfigurations;
	}

	public List<ISong> updateSongs(IProgressListener b) {
		return songs = songLogic.readSongDatabase(b);
	}

	public List<ISong> getSongs(IProgressListener b) {
		if (songs == null || songs.size() <= 0) {
			return updateSongs(b);
		}
		return songs;
	}

}
