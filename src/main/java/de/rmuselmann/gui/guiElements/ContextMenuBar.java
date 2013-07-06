package de.rmuselmann.gui.guiElements;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.Stage;
import de.rmuselmann.entity.ISong;
import de.rmuselmann.gui.dialogs.ChangeSongDialog;
import de.rmuselmann.gui.extraClasses.TableClass;

public class ContextMenuBar extends ContextMenu {
	private TableClass<ISong> tableView;
	private MenuItem fileDownload;

	// TODO auf Multiselektion erweitern
	public ContextMenuBar(Stage primaryStage, SongTableView tableView) {
		init(primaryStage, tableView);
	}

	private void init(final Stage primaryStage, final SongTableView tableView) {
		this.tableView = tableView;
		MenuItem fileUpload = new MenuItem("Song hochladen");
		fileUpload.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				ISong song = tableView.getSelectionModel().getSelectedItem();
				tableView.uploadSong(primaryStage, song);
			}
		});

		fileDownload = new MenuItem("Song herunterladen");
		fileDownload.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				ISong song = tableView.getSelectionModel().getSelectedItem();
				tableView.downloadSong(primaryStage, song);
			}
		});

		MenuItem change = new MenuItem("Song ver�ndern");
		change.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {

				new ChangeSongDialog(primaryStage, tableView, tableView
						.getSelectionModel().getSelectedItem());
			}
		});

		this.getItems().addAll(change, new SeparatorMenuItem(), fileUpload);
	}

	@Override
	protected void show() {
		if (tableView.getSelectionModel().getSelectedItems().size() > 0) {
			super.show();
			if (this.getItems().size() == 3) {
				if (tableView.getSelectionModel().getSelectedItem()
						.isFileExisting()) {
					this.getItems().add(fileDownload);
				}
			} else if (this.getItems().size() == 4) {
				if (!tableView.getSelectionModel().getSelectedItem()
						.isFileExisting()) {
					this.getItems().remove(fileDownload);
				}
			}
		}
	}
}
