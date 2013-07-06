package de.rmuselmann.logic.impl;

import java.util.List;

import de.rmuselmann.database.dao.ITanzartDAO;
import de.rmuselmann.database.dao.impl.ConnectionException;
import de.rmuselmann.database.dao.impl.ConnectionManager;
import de.rmuselmann.database.dao.impl.DAOFactory;
import de.rmuselmann.entity.IFields;
import de.rmuselmann.entity.ITanzart;
import de.rmuselmann.entity.impl.EntityFactory;
import de.rmuselmann.logic.ITanzartLogic;

public class TanzartLogic implements ITanzartLogic {
	private ITanzartDAO dao;

	protected TanzartLogic() throws ConnectionException {
		dao = DAOFactory.getNewTanzartDAO(ConnectionManager
				.getDatabaseConnection());
	}

	@Override
	public ITanzart addTanzart(ITanzart tanzart) {
		if (tanzart.getTanzartName().trim().equals("")) {
			return (ITanzart) IFields.NullObjects.TANZART.getObject();
		}
		return dao.addTanzart(tanzart);
	}

	@Override
	public List<ITanzart> getAllTanzart() {
		return dao.readDatabase();
	}

	@Override
	public ITanzart getTanzart(String tanzartName) {
		if (!tanzartName.trim().equals("")) {
			ITanzart tanzart = dao.getTanzart(tanzartName);
			if (tanzart != null) {
				return tanzart;
			} else {
				return addTanzart(EntityFactory.getNewTanzart(-1, tanzartName));
			}
		} else {
			return (ITanzart) IFields.NullObjects.TANZART.getObject();
		}
	}

	@Override
	public ITanzart getTanzartByID(long id) {
		return dao.getTanzartByID(id);
	}
}
