package de.rmuselmann.gui.fxml.dialogs2;

import java.util.ArrayList;
import java.util.List;

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
import de.rmuselmann.gui.fxml.PaneLoader;
import de.rmuselmann.gui.fxml.StageLoader;
import de.rmuselmann.gui.fxml.panes.MenuBar;
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

	@Override
	public String getFXMLPath() {
		return "/fxml/main.fxml";
	}

	@Override
	public Parent getRoot() {
		return pane;
	}

	public void setData() {
		final MenuBar w = (MenuBar) PaneLoader.load(new MenuBar().getFXMLPath());
		w.setData(this, searchField, searchBox);
		w.getRoot().prefWidthProperty().bind(this.pane.widthProperty());
		this.pane.setTop(w.getRoot());

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
		final WaitDialogV2 w = (WaitDialogV2) StageLoader.load(new WaitDialogV2().getFXMLPath());
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
