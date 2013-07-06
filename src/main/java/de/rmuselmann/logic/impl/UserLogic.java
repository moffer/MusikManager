package de.rmuselmann.logic.impl;

import java.util.List;

import de.rmuselmann.database.dao.IUserDAO;
import de.rmuselmann.database.dao.impl.ConnectionException;
import de.rmuselmann.database.dao.impl.ConnectionManager;
import de.rmuselmann.database.dao.impl.DAOFactory;
import de.rmuselmann.entity.IFields;
import de.rmuselmann.entity.IUser;
import de.rmuselmann.entity.impl.EntityFactory;
import de.rmuselmann.logic.IUserLogic;

public class UserLogic implements IUserLogic {
	private IUserDAO dao;

	protected UserLogic() throws ConnectionException {
		dao = DAOFactory.getNewUserDAO(ConnectionManager
				.getDatabaseConnection());
	}

	@Override
	public IUser addUser(IUser user) {
		if (user.getUserName().trim().equals("")) {
			return (IUser) IFields.NullObjects.USER.getObject();
		}
		return dao.addUser(user);
	}

	@Override
	public List<IUser> getAllUser() {
		return dao.readDatabase();
	}

	@Override
	public IUser getUser(String userName) {
		if (!userName.trim().equals("")) {
			IUser user = dao.getUser(userName);
			if (user != null) {
				return user;
			} else {
				return addUser(EntityFactory.getNewUser(-1, userName));
			}
		} else {
			return (IUser) IFields.NullObjects.USER.getObject();
		}
	}

	@Override
	public IUser getUserByID(long id) {
		return dao.getUserByID(id);
	}
}
