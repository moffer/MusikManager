package de.rmuselmann.gui.guiElements;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import de.rmuselmann.entity.ISong;

public class ButtonCell<T> extends TableCell<T, java.lang.Boolean> {
	private Button button;
	private ButtonCell<T> cell = this;

	public ButtonCell() {
		this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	}

	@Override
	public void updateItem(Boolean item, boolean empty) {
		super.updateItem(item, empty);
		if (!empty) {
			button = new Button();
			button.setPrefWidth(80);
			if (item) {
				button.setStyle("-fx-base: green;");
				button.setText("Download");
				button.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						ISong song = (ISong) cell.getTableRow().getItem();
						((SongTableView) cell.getTableView()).downloadSong(
								null, song);
					}
				});
			} else {
				button.setStyle("-fx-base: red;");
				button.setText("Upload");
				button.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						final ISong song = (ISong) cell.getTableRow().getItem();
						((SongTableView) cell.getTableView()).uploadSong(null,
								song);
					}
				});
			}
			this.setGraphic(button);
		}
	}
}
