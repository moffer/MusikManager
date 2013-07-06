package de.rmuselmann.logic.impl;

import java.util.List;

import de.rmuselmann.database.dao.impl.ConnectionException;
import de.rmuselmann.database.dao.impl.ConnectionManager;
import de.rmuselmann.database.dao.impl.DAOFactory;
import de.rmuselmann.database.dao.impl.TableConfigurationDAO;
import de.rmuselmann.entity.impl.TableConfiguration;

public class TableConfigurationLogic {

	public List<TableConfiguration> getTableConfigurationOfTable(
			String tableName) throws ConnectionException {
		TableConfigurationDAO dao = DAOFactory
				.getNewTableConfigurationDAO(ConnectionManager
						.getDatabaseConnection());
		return dao.getTableConfigurationOfTable(tableName);
	}
}
