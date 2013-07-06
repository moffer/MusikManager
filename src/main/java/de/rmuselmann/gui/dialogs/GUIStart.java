package de.rmuselmann.gui.dialogs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javafx.application.Application;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import de.rmuselmann.beanFacade.BeanFactory;
import de.rmuselmann.database.dao.impl.ConnectionManager;
import de.rmuselmann.gui.ConnectToDatabaseTask;
import de.rmuselmann.gui.events.ListListenerCaster;
import de.rmuselmann.gui.fxml.Loader;
import de.rmuselmann.gui.fxml.dialogs2.CannotConnectDialogV2;
import de.rmuselmann.gui.fxml.dialogs2.CannotConnectDialogV2.CannotConnectOption;
import de.rmuselmann.gui.fxml.dialogs2.MainStage;
import de.rmuselmann.gui.fxml.dialogs2.WaitDialogV2;

public class GUIStart extends Application {

	private static ListListenerCaster listListener = new ListListenerCaster();
	private static Stage primaryStage;
	private static FileChooser chooserForMP3;

	private static VBox screenVBox;
	private final static Logger LOGGER = Logger.getLogger(GUIStart.class.getName());

	/**
	 * Beendet das Programm.
	 */
	public static void exit() {
		LOGGER.log(Level.INFO, "Das Programm wird beendet.");
		ConnectionManager.close();
		System.exit(0);
	}

	/**
	 * @return the chooserForMP3
	 */
	public static FileChooser getChooserForMP3() {
		return chooserForMP3;
	}

	/**
	 * @return the listListener
	 */
	public static ListListenerCaster getListListener() {
		return listListener;
	}

	/**
	 * @return the screenVBox
	 */
	public static VBox getScreenVBox() {
		return screenVBox;
	}

	public static Stage getStage() {
		return primaryStage;
	}

	public static void main(String[] args) {

		try {
			new File("log").mkdir();
			// Create an appending file handler
			boolean append = true;
			FileHandler handler = new FileHandler("log/my.log", append);
			handler.setFormatter(new SimpleFormatter());
			// Add to the desired logger
			LOGGER.addHandler(handler);
		} catch (IOException e) {
		}
		Application.launch(GUIStart.class, args);
	}

	/**
	 * @param songs
	 *            the songs to set
	 */
	// public static void setSongs(ObservableList<ISong> songs) {
	// GUIStart.songs = songs;
	// }

	@Override
	public void start(final Stage primaryStage) {
		chooserForMP3 = new FileChooser();
		FileChooser.ExtensionFilter filterMP3 = new FileChooser.ExtensionFilter("MP3 Files (*.mp3)", "*.mp3");
		FileChooser.ExtensionFilter filterAll = new FileChooser.ExtensionFilter("Alle (*.*)", "*.*");
		chooserForMP3.getExtensionFilters().addAll(filterMP3, filterAll);

		GUIStart.primaryStage = primaryStage;

		final MainStage mainStage = (MainStage) Loader.load(new MainStage().getFXMLPath());
		mainStage.setData();

		// final SongTableView tableView = new SongTableView(
		// TableClass.getSongColumns(), GUIStart.getSongs());

		// menu = new MenuBarClass(mainStage, tableView);

		// tabpane = new TabPane();
		// menu.getSearchBox().setVisible(false);

		// heightSubtractOfTabPane = 92;
		// tabpane.prefHeightProperty()
		// .bind(mainStage.heightProperty().subtract(
		// heightSubtractOfTabPane));
		// tabpane.prefWidthProperty().bind(
		// mainStage.widthProperty().subtract(15));
		// tabpane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

		// Tab songTab = new Tab("Songs");

		// tableView.doIt(mainStage);
		// HBox contentBox = new HBox();
		// contentBox.getChildren().add(tableView);
		// MediaPlayer mediaplayer = new MediaPlayer(null);
		// PlayerPane playerPane = new PlayerPane(mediaplayer);
		// contentBox.getChildren().add(playerPane);
		// songTab.setContent(contentBox);
		//
		// tabpane.getTabs().addAll(songTab);

		//

		// TODO: KontextMenu
		// ContextMenuBar contextMenu = new ContextMenuBar(mainStage,
		// tableView);
		// tableView.setContextMenu(contextMenu);

		LOGGER.log(Level.INFO, "Erzeuge WaitDialog.");
		final WaitDialogV2 wd = (WaitDialogV2) Loader.load(new WaitDialogV2().getFXMLPath());
		wd.setData(mainStage, "Datenbankverbindung wird aufgebaut", "Die Verbindung zur Datenbank wird aufgebaut!", false);
		wd.show();

		ConnectToDatabaseTask s = new ConnectToDatabaseTask();
		s.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent event) {
				LOGGER.log(Level.INFO, "Versuche Einstellungen zu laden");
				try {
					BeanFactory.getSettingsBeanFacade().loadSettings();
				} catch (FileNotFoundException e) {
					new SettingsDialog(mainStage, true);
				} catch (IOException e) {
					new SettingsDialog(mainStage, false);
				}
				LOGGER.log(Level.INFO, "Einstellungen geladen");
				LOGGER.log(Level.INFO, "Schließe WaitDialog.");
				wd.close();
				LOGGER.log(Level.INFO, "WaitDialog geschlossen.");
				mainStage.updateTable();
				mainStage.show();
			};
		});
		s.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				LOGGER.log(Level.INFO, "Schließe WaitDialog.");
				wd.close();
				LOGGER.log(Level.INFO, "WaitDialog geschlossen.");

				LOGGER.log(Level.INFO, "Erzeuge CannotConnectDialog.");
				final CannotConnectDialogV2 cannotConnectDialog = (CannotConnectDialogV2) Loader.load(new CannotConnectDialogV2().getFXMLPath());
				cannotConnectDialog.setData(mainStage);
				cannotConnectDialog.show();
				cannotConnectDialog.setOnHiding(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent event) {
						LOGGER.log(Level.INFO, "CannotConnectDialog.close() aufgerufen.");
						if (cannotConnectDialog.getOption().equals(CannotConnectOption.AGAIN)) {
							LOGGER.log(Level.INFO, "Wiederhole Guistart.");
							start(mainStage);
						} else {
							GUIStart.exit();
						}
					}
				});

			}
		});

		LOGGER.log(Level.INFO, "Starte neuen Thread für Aufbau zur Datenbank.");
		s.start();
	}
}