package de.rmuselmann.gui.fxml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;

public class PaneLoader {

	public static FXMLPane load(String fxmlFile) throws FxmlLoadException {
		return load(fxmlFile, null, null);
	}

	public static FXMLPane load(String fxmlFile, String resourceBundle) throws FxmlLoadException {
		return load(fxmlFile, ResourceBundle.getBundle(resourceBundle), null);
	}

	public static FXMLPane load(String fxmlFile, ResourceBundle resources, Map<String, Object> variables) throws FxmlLoadException {
		InputStream fxmlStream = null;
		try {
			fxmlStream = PaneLoader.class.getResourceAsStream(fxmlFile);
			FXMLLoader loader = new FXMLLoader();
			loader.setBuilderFactory(new JavaFXBuilderFactory());

			File file = new File(fxmlFile);
			loader.setLocation(file.toURI().toURL());

			if (resources != null) {
				loader.setResources(resources);
			}

			if (variables != null) {
				loader.getNamespace().putAll(variables);
			}

			loader.load(fxmlStream);

			FXMLPane controller = (FXMLPane) loader.getController();
			return controller;

		} catch (Exception e) {
			// map checked exception to a runtime exception - this is a system
			// failure, not a business logic failure
			// so using checked exceptions for this is not necessary.
			throw new FxmlLoadException(String.format("Unable to load FXML from '%s': %s", fxmlFile, e.getMessage()), e);
		} finally {
			if (fxmlStream != null) {
				try {
					fxmlStream.close();
				} catch (IOException e) {
					System.err.println("WARNING: error closing FXML stream: " + e);
					e.printStackTrace(System.err);
				}
			}
		}
	}

}
