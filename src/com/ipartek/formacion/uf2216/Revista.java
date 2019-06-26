package com.ipartek.formacion.uf2216;

import java.io.Serializable;

/**
 * 
 * Clase Revista Se utiliza para crear un CMS que se encarga de gestionarlas
 * 
 * @author Jose Luis del Hierro
 *
 */

public class Revista implements Leible, Serializable {
	/**
	 * variables
	 */
	private static final long serialVersionUID = 1L;
	private String titulo; // tamaño mínimo 3 letras, máximo 150
	private long isbn; // número de longitud 10
	private int numeroDePaginas; // mínimo 1
	private boolean formato; // ( digital o papel ) true == digital - false == papel

	// Constructor
	public Revista(String titulo, long isbn, int numeroDePaginas, boolean formato) {
		super();
		this.titulo = titulo;
		this.isbn = isbn;
		this.numeroDePaginas = numeroDePaginas;
		this.formato = formato;
	}

	// Getters and Setters

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		try {
			if (titulo.length() < 3 || titulo.length() > 150) {
				throw new Exception("Se ha intentado introducir título < 3 o > 150");
			} else {
				this.titulo = titulo;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public long getIsbn() {
		return isbn;
	}

	public void setIsbn(long isbn) {
		try {
			int tamanyo = Long.toString(isbn).length();
			if (tamanyo < 10 || tamanyo > 10) {
				throw new Exception("Se ha intentado introducir un ISBN menor de 10 o mayor de 10");
			} else {
				this.isbn = isbn;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public int getNumeroDePaginas() {
		return numeroDePaginas;
	}

	public void setNumeroDePaginas(int numeroDePaginas) {
		try {
			if (numeroDePaginas > 0) {
				this.numeroDePaginas = numeroDePaginas;
			} else {
				throw new Exception("El número de páginas tiene que ser mínimo 1");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public boolean isFormatoDigital() {
		return formato;
	}

	public void setFormatoDigital(boolean formato) {
		this.formato = formato;
	}

	@Override
	public String toString() {
		return "Revista [titulo=" + titulo + ", isbn=" + isbn + ", numeroDePaginas=" + numeroDePaginas + ", formato="
				+ formato + "]";
	}

}
