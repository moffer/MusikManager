package de.rmuselmann.beanFacade;

import de.rmuselmann.database.dao.impl.ConnectionException;
import de.rmuselmann.entity.IUser;
import de.rmuselmann.logic.IUserLogic;
import de.rmuselmann.logic.impl.LogicFac;

public class UserBeanFacade {
	private IUserLogic userLogic;

	protected UserBeanFacade() throws ConnectionException {
		userLogic = LogicFac.getNewUserLogic();
	}

	public IUser getUser(String userName) {
		return userLogic.getUser(userName);
	}
}
