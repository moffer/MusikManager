package de.rmuselmann.gui.guiElements;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import de.rmuselmann.entity.IFields;
import de.rmuselmann.entity.ISong;
import de.rmuselmann.gui.dialogs.GUIStart;
import de.rmuselmann.gui.events.IListListener;
import de.rmuselmann.gui.events.ListEvent;
import de.rmuselmann.gui.events.ListEvent.ListChangeOption;
import de.rmuselmann.gui.extraClasses.TableClass;

public class SearchBox extends VBox {
	private class KeyEventHandler implements EventHandler<KeyEvent> {
		String oldVal;

		public KeyEventHandler() {

			IListListener a = new IListListener() {

				@Override
				public void handleEvent(ListEvent e) {
					if (searchBox.isVisible()) {
						if (e.isAdded().equals(ListChangeOption.ADDED)) {
							filteredList.addAll(e.getList());

						} else if (e.isAdded().equals(ListChangeOption.DELETED)) {
							filteredList.removeAll(e.getList());

						} else if (e.isAdded().equals(ListChangeOption.EDITED)) {
							for (ISong iSong : e.getList()) {
								int index = 0;
								for (ISong song : filteredList) {
									if (iSong.getID() == song.getID()) {
										filteredList.set(index, iSong);
									}
									index++;
								}
							}

						}

						handleSearch(oldVal, searchField.getText());
					}
				}
			};
			GUIStart.getListListener().add(a);
		}

		@Override
		public void handle(KeyEvent event) {
			char ch = event.getCharacter().charAt(0);
			// BackSpace
			if (event.getCharacter().equals("\b")) {
				String text = searchField.getText();
				handleSearch(oldVal, text);
				oldVal = text;
			} else if ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z')
					|| (ch >= 'A' && ch <= 'Z')) {

				handleSearch(oldVal, searchField.getText() + ch);
				oldVal = searchField.getText() + ch;
				// Return
			} else if (event.getCharacter().equals("\r")) {
				handleSearch(oldVal, searchField.getText());
			}
		}

		public void handleSearch(String oldVal, String newVal) {
			ObservableList<ISong> tmpList = FXCollections.observableArrayList();

			if (newVal != null && newVal.equals("")) {
				tableView.setItems(GUIStart.getSongs());
			} else {
				// If the number of characters in the text box is less than last
				// time
				// it must be because the user pressed delete
				if (oldVal != null && (newVal.length() < oldVal.length())) {
					// Restore the lists original set of entries
					// and start from the beginning
					tableView.setItems(GUIStart.getSongs());
				}
				// Break out all of the parts of the search text
				// by splitting on white space
				String[] parts = newVal.toLowerCase().split(" ");
				// ObservableList<ISong> list = tableView.getItems();
				// for (int i = 0; i < list.size(); i++) {
				// ISong song = list.get(i);
				for (ISong song : tableView.getItems()) {
					int counter = 0;
					for (String text : parts) {
						String albumName = "";
						String interpretName = "";
						String tanzartName = "";
						if (!song.getAlbum().equals(
								IFields.NullObjects.ALBUM.getObject())) {
							albumName = song.getAlbum().getAlbumName()
									.toLowerCase();
						}

						if (!song.getTanzart().equals(
								IFields.NullObjects.TANZART.getObject())) {
							tanzartName = song.getTanzart().getTanzartName()
									.toLowerCase();
						}
						if (!song.getInterpret().equals(
								IFields.NullObjects.INTERPRET.getObject())) {
							interpretName = song.getInterpret()
									.getInterpretName().toLowerCase();
						}

						if (albumName.contains(text)
								|| interpretName.contains(text)
								|| tanzartName.contains(text)
								|| song.getTitle().toLowerCase().contains(text)) {
							counter++;
						}
					}
					if (counter >= parts.length) {
						tmpList.add(song);
					}
				}
				filteredList = tmpList;
				tableView.setItems(filteredList);
			}
		}
	}

	SearchBox searchBox = this;

	private ObservableList<ISong> filteredList = FXCollections
			.observableArrayList();

	private TextField searchField;

	private TableClass<ISong> tableView;

	public SearchBox createSearchBox(final TableClass<ISong> tableView) {
		this.tableView = tableView;
		this.setPadding(new Insets(5));
		this.setSpacing(5);
		this.setVisible(true);
		this.setPrefHeight(40);

		Label text = new Label("Suche: ");
		searchField = new TextField();
		searchField.requestFocus();
		searchField.setOnKeyTyped(new KeyEventHandler());
		searchField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ESCAPE)) {
					tableView.setItems(GUIStart.getSongs());
					searchBox.setVisible(false);
					GUIStart.getScreenVBox().getChildren().remove(searchBox);
					GUIStart.setHeightPropertyOfTabPane();
				}
			};
		});
		HBox box = new HBox();
		box.setSpacing(5);
		box.getChildren().addAll(text, searchField);
		this.getChildren().addAll(box, new Separator());
		return this;
	}

	public TextField getSearchField() {
		return searchField;
	}

}
