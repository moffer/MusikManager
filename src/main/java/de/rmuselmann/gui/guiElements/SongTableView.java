package de.rmuselmann.gui.guiElements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import de.rmuselmann.entity.ISong;
import de.rmuselmann.gui.dialogs.ChangeSongDialog;
import de.rmuselmann.gui.dialogs.DeleteDialog;
import de.rmuselmann.gui.dialogs.ErrorOutputDialog;
import de.rmuselmann.gui.dialogs.GUIStart;
import de.rmuselmann.gui.dialogs.SureDialog;
import de.rmuselmann.gui.dialogs.SureDialog.Option;
import de.rmuselmann.gui.dialogs.UniqueConstraintDialog;
import de.rmuselmann.gui.extraClasses.DeleteFiles;
import de.rmuselmann.gui.extraClasses.InsertFiles;
import de.rmuselmann.gui.extraClasses.LoadSongDatabase;
import de.rmuselmann.gui.extraClasses.TableClass;
import de.rmuselmann.gui.fxml.Loader;
import de.rmuselmann.gui.fxml.dialogs2.CannotConnectDialogV2;
import de.rmuselmann.gui.fxml.dialogs2.CannotConnectDialogV2.CannotConnectOption;
import de.rmuselmann.gui.fxml.dialogs2.WaitDialogV2;
import de.rmuselmann.logic.IFoundListener;
import de.rmuselmann.logic.exceptions.UniqueConstraintException;
import de.rmuselmann.logic.impl.FileFinder;
import entagged.audioformats.exceptions.CannotReadException;

public class SongTableView extends TableClass<ISong> {
	SongTableView tableView = this;
	private final static Logger LOGGER = Logger.getLogger(GUIStart.class
			.getName());

	public SongTableView(ArrayList<String> columns, ObservableList<ISong> data) {
		super(columns, data);
	}

	/**
	 * @param primaryStage
	 * @param tableView
	 * @param delete
	 */
	public void deleteSong(final Stage primaryStage,
			final TableClass<ISong> tableView, final MenuItem delete) {
		if (tableView.getSelectionModel().getSelectedItem() != null) {
			LOGGER.log(Level.INFO, "Öffne DeleteDialog.");
			final DeleteDialog deleteDialog = new DeleteDialog(primaryStage);
			deleteDialog.setOnHiding(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent arg0) {
					LOGGER.log(Level.INFO,
							"CannotConnectDialog.close() aufgerufen.");
					if (deleteDialog.isSure()) {
						WaitDialogV2 waitDialog = (WaitDialogV2) Loader
								.load(new WaitDialogV2().getFXMLPath());
						waitDialog
								.setData(
										primaryStage,
										"Bitte warten",
										"Bitte warten Sie, bis alle Lieder gel�scht sind!",
										true);

						ArrayList<ISong> selectedItems = new ArrayList<>();
						for (ISong song : tableView.getSelectionModel()
								.getSelectedItems()) {
							if (!selectedItems.contains(song)) {
								selectedItems.add(song);
							}
						}
						DeleteFiles task = new DeleteFiles(selectedItems,
								waitDialog, primaryStage);
						LOGGER.log(Level.INFO,
								"Starte neuen Thread DeleteFiles.");
						new Thread(task).start();
					}
				};
			});
		}
	}

	public void doIt(final Stage primaryStage) {

		this.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		this.isSongTable(true);
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				ISong selectedItem = tableView.getSelectionModel()
						.getSelectedItem();
				if (event.getClickCount() == 2 && selectedItem != null
						&& event.getButton().equals(MouseButton.PRIMARY)) {
					new ChangeSongDialog(primaryStage, tableView, selectedItem);
				}
			}
		});
	}

	private void download(final File directory, final Stage primaryStage) {
		final ArrayList<ISong> failedSongs = new ArrayList<>();
		Task<Boolean> task = new Task<Boolean>() {

			@Override
			protected Boolean call() throws Exception {
				try {
					for (ISong song : tableView.getSelectionModel()
							.getSelectedItems()) {
						File file = new File(directory.getPath() + "/"
								+ song.getInterpret() + " - " + song.getTitle()
								+ ".mp3");
						if (!GUIStart.getLogic().downloadSong(song, file)) {
							failedSongs.add(song);
						}
					}
				} catch (IOException e) {
					LOGGER.log(Level.INFO,
							"Versuch gescheitert: " + e.getMessage());
					final CannotConnectDialogV2 can = (CannotConnectDialogV2) Loader
							.load(new CannotConnectDialogV2().getFXMLPath());
					can.setData(primaryStage);
					can.show();
					can.setOnHiding(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent arg0) {
							if (can.getOption().equals(
									CannotConnectOption.AGAIN)) {
								LOGGER.log(Level.INFO, "Erneuter Versuch.");
								download(directory, primaryStage);
							}
						};
					});
				}
				return true;
			}

		};
		// TODO with Status
		final WaitDialogV2 waitDialog = (WaitDialogV2) Loader
				.load(new WaitDialogV2().getFXMLPath());
		waitDialog.setData(primaryStage, "Bitte warten",
				"Die Lieder werden in den Ordner heruntergeladen.", false);
		waitDialog.show();
		task.valueProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				waitDialog.close();
				for (ISong song : failedSongs) {
					ErrorOutputDialog dialog = new ErrorOutputDialog(
							primaryStage, "Zum Song " + song.getTitle()
									+ " gibt es keine hochgeladene Datei");
					dialog.show();
				}
			}
		});
		new Thread(task).start();
	}

	public void download(final Stage primaryStage) {
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Ordner ausw�hlen");
		File directory = chooser.showDialog(primaryStage);
		download(directory, primaryStage);
	}

	/**
	 * Zeigt einen Speichern-Dialog und laedt den mitgegebenen Song herunter
	 * 
	 * @param song
	 * @throws IOException
	 */
	public void downloadSong(final Stage primaryStage, final ISong song) {
		FileChooser chooser = GUIStart.getChooserForMP3();
		chooser.setTitle("Song herunterladen");
		File file = chooser.showSaveDialog(primaryStage);
		LOGGER.log(Level.INFO, "File ausgew�hlt.");
		if (file != null) {
			if (!file.getPath().endsWith(".mp3")) {
				LOGGER.log(Level.INFO, "F�ge Endung an File.");
				file = new File(file.getPath() + ".mp3");
			}
			try {
				LOGGER.log(Level.INFO, "Versuche Song herunterzuladen.");
				if (!GUIStart.getLogic().downloadSong(song, file)) {
					LOGGER.log(Level.WARNING, "Keine Datei vorhanden");
					// TODO
					ErrorOutputDialog dialog = new ErrorOutputDialog(
							primaryStage, "Zum Song " + song.getTitle()
									+ " gibt es keine hochgeladene Datei");
					dialog.show();
				}
			} catch (IOException e) {
				LOGGER.log(Level.INFO, "Versuch gescheitert: " + e.getMessage());
				final CannotConnectDialogV2 can = (CannotConnectDialogV2) Loader
						.load(new CannotConnectDialogV2().getFXMLPath());
				can.setData(primaryStage);
				can.show();
				can.setOnHiding(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent arg0) {
						if (can.getOption().equals(CannotConnectOption.AGAIN)) {
							LOGGER.log(Level.INFO, "Erneuter Versuch.");
							downloadSong(primaryStage, song);
						}
					};
				});
			}
		}
	}

	/**
	 * �ffnet einen {@link WaitDialogV2}, der die Anzahl der geladenen
	 * Datens�tze anzeigt und l�dt die Daten in die tableView (neuer Thread).
	 * Gibt dabei den Task des Threads zur�ck.
	 * 
	 * @param tableView
	 * @return Task des neuen Threads
	 */
	public Task<Boolean> loadSongDatabase(Stage primaryStage,
			TableClass<ISong> tableView) {
		LOGGER.log(Level.INFO, "WaitDialog wird ge�ffnet.");
		final WaitDialogV2 waitDialog = (WaitDialogV2) Loader
				.load(new WaitDialogV2().getFXMLPath());
		waitDialog.setData(primaryStage, "Bitte warten",
				"Bitte warten. Die Daten werden geladen.", true);
		waitDialog.show();
		LoadSongDatabase setupTask = new LoadSongDatabase(waitDialog,
				tableView, primaryStage);
		LOGGER.log(Level.INFO, "Starte neuen Thread, lade Datenbank.");
		new Thread(setupTask).start();
		return setupTask;
	}

	/**
	 * @param song
	 */
	public void uploadSong(final Stage primaryStage, final ISong song) {
		LOGGER.log(Level.INFO, "�ffne FileChooser.");
		// �ffnet einen FileChooser, nur f�r MP3-Dateien (Filter)
		FileChooser fileChoos = GUIStart.getChooserForMP3();
		fileChoos.setTitle("Song hochladen");
		final File file = fileChoos.showOpenDialog(primaryStage);
		try {
			// Wenn die Daten/Tags aus der Datei mit der des Songs
			// �bereinstimmen
			if (GUIStart.getLogic().checkUploadingFile(file, song)) {
				LOGGER.log(Level.INFO, "Song und File stimmen �berein.");

				// Wird die Datei hochgeladen
				LOGGER.log(Level.INFO, "Versuche Song hochzuladen.");
				ISong upSong = GUIStart.getLogic().uploadSong(song, file);
				LOGGER.log(Level.INFO, "Song hochgeladen.");
				// Aktualisiert die Liste.
				for (int i = 0; i < GUIStart.getSongs().size(); i++) {
					ISong listSong = GUIStart.getSongs().get(i);
					if (listSong.getID() == upSong.getID()) {
						GUIStart.getSongs().set(i, upSong);
						LOGGER.log(Level.INFO, "Song neu gesetzt.");
					}
				}

				// Wenn die Tags nicht �bereinstimmen, wird ein Dialog ge�ffnet,
				// mit welchen Daten hochgeladen werden soll
			} else {
				if (file != null) {
					LOGGER.log(Level.INFO, "Sicherheitsabfrage wird ge�ffnet.");
					final SureDialog sure = new SureDialog(null, song, file);
					sure.setOnHiding(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) {
							try {
								LOGGER.log(Level.INFO,
										"Handle Sicherheitsabfrage.");
								ISong changedSong = null;
								// Wenn mit den Werten aus der Datei hochgeladen
								// werden soll, wird die Datei hochgeladen
								if (sure.getOption().equals(Option.FILE_UPLOAD)) {
									LOGGER.log(Level.INFO,
											"Sicherheitsabfrage: FileUpload.");
									changedSong = GUIStart.getLogic()
											.uploadSongFromFile(file, song);

									// Wenn die Werte des Songs genommen werden
									// sollen.
								} else if (sure.getOption().equals(
										Option.SONG_UPLOAD)) {
									LOGGER.log(Level.INFO,
											"Sicherheitsabfrage: SongUpload.");
									changedSong = GUIStart.getLogic()
											.uploadSong(song, file);
								}
								LOGGER.log(Level.INFO,
										"Setze neuen Song in Liste.");
								// Setzt den neuen Song anhand der ID.
								for (int i = 0; i < GUIStart.getSongs().size(); i++) {
									ISong listSong = GUIStart.getSongs().get(i);
									if (listSong.getID() == song.getID()) {
										GUIStart.getSongs().set(i, changedSong);
									}
								}

							} catch (IOException e) {
								LOGGER.log(Level.WARNING,
										"Fehler: konnte keine Verbindung herstellen: "
												+ e.getMessage());
								final CannotConnectDialogV2 can = (CannotConnectDialogV2) Loader
										.load(new CannotConnectDialogV2()
												.getFXMLPath());
								can.setData(primaryStage);
								can.show();
							} catch (UniqueConstraintException e) {
								LOGGER.log(
										Level.WARNING,
										"Fehler: Song schon vorhanden: "
												+ e.getMessage());
								new UniqueConstraintDialog(primaryStage, null);
							} catch (CannotReadException e) {
								LOGGER.log(
										Level.WARNING,
										"Fehler: ReadFehler in Datei: "
												+ e.getMessage()
												+ e.getStackTrace());
								e.printStackTrace();
							}

						};
					});

				}
			}
		} catch (IOException e) {
			LOGGER.log(
					Level.WARNING,
					"Fehler: konnte keine Verbindung herstellen:"
							+ e.getMessage());
			final CannotConnectDialogV2 can = (CannotConnectDialogV2) Loader
					.load(new CannotConnectDialogV2().getFXMLPath());
			can.setData(primaryStage);
			can.show();
		} catch (CannotReadException e) {
			LOGGER.log(
					Level.WARNING,
					"Fehler: ReadFehler in Datei: " + e.getMessage()
							+ e.getStackTrace());
			e.printStackTrace();
		}
	}

	/**
	 * Zeigt einen Dialog zum Ausw�hlen von Dateien an. L�dt ausgew�hlte Dateien
	 * hoch.
	 * 
	 * @param isFolder
	 *            - Ordner hochladen oder Dateien
	 * @param primaryStage
	 */
	public void uploadSongFiles(boolean isFolder, Stage primaryStage) {
		final ArrayList<File> listOfMP3Files = new ArrayList<>();

		if (isFolder) {
			LOGGER.log(Level.INFO, "Ordner hochladen.");
			DirectoryChooser directoryChosser = new DirectoryChooser();
			File directory = directoryChosser.showDialog(primaryStage);

			if (directory != null) {
				IFoundListener foundListener = new de.rmuselmann.logic.IFoundListener() {
					@Override
					public void fileFound(File f) {
						listOfMP3Files.add(f);
					}
				};
				LOGGER.log(Level.INFO, "Suche Dateien mit Endung mp3.");
				new FileFinder().sucheDateiMitRegEx(".+\\.mp3", directory,
						foundListener);
			}
		} else {
			LOGGER.log(Level.INFO, "Dateien hochladen.");
			FileChooser fileChoos = GUIStart.getChooserForMP3();
			fileChoos.setTitle("Lieder hochladen");
			List<File> fileList = fileChoos
					.showOpenMultipleDialog(primaryStage);
			if (fileList != null) {
				for (File file : fileList) {
					listOfMP3Files.add(file);
				}
			}
			LOGGER.log(Level.INFO, "Songs zu Liste hinzugef�gt.");
		}

		if (listOfMP3Files.size() > 0) {
			LOGGER.log(Level.INFO, "�ffne WaitDialog.");
			final WaitDialogV2 waitDialog = (WaitDialogV2) Loader
					.load(new WaitDialogV2().getFXMLPath());
			waitDialog
					.setData(
							primaryStage,
							"Dateien werden hochgeladen",
							"Bitte warten Sie bis alle Daten hochgeladen wurden!",
							true);
			final InsertFiles setupTask = new InsertFiles(listOfMP3Files,
					waitDialog, primaryStage);
			LOGGER.log(Level.INFO, "Starte Thread.");
			new Thread(setupTask).start();
			waitDialog.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					LOGGER.log(Level.INFO, "Cancel Task");
					setupTask.cancel();
					LOGGER.log(Level.INFO, "Task gecancelt.");
				}
			});
		}
	}
}
