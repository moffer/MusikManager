package de.rmuselmann.gui.guiElements;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import de.rmuselmann.beanFacade.BeanFactory;
import de.rmuselmann.entity.IFields;
import de.rmuselmann.entity.ISong;
import de.rmuselmann.gui.dialogs.GUIStart;
import de.rmuselmann.gui.events.IListListener;
import de.rmuselmann.gui.events.ListEvent;
import de.rmuselmann.gui.events.ListEvent.ListChangeOption;
import de.rmuselmann.gui.fxml.dialogs2.MainStage;

public class SearchKeyHandler implements EventHandler<KeyEvent> {
	private String oldVal = "";
	private List<ISong> filteredList = new ArrayList<>();
	private MainStage mainStage;
	private VBox searchBox;
	private TextField textField;

	public SearchKeyHandler(final VBox searchBox, MainStage mainStage, final TextField searchField) {
		this.mainStage = mainStage;
		this.searchBox = searchBox;
		this.textField = searchField;

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

					doSearch(oldVal, oldVal);
				}
			}
		};
		GUIStart.getListListener().add(a);
	}

	final KeyCombination ESCAPE = new KeyCodeCombination(KeyCode.ESCAPE);

	public void handle(KeyEvent event) {

		if (ESCAPE.match(event)) {
			searchBox.setVisible(false);
			searchBox.setPrefHeight(0);
			mainStage.updateTable();
		} else {
			oldVal = doSearch(textField.getText(), oldVal);
		}
	}

	private String doSearch(String newVal, String oldVal) {
		ObservableList<ISong> tmpList = FXCollections.observableArrayList();

		if (newVal != null && newVal.equals("") && !newVal.equals(oldVal)) {
			filteredList = null;
			mainStage.updateTable();
		} else {
			// If the number of characters in the text box is less than
			// last
			// time
			// it must be because the user pressed delete

			// Restore the lists original set of entries
			// and start from the beginning
			if ((filteredList == null || filteredList.isEmpty()) || (oldVal != null && (newVal.length() < oldVal.length()))) {
				filteredList = BeanFactory.getSongBeanFacade().getSongs(null);
			}

			// Break out all of the parts of the search text
			// by splitting on white space
			String[] parts = newVal.toLowerCase().split(" ");
			// ObservableList<ISong> list = tableView.getItems();
			// for (int i = 0; i < list.size(); i++) {
			// ISong song = list.get(i);
			for (ISong song : filteredList) {
				int counter = 0;
				for (String text : parts) {
					String albumName = "";
					String interpretName = "";
					String tanzartName = "";
					if (!song.getAlbum().equals(IFields.NullObjects.ALBUM.getObject())) {
						albumName = song.getAlbum().getAlbumName().toLowerCase();
					}

					if (!song.getTanzart().equals(IFields.NullObjects.TANZART.getObject())) {
						tanzartName = song.getTanzart().getTanzartName().toLowerCase();
					}
					if (!song.getInterpret().equals(IFields.NullObjects.INTERPRET.getObject())) {
						interpretName = song.getInterpret().getInterpretName().toLowerCase();
					}

					if (albumName.contains(text) || interpretName.contains(text) || tanzartName.contains(text) || song.getTitle().toLowerCase().contains(text)) {
						counter++;
					}
				}
				if (counter >= parts.length) {
					tmpList.add(song);
				}
			}
			filteredList = tmpList;
			mainStage.getTableViewAvailable().getItems().setAll(filteredList);
		}
		return newVal;
	};

}
