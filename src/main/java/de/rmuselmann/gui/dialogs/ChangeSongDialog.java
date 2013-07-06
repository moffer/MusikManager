package de.rmuselmann.gui.dialogs;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.sun.javafx.collections.ObservableListWrapper;

import de.rmuselmann.entity.ISong;
import de.rmuselmann.gui.extraClasses.GridPaneBox;
import de.rmuselmann.gui.extraClasses.TableClass;
import de.rmuselmann.gui.guiElements.MyStage;

public class ChangeSongDialog extends MyStage {
	private Stage stage = this;

	public ChangeSongDialog(Stage ownerStage, TableClass<ISong> tableView,
			ISong song) {
		setAll(ownerStage, 225, 140, false);
		init(tableView, song);
	}

	private void init(final TableClass<ISong> tableView, final ISong song) {
		ObservableList<String> data = null;
		final GridPaneBox fields;
		if (song != null) {
			this.setTitle("Song " + song.getID() + " �ndern");
			data = new ObservableListWrapper<>(song.convertToList());
			fields = new GridPaneBox(TableClass.getSongItems(), data);
		} else {
			this.setTitle("Song einf�gen");
			fields = new GridPaneBox(TableClass.getSongItems(), data);
		}

		fields.setPadding(new Insets(5, 5, 5, 5));
		fields.setAlignment(Pos.CENTER);

		final Button ok = new Button("Speichern");
		ok.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
//				ArrayList<String> data = fields.getData();
//				IInterpret interpret = GUIStart.getInterpretLogic()
//						.getInterpret(data.get(0).trim());
//				String title = data.get(1).trim();
//				IAlbum album = GUIStart.getAlbumLogic().getAlbum(
//						data.get(2).trim());
//				ITanzart tanzart = GUIStart.getTanzartLogic().getTanzart(
//						data.get(3).trim());
//
//				if (song != null) {
//					ISong changedSong = EntityFactory.getNewSong(song.getID(),
//							title, interpret, album, tanzart, song.getUser(),
//							song.isFileExisting(), new Date());
//					if (!changedSong.equals(song)) {
//						try {
//							changedSong = GUIStart.getLogic().changeSong(
//									changedSong);
//							if (changedSong != null) {
//								GUIStart.getSongs().set(
//										tableView.getSelectionModel()
//												.getSelectedIndex(),
//										changedSong);
//								ArrayList<ISong> songList = new ArrayList<>();
//								songList.add(changedSong);
//								GUIStart.getListListener()
//										.handleEvent(
//												new ListEvent(
//														GUIStart.class,
//														new ObservableListWrapper<ISong>(
//																songList),
//														ListChangeOption.EDITED));
//							}
//						} catch (IOException e) {
//							// Sollte es nicht funktionieren, wird ein Dialog
//							// ausgegeben und der Song wieder zur�ckgesetzt.
//							final CannotConnectDialogV2 can = (CannotConnectDialogV2) Loader
//									.load(new CannotConnectDialogV2()
//											.getFXMLPath());
//							can.setData(getPrimaryStage());
//							can.show();
//							can.setOnHiding(new EventHandler<WindowEvent>() {
//								@Override
//								public void handle(WindowEvent arg0) {
//									if (can.getOption().equals(
//											CannotConnectOption.AGAIN)) {
//										ok.fire();
//									} else if (can.getOption().equals(
//											CannotConnectOption.CANCEL)) {
//										try {
//											GUIStart.getLogic()
//													.changeSong(song);
//										} catch (IOException e) {
//										}
//									}
//								};
//							});
//						}
//					}
//				} else {
//					// neuer Song
//					ISong newSong = EntityFactory.getNewSong(-1, title,
//							interpret, album, tanzart, BeanFactory.getSettingsBeanFacade().getUser(), false,
//							new Date());
//					try {
//						newSong = GUIStart.getLogic().insertSong(newSong);
//					} catch (UniqueConstraintException e) {
//						new UniqueConstraintDialog(getPrimaryStage(), null);
//					}
//					if (newSong != null) {
//						GUIStart.getSongs().add(newSong);
//						ArrayList<ISong> songList = new ArrayList<>();
//						songList.add(newSong);
//						GUIStart.getListListener().handleEvent(
//								new ListEvent(GUIStart.class,
//										new ObservableListWrapper<ISong>(
//												songList),
//										ListChangeOption.ADDED));
//					}
//				}
//				stage.close();
			}
		});
		final Button cancel = new Button("Abbrechen");
		cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				stage.close();
			}
		});

		for (TextField textfield : fields.getTextFields()) {
			textfield.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					if (event.getCode().equals(KeyCode.ENTER)) {
						ok.fire();
					} else if (event.getCode().equals(KeyCode.ESCAPE)) {
						cancel.fire();
					}
				};
			});
		}

		HBox buttons = new HBox();
		buttons.getChildren().addAll(ok, cancel);
		buttons.setSpacing(5);
		buttons.setAlignment(Pos.CENTER);

		VBox box = new VBox();
		box.setSpacing(5);
		box.setPadding(new Insets(5, 5, 5, 5));
		box.getChildren().addAll(fields, buttons);

		getRoot().getChildren().add(box);
		getRoot().setAlignment(Pos.CENTER);
		this.show();
	}
}
