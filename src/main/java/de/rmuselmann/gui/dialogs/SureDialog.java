package de.rmuselmann.gui.dialogs;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import de.rmuselmann.entity.ISong;
import de.rmuselmann.gui.guiElements.MyStage;
import de.rmuselmann.logic.impl.EntaggedTagReader;
import entagged.audioformats.exceptions.CannotReadException;

public class SureDialog extends MyStage {
	public enum Option {
		FILE_UPLOAD, SONG_UPLOAD, CANCEL
	}

	private Stage stage = this;

	private Option option;

	public SureDialog(Stage ownerStage, ISong song, File file)
			throws CannotReadException {
		setAll(ownerStage, 420, 220, false);
		init(song, file);

	}

	public Option getOption() {
		return option;
	}

	private void init(final ISong song, File file) throws CannotReadException {
		this.setTitle("Andere Dateiwerte!");

		Label text = new Label("Einige ID3-Tag-Werte stimmen nicht �berein. \n"
				+ "Mit welchen Werten soll der Song hochgeladen werden? ");
		EntaggedTagReader reader = new EntaggedTagReader(file);

		Label headString = new Label("Song-Wert");
		headString.setUnderline(true);
		headString.setTooltip(new Tooltip("Dieser Wert ist aus der Datenbank. "
				+ "Sie haben ihn vor dem Upload eingegeben."));
		Label headFile = new Label("Datei-Wert");
		headFile.setUnderline(true);
		headFile.setTooltip(new Tooltip("Dieser Wert ist von der Datei, "
				+ "die sie hochladen wollen."));

		final TextField interpretSong = new TextField();
		interpretSong.setPromptText("Interpretenname");
		interpretSong.setText(song.getInterpret().getInterpretName());

		final TextField titleSong = new TextField();
		titleSong.setPromptText("Titel");
		titleSong.setText(song.getTitle());

		final TextField albumSong = new TextField();
		albumSong.setPromptText("Album");
		albumSong.setText(song.getAlbum().getAlbumName());

		Button fileButton = new Button("Mit Dateiwerten hochladen");

		fileButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				option = Option.FILE_UPLOAD;
				stage.close();
			}
		});

		Button songButton = new Button("Mit Songwerten hochladen");
		songButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				option = Option.SONG_UPLOAD;
				stage.close();
			}
		});

		Button cancel = new Button("Abbrechen");
		cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				option = Option.CANCEL;
				stage.close();
			}
		});
		Button defaultButton = new Button("Werte zur�cksetzen");
		defaultButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				interpretSong.setText(song.getInterpret().getInterpretName());
				titleSong.setText(song.getTitle());
				albumSong.setText(song.getAlbum().getAlbumName());
			}
		});

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setPadding(new Insets(15, 5, 2, 5));
		grid.setVgap(5);
		grid.setHgap(5);
		grid.add(headFile, 1, 0);
		grid.add(headString, 2, 0);
		grid.add(new Label("Interpret:"), 0, 1);
		grid.add(new Label(reader.getArtist()), 1, 1);
		grid.add(interpretSong, 2, 1);
		grid.add(new Label("Titel:"), 0, 2);
		grid.add(new Label(reader.getTitle()), 1, 2);
		grid.add(titleSong, 2, 2);
		grid.add(new Label("Album:"), 0, 3);
		grid.add(new Label(reader.getAlbum()), 1, 3);
		grid.add(albumSong, 2, 3);
		grid.add(fileButton, 1, 4);
		grid.add(songButton, 2, 4);

		HBox buttons = new HBox();
		buttons.getChildren().addAll(cancel, defaultButton);
		buttons.setSpacing(5);
		buttons.setAlignment(Pos.CENTER);

		VBox box = new VBox();
		box.setSpacing(5);
		box.setAlignment(Pos.CENTER);
		box.setPadding(new Insets(5, 5, 5, 5));
		box.getChildren().addAll(text, grid, buttons);

		getRoot().getChildren().add(box);
		getRoot().setAlignment(Pos.CENTER);

		this.show();
	}
}