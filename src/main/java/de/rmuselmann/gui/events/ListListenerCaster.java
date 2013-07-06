package de.rmuselmann.gui.events;

import java.util.Vector;

public class ListListenerCaster implements IListListener {
	protected Vector<IListListener> listener = new Vector<IListListener>();

	public void add(IListListener a) {
		if (!listener.contains(a)) {
			listener.addElement(a);
		}
	}

	@Override
	public void handleEvent(ListEvent e) {
		for (int i = 0; i < listener.size(); i++) {
			listener.elementAt(i).handleEvent(e);
		}
	}

	public void remove(IListListener l) {
		listener.remove(l);
	}
}
