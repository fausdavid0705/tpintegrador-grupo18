package frsf.isi.died.app.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import frsf.isi.died.app.dao.MaterialCapacitacionDao;
import frsf.isi.died.app.dao.MaterialCapacitacionDaoDefault;
import frsf.isi.died.app.dao.util.CsvDatasource;
import frsf.isi.died.app.vista.material.BusquedaPanel;
import frsf.isi.died.app.vista.material.DocumentosPanel;
import frsf.isi.died.app.vista.material.VideoPanel;
import frsf.isi.died.tp.estructuras.Nodo;
import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;

public class DocumentosController {

	private DocumentosPanel panelDocumentos;
	private MaterialCapacitacionDao materialDAO;
	
	public DocumentosController(){
		this.panelDocumentos = new DocumentosPanel();
		this.panelDocumentos.setController(this);
		materialDAO = new MaterialCapacitacionDaoDefault();
		for(MaterialCapacitacion m : materialDAO.listaMateriales()) {
			ArbolController arbolControl = new ArbolController(m,true);
		}
		this.panelDocumentos.setListaMateriales(materialDAO.listaMateriales(),false);
		this.panelDocumentos.construir();
	}

	public MaterialCapacitacionDao getMaterialDAO() {
		return this.materialDAO;
	}

	public void arbolDeContenidos(int selectedRow) {
		MaterialCapacitacion m =  panelDocumentos.getTableModel().getMaterialAt(selectedRow);
		ArbolController arb = new ArbolController(m);
	}
}
