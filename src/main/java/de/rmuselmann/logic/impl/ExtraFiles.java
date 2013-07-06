package de.rmuselmann.logic.impl;

import javafx.scene.image.Image;

public class ExtraFiles {

	public static Image getTaskImage() {

		return new Image(
				ExtraFiles.class.getResourceAsStream("/images/note2neu.png"));
	}
}
