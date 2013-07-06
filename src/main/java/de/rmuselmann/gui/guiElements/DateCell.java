package de.rmuselmann.gui.guiElements;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.scene.control.TableCell;

public class DateCell<T> extends TableCell<T, Date> {
	@Override
	protected void updateItem(Date item, boolean empty) {
		super.updateItem(item, empty);
		SimpleDateFormat dat = new SimpleDateFormat("dd.MMMM.yyyy");
		if (item != null) {
			String date = dat.format(item);
			this.setText(date);
		}
	}
}
