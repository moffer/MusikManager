package de.rmuselmann.gui.guiElements;

import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import de.rmuselmann.logic.impl.ExtraFiles;

public class MyStage extends Stage {

	private Stage primaryStage;
	private FlowPane root;

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public FlowPane getRoot() {
		return root;
	}

	public void setAll(Stage primaryStage, double width, double height,
			boolean resizable) {
		this.primaryStage = primaryStage;
		this.initOwner(primaryStage);
		this.initModality(Modality.APPLICATION_MODAL);
		this.setResizable(resizable);

		this.getIcons().add(ExtraFiles.getTaskImage());

		setRoot(new FlowPane());
		Scene scene = new Scene(getRoot(), width, height);
		this.setScene(scene);
	}

	public void setRoot(FlowPane root) {
		this.root = root;
	}
}