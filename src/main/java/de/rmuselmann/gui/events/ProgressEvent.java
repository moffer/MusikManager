package de.rmuselmann.gui.events;

import java.util.EventObject;

public class ProgressEvent extends EventObject {

	public static final double IOException = -1;

	private double percent;

	private String text;

	private static final long serialVersionUID = 1L;

	public static final double FINISHED = -2;

	public ProgressEvent(Object source, double percentOfDone) {
		super(source);
		this.percent = percentOfDone;
	}

	public ProgressEvent(Object source, double percentOfDone, String text) {
		super(source);
		this.percent = percentOfDone;
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public double getPercent() {
		return percent;
	}
}
