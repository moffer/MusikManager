package de.rmuselmann.gui.dialogs;


import java.io.File;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import com.sun.javafx.collections.ObservableListWrapper;

import de.rmuselmann.gui.guiElements.MyStage;

public class FailedSongsDialog extends MyStage {
	private Stage stage = this;

	public FailedSongsDialog(Stage primaryStage, ArrayList<File> list) {
		setAll(primaryStage, 300, 100, true);
		init(list);
	}

	private void init(ArrayList<File> list) {
		this.setMaxWidth(300);

		Button ok = new Button("Ok");
		ok.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.close();
			}
		});

		VBox box = new VBox();

		if (list == null || list.size() == 1) {
			this.setTitle("Fehlerhafter Eintrag");
			this.setResizable(false);
			Label head = new Label("Fehlerhafter Eintrag");
			head.setFont(Font.font(null, FontWeight.EXTRA_BOLD, 15));

			Label info = new Label(
					"Es ist ein Fehler bei diesem Song aufgetreten: "
							+ list.get(0));
			box.getChildren().addAll(head, info, ok);
		} else {
			double height = list.size() * 30 + 100;
			if (height > getPrimaryStage().getHeight()) {
				height = getPrimaryStage().getHeight();
			}

			this.setHeight(height);// evtl + 30 Width

			this.setTitle("Fehlerhafte Eintr�ge");
			Label head = new Label("Fehlerhafte Eintr�ge");
			head.setFont(Font.font(null, FontWeight.EXTRA_BOLD, 15));
			Label info = new Label(
					"Diese Songs konnten nicht hochgeladen werden:");
			ListView<File> view = new ListView<>();
			view.setPrefHeight(height - 100);
			view.setItems(new ObservableListWrapper<File>(list));
			box.getChildren().addAll(head, info, view, ok);
		}

		box.setSpacing(10);
		box.setPadding(new Insets(10));
		box.setAlignment(Pos.CENTER);

		getRoot().getChildren().add(box);
		getRoot().setAlignment(Pos.CENTER);
		this.show();
	}
}
