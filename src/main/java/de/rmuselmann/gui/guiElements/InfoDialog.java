package de.rmuselmann.gui.guiElements;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import de.rmuselmann.logic.impl.PropertyReader;

public class InfoDialog extends MyStage {
	public InfoDialog(Stage primaryStage) {
		setAll(primaryStage, 300, 100, false);
		init(primaryStage);
	}

	private void init(Stage primaryStage) {
		Label head = new Label("Kisumana");
		this.setTitle("Info");
		head.setUnderline(true);

		Label version = new Label("Version: "
				+ new PropertyReader().getProperty("version"));

		Label developer = new Label("Entwickelt von: Rafael Muselmann");
		Label infoUndHilfe = new Label(
				"Infos und Hilfe: rafael.muselmann@gmx.de");

		VBox box = new VBox();
		box.setSpacing(5);
		box.setPadding(new Insets(5));

		box.getChildren().addAll(head, version, developer, infoUndHilfe);
		getRoot().getChildren().addAll(box);
	}
}
