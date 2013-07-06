package de.rmuselmann.gui.extraClasses;


import java.util.ArrayList;
import java.util.Date;

import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import de.rmuselmann.gui.guiElements.DateCell;

public class TableClass<T> extends TableView<T> {

	public static ArrayList<String> getSongColumns() {
		ArrayList<String> columns = new ArrayList<String>();
		columns.add("Interpret");
		columns.add("interpret");
		columns.add("Titel");
		columns.add("title");
		columns.add("Album");
		columns.add("album");
		columns.add("Tanzart");
		columns.add("tanzart");
		columns.add("Vorhanden");
		columns.add("FileExisting");
		columns.add("User");
		columns.add("user");
		columns.add("Datum");
		columns.add("date");
		return columns;
	}

	public static ArrayList<String> getSongItems() {
		ArrayList<String> list = getSongColumns();
		ArrayList<String> returnList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			if (i % 2 == 0 && !list.get(i).equals("Vorhanden")
					&& !list.get(i).equals("User")
					&& !list.get(i).equals("Datum")) {
				returnList.add(list.get(i));
			}
		}
		return returnList;
	}

	public TableClass(ArrayList<String> columns, ObservableList<T> data) {
		setItems(data);
		this.setColumns(columns);
	}

	public void isSongTable(boolean b) {
		int columnsCount = this.getColumns().size();
		for (TableColumn<T, ?> column : getColumns()) {
			column.setMinWidth(70);
			column.prefWidthProperty()
					.bind(this.widthProperty().subtract(110)
							.divide(columnsCount - 1));
		}

		this.getColumns().get(0).setMinWidth(120);
		this.getColumns().get(1).setMinWidth(150);
		this.getColumns().get(4).setMinWidth(90);
		this.getColumns().get(4).setMaxWidth(90);
	}

	public void setColumns(final ArrayList<String> columns) {
		for (int i = 0; i < columns.size(); i = i + 2) {
			if (!columns.get(i).equals("Vorhanden")
					&& !columns.get(i).equals("Datum")) {
				TableColumn<T, String> col = new TableColumn<T, String>(
						columns.get(i));

				col.setCellValueFactory(new PropertyValueFactory<T, String>(
						columns.get(i + 1)));
				this.getColumns().add(col);
			} else if (columns.get(i).equals("Vorhanden")) {

				Callback<TableColumn<T, Boolean>, TableCell<T, Boolean>> checkBoxFactory = new Callback<TableColumn<T, Boolean>, TableCell<T, Boolean>>() {

					@Override
					public TableCell<T, Boolean> call(
							TableColumn<T, Boolean> arg0) {
						// return new ButtonCell<T>();
						return new TableCell<T, Boolean>() {
							@Override
							protected void updateItem(Boolean item,
									boolean empty) {
								super.updateItem(item, empty);
								if (!empty) {
									if (item) {
										this.setText("+");
									} else {
										this.setText("-");
									}
								}
							}
						};
					}
				};

				TableColumn<T, Boolean> col = new TableColumn<T, Boolean>(
						columns.get(i));
				col.setCellValueFactory(new PropertyValueFactory<T, Boolean>(
						columns.get(i + 1)));

				col.setCellFactory(checkBoxFactory);
				this.getColumns().add(col);

			} else if (columns.get(i).equals("Datum")) {
				Callback<TableColumn<T, Date>, TableCell<T, Date>> checkBoxFactory = new Callback<TableColumn<T, Date>, TableCell<T, Date>>() {

					@Override
					public TableCell<T, Date> call(TableColumn<T, Date> arg0) {
						return new DateCell<T>();
					}
				};

				TableColumn<T, Date> col = new TableColumn<T, Date>(
						columns.get(i));
				col.setCellValueFactory(new PropertyValueFactory<T, Date>(
						columns.get(i + 1)));

				col.setCellFactory(checkBoxFactory);
				this.getColumns().add(col);
			}
		}
	}
}
