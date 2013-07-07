package de.rmuselmann.gui.fxml.dialogs2;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import de.rmuselmann.entity.ISong;
import de.rmuselmann.gui.fxml.FXMLStage;

public class ChangeSongDialog extends FXMLStage {
	private ISong song;
	@FXML
	private Pane pane;
	@FXML
	private TextField interpretField;
	@FXML
	private TextField titleField;
	@FXML
	private TextField albumField;
	@FXML
	private TextField danceField;

	@Override
	public String getFXMLPath() {
		return "/fxml/changeSongDialog.fxml";
	}

	@Override
	public Parent getRoot() {
		return pane;
	}

	public void setData(Stage primaryStage, ISong song) {
		this.song = song;
		this.setPrimaryStage(primaryStage);
		this.initOwner(primaryStage);
		this.initModality(Modality.APPLICATION_MODAL);

		if (song != null) {
			this.setTitle("Song bearbeiten");
			if (song.getInterpret() != null) {
				interpretField.setText(song.getInterpret().getInterpretName());
			}
			titleField.setText(song.getTitle());
			if (song.getAlbum() != null) {
				albumField.setText(song.getAlbum().getAlbumName());
			}
			if (song.getTanzart() != null) {
				danceField.setText(song.getTanzart().getTanzartName());
			}
		} else {
			this.setTitle("Song einfügen");
		}
	}

	@FXML
	public void onCancel() {
		this.close();
	}
}
