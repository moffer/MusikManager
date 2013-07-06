package de.rmuselmann.gui.fxml.dialogs2;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import de.rmuselmann.gui.fxml.FXMLStage;

public class CannotConnectDialogV2 extends FXMLStage {
	public enum CannotConnectOption {
		AGAIN, CANCEL;
	}

	@FXML
	private BorderPane pane;

	private CannotConnectOption option = CannotConnectOption.CANCEL;

	@Override
	public String getFXMLPath() {
		return "/fxml/cannotConnectDialog.fxml";
	}

	@Override
	public Parent getRoot() {
		return pane;
	}

	public void setData(Stage primaryStage) {
		this.setTitle("Keine Verbindung");
		this.setPrimaryStage(primaryStage);
		this.initOwner(primaryStage);
		this.initModality(Modality.APPLICATION_MODAL);
	}

	@FXML
	public void reloadAction() {
		option = CannotConnectOption.AGAIN;
		this.close();
	}

	public CannotConnectOption getOption() {
		return option;
	}

	public void setOption(CannotConnectOption option) {
		this.option = option;
	}
}
