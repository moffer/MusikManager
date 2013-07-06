package de.rmuselmann.gui.fxml;

import java.util.logging.Logger;

import javafx.scene.Scene;
import javafx.stage.Stage;
import de.rmuselmann.logic.impl.ExtraFiles;

public abstract class FXMLStage extends Stage implements IFXMLStage {
	private Stage primaryStage;
	private final Logger LOGGER = Logger.getLogger(getClass().getName());

	public void initStage() {
		this.setScene(new Scene(this.getRoot()));
		this.getIcons().add(ExtraFiles.getTaskImage());
		// TODO: Modalität?
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public Logger getLOGGER() {
		return LOGGER;
	}
}
