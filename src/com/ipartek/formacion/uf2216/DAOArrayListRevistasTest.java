package com.ipartek.formacion.uf2216;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DAOArrayListRevistasTest {

	static DAOArrayListRevistas dao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		dao = DAOArrayListRevistas.getInstance();
	}

	@After
	public void tearDown() throws Exception {

		// borrar una a una las revistas
		for (int i = 0; i < dao.getAll().size(); i++) {

			assertTrue(dao.delete(dao.getAll().get(i).getIsbn()));
		}
		dao = null;
	}

	@Test
	public void testGetInstance() {

		assertNotNull(dao);

	}

	@Test
	public void testGetAll() {

		assertEquals(0, dao.getAll().size());

		dao.insert(new Revista("Galveston", 9788416237L, 388, true));
		dao.insert(new Revista("Hindenburg", 9788432238L, 298, false));

		assertEquals(2, dao.getAll().size());
	}

	@Test
	public void testGetByIsbn() {

		Revista magazine = new Revista("Hindenburg", 9788432238L, 298, false);
		dao.insert(magazine);
		assertEquals(magazine, dao.getByIsbn(9788432238L));

		assertNull(dao.getByIsbn(-1));

	}

	@Test
	public void testInsert() {

		Revista magazine = new Revista("Hindenburg", 9788432238L, 298, false);
		assertTrue(dao.insert(magazine));

		assertFalse(dao.insert(null));

	}

	@Test
	public void testExcepciones() {

		Revista magazine = new Revista("Hindenburg", 9788432238L, 298, false);
		assertTrue(dao.insert(magazine));
		try {

			// No deberia poder cambiar el título por ser solo 2 caracteres
			dao.getAll().get(0).setTitulo("as");
			assertEquals("Hindenburg", dao.getAll().get(0).getTitulo());

			// "Ahora SI se puede" es correcto
			dao.getAll().get(0).setTitulo("Ahora SI se puede");
			assertEquals("Ahora SI se puede", dao.getAll().get(0).getTitulo());
						
			// No deberia poder cambiar el numero de paginas a 0
			dao.getAll().get(0).setNumeroDePaginas(0);
			assertEquals(298, dao.getAll().get(0).getNumeroDePaginas());

			// Este número de páginas si funcionaría
			dao.getAll().get(0).setNumeroDePaginas(150);
			assertEquals(150, dao.getAll().get(0).getNumeroDePaginas());

			// No deberia poder cambiar el isbn a 12
			dao.getAll().get(0).setIsbn(12);
			assertEquals(9788432238L, dao.getAll().get(0).getIsbn());

			// Este isbn si se podría
			dao.getAll().get(0).setIsbn(2288432238L);
			assertEquals(2288432238L, dao.getAll().get(0).getIsbn());

		} catch (Exception e) {

			System.out.println(e.getMessage());
		}

	}

}
