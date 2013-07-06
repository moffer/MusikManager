package de.rmuselmann.logic.impl;

import java.util.List;

import de.rmuselmann.database.dao.IInterpretDAO;
import de.rmuselmann.database.dao.impl.ConnectionException;
import de.rmuselmann.database.dao.impl.ConnectionManager;
import de.rmuselmann.database.dao.impl.DAOFactory;
import de.rmuselmann.entity.IFields;
import de.rmuselmann.entity.IInterpret;
import de.rmuselmann.entity.impl.EntityFactory;
import de.rmuselmann.logic.IInterpretLogic;

public class InterpretLogic implements IInterpretLogic {
	private IInterpretDAO dao;

	protected InterpretLogic() throws ConnectionException {
		dao = DAOFactory.getNewInterpretDAO(ConnectionManager
				.getDatabaseConnection());
	}

	@Override
	public IInterpret addInterpret(IInterpret interpret) {
		if (interpret.getInterpretName().trim().equals("")) {
			return (IInterpret) IFields.NullObjects.INTERPRET.getObject();
		}
		return dao.addInterpret(interpret);
	}

	@Override
	public List<IInterpret> getAllInterpret() {
		return dao.readDatabase();
	}

	@Override
	public IInterpret getInterpret(String interpretName) {
		if (!interpretName.trim().equals("")) {
			IInterpret interpret = dao.getInterpret(interpretName);
			if (interpret != null) {
				return interpret;
			} else {
				return addInterpret(EntityFactory.getNewInterpret(-1,
						interpretName));
			}
		} else {
			return (IInterpret) IFields.NullObjects.INTERPRET.getObject();
		}
	}

	@Override
	public IInterpret getInterpretByID(long id) {
		return dao.getInterpretByID(id);
	}
}
