package de.rmuselmann.logic.exceptions;

/**
 * Exception, die geworfen wird, wenn ein Eintrag schon vorhanden ist (unique).
 */
public class UniqueConstraintException extends Exception {
	private static final long serialVersionUID = 1L;

	public UniqueConstraintException() {
		// Aufruf des �bergeordneten Konstruktors mit dem zu
		// erscheinenden Fehlertext
		super("Es wurde ein UniqueConstraint verletzt!");
	}
}
