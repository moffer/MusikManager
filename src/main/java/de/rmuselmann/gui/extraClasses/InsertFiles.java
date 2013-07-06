package de.rmuselmann.gui.extraClasses;

import java.io.File;
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
import de.rmuselmann.gui.dialogs.AfterInsertDialog;
import de.rmuselmann.gui.dialogs.GUIStart;
import de.rmuselmann.gui.events.IProgressListener;
import de.rmuselmann.gui.events.ListEvent;
import de.rmuselmann.gui.events.ListEvent.ListChangeOption;
import de.rmuselmann.gui.events.ProgressEvent;
import de.rmuselmann.gui.fxml.StageLoader;
import de.rmuselmann.gui.fxml.dialogs2.CannotConnectDialogV2;
import de.rmuselmann.gui.fxml.dialogs2.CannotConnectDialogV2.CannotConnectOption;
import de.rmuselmann.gui.fxml.dialogs2.WaitDialogV2;

public class InsertFiles extends Task<Boolean> {
	private ArrayList<File> listOfMP3Files;
	private WaitDialogV2 waitDialog;
	private Stage primaryStage;
	private IProgressListener prog;
	@SuppressWarnings("rawtypes")
	private ArrayList<ArrayList> lists;
	private InsertFiles task = this;
	private final static Logger LOGGER = Logger.getLogger(GUIStart.class
			.getName());

	public InsertFiles(ArrayList<File> listOfMP3Files,
			final WaitDialogV2 waitDialog, Stage primaryStage) {
		this.listOfMP3Files = listOfMP3Files;
		this.waitDialog = waitDialog;
		this.primaryStage = primaryStage;
		this.prog = new IProgressListener() {

			@Override
			public void handleEvent(ProgressEvent e) {

				waitDialog.setProgress(e.getPercent());
				if (task.isCancelled()) {
					GUIStart.getLogic().isCanceled(true);
					waitDialog
							.setInfoText("Bitte warten Sie bis alle Daten hochgeladen wurden! "
									+ e.getText());
				}
			}
		};
		addListener();
	}

	@SuppressWarnings("unchecked")
	private void addListener() {
		this.valueProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				if (newValue) {

					if (lists != null) {
						ArrayList<ISong> sucessList = lists.get(0);
						ArrayList<File> listOfUniqueSongs = lists.get(1);
						ArrayList<File> listOfFailedSongs = lists.get(2);
						new AfterInsertDialog(primaryStage, sucessList,
								listOfUniqueSongs, listOfFailedSongs);
						GUIStart.getSongs().addAll(
								new ObservableListWrapper<ISong>(sucessList));
						if (sucessList.size() > 0) {
							GUIStart.getListListener().handleEvent(
									new ListEvent(GUIStart.class,
											new ObservableListWrapper<ISong>(
													sucessList),
											ListChangeOption.ADDED));
						}
					}
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
								InsertFiles task = new InsertFiles(
										listOfMP3Files, waitDialog,
										primaryStage);
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
			LOGGER.log(Level.INFO, "Starte Upload.");
			lists = GUIStart.getLogic().uploadSongListFromFile(listOfMP3Files,
					prog);
			LOGGER.log(Level.INFO, "Upload beendet.");
		} catch (IOException e) {
			return false;
		}
		return true;
	};
}
