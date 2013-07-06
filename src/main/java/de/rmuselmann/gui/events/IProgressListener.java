package de.rmuselmann.gui.events;

import java.util.EventListener;

public interface IProgressListener extends EventListener {

	public void handleEvent(ProgressEvent e);
}
