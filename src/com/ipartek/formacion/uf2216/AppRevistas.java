package com.ipartek.formacion.uf2216;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.Scanner;

/**
 * 
 * Aplicación de revistas
 * 
 * UF2216 - Examen de Java, JUnit... - 25-06-2019
 * CMS que se encarga de gestionar revistas
 * 
 * @version 1.0
 * @author Jose Luis del Hierro
 *
 */
public class AppRevistas {

	final static int OPCION_LISTAR = 1;
	final static int OPCION_CREAR = 2;
	final static int OPCION_GUARDAR_A_FICHERO = 3;
	final static int OPCION_SALIR = 0;

	static int opcionSeleccionada;
	static Scanner sc;
	static DAOArrayListRevistas dao;
	private static final String FILE_NAME = "revistas.dat";

	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException {

		sc = new Scanner(System.in);
		dao = DAOArrayListRevistas.getInstance();

		do {

			pintarMenuYpedirOpcion();

			switch (opcionSeleccionada) {
			case OPCION_LISTAR:
				listar();
				break;

			case OPCION_CREAR:
				crear();
				break;

			case OPCION_GUARDAR_A_FICHERO:
				guardarAFichero();
				break;

			default:
				break;
			}

		} while (opcionSeleccionada != OPCION_SALIR);

		System.out.println("Hasta la próxima!!!");

		sc.close();

	}

	private static void guardarAFichero() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
			oos.writeObject(dao.getAll());
			oos.flush();

			System.out.println("El archivo " + FILE_NAME + " se ha creado.");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void crear() {
		try {
			Revista magazine;
			String titulo;
			Long isbn;
			int paginas;
			boolean formatoDigital;
			char opcion = '-';
			char confirmacion = 'n';

			// tamaño mínimo 3 letras, máximo 150
			do {
				System.out.println("Introduzca el TÍTULO: (mínimo 3 caracteres, máximo 150)");
				titulo = sc.nextLine();
			} while (titulo.length() < 3 || titulo.length() > 150);

			do {
				System.out.println("Introduzca el ISBN: (número de longitud 10)");
				isbn = Long.parseLong(sc.nextLine());
			} while (isbn.toString().length() < 10 || isbn.toString().length() > 10);

			// Número de Páginas mínimo 1
			do {
				System.out.println("Introduzca número de páginas: (mínimo 1)");
				paginas = Integer.parseInt(sc.nextLine());
			} while (paginas < 1);

			// Formato ( digital o papel ) true == digital false == papel
			do {
				System.out.println("Introduzca el formato: digital (d) o papel (p)");

				opcion = sc.nextLine().charAt(0);
			} while (opcion != 'd' && opcion != 'p');

			if (opcion == 'd') {
				formatoDigital = true;
			} else {
				formatoDigital = false;
			}

			System.out.println("\nDesea crear la revista:");
			System.out.println("ISBN: " + isbn + " Título: " + titulo + " Páginas: " + paginas + " formato digital: "
					+ formatoDigital);
			System.out.println("Pulse 's' para confirmar (otro caracter lo descarta)");
			confirmacion = sc.nextLine().charAt(0);

			if (confirmacion == 's') {
				magazine = new Revista(titulo, isbn, paginas, formatoDigital);
				// persistir a traves del dao
				if (dao.insert(magazine)) {
					System.out.println("Revista creado satisfactoriamente");
				} else {
					System.out.println("Fallo al insertar la revista");
				}
			} else {

				System.out.println("Ha elegido no guardar la revista");
			}

			magazine = null;

		} catch (Exception e) {
			System.out.println("Introduce un valor numerico en id");
			crear();
		}

	}

	private static void listar() {

		// ordenar por numero de paginas
		Collections.sort(dao.getAll(), new ComparatorNumeroPaginas());

		String descripcionRevista = "";

		System.out.println("----------------------------------------------------------");
		System.out.println("------------------Listado de Revistas---------------------");
		System.out.println("----------------------------------------------------------");
		System.out.println(" ISBN\t\tTitulo\t\tNº Páginas\tFormato");

		for (Revista magazine : dao.getAll()) {
			descripcionRevista = " " + magazine.getIsbn() + "\t" + magazine.getTitulo() + "\t"
					+ magazine.getNumeroDePaginas() + " ";
			if (magazine.isFormatoDigital()) {
				descripcionRevista += "\t\tdigital";
			} else {
				descripcionRevista += "\t\tpapel";
			}

			System.out.println(descripcionRevista);
		}

	}

	private static void pintarMenuYpedirOpcion() {

		System.out.println("----------------------------------------------------------");
		System.out.println("  1 Listar (Ordenados por nº páginas)");
		System.out.println("  2 Crear");
		System.out.println("  3 Guardar a fichero");
		System.out.println("----------------------------------------------------------");
		System.out.println("  0 para salir");
		System.out.println("----------------------------------------------------------");
		System.out.println("");
		System.out.println("Dime tu opcion.....");

		try {

			opcionSeleccionada = Integer.parseInt(sc.nextLine());

		} catch (Exception e) {
			System.out.println("*** escriba un número, seleccione 1, 2, 3 o 0 **** ");

			pintarMenuYpedirOpcion();

		}
	}

}
