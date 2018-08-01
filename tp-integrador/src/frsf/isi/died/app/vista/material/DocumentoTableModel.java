package frsf.isi.died.app.vista.material;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import frsf.isi.died.tp.estructuras.Nodo;
import frsf.isi.died.tp.estructuras.TipoNodo;
import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;

public class DocumentoTableModel extends AbstractTableModel {

	private List<MaterialCapacitacion> materiales;
	private String[] columnas = {"ID","Titulo","Metadato","Editorial"};
	
	
	@Override
	public String getColumnName(int indice) {
		return this.columnas[indice];
	}
	
	public List<MaterialCapacitacion> getMateriales() {
		return materiales;
	}

	public void setMateriales(List<MaterialCapacitacion> materiales) {
		this.materiales = materiales;
	}

	@Override
	public int getRowCount() {
		return materiales.size();
	}

	@Override
	public int getColumnCount() {
		return columnas.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object valor = null;
		switch (columnIndex) {
		case 0:
			valor = this.materiales.get(rowIndex).getId();
			break;
		case 1:
			valor = this.materiales.get(rowIndex).getContenido().getValor();
			break;
		case 2:
			valor = this.buscaValor(this.materiales.get(rowIndex).getContenido(), TipoNodo.METADATO);
			break;
		case 3:
			valor = this.buscaValor(this.materiales.get(rowIndex).getContenido(), TipoNodo.EDITORIAL);
			break;
		default:
			System.out.println("Indice fuera de rango");
			valor = "S/D";
			break;
		}
		return valor;
	}
	
	public String buscaValor(Nodo n, TipoNodo tipo) {
		String valor = null;
		for(Nodo nodo1 :  n.getHijos()) {
			if(nodo1.getTipoNodo().equals(tipo)) {
				valor = nodo1.getValor();
			}
			else {
				for (Nodo nodo2 : nodo1.getHijos()) {
					if(nodo2.getTipoNodo().equals(tipo)) {
						valor = nodo2.getValor();
					}
				}
			}
		}
		return valor;
	}
	
	public MaterialCapacitacion getMaterialAt(int index) {
		return this.materiales.get(index);
	}

}
