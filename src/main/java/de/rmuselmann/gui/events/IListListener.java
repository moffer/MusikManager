package de.rmuselmann.gui.events;

import java.util.EventListener;

public interface IListListener extends EventListener {

	public void handleEvent(ListEvent e);
}
