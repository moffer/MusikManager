package de.rmuselmann.gui.fxml.dialogs2;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import de.rmuselmann.gui.fxml.FXMLStage;
import de.rmuselmann.logic.impl.PropertyReader;

public class InfoDialog extends FXMLStage implements Initializable {
	@FXML
	private Pane pane;
	@FXML
	private Label versionLabel;

	@Override
	public String getFXMLPath() {
		return "/fxml/infoDialog.fxml";
	}

	@Override
	public Parent getRoot() {
		return pane;
	}

	public void setData(Stage primaryStage) {
		this.setPrimaryStage(primaryStage);
		this.initOwner(primaryStage);
		this.initModality(Modality.APPLICATION_MODAL);
		this.setTitle("Info");
		this.setResizable(false);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		versionLabel.setText("Version: " + new PropertyReader().getProperty("version"));
	}
}
