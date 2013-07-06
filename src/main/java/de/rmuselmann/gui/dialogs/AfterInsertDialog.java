package de.rmuselmann.gui.dialogs;

import java.io.File;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.sun.javafx.collections.ObservableListWrapper;

import de.rmuselmann.entity.ISong;
import de.rmuselmann.gui.guiElements.MyStage;

public class AfterInsertDialog extends MyStage {
	private ArrayList<ISong> sucessArrayList;
	private ArrayList<File> failedArrayList;
	private ArrayList<File> uniqueArrayList;

	public AfterInsertDialog(Stage primaryStage,
			ArrayList<ISong> successArrayList,
			ArrayList<File> listOfUniqueSongs, ArrayList<File> listOfFailedSongs) {
		this.sucessArrayList = successArrayList;
		this.uniqueArrayList = listOfUniqueSongs;
		this.failedArrayList = listOfFailedSongs;
		setAll(primaryStage, 500, 300, true);
		init();
	}

	private void init() {
		this.setTitle("Songs hochgeladen");

		TabPane tabPane = new TabPane();
		tabPane.prefWidthProperty().bind(this.widthProperty().subtract(15));
		tabPane.prefHeightProperty().bind(this.heightProperty().subtract(50));

		// Erfolgreiche Lieder
		ListView<ISong> sucessList = new ListView<>(
				new ObservableListWrapper<>(sucessArrayList));
		sucessList.prefHeightProperty().bind(
				tabPane.heightProperty().subtract(10));
		Tab successTab = new Tab("Erfolgreich");
		VBox sucessContent = new VBox();
		sucessContent.setSpacing(10);
		sucessContent.setPadding(new Insets(5));
		sucessContent.getChildren().addAll(
				new Label("Erfolgreich hochgeladen:"), sucessList);
		successTab.setContent(sucessContent);

		// Doppelte Files
		ListView<File> uniqueList = new ListView<>(new ObservableListWrapper<>(
				uniqueArrayList));
		uniqueList.prefHeightProperty().bind(
				tabPane.prefHeightProperty().subtract(10));
		Tab uniqueTab = new Tab("Doppelt");
		VBox uniqueContent = new VBox();
		uniqueContent.setSpacing(10);
		uniqueContent.setPadding(new Insets(5));
		uniqueContent.getChildren().addAll(
				new Label("Bereits vorhandene Eintr�ge:"), uniqueList);
		uniqueTab.setContent(uniqueContent);

		// Fehlerhafte Dateien
		ListView<File> failedList = new ListView<>(new ObservableListWrapper<>(
				failedArrayList));
		failedList.prefHeightProperty().bind(
				tabPane.heightProperty().subtract(10));
		Tab failedTab = new Tab("Fehlerhaft");
		VBox failedContent = new VBox();
		failedContent.setSpacing(10);
		failedContent.setPadding(new Insets(5));
		failedContent.getChildren().addAll(
				new Label("Diese Dateien sind fehlerhaft:"), failedList);
		failedTab.setContent(failedContent);

		tabPane.getTabs().addAll(successTab, uniqueTab, failedTab);
		this.getRoot().getChildren().add(tabPane);

		this.show();
		this.show();
	}
}
