package de.rmuselmann.beanFacade;

import de.rmuselmann.database.dao.impl.ConnectionException;

public class BeanFactory {

	private static SongBeanFacade songBeanFacade = null;
	private static SettingsBeanFacade settingsBeanFacade = null;
	private static UserBeanFacade userBeanFacade = null;

	private BeanFactory() {
	}

	public static void initialize() throws ConnectionException {
		songBeanFacade = new SongBeanFacade();
		settingsBeanFacade = new SettingsBeanFacade();
		userBeanFacade = new UserBeanFacade();
	}

	public static SongBeanFacade getSongBeanFacade() {
		return songBeanFacade;
	}

	public static SettingsBeanFacade getSettingsBeanFacade() {
		return settingsBeanFacade;
	}

	public static UserBeanFacade getUserBeanFacade() {
		return userBeanFacade;
	}
}
