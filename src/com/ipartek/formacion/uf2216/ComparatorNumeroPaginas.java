package com.ipartek.formacion.uf2216;

import java.util.Comparator;


public class ComparatorNumeroPaginas implements Comparator<Revista> {


	@Override
	public int compare(Revista r1, Revista r2) {	
		return r1.getNumeroDePaginas() - r2.getNumeroDePaginas();
	}

}