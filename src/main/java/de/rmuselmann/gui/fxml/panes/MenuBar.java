package de.rmuselmann.gui.fxml.panes;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import de.rmuselmann.gui.dialogs.GUIStart;
import de.rmuselmann.gui.fxml.FXMLPane;
import de.rmuselmann.gui.fxml.StageLoader;
import de.rmuselmann.gui.fxml.dialogs2.ChangeSongDialog;
import de.rmuselmann.gui.fxml.dialogs2.InfoDialog;
import de.rmuselmann.gui.fxml.dialogs2.MainStage;
import de.rmuselmann.gui.guiElements.SearchKeyHandler;

public class MenuBar extends FXMLPane implements Initializable {

	private TextField searchField;
	private Pane searchBox;
	private MainStage mainStage;
	@FXML
	private Pane pane;
	@FXML
	private MenuItem updateTableMenuItem;
	@FXML
	private MenuItem exitMenuItem;
	@FXML
	private MenuItem newSongMenuItem;
	@FXML
	private MenuItem editSongMenuItem;
	@FXML
	private MenuItem deleteSongMenuItem;
	@FXML
	private MenuItem uploadFilesMenuItem;
	@FXML
	private MenuItem uploadFolderMenuItem;
	@FXML
	private MenuItem downloadMenuItem;
	@FXML
	private MenuItem searchMenuItem;
	@FXML
	private MenuItem infoMenuItem;

	public String getFXMLPath() {
		return "/fxml/menubar.fxml";
	}

	public void setData(MainStage mainStage, TextField searchfield, Pane searchBox) {
		this.mainStage = mainStage;
		this.searchField = searchfield;
		this.searchBox = searchBox;
	}

	public Pane getRoot() {
		return pane;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		updateTableMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.F5));
		updateTableMenuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Logger.getLogger(getFXMLPath()).log(Level.INFO, "Lade Datenbank und setzte in der GUI");
				mainStage.updateTable();
			}
		});

		exitMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GUIStart.exit();
			}
		});
		newSongMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO
				ChangeSongDialog d = (ChangeSongDialog) StageLoader.load(new ChangeSongDialog().getFXMLPath());
				d.setData(mainStage, null);
				d.show();
			}
		});

		editSongMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// change.setAccelerator(new KeyCodeCombination(KeyCode.G));
				// TODO
				// ISong selectedItem =
				// tableView.getSelectionModel().getSelectedItem();
				// if (selectedItem != null) {
				// new ChangeSongDialog(primaryStage, tableView, selectedItem);
				// }
			}
		});

		deleteSongMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// delete.setAccelerator(new
				// KeyCodeCombination(KeyCode.DELETE));
				// TODO
				// tableView.deleteSong(primaryStage, tableView, delete);

			}
		});
		uploadFilesMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO
				// tableView.uploadSongFiles(false, primaryStage);
			}
		});
		uploadFolderMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// orderFile.setAccelerator(new
				// KeyCodeCombination(KeyCode.INSERT));
				// TODO
				// tableView.uploadSongFiles(true, primaryStage);
			}
		});
		downloadMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO
				// tableView.download(primaryStage);
			}
		});
		searchMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN));
		searchMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO
				searchBox.setPrefHeight(30);
				searchBox.setVisible(true);
				searchField.requestFocus();
				if (searchField.getOnKeyReleased() != null && searchField.getText() != null) {
					((SearchKeyHandler) searchField.getOnKeyReleased()).doSearch(searchField.getText(), null);
				}
				searchField.selectAll();
			}
		});
		infoMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				InfoDialog d = (InfoDialog) StageLoader.load(new InfoDialog().getFXMLPath());
				d.setData(mainStage);
				d.show();
			}
		});
	}
}
