package de.rmuselmann.gui.guiElements;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import de.rmuselmann.entity.ISong;
import de.rmuselmann.gui.dialogs.ChangeSongDialog;
import de.rmuselmann.gui.dialogs.GUIStart;
import de.rmuselmann.gui.dialogs.MusikplayerDialog;

public class MenuBarClass extends MenuBar {
	private SearchBox searchBox;
	private final static Logger LOGGER = Logger.getLogger(MenuBarClass.class
			.getName());

	public MenuBarClass(Stage primaryStage, SongTableView tableView) {
		init(primaryStage, tableView);
	}

	/**
	 * @return the searchBox
	 */
	public SearchBox getSearchBox() {
		return searchBox;
	}

	private void init(final Stage primaryStage, final SongTableView tableView) {
		searchBox = new SearchBox().createSearchBox(tableView);

		Menu file = new Menu("Datei");

		MenuItem tableUpdate = new MenuItem("Tabelle aktualisieren");
		
		tableUpdate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				LOGGER.log(Level.INFO,
						"Lade Datenbank und setzte in der GUI");
//				tableView.loadSongDatabase(primaryStage, tableView);
			}
		});
		MenuItem test = new MenuItem("Test");
		test.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				new MusikplayerDialog(primaryStage).show();
			}
		});
		MenuItem exit = new MenuItem("Beenden");
		exit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				GUIStart.exit();
			}
		});

		file.getItems().addAll(tableUpdate, exit, test);

		Menu changeMenu = new Menu("Bearbeiten");

		MenuItem song = new MenuItem("Neuer Song");
		song.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				new ChangeSongDialog(primaryStage, tableView, null);
			}
		});

		MenuItem change = new MenuItem("Song �ndern");
		change.setAccelerator(new KeyCodeCombination(KeyCode.G));
		change.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				ISong selectedItem = tableView.getSelectionModel()
						.getSelectedItem();
				if (selectedItem != null) {
					new ChangeSongDialog(primaryStage, tableView, selectedItem);
				}
			}
		});

		final MenuItem delete = new MenuItem("Song l�schen");
		delete.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));
		delete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				tableView.deleteSong(primaryStage, tableView, delete);
			}
		});

		changeMenu.getItems().addAll(song, change, delete);

		Menu cloud = new Menu("Cloud");

		Menu upload = new Menu("Upload");
		MenuItem songFile = new MenuItem("Songs von Dateien hochladen");
		songFile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				tableView.uploadSongFiles(false, primaryStage);
			}
		});

		MenuItem orderFile = new MenuItem("Ordner hochladen");
		orderFile.setAccelerator(new KeyCodeCombination(KeyCode.INSERT));
		orderFile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				tableView.uploadSongFiles(true, primaryStage);
			}
		});
		upload.getItems().addAll(songFile, orderFile);

		MenuItem download = new MenuItem("Download");
		download.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				tableView.download(primaryStage);
			}
		});

		cloud.getItems().addAll(upload, download);

		Menu extras = new Menu("Extras");

		MenuItem search = new MenuItem("Suche");
		search.setAccelerator(new KeyCodeCombination(KeyCode.F,
				KeyCombination.CONTROL_DOWN));
		search.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				GUIStart.getScreenVBox().getChildren().add(2, searchBox);
				searchBox.setVisible(true);
				GUIStart.setHeightPropertyOfTabPane();
				searchBox.getSearchField().requestFocus();
			}
		});
		extras.getItems().addAll(search);

		Menu help = new Menu("Hilfe");
		MenuItem info = new MenuItem("Info");
		info.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				new InfoDialog(primaryStage).show();
			}
		});

		help.getItems().add(info);

		this.getMenus().addAll(file, changeMenu, cloud, extras, help);
		this.prefWidthProperty()
				.bind(primaryStage.widthProperty().subtract(15));
	}
}
