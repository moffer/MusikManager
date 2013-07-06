package de.rmuselmann.database.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.rmuselmann.database.dao.IInterpretDAO;
import de.rmuselmann.entity.IFields.NullObjects;
import de.rmuselmann.entity.IInterpret;
import de.rmuselmann.entity.impl.EntityFactory;

public class InterpretDAO implements IInterpretDAO {
	private Connection con;

	protected InterpretDAO(Connection con) {
		this.con = con;
	}

	@Override
	public IInterpret addInterpret(IInterpret interpret) {
		long id = getNextID();
		String name = interpret.getInterpretName();

		try (PreparedStatement ps = con
				.prepareStatement("INSERT INTO Interpret VALUES(" + id + ",?)")) {

			ps.setString(1, name);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		interpret.setID(id);
		return interpret;

	}

	@Override
	public IInterpret getInterpret(String interpretName) {
		if (interpretName != null && !interpretName.equals("")) {
			ResultSet rs = null;
			try {
				PreparedStatement ps = con
						.prepareStatement("SELECT idInterpret FROM Interpret WHERE name = ?");
				ps.setString(1, interpretName);
				rs = ps.executeQuery();
				if (rs.next()) {
					return EntityFactory.getNewInterpret(rs.getLong(1),
							interpretName);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public IInterpret getInterpretByID(long interpretID) {
		if (interpretID > 0) {
			String interpretName = null;
			try (PreparedStatement ps = con
					.prepareStatement("SELECT name FROM Interpret WHERE idInterpret = ?");) {

				ps.setLong(1, interpretID);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					interpretName = rs.getString(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return EntityFactory.getNewInterpret(interpretID, interpretName);
		} else {
			return (IInterpret) NullObjects.INTERPRET.getObject();
		}
	}

	private long getNextID() {
		ResultSet rs = null;
		long id = -1;
		try {
			PreparedStatement ps = con
					.prepareStatement("SELECT Max(idInterpret) FROM Interpret");
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
	public List<IInterpret> readDatabase() {
		List<IInterpret> interprets = new ArrayList<IInterpret>();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("SELECT idInterpret, name FROM Interpret");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				long interpretID = rs.getLong(1);
				String name = rs.getString(2);

				IInterpret interpret = EntityFactory.getNewInterpret(
						interpretID, name);
				interprets.add(interpret);
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

		return interprets;
	}
}
