package de.rmuselmann.gui.dialogs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import de.rmuselmann.gui.guiElements.MyStage;

public class DeleteDialog extends MyStage {
	private boolean sure = false;
	private Stage stage = this;

	public DeleteDialog(Stage primaryStage) {
		setAll(primaryStage, 300, 80, false);
		init();
	}

	private void init() {
		this.setTitle("Datensatz sicher l�schen?");

		Label text = new Label("Wollen Sie den Datensatz wircklich l�schen?");

		Label warning = new Label("Dies kann nicht r�ckg�ngig gemacht werden!");
		warning.setFont(Font.font(null, 10));

		final Button ok = new Button("OK");
		ok.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				sure = true;
				stage.hide();
			}
		});

		final Button cancel = new Button("Abbrechen");
		cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				stage.close();
			}
		});

		getRoot().setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					ok.fire();
				} else if (event.getCode().equals(KeyCode.ESCAPE)) {
					cancel.fire();
				}
			}
		});

		HBox buttons = new HBox();
		buttons.getChildren().addAll(ok, cancel);
		buttons.setSpacing(5);
		buttons.setAlignment(Pos.CENTER);

		VBox box = new VBox();
		box.setSpacing(5);
		box.setAlignment(Pos.CENTER);
		box.setPadding(new Insets(5, 5, 5, 5));
		box.getChildren().addAll(text, warning, buttons);

		getRoot().getChildren().add(box);
		getRoot().setAlignment(Pos.CENTER);

		this.show();
	}

	public boolean isSure() {
		return sure;
	}
}
