package de.rmuselmann.gui.fxml;

public class FxmlLoadException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FxmlLoadException(String format, Exception e) {
		super(format, e);
	}

}
