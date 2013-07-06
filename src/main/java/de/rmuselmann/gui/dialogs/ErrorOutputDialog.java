package de.rmuselmann.gui.dialogs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import de.rmuselmann.gui.guiElements.MyStage;

public class ErrorOutputDialog extends MyStage {
	Stage stage = this;

	public ErrorOutputDialog(Stage primaryStage, String errorText) {
		setAll(primaryStage, (errorText.length() * 6.1 + 10), 90, false);
		init(errorText);
	}

	private void init(String errorText) {
		this.setTitle("Fehler");
		Label head = new Label("Fehler:");
		head.setFont(Font.font(null, FontWeight.EXTRA_BOLD, 15));

		Label errorTextLabel = new Label(errorText);
		errorTextLabel.setStyle("-fx-text-background-color: red;");

		Button closeButton = new Button("Schlie�en");
		closeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				stage.close();
			}
		});

		getRoot().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)
						|| event.getCode().equals(KeyCode.ESCAPE)) {
					stage.close();
				}
			};

		});

		VBox box = new VBox();
		box.setSpacing(10);
		box.setAlignment(Pos.CENTER);
		box.getChildren().addAll(head, errorTextLabel, closeButton);
		getRoot().getChildren().add(box);
		getRoot().setAlignment(Pos.CENTER);
	}
}
