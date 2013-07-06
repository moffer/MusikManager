package de.rmuselmann.gui.fxml.dialogs2;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import de.rmuselmann.beanFacade.BeanFactory;
import de.rmuselmann.beanFacade.SongBeanFacade;
import de.rmuselmann.entity.ISong;
import de.rmuselmann.entity.impl.TableConfiguration;
import de.rmuselmann.gui.dialogs.GUIStart;
import de.rmuselmann.gui.events.ProgressWaitDialogListener;
import de.rmuselmann.gui.fxml.FXMLStage;
import de.rmuselmann.gui.fxml.Loader;
import de.rmuselmann.gui.guiElements.SearchKeyHandler;
import de.rmuselmann.logic.impl.PropertyReader;

public class MainStage extends FXMLStage {

	@FXML
	private BorderPane pane;

	@FXML
	private VBox searchBox;
	@FXML
	private TableView<ISong> tableViewAvailable;
	@FXML
	private TextField searchField;

	@SuppressWarnings("unused")
	@FXML
	private void onUpdateTable() {
		// tableUpdate.setAccelerator(new KeyCodeCombination(KeyCode.F5));
		getLOGGER().log(Level.INFO, "Lade Datenbank und setzte in der GUI");
		updateTable();
	}

	@SuppressWarnings("unused")
	@FXML
	private void onExit() {
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
		searchField.selectAll();
	}

	@SuppressWarnings("unused")
	@FXML
	private void onInfo() {
		// TODO
	}

	@Override
	public String getFXMLPath() {
		return "/fxml/main.fxml";
	}

	@Override
	public Parent getRoot() {
		return pane;
	}

	public void setData() {
		this.setTitle("Kisumana " + new PropertyReader().getProperty("version"));
		this.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				GUIStart.exit();
			}
		});

		searchField.setOnKeyReleased(new SearchKeyHandler(searchBox, this, searchField));
	}

	public void updateTable() {
		SongBeanFacade songBeanFacade = BeanFactory.getSongBeanFacade();
		List<TableConfiguration> list = songBeanFacade.updateTableConfigurations();
		List<TableColumn<ISong, String>> columns = new ArrayList<>();
		for (TableConfiguration tableConfiguration : list) {
			columns.add(createColumn(tableConfiguration.getColumnName(), tableConfiguration.getReflectionName()));
		}
		tableViewAvailable.getColumns().clear();
		tableViewAvailable.getColumns().addAll(columns);
		final WaitDialogV2 w = (WaitDialogV2) Loader.load(new WaitDialogV2().getFXMLPath());
		w.setData(this, "Lade Daten", "Die Tabelle wird aktualisiert", true);
		w.show();

		Service<Void> s = new Service<Void>() {

			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						tableViewAvailable.getItems().setAll(BeanFactory.getSongBeanFacade().getSongs(new ProgressWaitDialogListener(w)));
						return null;
					}
				};
			}
		};
		s.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent event) {
				w.close();
			};
		});
		s.start();
	}

	private TableColumn<ISong, String> createColumn(String columnName, String columnReflect) {
		TableColumn<ISong, String> interpretColumn = new TableColumn<ISong, String>(columnName);
		interpretColumn.setCellValueFactory(new PropertyValueFactory<ISong, String>(columnReflect));
		return interpretColumn;
	}

	public TableView<ISong> getTableViewAvailable() {
		return tableViewAvailable;
	}
}
