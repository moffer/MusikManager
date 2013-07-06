package de.rmuselmann.gui.events;

import java.util.EventObject;

import javafx.collections.ObservableList;
import de.rmuselmann.entity.ISong;

public class ListEvent extends EventObject {

	public enum ListChangeOption {
		ADDED, DELETED, EDITED;
	}

	private static final long serialVersionUID = 1L;
	private ObservableList<ISong> list;

	private ListChangeOption option;

	/**
	 * ListEvent, dass gefeuert werden sollte, wenn eine Liste ver�ndert wird.
	 * Ben�tigt die ge�nderten Daten der Liste (list)
	 * 
	 * @param source
	 * @param list
	 *            - ge�nderte Eintr�ge der Liste.
	 * @param option
	 *            : gibt an, ob hinzugef�gt oder gel�scht wurde
	 */
	public ListEvent(Object source, ObservableList<ISong> list,
			ListChangeOption option) {
		super(source);
		this.list = list;
		this.option = option;
	}

	/**
	 * Gibt die ge�nderten Eintr�ge zur�ck.
	 * 
	 * @return
	 */
	public ObservableList<ISong> getList() {
		return list;
	}

	/**
	 * @return isAdded: gibt an, ob hinzugef�gt oder gel�scht wurde
	 */
	public ListChangeOption isAdded() {
		return option;
	}
}
