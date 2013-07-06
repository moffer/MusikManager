package de.rmuselmann.gui.extraClasses;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class GridPaneBox extends GridPane {
	private ArrayList<TextField> dataList;

	public GridPaneBox(ArrayList<String> names, ObservableList<String> data) {
		dataList = new ArrayList<TextField>();
		init(names, data);
	}

	public ArrayList<String> getData() {
		ArrayList<String> arr = new ArrayList<String>();
		for (TextField string : dataList) {
			arr.add(string.getText());
		}
		return arr;
	}

	public ArrayList<TextField> getTextFields() {
		return dataList;
	}

	private void init(ArrayList<String> names, ObservableList<String> data) {
		for (int i = 0; i < names.size(); i++) {
			Label text = new Label(names.get(i) + ":   ");

			TextField textField;
			if (data != null && data.get(i) != null) {
				textField = new TextField(data.get(i));
			} else {
				textField = new TextField();
			}
			textField.setPromptText(names.get(i));
			dataList.add(textField);
			this.add(text, 0, i);
			this.add(textField, 1, i);
		}

	}
}
