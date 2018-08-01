package frsf.isi.died.tp.util;

import java.util.Comparator;

import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;
import frsf.isi.died.tp.modelo.productos.Relevancia;

public class PrioridadMaterial implements Comparator<MaterialCapacitacion>{

		@Override
		public int compare(MaterialCapacitacion m1, MaterialCapacitacion m2) {
			
			//RELEVANCIA
			if (Relevancia.intValue(m1.getRelevancia()) < Relevancia.intValue(m2.getRelevancia())) {
				//Si el primero es menor que el segundo, devolvemos menor que 0
				//Si son iguales devolves 0
				//Y sino mayor que 0
				return 1;
			}
			else { if (Relevancia.intValue(m1.getRelevancia()) > Relevancia.intValue(m2.getRelevancia())){	
				return -1;
				}
				else {
					if(m1.getCalificacion().intValue() > m2.getCalificacion().intValue()) {
						return -1;
					}
					else if (m1.getCalificacion().intValue() < m2.getCalificacion().intValue()){
						return 1;
					}
					else {
							return m1.getCosto().compareTo(m2.getCosto());
		}}}}
}
