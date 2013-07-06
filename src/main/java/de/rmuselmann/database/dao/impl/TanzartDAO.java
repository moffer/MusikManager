package de.rmuselmann.database.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import de.rmuselmann.database.dao.ITanzartDAO;
import de.rmuselmann.entity.IFields.NullObjects;
import de.rmuselmann.entity.ITanzart;
import de.rmuselmann.entity.impl.EntityFactory;

public class TanzartDAO implements ITanzartDAO {

	private Connection con;

	protected TanzartDAO(Connection con) {
		this.con = con;
	}

	@Override
	public ITanzart addTanzart(ITanzart tanzart) {
		long id = getNextID();
		String name = tanzart.getTanzartName();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("INSERT INTO Tanzart VALUES(" + id
					+ ",?)");
			ps.setString(1, name);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				if (ps != null) {
					ps.close();
					tanzart.setID(id);
					return tanzart;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private long getNextID() {
		ResultSet rs = null;
		long id = -1;
		try {
			PreparedStatement ps = con
					.prepareStatement("SELECT Max(idTanzart) FROM Tanzart");
			rs = ps.executeQuery();
			rs.next();
			id = rs.getLong(1) + 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return id;
	}

	@Override
	public ITanzart getTanzart(String tanzartName) {
		if (tanzartName != null) {
			ResultSet rs = null;
			try {
				PreparedStatement ps = con
						.prepareStatement("SELECT idTanzart FROM Tanzart WHERE name = ?");
				ps.setString(1, tanzartName);
				rs = ps.executeQuery();
				if (rs.next()) {
					return EntityFactory.getNewTanzart(rs.getLong(1),
							tanzartName);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public ITanzart getTanzartByID(long tanzartID) {
		if (tanzartID > 0) {
			String tanzartName = null;
			try (PreparedStatement ps = con
					.prepareStatement("SELECT name FROM Tanzart WHERE idTanzart = ?");) {

				ps.setLong(1, tanzartID);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					tanzartName = rs.getString(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return EntityFactory.getNewTanzart(tanzartID, tanzartName);
		} else {
			return (ITanzart) NullObjects.TANZART.getObject();
		}
	}

	@Override
	public ArrayList<ITanzart> readDatabase() {
		ArrayList<ITanzart> tanzarts = new ArrayList<ITanzart>();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("SELECT idTanzart, name FROM Tanzart");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				long tanzartID = rs.getLong(1);
				String name = rs.getString(2);

				ITanzart tanzart = EntityFactory.getNewTanzart(tanzartID, name);
				tanzarts.add(tanzart);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return tanzarts;
	}
}
