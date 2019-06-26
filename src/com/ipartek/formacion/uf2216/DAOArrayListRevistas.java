package com.ipartek.formacion.uf2216;

import java.util.ArrayList;
import java.util.List;

public class DAOArrayListRevistas implements IPersistible<Revista> {

	private static DAOArrayListRevistas INSTANCE;
	private ArrayList<Revista> lista;

	/**
	 * Encargado de devolver solo 1 objeto, patron singleton
	 * 
	 * @return INSTANCE
	 */
	public static synchronized DAOArrayListRevistas getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DAOArrayListRevistas();
		}
		return INSTANCE;
	}

	private DAOArrayListRevistas() {

		super();

		lista = new ArrayList<Revista>();
		// Ejemplos de prueba, COMENTARLOS si se va a usar DAOArrayListRevistasTest
		// lista.add(new Revista("Galveston", 9788416237L, 388, true));
		// lista.add(new Revista("Hindenburg", 9788432238L, 298, false));

	}

	@Override
	public List<Revista> getAll() {
		return lista;
	}

	@Override
	public Revista getByIsbn(long id) {
		Revista resul = null;
		for (Revista a : lista) {

			if (a.getIsbn() == id) {
				resul = a;
				break;
			}
		}
		return resul;
	}

	@Override
	public boolean insert(Revista pojo) {
		boolean result = false;

		if (pojo != null) {
			result = lista.add(pojo);
		}

		return result;
	}

	@Override
	public boolean delete(long id) {

		return lista.remove(this.getByIsbn(id));

	}

	@Override
	public boolean update(Revista pojo) {
		boolean encontrado = false;

		for (Revista magazine : lista) {
			if (magazine.getIsbn() == pojo.getIsbn()) {
				magazine = pojo;
				encontrado = true;
				break;
			}

		}

		return encontrado;
	}

}
