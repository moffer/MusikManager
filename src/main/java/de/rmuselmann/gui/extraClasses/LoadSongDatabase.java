package de.rmuselmann.gui.extraClasses;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import de.rmuselmann.entity.ISong;
import de.rmuselmann.gui.dialogs.GUIStart;
import de.rmuselmann.gui.events.IProgressListener;
import de.rmuselmann.gui.events.ProgressEvent;
import de.rmuselmann.gui.fxml.dialogs2.WaitDialogV2;

public class LoadSongDatabase extends Task<Boolean> {

	private WaitDialogV2 waitDialog;
	private TableClass<ISong> tableView;
	private Stage mainStage;

	public LoadSongDatabase(WaitDialogV2 waitDialog2,
			TableClass<ISong> tableView, Stage mainStage) {
		this.waitDialog = waitDialog2;
		this.tableView = tableView;
		this.mainStage = mainStage;

		addListener();
	}

	private void addListener() {
		this.valueProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				if (newValue) {
					waitDialog.close();
					tableView.setItems(GUIStart.getSongs());
					mainStage.show();
				}
			}
		});
	}

	@Override
	protected Boolean call() {
		IProgressListener pr = new IProgressListener() {

			@Override
			public void handleEvent(ProgressEvent e) {
				waitDialog.setProgress(e.getPercent());
			}
		};
		// GUIStart.setSongs(new ObservableListWrapper<>(GUIStart.getLogic()
		// .readSongDatabase(pr)));
		return true;
	}
}