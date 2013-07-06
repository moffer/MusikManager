package de.rmuselmann.gui;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import de.rmuselmann.beanFacade.BeanFactory;
import de.rmuselmann.database.dao.impl.ConnectionManager;

public class ConnectToDatabaseTask extends Service<Boolean> {

	protected static final Logger LOGGER = Logger
			.getLogger(ConnectToDatabaseTask.class.getName());

	@Override
	protected Task<Boolean> createTask() {
		return new Task<Boolean>() {

			@Override
			protected Boolean call() throws Exception {
				LOGGER.log(Level.INFO,
						"Versuche Verbindung zur Datenbank aufzubauen.");
				ConnectionManager.getDatabaseConnection();
				LOGGER.log(Level.INFO, "Verbindung zur Datenbank aufgebaut.");
				LOGGER.log(Level.INFO, "Versuche Logikklassen zu erstellen.");
				BeanFactory.initialize();
				// settings = LogicFac.getNewSettingsLogic(userLogic);
				LOGGER.log(Level.INFO, "Logikklassen erstellt.");
				return true;
			}
		};
	}

	@Override
	protected void succeeded() {
		super.succeeded();
	}

	@Override
	protected void failed() {
		super.failed();
	}
}
