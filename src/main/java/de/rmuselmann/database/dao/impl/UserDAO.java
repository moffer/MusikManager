package de.rmuselmann.database.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import de.rmuselmann.database.dao.IUserDAO;
import de.rmuselmann.entity.IFields.NullObjects;
import de.rmuselmann.entity.IUser;
import de.rmuselmann.entity.impl.EntityFactory;

public class UserDAO implements IUserDAO {

	private Connection con;

	protected UserDAO(Connection con) {
		this.con = con;
	}

	@Override
	public IUser addUser(IUser user) {
		long id = getNextID();
		String name = user.getUserName();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("INSERT INTO User VALUES(" + id + ",?)");
			ps.setString(1, name);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				if (ps != null) {
					ps.close();
					user.setID(id);
					return user;
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
					.prepareStatement("SELECT Max(idUser) FROM User");
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
	public IUser getUser(String userName) {
		if (userName != null) {
			ResultSet rs = null;
			try {
				PreparedStatement ps = con
						.prepareStatement("SELECT idUser FROM User WHERE name = ?");
				ps.setString(1, userName);
				rs = ps.executeQuery();
				if (rs.next()) {
					return EntityFactory.getNewUser(rs.getLong(1), userName);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public IUser getUserByID(long userID) {
		if (userID > 0) {
			String userName = null;
			try (PreparedStatement ps = con
					.prepareStatement("SELECT name FROM User WHERE idUser = ?");) {

				ps.setLong(1, userID);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					userName = rs.getString(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return EntityFactory.getNewUser(userID, userName);
		} else {
			return (IUser) NullObjects.USER.getObject();
		}
	}

	@Override
	public ArrayList<IUser> readDatabase() {
		ArrayList<IUser> users = new ArrayList<IUser>();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("SELECT idUser, name FROM User");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				long userID = rs.getLong(1);
				String name = rs.getString(2);

				IUser user = EntityFactory.getNewUser(userID, name);
				users.add(user);
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

		return users;
	}
}
