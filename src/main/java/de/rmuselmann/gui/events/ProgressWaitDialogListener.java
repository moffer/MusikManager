package de.rmuselmann.gui.events;

import de.rmuselmann.gui.fxml.dialogs2.WaitDialogV2;

public class ProgressWaitDialogListener implements IProgressListener {
	private WaitDialogV2 waitDialog;

	public ProgressWaitDialogListener(WaitDialogV2 waitDialog) {
		this.waitDialog = waitDialog;
	}

	@Override
	public void handleEvent(ProgressEvent e) {
		waitDialog.setProgress(e.getPercent());
	}
}
