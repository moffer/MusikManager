package de.rmuselmann.gui.fxml.dialogs2;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import de.rmuselmann.gui.fxml.FXMLStage;

public class WaitDialogV2 extends FXMLStage {
	@FXML
	private BorderPane pane;
	@FXML
	private Label infoLabel;
	@FXML
	private ProgressBar progressBar;
	@FXML
	private ProgressIndicator progressIndicator;

	@FXML
	public void onExit() {
		System.out.println("onExit");
	}

	@Override
	public String getFXMLPath() {
		return "/fxml/waitDialog.fxml";
	}

	public void setData(Stage primaryStage, String title, String infoText,
			boolean isWithStatus) {
		this.setPrimaryStage(primaryStage);
		this.initOwner(primaryStage);
		this.initModality(Modality.APPLICATION_MODAL);
		this.setTitle(title);
		this.infoLabel.setText(infoText);

		this.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				event.consume();
			}
		});

		if (isWithStatus) {
			progressBar.setProgress(0);
			progressIndicator.setProgress(0);
		} else {
			progressBar.setProgress(-1);
			progressIndicator.setProgress(0);
			progressIndicator.setVisible(false);
		}

	}

	public void setProgress(double value) {
		this.progressBar.setProgress(value);
		this.progressIndicator.setProgress(value);
	}

	@Override
	public Parent getRoot() {
		return this.pane;
	}

	public void setInfoText(String string) {
		this.infoLabel.setText(string);
	}
}
