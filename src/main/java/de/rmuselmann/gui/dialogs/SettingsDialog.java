package de.rmuselmann.gui.dialogs;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.WindowEvent;
import de.rmuselmann.beanFacade.BeanFactory;
import de.rmuselmann.entity.IUser;
import de.rmuselmann.gui.fxml.dialogs2.MainStage;
import de.rmuselmann.gui.guiElements.MyStage;

public class SettingsDialog extends MyStage {
	SettingsDialog stage = this;
	String user;
	private boolean isFirstTime;

	public SettingsDialog(final MainStage mainStage, boolean isFirstTime) {
		setAll(mainStage, 350, 120, false);
		this.isFirstTime = isFirstTime;

		this.setOnHiding(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				String userString = stage.getUser();
				if (userString != null) {
					IUser user = BeanFactory.getUserBeanFacade().getUser(
							userString);
					BeanFactory.getSettingsBeanFacade().setStandartSettings(
							user);
					mainStage.updateTable();
					mainStage.show();
				}
			}
		});
		init();

	}

	public String getUser() {
		return user;
	}

	private void init() {
		this.setTitle("Einstellungen");

		Label head = new Label("Einstellungen");
		head.setFont(Font.font(null, FontWeight.EXTRA_BOLD, 15));

		Label infoTextLabel;
		if (isFirstTime) {
			infoTextLabel = new Label(
					"Vor dem Start nehmen Sie bitte folgende Einstellungen vor:");
		} else {
			infoTextLabel = new Label(
					"Die Einstellungen konnten leider nicht gespeichert werden. \nBitte nehmen Sie sie erneut vor:");
		}

		final Label errorTextLabel = new Label("");
		errorTextLabel.setStyle("-fx-text-background-color: red;");

		Label field = new Label("Name: ");
		final TextField nameField = new TextField();
		nameField.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				if (!newValue && nameField.getText().trim().equals("")) {
					errorTextLabel.setText("Pflichtfeld!");
				}

			}

		});

		GridPane settingsGrid = new GridPane();
		settingsGrid.setHgap(10);
		settingsGrid.setPadding(new Insets(10, 5, 10, 5));
		settingsGrid.setAlignment(Pos.TOP_LEFT);
		settingsGrid.add(field, 0, 0);
		settingsGrid.add(nameField, 1, 0);
		settingsGrid.add(errorTextLabel, 2, 0);

		final Button okButton = new Button("Ok");
		okButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!nameField.getText().trim().equals("")) {
					user = nameField.getText();
					stage.close();
				} else {
					nameField
							.setStyle("-fx-shadow-highlight-color: derive(red, 30.0%);");
				}
			}
		});

		final Button cancelButton = new Button("Abbrechen");
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				stage.close();
				GUIStart.exit();
			}
		});
		nameField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					okButton.fire();
				} else if (event.getCode().equals(KeyCode.ESCAPE)) {
					cancelButton.fire();
				}
			}
		});

		this.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				cancelButton.fire();
			};
		});

		HBox buttons = new HBox();
		buttons.getChildren().addAll(okButton, cancelButton);
		buttons.setSpacing(5);
		buttons.setPadding(new Insets(5, 0, 0, 0));
		buttons.setAlignment(Pos.CENTER);

		VBox box = new VBox();
		box.setAlignment(Pos.CENTER);
		box.setPadding(new Insets(5));
		box.getChildren().addAll(head, infoTextLabel, settingsGrid, buttons);

		getRoot().getChildren().add(box);
		getRoot().setAlignment(Pos.CENTER);
		this.show();
	}
}
