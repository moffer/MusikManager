package de.rmuselmann.gui.extraClasses;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.sun.javafx.collections.ObservableListWrapper;

import de.rmuselmann.entity.ISong;
import de.rmuselmann.gui.dialogs.GUIStart;
import de.rmuselmann.gui.events.IProgressListener;
import de.rmuselmann.gui.events.ListEvent;
import de.rmuselmann.gui.events.ListEvent.ListChangeOption;
import de.rmuselmann.gui.events.ProgressWaitDialogListener;
import de.rmuselmann.gui.fxml.StageLoader;
import de.rmuselmann.gui.fxml.dialogs2.CannotConnectDialogV2;
import de.rmuselmann.gui.fxml.dialogs2.CannotConnectDialogV2.CannotConnectOption;
import de.rmuselmann.gui.fxml.dialogs2.WaitDialogV2;

public class DeleteFiles extends Task<Boolean> {

	private ArrayList<ISong> listOfSongs;
	private WaitDialogV2 waitDialog;
	private Stage primaryStage;
	private IProgressListener progressListener;

	private final static Logger LOGGER = Logger.getLogger(GUIStart.class
			.getName());

	public DeleteFiles(ArrayList<ISong> listOfSongs,
			final WaitDialogV2 waitDialog, Stage primaryStage) {
		this.listOfSongs = listOfSongs;
		this.waitDialog = waitDialog;
		this.primaryStage = primaryStage;
		this.progressListener = new ProgressWaitDialogListener(waitDialog);
		addListener();
	}

	private void addListener() {
		this.valueProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				if (newValue) {
					GUIStart.getSongs().removeAll(listOfSongs);
					GUIStart.getListListener().handleEvent(
							new ListEvent(GUIStart.class,
									new ObservableListWrapper<ISong>(
											listOfSongs),
									ListChangeOption.DELETED));
				} else {
					final CannotConnectDialogV2 can = (CannotConnectDialogV2) StageLoader
							.load(new CannotConnectDialogV2().getFXMLPath());
					can.setData(primaryStage);
					can.show();
					can.setOnHiding(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent arg0) {
							if (can.getOption().equals(
									CannotConnectOption.AGAIN)) {
								waitDialog.show();
								DeleteFiles task = new DeleteFiles(listOfSongs,
										waitDialog, primaryStage);
								// task.addListener();

								new Thread(task).start();
							}
						};
					});
				}
				waitDialog.close();
			}
		});
	}

	@Override
	protected Boolean call() {
		try {
			LOGGER.log(Level.INFO, "Starte L�schvorgang.");
			GUIStart.getLogic().deleteSongList(listOfSongs, progressListener);
			LOGGER.log(Level.INFO, "L�schvorgang beendet.");
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
