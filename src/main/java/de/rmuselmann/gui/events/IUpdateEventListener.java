package de.rmuselmann.gui.events;

import java.util.EventListener;

public interface IUpdateEventListener extends EventListener {

	public void handleEvent(UpdateEvent updateEvent);
}
