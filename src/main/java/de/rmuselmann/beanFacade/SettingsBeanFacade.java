package de.rmuselmann.beanFacade;

import java.io.IOException;

import de.rmuselmann.entity.IUser;
import de.rmuselmann.logic.IUserLogic;
import de.rmuselmann.logic.impl.LogicFac;
import de.rmuselmann.logic.impl.SettingsLogic;

public class SettingsBeanFacade {
	private SettingsLogic settingsLogic;
	private IUserLogic userLogic;

	private IUser user;

	protected SettingsBeanFacade() {
		try {
			userLogic = LogicFac.getNewUserLogic();
			settingsLogic = LogicFac.getNewSettingsLogic(userLogic);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadSettings() throws IOException {
		user = this.settingsLogic.loadSettings();
	}

	public IUser getUser() {
		return user;
	}

	public IUser setStandartSettings(IUser user2) {
		return this.settingsLogic.setStandartSettings(user2);
	}

}
