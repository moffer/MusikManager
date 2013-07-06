package de.rmuselmann.database.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.rmuselmann.entity.impl.TableConfiguration;

public class TableConfigurationDAO {
	private Connection con;

	protected TableConfigurationDAO(Connection con) {
		this.con = con;
	}

	public List<TableConfiguration> getTableConfigurationOfTable(
			String tableName) {

		List<TableConfiguration> tcs = new ArrayList<>();

		try (PreparedStatement ps = con
				.prepareStatement("SELECT id, columnName, reflectionName FROM tableconfiguration WHERE tableconfiguration.table = ?");) {
			ps.setString(1, tableName);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				long id = rs.getLong(1);
				String columnName = rs.getString(2);
				String reflectionName = rs.getString(3);
				TableConfiguration t = new TableConfiguration(id, columnName,
						reflectionName);
				tcs.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tcs;
	}
}
