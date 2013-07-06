package de.rmuselmann.gui.fxml.dialogs2;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import de.rmuselmann.gui.dialogs.GUIStart;
import de.rmuselmann.gui.fxml.FXMLPane;
import de.rmuselmann.gui.guiElements.SearchKeyHandler;

public class MenuBar extends FXMLPane {

	private TextField searchField;
	private Pane searchBox;
	private MainStage mainStage;
	@FXML
	private Pane pane;

	public String getFXMLPath() {
		return "/fxml/menubar.fxml";
	}

	public void setData(MainStage mainStage, TextField searchfield, Pane searchBox) {
		this.mainStage = mainStage;
		this.searchField = searchfield;
		this.searchBox = searchBox;
	}

	@SuppressWarnings("unused")
	@FXML
	private void onUpdateTable() {
		// tableUpdate.setAccelerator(new KeyCodeCombination(KeyCode.F5));

		Logger.getLogger(getFXMLPath()).log(Level.INFO, "Lade Datenbank und setzte in der GUI");
		mainStage.updateTable();
	}

	@FXML
	public void onExit() {
		GUIStart.exit();
	}

	@SuppressWarnings("unused")
	@FXML
	private void onNewSong() {
		// TODO
		// new ChangeSongDialog(this, tableView, null);
	}

	@SuppressWarnings("unused")
	@FXML
	private void onEditSong() {
		// change.setAccelerator(new KeyCodeCombination(KeyCode.G));
		// TODO
		// ISong selectedItem = tableView.getSelectionModel().getSelectedItem();
		// if (selectedItem != null) {
		// new ChangeSongDialog(primaryStage, tableView, selectedItem);
		// }
	}

	@SuppressWarnings("unused")
	@FXML
	private void onDeleteSong() {
		// delete.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));
		// TODO
		// tableView.deleteSong(primaryStage, tableView, delete);
	}

	@SuppressWarnings("unused")
	@FXML
	private void onUploadFiles() {
		// TODO
		// tableView.uploadSongFiles(false, primaryStage);
	}

	@SuppressWarnings("unused")
	@FXML
	private void onUploadFolder() {
		// orderFile.setAccelerator(new KeyCodeCombination(KeyCode.INSERT));
		// TODO
		// tableView.uploadSongFiles(true, primaryStage);
	}

	@SuppressWarnings("unused")
	@FXML
	private void onDownload() {
		// TODO
		// tableView.download(primaryStage);
	}

	@SuppressWarnings("unused")
	@FXML
	private void onSearch() {
		// search.setAccelerator(new KeyCodeCombination(KeyCode.F,
		// KeyCombination.CONTROL_DOWN));
		// TODO
		searchBox.setPrefHeight(30);
		searchBox.setVisible(true);
		searchField.requestFocus();
		if (searchField.getOnKeyReleased() != null && searchField.getText() != null) {
			((SearchKeyHandler) searchField.getOnKeyReleased()).doSearch(searchField.getText(), null);
		}

		searchField.selectAll();
	}

	@SuppressWarnings("unused")
	@FXML
	private void onInfo() {
		// TODO
	}

	public Pane getRoot() {
		return pane;
	}
}
